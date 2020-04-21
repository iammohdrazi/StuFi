package com.zaph.loginsignupdesign.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zaph.loginsignupdesign.DatePickerFragment;
import com.zaph.loginsignupdesign.NewTaskActivity;
import com.zaph.loginsignupdesign.R;
import com.zaph.loginsignupdesign.TimePickerFragment;
import com.zaph.loginsignupdesign.models.Student;
import com.zaph.loginsignupdesign.ui.DatabaseHelperClass;
import com.zaph.loginsignupdesign.utils.ProgressDialog;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddStudent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

    private RadioGroup radioGenderGroup;
    private RadioButton radioGenderButton;
    private Button addStudentConfirm;

    private EditText hostName;
    private EditText hostPhone;
    private EditText hostId;
    private EditText hostEmail;
    private EditText eventName;
    private EditText eventVenue;
    private EditText eventFee;
    private EditText eventPayment;

    private String[] availableBranches = {" Course "," Other "," PH.D (CE) "," PH.D (ECE) ", " PH.D (ME) ", " PH.D (CSE) "," M.Tech (CSE) "," M.Tech (CE) "," M.Tech (ECE) "," B.Tech (CSE) "," B.Tech (CE) "," B.Tech (ME) "," B.Tech (EEE) "," B.Tech (ECE) "," B.Tech (LEET) "};
    private String[] yearOfCourse = {" Year "," N/A"," 5th Year ", " 4th Year ", " 3rd Year ", " 2nd Year ", " 1st Year "};
    private String[] eventCategory = {" Event Category "," Adventure "," Educational "," Singing "," Trivia "," Dance "," Games "," Debate "," Seminar "," Sports "," Fest "," Fresher "," Convocation "," Prizes "," Festival "," Other "};
    private String[] joining = {" Joining Criteria "," Students Only "," Teachers Only "," Teachers and Students "," Other "};

    private Spinner hostBranch;
    private Spinner hostYear;
    private Spinner category;
    private Spinner joiningCriteria;
    private String branchName,currentYear,categoryName,joinType;
    private String gender="Male";

    //date picker
    private TextView mDisplayData;
    //time picker
    private TextView timeSelector;

    private String attendance = "On Working";

    private DatabaseHelperClass myDb ;
    private ImageView ivBack;

    private DatabaseReference reference;
    private Integer doesNum = new Random().nextInt();

    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        myDb = new DatabaseHelperClass(this); // dataBase constructor is calling here

        hostName = (EditText) findViewById(R.id.ethostname);
        hostPhone = (EditText) findViewById(R.id.ethostphone);
        hostId = (EditText) findViewById(R.id.ethostid);
        hostEmail = (EditText) findViewById(R.id.ethostemail);

        eventName = findViewById(R.id.eteventname);
        eventVenue = findViewById(R.id.etvenue);
        eventFee = findViewById(R.id.eteventfee);
        eventPayment = findViewById(R.id.etpayment);

        hostBranch = (Spinner) findViewById(R.id.neweventspinnerbranch);
        hostYear = (Spinner) findViewById(R.id.neweventspinneryear);
        category = (Spinner) findViewById(R.id.spinnereventcategory);
        joiningCriteria = (Spinner) findViewById(R.id.spinnerjoiningcriteria);

        ivBack = findViewById(R.id.ivBack);
        ivBack.setVisibility(View.VISIBLE);

        radioGenderGroup = (RadioGroup) findViewById(R.id.event_radio_gender);
        radioGenderGroup.check(R.id.event_radio_male);
        radioGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.event_radio_male){
                    gender="Male";
                }else{
                    gender="Female";
                }

            }
        });

        mDisplayData = findViewById(R.id.dateselector);
        mDisplayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        timeSelector = findViewById(R.id.timeselector);
        timeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        addStudentConfirm = (Button) findViewById(R.id.createevent);
        addStudentConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGenderGroup.getCheckedRadioButtonId();
                radioGenderButton = (RadioButton) findViewById(selectedId);
                if(!validation(hostName.getText().toString(),
                        hostPhone.getText().toString(),
                        hostId.getText().toString(),
                        hostEmail.getText().toString(),
                        eventName.getText().toString(),
                        eventVenue.getText().toString(),
                        v,
                        eventFee.getText().toString(),
                        eventPayment.getText().toString()
                        )){
                    return;
                }

                boolean isInserted = myDb.insertData(hostId.getText().toString(), hostName.getText().toString(),gender,hostPhone.getText().toString(),hostEmail.getText().toString(),branchName,currentYear,attendance);
                if(isInserted == true){
                    /*Toast.makeText(AddStudent.this, "Student Added", Toast.LENGTH_SHORT).show();*/
                }else{
                    Toast.makeText(AddStudent.this, "Student Not Added", Toast.LENGTH_SHORT).show();
                    return;
                }


                //Database Firestore

                mFirestore = FirebaseFirestore.getInstance();
                Map<String, String> event = new HashMap<>();

                event.put("hostname",hostName.getText().toString());
                event.put("hostphone",hostPhone.getText().toString());
                event.put("hostgender",gender);
                event.put("hostid",hostId.getText().toString());
                event.put("hostemail",hostEmail.getText().toString());
                event.put("hostcourse",branchName);
                event.put("hostyear",currentYear);
                event.put("eventname",eventName.getText().toString());
                event.put("eventcategory",categoryName);
                event.put("eventvenue",eventVenue.getText().toString());
                event.put("eventfee",eventFee.getText().toString());
                event.put("eventpayment",eventPayment.getText().toString());
                event.put("joiningcriteria",joinType);


                mFirestore.collection("events").document("hostDetails").set(event).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        String error = e.getMessage();
                        Toast.makeText(AddStudent.this, "Error :" + error, Toast.LENGTH_SHORT).show();

                    }
                });

                Intent intent = new Intent();
                intent.putExtra("data",new Student(hostName.getText().toString(),hostPhone.getText().toString(),gender,hostId.getText().toString(),hostEmail.getText().toString(),branchName,currentYear,eventName.getText().toString(),categoryName,eventVenue.getText().toString(),eventFee.getText().toString(),eventPayment.getText().toString(),joinType));
                setResult(RESULT_OK,intent);

                finish();
            }
        });

        //for back
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//Spinner Code

        //Creating the ArrayAdapter instance having the branch name list
        ArrayAdapter aaBranch = new ArrayAdapter(AddStudent.this, android.R.layout.simple_spinner_item, availableBranches);
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

        ArrayAdapter aaYear = new ArrayAdapter(AddStudent.this, android.R.layout.simple_spinner_item, yearOfCourse);
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

        ArrayAdapter aaeventCategory = new ArrayAdapter(AddStudent.this, android.R.layout.simple_spinner_item, eventCategory);
        aaeventCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(aaeventCategory);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryName=eventCategory[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter aaeventJoining = new ArrayAdapter(AddStudent.this, android.R.layout.simple_spinner_item, joining);
        aaeventCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        joiningCriteria.setAdapter(aaeventJoining);

        joiningCriteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                joinType=joining[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        TextView textView = findViewById(R.id.dateselector);
        textView.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String am_pm;
        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY,hourOfDay);
        datetime.set(Calendar.MINUTE,minute);

        if(datetime.get(Calendar.AM_PM) == Calendar.AM){
            am_pm = "AM";
        }else{
            am_pm = "PM";
        }

        TextView textView = findViewById(R.id.timeselector);
        textView.setText(hourOfDay+" : "+minute+" "+am_pm);
    }

    private Boolean validation(String name, String phone, String id, String email, String eventname, String eventvenue, View v,String eventfee,String eventpayment) {
        if(name.isEmpty()&&phone.isEmpty()&&id.isEmpty()&&email.isEmpty()&&eventname.isEmpty()&&eventvenue.isEmpty()&&eventfee.isEmpty()&&eventpayment.isEmpty()){
            Snackbar.make(v,"Fields should not be empty",Snackbar.LENGTH_LONG).setAction("Action",null).show();
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
            Snackbar.make(v,"Id should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (email.toString().isEmpty()) {
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
        if (categoryName == " Event Category ") {
            Snackbar.make(v,"Select Event Category",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (eventname.isEmpty()) {
            Snackbar.make(v,"EventName should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
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
            Snackbar.make(v,"Give a payment method",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }

        return true;
    }

}
