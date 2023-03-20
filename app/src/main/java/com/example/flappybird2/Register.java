package com.example.flappybird2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://flappybird2-e6513-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullname=findViewById(R.id.full_name);
        final EditText email=findViewById(R.id.email);
        final EditText phone =findViewById(R.id.phone);
        final EditText password=findViewById(R.id.password);
        final EditText conPassword=findViewById(R.id.conform_password);


        final Button registerButton=findViewById(R.id.register_here_button);
        final TextView logInNowButton=findViewById(R.id.login_now_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullNameText=fullname.getText().toString();
                final String emailText=email.getText().toString();
                final String phoneText=phone.getText().toString();
                final String passwordText=password.getText().toString();
                final String conPasswordText=conPassword.getText().toString();



                if(fullNameText.isEmpty() || emailText.isEmpty() || phoneText.isEmpty() || passwordText.isEmpty() || conPasswordText.isEmpty()){
                    Toast.makeText(Register.this, "Please all the Form", Toast.LENGTH_SHORT).show();
                }else if(!passwordText.equals(conPasswordText)){
                    Toast.makeText(Register.this, "Password are not matching", Toast.LENGTH_SHORT).show();
                }else{


                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(phoneText)){
                                Toast.makeText(Register.this,"Phone is Already Registered",Toast.LENGTH_SHORT).show();
                            }else{

                                databaseReference.child("users").child(phoneText).child("fullname").setValue(fullNameText);
                                databaseReference.child("users").child(phoneText).child("email").setValue(emailText);
                                databaseReference.child("users").child(phoneText).child("password").setValue(passwordText);
                                databaseReference.child("users").child(phoneText).child("score").setValue("0");

                                Toast.makeText(Register.this, "Register Successful.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });

        logInNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}