package com.example.cureeasy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class DoctorHome extends AppCompatActivity {
    RecyclerView homedoctor;
    private int camera_permission=101;
    ArrayList<DoctorHome.DoctorHomeDefault> slider_adapter;
    ArrayList<String> slide;
    SliderView sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_doctor);
        slide=new ArrayList<>();
        sliderView=findViewById(R.id.imageSlider);
        showSlider();
        homedoctor=findViewById(R.id.doctor_recycler);
        slider_adapter=declarepatient();
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        DoctorHomeAdapter homeadapter=new DoctorHomeAdapter(this,slider_adapter,DoctorHome.this);
        homedoctor.setLayoutManager(manager);
        homedoctor.setAdapter(homeadapter);
    }



    public void showSlider()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("slider").document("qBYDtXqixRwl94oIbMez");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.e("hii",documentSnapshot.getData().get("images").toString());
                slide=(ArrayList<String>) documentSnapshot.getData().get("images");

                sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                sliderView.setIndicatorSelectedColor(Color.WHITE);
                sliderView.setIndicatorUnselectedColor(Color.GRAY);
                sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                sliderView.startAutoCycle();
                sliderView.setSliderAdapter(new SliderAdapterExample(DoctorHome.this,slide));
            }
        });
    }
    class DoctorHomeDefault
    {
        String title;
        int image;
        int col;
        public DoctorHomeDefault(String t,int i,int c)
        {
            title=t;
            image=i;
            col=c;
        }
    }

    public ArrayList<DoctorHome.DoctorHomeDefault>declarepatient()
    {
        ArrayList<DoctorHome.DoctorHomeDefault> def=new ArrayList<>();
        //def.add(new DoctorHome.DoctorHomeDefault("Prescription",R.drawable.prescription_icon_img,R.color.tile1));
        def.add(new DoctorHome.DoctorHomeDefault("Chat",R.drawable.chat_img,R.color.tile3));
        def.add(new DoctorHome.DoctorHomeDefault("Scan QR Code",R.drawable.qrcode_img,R.color.tile2));
        def.add(new DoctorHome.DoctorHomeDefault("My Profile",R.drawable.profile_img,R.color.tile4));
        def.add(new DoctorHome.DoctorHomeDefault("Health Tips",R.drawable.healthyoutube,R.color.tile5));

        return def;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==camera_permission&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            Log.e("here","i am here");
            Intent i=new Intent(getApplicationContext(),BarCodeScanningActivity.class);
            startActivity(i);
        }
        else
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    camera_permission);
        }
    }
}
