package com.example.stokbat;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MahasiswaViewHolder> {

    private List<Obat> daftarObat;

    public interface OnItemClickListener {
        void onViewClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public Adapter(List<Obat> daftarObat) {
        this.daftarObat = daftarObat;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.obat, parent, false);
        return new MahasiswaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Obat obat = daftarObat.get(position);

        holder.Nama.setText("Nama: " + obat.getNama());
        holder.Kategori.setText("Kategori: " + obat.getKategori());
        holder.Stok.setText("Stok: " + obat.getStok());

        holder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onViewClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return daftarObat.size();
    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        public TextView Nama;
        public TextView Kategori;
        public TextView Stok;
        public Button buttonView;

        public MahasiswaViewHolder(View itemView) {
            super(itemView);
            Nama = itemView.findViewById(R.id.namaobat);
            Kategori = itemView.findViewById(R.id.jenisobat);
            Stok = itemView.findViewById(R.id.stokobat);
            buttonView = itemView.findViewById(R.id.buttonView);
        }
    }
}

