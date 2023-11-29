package com.example.stokbat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    private EditText editTextNama;
    private EditText editTextId;
    private EditText editTextKategori;
    private EditText editTextStok;
    private EditText editTextDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        editTextNama = findViewById(R.id.editTextNama);
        editTextId = findViewById(R.id.editTextId);
        editTextKategori = findViewById(R.id.editTextKategori);
        editTextStok = findViewById(R.id.editTextStok);
        editTextDesc = findViewById(R.id.editTextDesc);

        // Retrieve the Mahasiswa object passed from MainActivity
        Obat obat = getIntent().getParcelableExtra("Obat");

        // Populate the EditText fields with the data of the selected Mahasiswa
        editTextNama.setText(obat.getNama());
        editTextKategori.setText(obat.getKategori());
        editTextStok.setText(String.valueOf(obat.getStok()));
        editTextDesc.setText(String.valueOf(obat.getDeskripsi()));

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

                // Return the updated Obat object to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Obat", updatedObat);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}