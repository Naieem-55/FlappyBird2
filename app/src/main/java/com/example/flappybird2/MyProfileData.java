package com.example.flappybird2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class MyProfileData extends AppCompatActivity {

    private EditText tName,tPhone,tscore,tEmail;
    private String cellNo;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_data);

        tName=findViewById(R.id.editTextTextPersonName);
        tPhone=findViewById(R.id.editTextTextPersonName2);
        tscore=findViewById(R.id.editTextTextPersonName3);
        tEmail=findViewById(R.id.editTextTextPersonName4);
        back=findViewById(R.id.button2);
        cellNo=Login.phoneNumber;

        DatabaseReference ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://flappybird2-e6513-default-rtdb.firebaseio.com/").child("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(cellNo)){
                    final String dname=snapshot.child(cellNo).child("fullname").getValue(String.class);
                    final String dscore=snapshot.child(cellNo).child("score").getValue(String.class);
                    final String demail=snapshot.child(cellNo).child("email").getValue(String.class);

                    tName.setText(dname);
                    tPhone.setText(cellNo);
                    tscore.setText(dscore);
                    tEmail.setText(demail);
                    Toast.makeText(MyProfileData.this, "User Information", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MyProfileData.this, "Wrong User Info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfileData.this,StartActivity.class));
            }
        });
    }
}