//package com.example.projectbin;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentActivity;
//
//import android.os.Build;
//import android.os.Bundle;
//import android.view.WindowManager;
//import android.widget.Toast;
//
//import com.example.projectbin.model.LatLongPOJO;
//import com.example.projectbin.retrofit.DustbinAPI;
//import com.example.projectbin.retrofit.RetrofitService;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.example.projectbin.databinding.ActivityDustbinsMapsBinding;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//
//public class DustbinsMapsActivity extends AppCompatActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private ActivityDustbinsMapsBinding binding;
//    private Call<List<LatLongPOJO>> call;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
////        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//        }
//        binding = ActivityDustbinsMapsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        //uncomment it
//       // getLatLong(mMap);
//        // Add a marker in Sydney and move the camera
//        // comment it
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
//    public void getLatLong(GoogleMap mMap){
//        RetrofitService retrofitService=new RetrofitService();
//        DustbinAPI dustbinAPI=retrofitService.getRetrofit().create(DustbinAPI.class);
//        call= dustbinAPI.getLatLong();
//        call.enqueue(new Callback<List<LatLongPOJO>>() {
//            @Override
//            public void onResponse(Call<List<LatLongPOJO>> call, Response<List<LatLongPOJO>> response) {
//                List<LatLongPOJO> LatLongList = response.body();
//                for(LatLongPOJO l:LatLongList) {
//                    LatLng latLng = new LatLng(l.getLatitude(), l.getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(latLng).title("Dustbin Marker"));
//                }
//            }
//            @Override
//            public void onFailure(Call<List<LatLongPOJO>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Unable to set all the locations.", Toast.LENGTH_LONG).show();
//            }
//
//        });
//    }
//}