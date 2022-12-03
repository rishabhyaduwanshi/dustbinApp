//package com.example.projectbin;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.fragment.app.Fragment;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationRequest;
//
//class InAuthFrag extends Fragment {
//    public static final int PERM_CODE = 10;
//    public static final int CAMERA_REQUEST_CODE = 202;
//    public static final int DEFALUT_UPDATE_INTERVAL = 30;
//    public static final int FASTEST_UPDATE_INTERVEL = 5;
//    int REQUEST_IMAGE_CAPTURE = 1;
//    Uri locationForPhotos;
//    private static final int TAKE_PICTURE = 1;
//    private Uri imageUri;
//    private Bitmap mImageBitmap;
//    private String mCurrentPhotoPath;
//    private ImageView mImageView;
//    // ConstraintLayout layout1_1=findViewById(R.id.id_map_img);
//    ConstraintLayout img_button;
//    private ImageView img;
//    String currentPhotoPath;
//    Uri photoURI;
//    FusedLocationProviderClient fusedLocationProviderClient;
//    LocationRequest locationRequest;
//    Double latitude;
//    Double longitude;
//    //    Switch aSwitch;
//    private String imgString;
//    public String getDataDir(Context context) throws Exception {
//        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.dataDir;
//    }
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        try {
//            locationForPhotos = Uri.parse(getDataDir(getActivity().getApplicationContext()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        img_button = (ConstraintLayout) findViewById(R.id.id_capture_image);
//        img = findViewById(R.id.capturedImage);
//        locationRequest = new LocationRequest();
//        locationRequest.setInterval(1000 * DEFALUT_UPDATE_INTERVAL);
//        locationRequest.setFastestInterval(1000 * FASTEST_UPDATE_INTERVEL);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        img_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                askPermissions();
//                Toast.makeText(MainScreen.this, "The camera app is asked", Toast.LENGTH_SHORT).show();
//            }
//        });
//        return inflater.inflate(R.layout.fragment_in_auth, container, false);
//    }
//
//}