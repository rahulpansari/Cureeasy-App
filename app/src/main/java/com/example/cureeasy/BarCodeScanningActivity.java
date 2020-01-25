package com.example.cureeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;

import java.security.Permission;

public class BarCodeScanningActivity extends AppCompatActivity {
    private BarcodeDetector barcodeDetector;
    private CameraSource camerasource;
    private SurfaceView cameraview;
    private TextView barcodevalue;
    int flag=1;
private int camera_permission=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_scanning);
        /*if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {*/
            openCamera();
    /* }
        else
        {
            askPermission();
        }*/

    }

  /*  public void askPermission()
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BarCodeScanningActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    camera_permission);


        }
    }*/


    /*public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==camera_permission&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            Log.e("here","i am here");
            openCamera();
        }
        else
        {
            ActivityCompat.requestPermissions(BarCodeScanningActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    camera_permission);
        }
    }*/

    public void openCamera()
    {

        cameraview = findViewById(R.id.camsurface);
        barcodevalue = findViewById(R.id.camtext);
        barcodeDetector=new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        camerasource=new CameraSource.Builder(this,barcodeDetector).setRequestedPreviewSize(1600,1024).setAutoFocusEnabled(true).build();
        cameraview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try
                {Log.e("here","heretoo");
                    camerasource.start(cameraview.getHolder());
                }
                catch (Exception e)
                {

                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                camerasource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            camerasource.release();
            barcodeDetector.release();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes=detections.getDetectedItems();
                try {
                    if (barcodes.size() != 0) {
                        if (barcodes.valueAt(0) != null) {
                            if (flag == 1) {
                                flag = 0;
                                Intent i = new Intent(getApplicationContext(), DoctorPres.class);
                                i.putExtra("userid", barcodes.valueAt(0).displayValue);

                                startActivity(i);
                                finish();
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Wrong QR Code",Toast.LENGTH_SHORT).show();
                    finish();
                }
    }});}

    @Override
    protected void onPause() {
        super.onPause();
        //camerasource.release();

}


}