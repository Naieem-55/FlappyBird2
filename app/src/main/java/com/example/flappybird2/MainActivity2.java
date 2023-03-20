package com.example.flappybird2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity {

    String name[]=new String[10];
    int imageAraay[]={R.drawable.icon21,R.drawable.icon22,R.drawable.icon23,R.drawable.icon24,R.drawable.icon25,R.drawable.icon26,R.drawable.icon27,R.drawable.icon22,R.drawable.icon17,R.drawable.icon16,R.drawable.icon11,R.drawable.icon12,R.drawable.icon13,R.drawable.icon14,R.drawable.icon15,R.drawable.icon16,R.drawable.icon17,R.drawable.icon18,R.drawable.icon19,R.drawable.icon20,R.drawable.icon21,R.drawable.icon22,R.drawable.icon23,R.drawable.icon24,R.drawable.icon25,R.drawable.icon26,R.drawable.icon27,R.drawable.icon22,R.drawable.icon17,R.drawable.icon16,R.drawable.icon11,R.drawable.icon12,R.drawable.icon13,R.drawable.icon14,R.drawable.icon15,R.drawable.icon16,R.drawable.icon17,R.drawable.icon18,R.drawable.icon19,R.drawable.icon20};
    String score[]=new String[10];
    public static int j=0;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://flappybird2-e6513-default-rtdb.firebaseio.com/").child("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    name[j]=(String)snap.child("fullname").getValue(String.class);
                    score[j]=(String)snap.child("score").getValue(String.class);
                    j++;
                }

                listView=(ListView)findViewById(R.id.customListView);
                CustomBaseAdapter customBaseAdapter= new CustomBaseAdapter(getApplicationContext(),name,imageAraay,score);
                listView.setAdapter(customBaseAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        j=0;

    }
}