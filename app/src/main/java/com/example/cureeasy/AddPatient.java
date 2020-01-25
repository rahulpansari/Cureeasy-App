package com.example.cureeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddPatient extends AppCompatActivity {

    private static final String TAG = "AddPatient";
    EditText Patient, Doctor, Clinic, Disease, Rect, Pret, Bill, Insaurance;
    String Pname, Dname, Cname, DisName, RTname, PTname, Bname, Iname, AppStatus,recordId;
    static int id=1;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    boolean checked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        Patient = (EditText) findViewById(R.id.patient);
        Doctor = (EditText) findViewById(R.id.doctor);
        Clinic = (EditText) findViewById(R.id.clinic);
        Disease = (EditText) findViewById(R.id.disease);
        Rect = (EditText) findViewById(R.id.rect);
        Pret = (EditText) findViewById(R.id.pret);
        Bill = (EditText) findViewById(R.id.bill);
        Insaurance = (EditText) findViewById(R.id.insaurance);
    }

    public void RadioButtonClicked(View v){
        checked = ((RadioButton) v).isChecked();
        switch(v.getId()) {
            case R.id.active:
                AppStatus = "Active";
                break;
            case R.id.inactive:
                AppStatus = "Inactive";
                break;
        }
    }

    Map<String,String> getDbData(){
        Map<String,String> docData = new HashMap<>();
        docData.put("Patient_id", Pname);
        docData.put("Doctor_id", Dname);
        docData.put("Clinic_name", Cname);
        docData.put("Disease",DisName);
        docData.put("Rec_Treatment", RTname);
        docData.put("Prev_Treatment",PTname);
        docData.put("Insaurance_id",Iname);
        docData.put("Application_status",AppStatus);
        docData.put("Billing Amount", Bname);
        return  docData;
    }

    public void set_data(){
        Pname = Patient.getText().toString();
        Dname = Doctor.getText().toString();
        Cname = Clinic.getText().toString();
        DisName = Disease.getText().toString();
        RTname = Rect.getText().toString();
        PTname = Pret.getText().toString();
        Bname = Bill.getText().toString();
        Iname = Insaurance.getText().toString();
        recordId = String.valueOf(id++);
    }

    public void addToDb(View v){
        set_data();
        Log.w(TAG, "Hello World" + Pname);
        Map<String,String> db_data = getDbData();
        database.collection("DiagnosticResult")
                .document("Patient"+recordId)
                .set(db_data);
    }
}
