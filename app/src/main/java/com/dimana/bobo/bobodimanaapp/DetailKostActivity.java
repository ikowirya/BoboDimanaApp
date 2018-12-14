package com.dimana.bobo.bobodimanaapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dimana.bobo.bobodimanaapp.Adapter.ListKostAdapter;
import com.dimana.bobo.bobodimanaapp.Model.Kost;
import com.dimana.bobo.bobodimanaapp.Model.KostSchema;
import com.dimana.bobo.bobodimanaapp.Rest.ApiClient;
import com.dimana.bobo.bobodimanaapp.Rest.ApiInteface;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class DetailKostActivity extends AppCompatActivity {
    @BindView(R.id.imgKost)
    ImageView imgKost;
    @BindView(R.id.tvHarga)
    TextView tvHarga;
    @BindView(R.id.tvNamaKost)
    TextView tvNamaKost;
    @BindView(R.id.tvAlamat)
    TextView tvAlamat;
    @BindView(R.id.tvJenisKost)
    TextView tvJenisKost;
    @BindView(R.id.tvSisaKamar)
    TextView tvSisaKamar;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvFasilitas)
    TextView tvFasilitas;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String kode;
    ApiInteface apiInteface;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kost);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_detail);
        Intent intent = getIntent();
        kode = intent.getStringExtra("kode");
        apiInteface = ApiClient.getClient().create(ApiInteface.class);


        getKost(kode);
    }


    private void getKost(String kode){
        dialog = new SpotsDialog(this);
        dialog.show();
        Call<KostSchema> users = apiInteface.getDetailKost(kode);
        users.enqueue(new Callback<KostSchema>() {
            @Override
            public void onResponse(Call<KostSchema> call, retrofit2.Response<KostSchema> response) {
                if (response.isSuccessful()) {

                    List<Kost> kostList = response.body().getData();
                    String img = kostList.get(0).getGambar();

                    int index;
                    index = img.indexOf("/");

                    String newURL = img.substring(index);
                    String url = "http://192.168.43.81/rest_server"+newURL;
                    Glide.with(DetailKostActivity.this).load(url)
                            .thumbnail(0.5f)
                            .into(imgKost);

                    tvNamaKost.setText(kostList.get(0).getJudul());
                    double harga = Double.parseDouble(kostList.get(0).getHarga());
                    NumberFormat formatter = new DecimalFormat("#,###");
                    tvHarga.setText("Rp " + formatter.format(harga) + "/bulan");
                    tvJenisKost.setText(kostList.get(0).getTipe_kost());
                    tvAlamat.setText(kostList.get(0).getAlamat());
                    tvSisaKamar.setText("Tersisa " + kostList.get(0).getJumlah_kamar() + " Kamar");
                    tvDesc.setText(kostList.get(0).getDeskripsi());
                    tvFasilitas.setText(kostList.get(0).getFasilitas());
                    String latitude = kostList.get(0).getLatitude();
                    String longitude = kostList.get(0).getLongitude();

                    //include load maps fragment
                    DetailMapsKostFragment fragment = new DetailMapsKostFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString( "latitude" ,latitude);
                    arguments.putString( "longitude" ,longitude);
                    fragment.setArguments(arguments);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.vMaps,fragment);
                    fragmentTransaction.commit();

                } else {
                    Toast.makeText(getApplicationContext(), "Try Again",
                            Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<KostSchema> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
