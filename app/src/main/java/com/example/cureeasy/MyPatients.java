package com.example.cureeasy;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyPatients extends AppCompatActivity {

    final String TAG = "MyPatients";
    RecyclerView mRecyclerView;
    PatientDataAdapter mPatientDataAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<PatientData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients);

        list = new ArrayList<>();
        getData();
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mPatientDataAdapter = new PatientDataAdapter(this, list);
        mRecyclerView.setAdapter(mPatientDataAdapter);

    }

    public void getData(){
        db.collection("DiagnosticResult")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<PatientData> x = new ArrayList<>();
                int count = 0;
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    count++;
//                    Map<String, Object> document;
//                    document = document.getData();
//                    Toast.makeText(MyPatients.this, document.toString(), Toast.LENGTH_SHORT).show();
                    PatientData current = new PatientData(document.get("Patient_id").toString(),
                            document.get("Doctor_id").toString(),
                            document.get("Clinic_name").toString(),
                            document.get("Disease").toString(),
                            document.get("Rec_Treatment").toString(),
                            document.get("Prev_Treatment").toString(),
                            document.get("Billing Amount").toString(),
                            document.get("Application_status").toString(),
                            document.get("Insaurance_id").toString());
                    Log.e("", current.toString());
                    x.add(current);
                    Log.e("", current.toString());
                }
                Log.e("", count + "");
                list.addAll(x);
                Log.e("", list.size() + "");
                Log.e("", list.toString());
                mPatientDataAdapter.notifyDataSetChanged();
//                Log.d(TAG, list.toString());
            }
        });
    }
}
