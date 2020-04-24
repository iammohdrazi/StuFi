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
import com.zaph.loginsignupdesign.models.Event;
import com.zaph.loginsignupdesign.models.Student;
import com.zaph.loginsignupdesign.ui.DatabaseHelperClass;


public class StudentEditor extends AppCompatActivity {

    private RadioGroup eventEditorRadioGender;
    private RadioButton eventEditorRadioFemale;
    private RadioButton eventEditorRadioMale;
    private EditText edthostName;
    private EditText edthostPhone;
    private EditText edthostId;
    private EditText edthostEmail;
    private EditText edteventName;
    private EditText edteventVenue;
    private EditText edteventFee;
    private EditText edteventPayment;
    private EditText edteventPrize;
    private EditText edteventDescription;
    private ImageView edteventImage;

    private String[] availableBranches = {" Course "," Other "," PH.D (CE) "," PH.D (ECE) ", " PH.D (ME) ", " PH.D (CSE) "," M.Tech (CSE) "," M.Tech (CE) "," M.Tech (ECE) "," B.Tech (CSE) "," B.Tech (CE) "," B.Tech (ME) "," B.Tech (EEE) "," B.Tech (ECE) "," B.Tech (LEET) "};
    private String[] yearOfCourse = {" Year "," N/A"," 5th Year ", " 4th Year ", " 3rd Year ", " 2nd Year ", " 1st Year "};
    private String[] eventCategory = {" Event Category "," Adventure "," Educational "," Singing "," Trivia "," Dance "," Games "," Debate "," Seminar "," Sports "," Fest "," Fresher "," Convocation "," Prizes "," Festival "," Other "};
    private String[] joining = {" Joining Criteria "," Students Only "," Teachers Only "," All "," Other "};

    private Spinner hostBranch;
    private Spinner hostYear;
    private Spinner category;
    private Spinner joiningCriteria;
    private String branchName,currentYear,categoryName,joinType;
    private String edtgender;

    private Button editEvent;

    private DatabaseHelperClass myDb ;

    private TextView toolbarHeading;
    private ImageView ivBack;
    private ImageView ivDelete;
    private MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_editor);

        edthostName = (EditText)findViewById(R.id.edthostname);
        edthostPhone = (EditText)findViewById(R.id.edthostphone);
        edthostId = (EditText)findViewById(R.id.edthostid);
        edthostEmail = (EditText)findViewById(R.id.edthostemail);
        edteventName = (EditText)findViewById(R.id.edteventname);
        edteventVenue = (EditText)findViewById(R.id.edtvenue);
        edteventFee = (EditText)findViewById(R.id.edteventfee);
        edteventPayment = (EditText)findViewById(R.id.edtpayment);
        edteventPrize = (EditText)findViewById(R.id.edteventprize);
        edteventDescription = (EditText)findViewById(R.id.edteventdescription);
        edteventImage = (ImageView) findViewById(R.id.edteventimage);
        editEvent = (Button) findViewById(R.id.editevent);

        hostBranch = (Spinner)findViewById(R.id.editeventspinnerbranch);
        hostYear = (Spinner)findViewById(R.id.editeventspinneryear);
        category = (Spinner)findViewById(R.id.edtspinnereventcategory);
        joiningCriteria = (Spinner)findViewById(R.id.edtspinnerjoiningcriteria);

        eventEditorRadioGender = (RadioGroup)findViewById(R.id.event_editor_radio_gender);
        eventEditorRadioFemale = (RadioButton)findViewById(R.id.event_editor_radio_female);
        eventEditorRadioMale = (RadioButton)findViewById(R.id.event_editor_radio_male);


        Intent intent = getIntent();
        Event event= (Event) intent.getSerializableExtra("data2");

        edthostName.setText(event.getHostname());
        edthostPhone.setText(event.getHostphone());
        edthostId.setText(event.getHostid());
        edthostEmail.setText(event.getHostemail());
        edteventName.setText(event.getEventname());
        edteventVenue.setText(event.getEventvenue());
        edteventFee.setText(event.getEventfee());
        edteventPayment.setText(event.getEventpayment());
        edteventPrize.setText(event.getEventprize());
        edteventDescription.setText(event.getEventdescription());

        toolbarHeading = (TextView) findViewById(R.id.tvToolbarHeading);
        ivBack =(ImageView) findViewById(R.id.ivBack);

        myDb = new DatabaseHelperClass(this); // dataBase constructor is calling here

        ivBack.setVisibility(View.VISIBLE);

        edtgender = event.getHostgender();

        if(edtgender == "Female"){
        eventEditorRadioGender.check(R.id.event_editor_radio_female);
        }else{
            eventEditorRadioGender.check(R.id.event_editor_radio_male);
        }

        eventEditorRadioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.event_radio_male){
                    edtgender="Male";
                }else{
                    edtgender="Female";
                }

            }
        });

        branchName = event.getHostcourse();
        ArrayAdapter aaBranch = new ArrayAdapter(StudentEditor.this, android.R.layout.simple_spinner_item , availableBranches );
        aaBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        hostBranch.setAdapter(aaBranch);
        hostBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        hostBranch.setSelection(branchPosition);


        currentYear = event.getHostyear();
        ArrayAdapter aaYear = new ArrayAdapter(StudentEditor.this, android.R.layout.simple_spinner_item, yearOfCourse);
        aaYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostYear.setAdapter(aaYear);

        hostYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        hostYear.setSelection(branchYear);

        categoryName = event.getEventcategory();
        ArrayAdapter aaCategory = new ArrayAdapter(StudentEditor.this, android.R.layout.simple_spinner_item, eventCategory);
        aaYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(aaCategory);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryName=eventCategory[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        int categories = -1;
        for(int i=0;i< eventCategory.length;i++){
            if(categoryName.equals(eventCategory[i])){
                categories = i;
            }
        }
        category.setSelection(categories);

        joinType = event.getJoiningcriteria();
        ArrayAdapter aaJoining = new ArrayAdapter(StudentEditor.this, android.R.layout.simple_spinner_item, joining);
        aaJoining.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        joiningCriteria.setAdapter(aaJoining);

        joiningCriteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                joinType=joining[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        int join = -1;
        for(int i=0;i< joining.length;i++){
            if(joinType.equals(joining[i])){
                join = i;
            }
        }
        joiningCriteria.setSelection(join);

        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validation(edthostName.getText().toString(),
                        edthostPhone.getText().toString(),
                        edthostId.getText().toString(),
                        edthostEmail.getText().toString(),
                        v,
                        edteventName.getText().toString(),
                        edteventVenue.getText().toString(),
                        edteventFee.getText().toString(),
                        edteventPayment.getText().toString(),
                        edteventPrize.getText().toString(),
                        edteventDescription.getText().toString()
                        )){
                    return;
                }

                /*boolean isUpdated = myDb.updateData(editStudentId.getText().toString(),editStudentName.getText().toString(),gender,editStudentPhone.getText().toString(),editStudentEmail.getText().toString(),branchName,currentYear,editAttendance);
                if(isUpdated){
                    Toast.makeText(StudentEditor.this, "Student Updated", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(StudentEditor.this, "Error Occured", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();*/
            }
        });

          // for deleting a student

       /* ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(StudentEditor.this);
                builder.setTitle(" Confirm ");
                builder.setMessage(" Do You Want To Delete "+edteventName.getText().toString()+"?");

                builder.setPositiveButton("     Yes     ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        *//*myDb.deleteData(editStudentId.getText().toString());*//*
                        setResult(RESULT_OK);
                        finish();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });*/


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private Boolean validation(String name,
                               String phone,
                               String id,
                               String email,
                               View v,
                               String eventname,
                               String eventvenue,
                               String eventfee,
                               String eventpayment,
                               String eventprize,
                               String eventdescription
    ) {
        if(name.isEmpty()&&phone.isEmpty()&&id.isEmpty()&&email.isEmpty()&&eventname.isEmpty()&&eventvenue.isEmpty()&&eventfee.isEmpty()&&eventpayment.isEmpty()&&eventprize.isEmpty()&&eventdescription.isEmpty()){
            Snackbar.make(v,"Fields should not be empty",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (name.isEmpty()) {
            Snackbar.make(v,"Name should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (phone.isEmpty()) {
            Snackbar.make(v,"Phone should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;

        }

        if (id.isEmpty()) {
            Snackbar.make(v,"Id should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (email.isEmpty()) {
            Snackbar.make(v,"E-Mail should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }

        if (branchName == " Course ") {
            Snackbar.make(v,"Select Course",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (currentYear == " Year ") {
            Snackbar.make(v,"Select Year",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (eventname.isEmpty()) {
            Snackbar.make(v,"EventName should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }if (categoryName == " Event Category ") {
            Snackbar.make(v,"Select Event Category",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (eventvenue.isEmpty()) {
            Snackbar.make(v,"Enter Venue of Event",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (eventfee.isEmpty()) {
            Snackbar.make(v,"You Forgot to put Fee Here",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (eventpayment.isEmpty()) {
            Snackbar.make(v,"Give a payment method",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (joinType == " Joining Criteria ") {
            Snackbar.make(v,"Select a Joining Criteria",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (eventprize.isEmpty()) {
            Snackbar.make(v,"Provide a Prize",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }if (eventdescription.isEmpty()) {
            Snackbar.make(v,"Description is Required",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        return true;
    }

}
