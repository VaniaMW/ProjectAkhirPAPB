package com.example.stokbat;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends FirebaseRecyclerAdapter<Obat, Adapter.ObatViewHolder> {

//    private AdapterView.OnItemClickListener listener;
    private OnItemClickListener listener; // Menggunakan interface OnItemClickListener yang benar

    public Adapter(@NonNull FirebaseRecyclerOptions<Obat> options) {
        super(options);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ObatViewHolder holder, int position, @NonNull Obat model) {
        holder.Nama.setText("Nama: " + model.getNama());
        holder.Kategori.setText("Kategori: " + model.getKategori());
        holder.Stok.setText("Stok: " + model.getStok());

        holder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onViewClick(adapterPosition);
                }
            }
        });
    }


    @NonNull
    @Override
    public ObatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.obat, parent, false);
        return new ObatViewHolder(itemView);
    }

    public class ObatViewHolder extends RecyclerView.ViewHolder {
        public TextView Nama;
        public TextView Kategori;
        public TextView Stok;
        public Button buttonView;

        public ObatViewHolder(View itemView) {
            super(itemView);
            Nama = itemView.findViewById(R.id.namaobat);
            Kategori = itemView.findViewById(R.id.jenisobat);
            Stok = itemView.findViewById(R.id.stokobat);
            buttonView = itemView.findViewById(R.id.buttonView);
        }
    }

    public interface OnItemClickListener {
        void onViewClick(int position);
    }
}
