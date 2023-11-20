package com.example.stokbat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private EditText editTextNama;
    private EditText editTextId;
    private EditText editTextKategori;
    private EditText editTextStok;
    private EditText editTextDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        editTextNama = findViewById(R.id.editTextNama);
        editTextId = findViewById(R.id.editTextId);
        editTextKategori = findViewById(R.id.editTextKategori);
        editTextStok = findViewById(R.id.editTextStok);
        editTextDesc = findViewById(R.id.editTextDesc);

        Button addButton = findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user's input from EditText fields
                String nama = editTextNama.getText().toString();
                String ID = editTextId.getText().toString();
                String kategori = editTextKategori.getText().toString();
                int stok = Integer.parseInt(editTextStok.getText().toString());
                String desc = editTextDesc.getText().toString();

                // Create a new Mahasiswa object with user input
                Obat newObat = new Obat(nama, kategori, desc, stok);

                // Return the new Mahasiswa object to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Obat", newObat);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
