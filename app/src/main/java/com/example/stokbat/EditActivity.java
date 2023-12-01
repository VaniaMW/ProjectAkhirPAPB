package com.example.stokbat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class EditActivity extends AppCompatActivity {

    private EditText editTextNama;
    private TextView editTextId;
    private EditText editTextKategori;
    private EditText editTextStok;
    private EditText editTextDesc;
    private DatabaseReference obatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        editTextNama = findViewById(R.id.editTextNama);
        editTextId = findViewById(R.id.editTextId);
        editTextKategori = findViewById(R.id.editTextKategori);
        editTextStok = findViewById(R.id.editTextStok);
        editTextDesc = findViewById(R.id.editTextDesc);

        // Retrieve the Obat object passed from MainActivity
        Obat obat = getIntent().getParcelableExtra("Obat");

        // Populate the EditText fields with the data of the selected Obat
        editTextNama.setText(obat.getNama());
        editTextId.setText(obat.getProdukID());
        editTextKategori.setText(obat.getKategori());
        editTextStok.setText(String.valueOf(obat.getStok()));
        editTextDesc.setText(obat.getDeskripsi());

        // Menghubungkan ke Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        obatRef = database.getReference("obat");

        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated user input from EditText fields
                String updatedNama = editTextNama.getText().toString();
                String updatedID = editTextId.getText().toString();
                String updatedKategori = editTextKategori.getText().toString();
                int updatedStok = Integer.parseInt(editTextStok.getText().toString());
                String updatedDesc = editTextDesc.getText().toString();

                // Create an updated Obat object
                Obat updatedObat = new Obat(updatedNama, updatedID, updatedKategori, updatedDesc, updatedStok);

                // Save the updated Obat object to Firebase Database
                DatabaseReference obatRef = FirebaseDatabase.getInstance().getReference().child("obat");

                obatRef.child(obat.getProdukID()).setValue(updatedObat)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Notifikasi perubahan berhasil disimpan
                                Toast.makeText(EditActivity.this, "Perubahan Disimpan", Toast.LENGTH_SHORT).show();
                                // Kembali ke MainActivity atau halaman sebelumnya
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Penanganan jika gagal menyimpan perubahan
                                Toast.makeText(EditActivity.this, "Gagal Menyimpan Perubahan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });

    }
}