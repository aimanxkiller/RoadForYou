package com.example.roadforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.view.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class ReportActivity extends AppCompatActivity implements LocationListener {

    private String temp;
    private DatabaseReference reference,referenceRpt;
    private LocationManager manager;
    private String lat, longi;
    private Double lat2,longi2;
    private FirebaseAuth mAuth;
    EditText name,desc;

    private final int MIN_TIME = 1000; // 1sec
    private final int MIN_DIST = 5; // 5 meter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mAuth = FirebaseAuth.getInstance();
        name = (EditText)findViewById(R.id.inputRname);
        desc = (EditText)findViewById(R.id.inputDesc);

        //getDeviceID to store location without logging in
        temp = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        reference = FirebaseDatabase.getInstance().getReference("Location").child(temp);

        readLocation();

    }

    private void readLocation() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    try {
                        MyLocation location = dataSnapshot.getValue(MyLocation.class);
                        if (location != null) {

                            DecimalFormat df = new DecimalFormat("#.####");

                            lat =  df.format(location.getLatitude());
                            longi =  df.format(location.getLongitude());

                            lat2 = location.getLatitude();
                            longi2 = location.getLongitude();

                            TextView textLat = (TextView) findViewById(R.id.latPos);
                            TextView textLong = (TextView) findViewById(R.id.longPos);

                            textLat.setText(lat);
                            textLong.setText(longi);

                        }
                    }catch (Exception e){
                        Toast.makeText(ReportActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    public void saveReport(View v){


        if(name.getText().toString().equals("") && desc.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Blank is not allowed !", Toast.LENGTH_SHORT).show();
        }else{

            String rptName = name.getText().toString();
            String rptDesc = desc.getText().toString().trim();
            Double rptLat =  lat2 ;
            Double rptLong = longi2 ;
            String crtUser = mAuth.getCurrentUser().toString();

            referenceRpt = FirebaseDatabase.getInstance().getReference("Location").child(rptName);

            Report newReport = new Report(rptName,rptDesc,crtUser,lat2,longi2);

            FirebaseDatabase.getInstance().getReference("Report")
                    .child(rptName).setValue(newReport).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ReportActivity.this,"Report Added",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ReportActivity.this,"Report Failed to add",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Toast.makeText(getApplicationContext(),"New report added", Toast.LENGTH_SHORT).show();
            finish();

            Intent i = new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(i);
        }
    }

}




