package com.zaph.loginsignupdesign.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.zaph.loginsignupdesign.R;
import com.zaph.loginsignupdesign.models.Student;
import com.zaph.loginsignupdesign.ui.DatabaseHelperClass;


public class StudentEditor extends AppCompatActivity {

    private EditText editStudentName;
    private EditText editStudentId;
    private EditText editStudentPhone;
    private RadioGroup editRadioGender;
    private EditText editStudentEmail;
    private Spinner editSpinnerBranch;
    private Spinner editSpinnerYear;
    private String editAttendance = "Updated";
    private String[] availableBranches = {" Course "," Other "," PH.D (CE) "," PH.D (ECE) ", " PH.D (ME) ", " PH.D (CSE) "," M.Tech (CSE) "," M.Tech (CE) "," M.Tech (ECE) "," B.Tech (CSE) "," B.Tech (CE) "," B.Tech (ME) "," B.Tech (EEE) "," B.Tech (ECE) "," B.Tech (LEET) "};
    private String[] yearOfCourse = {" Year "," N/A"," 5th Year ", " 4th Year ", " 3rd Year ", " 2nd Year ", " 1st Year "};

    private Spinner branch;
    private Spinner year;
    private String branchName,currentYear;
    private String gender;

    private Button editStudentInfo;

    private DatabaseHelperClass myDb ;

    private TextView toolbarHeading;
    private ImageView ivBack;
    private ImageView ivDelete;
    private MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_editor);

        editStudentName = (EditText)findViewById(R.id.edit_student_name);
        editStudentId = (EditText)findViewById(R.id.edit_student_id);
        editStudentPhone= (EditText)findViewById(R.id.edit_student_phone);
        editRadioGender = (RadioGroup) findViewById(R.id.edit_radio_gender);
        editStudentEmail = (EditText)findViewById(R.id.edit_student_email);
        editSpinnerBranch = (Spinner)findViewById(R.id.edit_spinner_branch);
        editSpinnerYear = (Spinner)findViewById(R.id.edit_spinner_year);
        ivDelete = findViewById(R.id.ivDelete);


        Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra("data2");

        editStudentName.setText(student.getName());
        editStudentId.setText(student.get_id());
        editStudentPhone.setText(student.getPhone());
        editStudentEmail.setText(student.getEmail());

        toolbarHeading = (TextView) findViewById(R.id.tvToolbarHeading);
        ivBack =(ImageView) findViewById(R.id.ivBack);

        myDb = new DatabaseHelperClass(this); // dataBase constructor is calling here

        toolbarHeading.setText("Student Editor");
        toolbarHeading.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);
        ivDelete.setVisibility(View.VISIBLE);

        gender = student.getGender();

        if(gender == "Female"){
        editRadioGender.check(R.id.edit_radio_female);
        }else{
            editRadioGender.check(R.id.edit_radio_male);
        }

        editRadioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.event_radio_male){
                    gender="Male";
                }else{
                    gender="Female";
                }

            }
        });

        editStudentId.setEnabled(false);

        branchName = student.getBranch();
        ArrayAdapter aaBranch = new ArrayAdapter(StudentEditor.this, android.R.layout.simple_spinner_item , availableBranches );
        aaBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        editSpinnerBranch.setAdapter(aaBranch);
        editSpinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branchName=availableBranches[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        int branchPosition = -1;
        for(int i=0;i< availableBranches.length;i++){
            if(branchName.equals(availableBranches[i])){
                branchPosition = i;
            }
        }
        editSpinnerBranch.setSelection(branchPosition);


        currentYear = student.getYear();
        ArrayAdapter aaYear = new ArrayAdapter(StudentEditor.this, android.R.layout.simple_spinner_item, yearOfCourse);
        aaYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editSpinnerYear.setAdapter(aaYear);

        editSpinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentYear=yearOfCourse[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        int branchYear = -1;
        for(int i=0;i< yearOfCourse.length;i++){
            if(currentYear.equals(yearOfCourse[i])){
                branchYear = i;
            }
        }
        editSpinnerYear.setSelection(branchYear);

        editStudentInfo = (Button) findViewById(R.id.update_student_button);
        editStudentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validation(editStudentName.getText().toString(),editStudentPhone.getText().toString(),editStudentId.getText().toString(),editStudentEmail.getText().toString(),v)){
                    return;
                }
                boolean isUpdated = myDb.updateData(editStudentId.getText().toString(),editStudentName.getText().toString(),gender,editStudentPhone.getText().toString(),editStudentEmail.getText().toString(),branchName,currentYear,editAttendance);
                if(isUpdated){
                    Toast.makeText(StudentEditor.this, "Student Updated", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(StudentEditor.this, "Error Occured", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

          // for deleting a student

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(StudentEditor.this);
                builder.setTitle(" Confirm ");
                builder.setMessage(" Do You Want To Delete "+editStudentName.getText().toString()+"?");

                builder.setPositiveButton("     Yes     ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDb.deleteData(editStudentId.getText().toString());
                        setResult(RESULT_OK);
                        finish();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private Boolean validation(String name,String phone,String id,String email,View v) {
        if(name.isEmpty()&&phone.isEmpty()&&id.isEmpty()&&email.isEmpty()){
            Toast.makeText(StudentEditor.this, "Please fill the details", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.toString().isEmpty()) {
            Snackbar.make(v,"Name should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (phone.toString().isEmpty()) {
            Snackbar.make(v,"Phone should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();

            return false;

        }

        if (id.toString().isEmpty()) {
            Snackbar.make(v,"ID should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (email.toString().isEmpty()) {
            Snackbar.make(v,"E-Mail should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }

        if (branchName.toString() == " Course ") {
            Snackbar.make(v,"Select Branch",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (currentYear.toString() == " Year ") {
            Snackbar.make(v,"Select Year",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        return true;
    }

}
