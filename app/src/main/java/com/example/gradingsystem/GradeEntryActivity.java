package com.example.gradingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GradeEntryActivity extends AppCompatActivity {

    EditText editStudentId, editSubject, editGrade;
    Button btnAddGrade;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_entry);

        dbHelper = new DBHelper(this);

        editStudentId = findViewById(R.id.editStudentId);
        editSubject = findViewById(R.id.editSubject);
        editGrade = findViewById(R.id.editGrade);
        btnAddGrade = findViewById(R.id.btnAddGrade);

        btnAddGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentId = editStudentId.getText().toString();
                String subject = editSubject.getText().toString();
                String grade = editGrade.getText().toString();

                boolean isInserted = dbHelper.insertGrade(studentId, subject, grade);
                if (isInserted) {
                    Toast.makeText(GradeEntryActivity.this, "Grade Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GradeEntryActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(GradeEntryActivity.this, "Error Adding Grade", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
