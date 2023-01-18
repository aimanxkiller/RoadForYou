package com.example.roadforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText name,password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = (EditText)findViewById(R.id.inputEmail);
        password = (EditText)findViewById(R.id.inputPassword);
        mAuth=FirebaseAuth.getInstance();

        TextView btn=findViewById(R.id.textViewSignUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }

    public void loginUser(View v){
        if(name.getText().toString().equals("") && password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Blank is not allowed !", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(name.getText().toString(),password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"User Logged in Succesfully",Toast.LENGTH_SHORT).show();
                                finish();
                                Intent i = new Intent(getApplicationContext(),ReportActivity.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(getApplicationContext(),"User could not be logged in",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}