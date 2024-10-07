package com.example.gradingsystem;


import static com.example.gradingsystem.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StudentEntryActivity extends AppCompatActivity {

    EditText editStudentId, editStudentName, editStudentClass;
    Button btnAddStudent;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_entry);

        dbHelper = new DBHelper(this);

        editStudentId = findViewById(R.id.editStudentId);
        editStudentName = findViewById(R.id.editStudentName);
        editStudentClass = findViewById(R.id.editStudentClass);
        btnAddStudent = findViewById(R.id.btnAddStudent);

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentId = editStudentId.getText().toString();
                String studentName = editStudentName.getText().toString();
                String studentClass = editStudentClass.getText().toString();

                boolean isInserted = dbHelper.insertStudent(studentId, studentName, studentClass);
                if (isInserted) {
                    Toast.makeText(StudentEntryActivity.this, "Student Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StudentEntryActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(StudentEntryActivity.this, "Error Adding Student", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
