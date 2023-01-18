package com.example.roadforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    private DatabaseReference reference;
    private String temp,cName,cMail,cAdd;
    EditText uName,uAdd;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        temp = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(temp);

        readDetails();

    }

    private void readDetails() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    try{
                        User cUser = dataSnapshot.getValue(User.class);

                        if(cUser != null){
                            cName = cUser.getFullname();
                            cMail = cUser.getEmail();
                            cAdd = cUser.getAddress();

                            TextView textName = (TextView) findViewById(R.id.txt_name);
                            TextView textMail = (TextView) findViewById(R.id.txt_email);

                            textName.setText(cName);
                            textMail.setText(cMail);

                            uName = (EditText)findViewById(R.id.editName);
                            uAdd = (EditText) findViewById(R.id.editAdd);

                            uName.setText(cName);
                            uAdd.setText(cAdd);

                        }
                    }catch (Exception e){
                        Toast.makeText(UserProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}