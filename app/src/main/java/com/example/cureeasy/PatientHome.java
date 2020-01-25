package com.example.cureeasy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class PatientHome extends AppCompatActivity {
RecyclerView homepatient;
ArrayList<PatientHomeDefault> slider_adapter;
ArrayList<String> slide;
SliderView sliderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_patient);
        slide=new ArrayList<>();
        sliderView=findViewById(R.id.imageSlider);
        showSlider();
homepatient=findViewById(R.id.patient_recycler);
slider_adapter=declarepatient();


        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        PatientHomeAdapter homeadapter=new PatientHomeAdapter(this,slider_adapter);
        homepatient.setLayoutManager(manager);
        homepatient.setAdapter(homeadapter);




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
                sliderView.setScrollTimeInSec(3); //set scroll delay in seconds :
                sliderView.startAutoCycle();
                sliderView.setSliderAdapter(new SliderAdapterExample(PatientHome.this,slide));
            }
        });
    }

class PatientHomeDefault
{
    String title;
    int image;
int col;
    public PatientHomeDefault(String t,int i,int c)
    {
        title=t;
        image=i;
        col=c;
    }
}

public ArrayList<PatientHomeDefault>declarepatient()
{
    ArrayList<PatientHomeDefault> def=new ArrayList<>();
    def.add(new PatientHomeDefault("Prescription",R.drawable.prescription_icon_img,R.color.tile1));
    def.add(new PatientHomeDefault("Chat",R.drawable.chat_img,R.color.tile3));
    def.add(new PatientHomeDefault("QR Code",R.drawable.qrcode_img,R.color.tile2));
    def.add(new PatientHomeDefault("My Profile",R.drawable.profile_img,R.color.tile4));
    def.add(new PatientHomeDefault("Health Tips",R.drawable.healthyoutube,R.color.tile5));
    return def;
}
}
