package com.example.flappybird2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DifficultyLevel extends AppCompatActivity {

    private Button bEasy,bMedium,bHard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_level);

        bEasy=findViewById(R.id.buttonEasy);
        bMedium=findViewById(R.id.buttonMedium);
        bHard=findViewById(R.id.buttonHard);

        bEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameView.difficulty=400;
                Toast.makeText(DifficultyLevel.this, "Easy mode Enable", Toast.LENGTH_SHORT).show();
            }
        });
        bMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameView.difficulty=300;
                Toast.makeText(DifficultyLevel.this, "Medium mode Enable", Toast.LENGTH_SHORT).show();
            }
        });
        bHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameView.difficulty=240;
                Toast.makeText(DifficultyLevel.this, "Hard Mode Enable", Toast.LENGTH_SHORT).show();
            }
        });
    }
}