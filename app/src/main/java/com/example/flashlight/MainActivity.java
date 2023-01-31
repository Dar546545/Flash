package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton toggleButton;

    boolean HasCameraFlash = false;
    boolean Zapnuto = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.toggleButton);

        HasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HasCameraFlash){
                    if(Zapnuto){
                        Zapnuto = false;
                        toggleButton.setImageResource(R.drawable.power_off);
                        try {
                            FlashOff();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Zapnuto = true;
                        toggleButton.setImageResource(R.drawable.power_on);
                        try {
                            FlashOn();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Zadny flash neni k dispozici", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void FlashOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager != null;
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, true);
        Toast.makeText(MainActivity.this, "Flash je zapnuty", Toast.LENGTH_SHORT).show();
    }

    private void FlashOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager != null;
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, false);
        Toast.makeText(MainActivity.this, "Flash je vypnuty", Toast.LENGTH_SHORT).show();
    }


}