package com.example.gradingsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GradingSystem.db";

    // Users table
    private static final String USERS_TABLE = "users";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    // Students table
    private static final String STUDENTS_TABLE = "students";
    private static final String COL_STUDENT_ID = "student_id";
    private static final String COL_STUDENT_NAME = "student_name";
    private static final String COL_CLASS = "class";

    // Grades table
    private static final String GRADES_TABLE = "grades";
    private static final String COL_SUBJECT = "subject";
    private static final String COL_GRADE = "grade";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        db.execSQL("CREATE TABLE " + USERS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, "  + COL_PASSWORD + " TEXT)");

        // Create students table
        db.execSQL("CREATE TABLE " + STUDENTS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_STUDENT_ID + " TEXT, " + COL_STUDENT_NAME + " TEXT, " + COL_CLASS + " TEXT)");

        // Create grades table
        db.execSQL("CREATE TABLE " + GRADES_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_STUDENT_ID + " TEXT, " + COL_SUBJECT + " TEXT, " + COL_GRADE + " TEXT, " +
                "FOREIGN KEY(" + COL_STUDENT_ID + ") REFERENCES " + STUDENTS_TABLE + "(" + COL_STUDENT_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GRADES_TABLE);
        onCreate(db);
    }

    // Insert a new user (teacher)
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);

        long result = db.insert(USERS_TABLE, null, contentValues);
        return result != -1;  // Return true if insert is successful
    }

    // Insert a new student
    public boolean insertStudent(String studentId, String studentName, String studentClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_STUDENT_ID, studentId);
        contentValues.put(COL_STUDENT_NAME, studentName);
        contentValues.put(COL_CLASS, studentClass);

        long result = db.insert(STUDENTS_TABLE, null, contentValues);
        return result != -1;
    }

    // Insert a grade for a student
    public boolean insertGrade(String studentId, String subject, String grade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_STUDENT_ID, studentId);
        contentValues.put(COL_SUBJECT, subject);
        contentValues.put(COL_GRADE, grade);

        long result = db.insert(GRADES_TABLE, null, contentValues);
        return result != -1;
    }

    // Get all students
    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + STUDENTS_TABLE, null);
    }

    // Search for a student by ID and get transcript (grades)
    public Cursor getStudentTranscript(String studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Join the grades and students tables to get the student class and transcript
        String query = "SELECT s." + COL_CLASS + ", g." + COL_SUBJECT + ", g." + COL_GRADE +
                " FROM " + GRADES_TABLE + " g " +
                " JOIN " + STUDENTS_TABLE + " s ON g." + COL_STUDENT_ID + " = s." + COL_STUDENT_ID +
                " WHERE g." + COL_STUDENT_ID + " = ?";
        return db.rawQuery(query, new String[]{studentId});
    }

    // Get a user by username and password (for login)
    public Cursor getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?", new String[]{username, password});
    }
}
