package com.dimana.bobo.bobodimanaapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dimana.bobo.bobodimanaapp.Adapter.ListKostAdapter;
import com.dimana.bobo.bobodimanaapp.Model.Kost;
import com.dimana.bobo.bobodimanaapp.Model.KostSchema;
import com.dimana.bobo.bobodimanaapp.Rest.ApiClient;
import com.dimana.bobo.bobodimanaapp.Rest.ApiInteface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
public class KostFragment extends Fragment {

    @BindView(R.id.rvDaftarKost)
    RecyclerView rvDaftarKost;
    ApiInteface apiInteface;
    RecyclerView.LayoutManager mLayoutManager;
    ListKostAdapter listKostAdapter;
    android.app.AlertDialog dialog;
    public KostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_kost_list, container, false);
        ButterKnife.bind(this, root);

        apiInteface = ApiClient.getClient().create(ApiInteface.class);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvDaftarKost.setLayoutManager(mLayoutManager);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            getKost();
    }

    private void getKost(){
        dialog = new SpotsDialog(getActivity());
        dialog.show();
        Call<KostSchema> users = apiInteface.getKost();
        users.enqueue(new Callback<KostSchema>() {
            @Override
            public void onResponse(Call<KostSchema> call, retrofit2.Response<KostSchema> response) {
                if (response.isSuccessful()) {

                    List<Kost> notificationsList = response.body().getData();

                    listKostAdapter = new ListKostAdapter(notificationsList);
                    rvDaftarKost.setAdapter(listKostAdapter);


                } else {
                    Toast.makeText(getContext(), "Try Again",
                            Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<KostSchema> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());
                t.printStackTrace();
                Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
