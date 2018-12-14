package com.dimana.bobo.bobodimanaapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dimana.bobo.bobodimanaapp.DetailKostActivity;
import com.dimana.bobo.bobodimanaapp.Model.Kost;
import com.dimana.bobo.bobodimanaapp.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListKostAdapter extends RecyclerView.Adapter<ListKostAdapter.ListKostViewholder> {
    List<Kost> kostList;

    public ListKostAdapter(List<Kost> kostList) {
        this.kostList = kostList;
    }

    @Override
    public ListKostViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kost, parent, false);
        return new ListKostAdapter.ListKostViewholder(view);
    }

    @Override
    public void onBindViewHolder(ListKostViewholder holder, int position) {
        final Context context = holder.itemView.getContext();

        //menampilkan
        holder.tvNamaKost.setText(kostList.get(position).getJudul());
        double harga = Double.parseDouble(kostList.get(position).getHarga());
        NumberFormat formatter = new DecimalFormat("#,###");
        holder.tvHarga.setText("Rp " + formatter.format(harga) + "/bulan");
        holder.tvJenisKost.setText(kostList.get(position).getTipe_kost());
        holder.tvAlamat.setText(kostList.get(position).getAlamat());
        holder.tvSisaKamar.setText("Tersisa " + kostList.get(position).getJumlah_kamar() + " Kamar");

        //ambil url
        String img = kostList.get(position).getGambar();

        int index;
        index = img.indexOf("/");

        String newURL = img.substring(index);
        String url = "http://192.168.43.81/rest_server"+newURL;
        Glide.with(context).load(url)
                .thumbnail(0.5f)
                .into(holder.imgKost);


        final String kode = kostList.get(position).getId_kost();

        //pindah activity
        holder.lineKost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailKostActivity.class);
                intent.putExtra("kode", kode);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kostList.size();
    }

    public class ListKostViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.lineKost)
        LinearLayout lineKost;
        @BindView(R.id.imgKost)
        ImageView imgKost;
        @BindView(R.id.tvNamaKost)
        TextView tvNamaKost;
        @BindView(R.id.tvHarga)
        TextView tvHarga;
        @BindView(R.id.tvJenisKost)
        TextView tvJenisKost;
        @BindView(R.id.tvAlamat)
        TextView tvAlamat;
        @BindView(R.id.tvSisaKamar)
        TextView tvSisaKamar;

        public ListKostViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
