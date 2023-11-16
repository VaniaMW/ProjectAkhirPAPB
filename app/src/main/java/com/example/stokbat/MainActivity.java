package com.example.stokbat;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {

    private List<Obat> daftarObat;
    private Adapter adapter;
    private static final int ADD_EDIT_REQUEST_CODE = 1;
    private int editedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        daftarObat = new ArrayList<>();

        Obat obat1 = new Obat("Paracetamol", "Obat Bebas", "Obat pereda nyeri", 20);

        daftarObat.add(obat1);

        adapter = new Adapter(daftarObat);
        adapter.setOnItemClickListener(this); // Set the click listener

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onViewClick(int position) {
        startDetailActivity();
    }

    private void startDetailActivity() {
        Intent intent = new Intent(this, DetailActivity.class);
        // Pass any data needed to DetailActivity using intent.putExtra if necessary
        startActivity(intent);
    }
}
