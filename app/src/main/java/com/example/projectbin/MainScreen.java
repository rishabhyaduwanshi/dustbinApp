package com.example.projectbin;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;


//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.projectbin.model.Dustbin;
import com.example.projectbin.retrofit.DustbinAPI;
import com.example.projectbin.retrofit.RetrofitService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainScreen extends FragmentActivity {
    public static final int PERM_CODE = 10;
    public static final int CAMERA_REQUEST_CODE = 202;
    public static final int DEFALUT_UPDATE_INTERVAL = 30;
    public static final int FASTEST_UPDATE_INTERVEL = 5;
    int REQUEST_IMAGE_CAPTURE = 1;
    Uri locationForPhotos;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;
    // ConstraintLayout layout1_1=findViewById(R.id.id_map_img);
    ConstraintLayout img_button;
    private ImageView img;
    String currentPhotoPath;
    Uri photoURI;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    Double latitude;
    Double longitude;
    Switch aSwitch;
    private String imgString;
    public MainScreen() throws Exception {
    }

    //ImageView img_button;
    public String getDataDir(Context context) throws Exception {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.dataDir;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
//        getSupportActionBar().hide();
//        if (Build.VERSION.SDK_INT >= 21) {
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.white));
//        }
        try {
            locationForPhotos = Uri.parse(getDataDir(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        img_button = (ConstraintLayout) findViewById(R.id.id_capture_image);
        img = findViewById(R.id.capturedImage);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000 * DEFALUT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(1000 * FASTEST_UPDATE_INTERVEL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermissions();
                Toast.makeText(MainScreen.this, "The camera app is asked", Toast.LENGTH_SHORT).show();
            }
        });
        aSwitch=findViewById(R.id.switch1);
//        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.fragView);
        MapsFragment mapsFragment=new MapsFragment();

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                if(isChecked) {
                    fragmentTransaction.replace(R.id.fragView, mapsFragment);
                }
                else{
                    fragmentTransaction.remove(mapsFragment);
                }
                fragmentTransaction.commit();
            }
        });
    }

    private void GPS() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                storeLatLong(location);
            }
        });
    }
    private void askPermissions() {
        if(ContextCompat.checkSelfPermission(MainScreen.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(MainScreen.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(MainScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(MainScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
           // ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_PERM_CODE);
           ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},PERM_CODE);
        }
        else{
            Toast.makeText(MainScreen.this,"2.The open camera is called",Toast.LENGTH_SHORT).show();
            //Log.d("logd","2.The open camera is called");
             openCamera();
             GPS();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERM_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openCamera();
                GPS();
            }
            else{
                Toast.makeText(MainScreen.this,"The camera permission is required",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            img.setImageURI(photoURI);
//            Toast.makeText(this, "img obj set", Toast.LENGTH_SHORT).show();

//                    send();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , photoURI);
                imageToString(bitmap);
                send();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    public void storeLatLong(Location location){
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        Toast.makeText(this, "latitude: "+latitude+" longitude: "+longitude, Toast.LENGTH_LONG).show();
    }
    public void imageToString(Bitmap BitmapData) {
        Bitmap immagex=BitmapData;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] b = baos.toByteArray();
        imgString = Base64.encodeToString(b,Base64.NO_WRAP);
//        return imageEncoded;
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        BitmapData.compress(Bitmap.CompressFormat.PNG, 20, bos);
//        byte[] byte_arr = bos.toByteArray();
//        imgString = Base64.encodeToString(byte_arr, Base64.DEFAULT);
        //appendLog(file);
    }
    public void send(){
        RetrofitService retrofitService=new RetrofitService();
        DustbinAPI dustbinAPI=retrofitService.getRetrofit().create(DustbinAPI.class);
        runOnUiThread(new Runnable() {
    @Override
    public void run() {
//        if(latitude!=null&&longitude!=null) {
        if(latitude!=null&&longitude!=null&&imgString!=null) {
            Dustbin dustbin = new Dustbin(latitude, longitude, imgString);
//            Dustbin dustbin = new Dustbin(latitude, longitude);
            dustbinAPI.save(dustbin).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    Toast.makeText(MainScreen.this, "Save successful", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(MainScreen.this, "Save failed :( \n can you please do that again.", Toast.LENGTH_SHORT).show();
                    Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE,"Error modi");
                }
            });
        }
        else{
            Log.d("sendError","Error occured in initialization");
            throw new RuntimeException();
        }
        }
        });
    }
    private void loadFragment(MapsFragment mapsFragment) {
// create a FragmentManager

// create a FragmentTransaction to begin the transaction and replace the Fragment

// replace the FrameLayout with new Fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragView, mapsFragment);
        fragmentTransaction.commit(); // save the changes
    }
}