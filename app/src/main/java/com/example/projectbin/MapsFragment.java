package com.example.projectbin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectbin.databinding.ActivityDustbinsMapsBinding;
import com.example.projectbin.model.LatLongPOJO;
import com.example.projectbin.retrofit.DustbinAPI;
import com.example.projectbin.retrofit.RetrofitService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment {
    private GoogleMap mMap;
    private ActivityDustbinsMapsBinding binding;
    private Call<List<LatLongPOJO>> call;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // getLatLong(mMap);
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
    public void getLatLong(GoogleMap mMap){
        RetrofitService retrofitService=new RetrofitService();
        DustbinAPI dustbinAPI=retrofitService.getRetrofit().create(DustbinAPI.class);
        call= dustbinAPI.getLatLong();
        call.enqueue(new Callback<List<LatLongPOJO>>() {
            @Override
            public void onResponse(Call<List<LatLongPOJO>> call, Response<List<LatLongPOJO>> response) {
                List<LatLongPOJO> LatLongList = response.body();
                for(LatLongPOJO l:LatLongList) {
                    LatLng latLng = new LatLng(l.getLatitude(), l.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Dustbin Marker"));
                }
            }
            @Override
            public void onFailure(Call<List<LatLongPOJO>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Unable to set all the locations.", Toast.LENGTH_LONG).show();
                //getApplicationContext()
            }

        });
    }
}