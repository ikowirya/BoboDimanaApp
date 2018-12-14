package com.dimana.bobo.bobodimanaapp;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dimana.bobo.bobodimanaapp.Model.Kost;
import com.dimana.bobo.bobodimanaapp.Model.KostSchema;
import com.dimana.bobo.bobodimanaapp.Rest.ApiClient;
import com.dimana.bobo.bobodimanaapp.Rest.ApiInteface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsKostFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    GoogleMap mMap;
    ApiInteface apiInteface;
    android.app.AlertDialog dialog;

    private void MapsKostFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_kost_maps, container, false);
        // Try to obtain the map from the SupportMapFragment.
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        mapFragment.getMapAsync(this);
        ButterKnife.bind(this, rootView);

        apiInteface = ApiClient.getClient().create(ApiInteface.class);
        return rootView;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        if ((ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            mMap.setMyLocationEnabled(true);
        }
        dialog = new SpotsDialog(getActivity());
        dialog.show();
        maps();
    }

    private void maps(){

        Call<KostSchema> users = apiInteface.getKost();
        users.enqueue(new Callback<KostSchema>() {
            @Override
            public void onResponse(Call<KostSchema> call, Response<KostSchema> response) {
                mMap.clear();

                List<Kost> kostList = response.body().getData();
                for (int i = 0; i < kostList.size(); i++) {
                    if (kostList.size() > 0){

                        double lat = Double.parseDouble(kostList.get(i).getLatitude());
                        double lang = Double.parseDouble(kostList.get(i).getLongitude());
                        Double harga = Double.parseDouble(kostList.get(i).getHarga());
                        NumberFormat formatter = new DecimalFormat("#,###");
                        String hargaKost = "Rp " + formatter.format(harga);
                        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(
                                getMarkerBitmapFromView(hargaKost))).position
                                (new LatLng(lat,lang))).setTag(kostList.get(i));
                        LatLng sydney = new LatLng(Double.parseDouble(kostList.get(0).getLatitude()),
                                Double.parseDouble(kostList.get(0).getLongitude()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                Kost i = (Kost) marker.getTag();
                                String kode = String.valueOf(i.getId_kost());
                                Intent intent = new Intent(getActivity(), DetailKostActivity.class);
                                intent.putExtra("kode", kode);
                                getActivity().startActivity(intent);
                                return false;
                            }
                        });

                    }

                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<KostSchema> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());
                t.printStackTrace();
                maps();
            }

        });

    }

    private Bitmap getMarkerBitmapFromView(String txtDevice) {

        View customMarkerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        TextView markerTextView= (TextView) customMarkerView.findViewById(R.id.tvHarga);
        markerTextView.setText(txtDevice);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }
}
