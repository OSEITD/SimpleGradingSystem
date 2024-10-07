package com.example.gradingsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TranscriptSearchActivity extends AppCompatActivity {

    EditText editStudentId;
    Button btnSearchTranscript;
    TextView txtTranscript;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcript_search);

        dbHelper = new DBHelper(this);

        editStudentId = findViewById(R.id.editStudentId);
        btnSearchTranscript = findViewById(R.id.btnSearchTranscript);
        txtTranscript = findViewById(R.id.txtTranscript);

        btnSearchTranscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentId = editStudentId.getText().toString();
                Cursor cursor = dbHelper.getStudentTranscript(studentId);

                if (cursor.getCount() > 0) {
                    StringBuilder transcript = new StringBuilder();
                    boolean classDisplayed = false; // To ensure class is only displayed once

                    while (cursor.moveToNext()) {
                        if (!classDisplayed) {
                            String studentClass = cursor.getString(0); // Retrieve the student's class
                            transcript.append("Class: ").append(studentClass).append("\n");
                            classDisplayed = true;
                        }
                        String subject = cursor.getString(1); // Subject
                        String grade = cursor.getString(2); // Grade
                        transcript.append(subject).append(": ").append(grade).append("\n");
                    }
                    txtTranscript.setText(transcript.toString());
                } else {
                    Toast.makeText(TranscriptSearchActivity.this, "No Transcript Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
