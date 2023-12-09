package com.example.stokbat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView2;
    private String imageUrl;
    //...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        // Mendapatkan data obat yang dikirim dari MainActivity
        Obat obat = getIntent().getParcelableExtra("Obat");

        // Menampilkan data obat pada tampilan detail
        displayObatDetail(obat, null);
        imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(view -> selectImage());

        // Mengambil referensi ke tombol "OK"
        Button buttonOk = findViewById(R.id.button2);

        // Mengatur listener untuk tombol "OK"
        buttonOk.setOnClickListener(view -> {
            // Panggil method upload() untuk menyimpan perubahan gambar ke Firebase Storage
            upload();
        });
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 10);
            } else if (items[item].equals("Choose from Library")) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 20); // Perbaikan di sini
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == RESULT_OK && data != null) {
            final Uri path = data.getData();
            Thread thread = new Thread(() -> {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(path);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imageView2.post(() -> {
                        imageView2.setImageBitmap(bitmap);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }

    // ... (previous code remains unchanged)
    private void saveChangesToDatabase() {
        Obat obat = getIntent().getParcelableExtra("Obat");

        if (obat != null && imageUrl != null) {
            // Simpan imageUrl ke Firebase Database di sini (lihat kode yang telah diberikan sebelumnya)
            // Pastikan kode simpan ke Firebase Database disesuaikan dengan struktur database Anda
            FirebaseDatabase.getInstance()
                    .getReference().child("obat").child(obat.getProdukID())
                    .child("imageUrl")
                    .setValue(imageUrl)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Perubahan berhasil disimpan", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Gagal menyimpan perubahan", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }



    private void upload() {
        imageView2.setDrawingCacheEnabled(true);
        imageView2.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("images");


        //FirebaseStorage storage = FirebaseStorage.getInstance();
        // StorageReference reference = storage.getReference().child("images");
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                imageUrl = downloadUri.toString();
                                Toast.makeText(getApplicationContext(), "Gambar berhasil diunggah", Toast.LENGTH_SHORT).show();
                                // Tambahkan kode untuk kembali ke MainActivity setelah selesai
                                finish(); // Menutup DetailActivity
                            } else {
                                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void displayObatDetail(Obat obat, String imageUrl) {
        TextView namaObatTextView = findViewById(R.id.nama);
        TextView idObatTextView = findViewById(R.id.produkID);
        TextView kategoriObatTextView = findViewById(R.id.kategori);
        TextView deskripsiObatTextView = findViewById(R.id.desc);
        TextView stokObatTextView = findViewById(R.id.stok);
        //ImageView fotoObat = findViewById(R.id.imageView2);

        if (obat != null) {
            namaObatTextView.setText(obat.getNama());
            idObatTextView.setText("ID: " + obat.getProdukID());
            kategoriObatTextView.setText("Kategori: " + obat.getKategori());
            deskripsiObatTextView.setText("Deskripsi: " + obat.getDeskripsi());
            stokObatTextView.setText("Stok: " + obat.getStok());

            if (imageUrl != null) {
                Glide.with(this)
                        .load(imageUrl);
                //.into(fotoObat);
                upload();
            }
        }
    }
}
