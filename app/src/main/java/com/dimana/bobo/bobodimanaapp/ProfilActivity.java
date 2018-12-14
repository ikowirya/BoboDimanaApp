package com.dimana.bobo.bobodimanaapp;

import android.app.AlertDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class ProfilActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgProfil)
    CircleImageView imgProfil;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtUser)
    TextView txtUser;
    private FirebaseAuth mAuth;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ButterKnife.bind(this);
        dialog = new SpotsDialog(this);
        dialog.show();
        mAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_profile);
        Uri url = mAuth.getCurrentUser().getPhotoUrl();
        Glide.with(ProfilActivity.this).load(url)
                .thumbnail(0.5f)
                .into(imgProfil);
        txtEmail.setText(mAuth.getCurrentUser().getEmail());
        txtUser.setText(mAuth.getCurrentUser().getDisplayName());
        dialog.dismiss();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
