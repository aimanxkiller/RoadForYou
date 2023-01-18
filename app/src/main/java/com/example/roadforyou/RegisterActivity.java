package com.example.roadforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText email,password,name,add;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText)findViewById(R.id.inputEmail);
        password = (EditText)findViewById(R.id.inputPassword);
        name = (EditText)findViewById(R.id.inputName);
        add = (EditText)findViewById(R.id.inputAddress);

        mAuth = FirebaseAuth.getInstance();

        TextView btn=findViewById(R.id.alreadyHaveAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

    }

    public void createUser(View v){
        if(email.getText().toString().equals("") && password.getText().toString().equals("")
        && name.getText().toString().equals("") && add.getText().toString().equals("")){

            Toast.makeText(getApplicationContext(),"Blank not allowed",Toast.LENGTH_SHORT).show();

        }else{

            String mail = email.getText().toString();
            String pw = password.getText().toString();
            String fName = name.getText().toString().trim();
            String address = add.getText().toString().trim();


            mAuth.createUserWithEmailAndPassword(mail,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        User user = new User(fName,address,mail);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser()
                                        .getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this,"Data Added",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(RegisterActivity.this,"Data Failed to add",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        Toast.makeText(getApplicationContext(),"New user registered", Toast.LENGTH_SHORT).show();
                        finish();

                        Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                        startActivity(i);

                    }else{
                        Toast.makeText(getApplicationContext(),"User could not be registered",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

}