package com.example.gradingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnStudentEntry, btnGradeEntry, btnTranscriptSearch, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        btnStudentEntry = findViewById(R.id.btnStudentEntry);
        btnGradeEntry = findViewById(R.id.btnGradeEntry);
        btnTranscriptSearch = findViewById(R.id.btnTranscriptSearch);
        btnLogout = findViewById(R.id.btnLogout);

        // Set button click listeners to navigate to respective activities
        btnStudentEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StudentEntryActivity.class);
                startActivity(intent);
            }
        });

        btnGradeEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GradeEntryActivity.class);
                startActivity(intent);
            }
        });

        btnTranscriptSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TranscriptSearchActivity.class);
                startActivity(intent);
            }
        });

        // Logout button logic
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear any session data if applicable
                // For now, we just redirect to the login activity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();  // Close the current activity
            }
        });
    }
}
