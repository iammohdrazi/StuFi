package com.zaph.loginsignupdesign.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import android.webkit.MimeTypeMap;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zaph.loginsignupdesign.DatePickerFragment;
import com.zaph.loginsignupdesign.NewTaskActivity;
import com.zaph.loginsignupdesign.R;
import com.zaph.loginsignupdesign.TimePickerFragment;
import com.zaph.loginsignupdesign.Upload;
import com.zaph.loginsignupdesign.firebase.FirebaseHelper;
import com.zaph.loginsignupdesign.models.Student;
import com.zaph.loginsignupdesign.ui.DatabaseHelperClass;
import com.zaph.loginsignupdesign.utils.ProgressDialog;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    private EditText eventPrize;
    private EditText eventDescription;
    private FirebaseAuth mAuth;
    private ImageView eventImage;

    private TextView titlepage;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tv10;
    private TextView tv11;
    private TextView tv12;
    private TextView tv13;
    private TextView tv14;

    String currentDateString = "";
    String am_pm = "";
    String timings;
    String image;

    int SELECT_PHOTO = 1;
     Uri uri;
     private StorageReference mStorageReference;
     private DatabaseReference mDatabaseReference;
     String downloadUrl;

    private String[] availableBranches = {" Course "," Other "," PH.D (CE) "," PH.D (ECE) ", " PH.D (ME) ", " PH.D (CSE) "," M.Tech (CSE) "," M.Tech (CE) "," M.Tech (ECE) "," B.Tech (CSE) "," B.Tech (CE) "," B.Tech (ME) "," B.Tech (EEE) "," B.Tech (ECE) "," B.Tech (LEET) "};
    private String[] yearOfCourse = {" Year "," N/A"," 5th Year ", " 4th Year ", " 3rd Year ", " 2nd Year ", " 1st Year "};
    private String[] eventCategory = {" Event Category "," Adventure "," Educational "," Singing "," Trivia "," Dance "," Games "," Debate "," Seminar "," Sports "," Fest "," Fresher "," Convocation "," Prizes "," Festival "," Other "};
    private String[] joining = {" Joining Criteria "," Students Only "," Teachers Only "," All "," Other "};

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
        eventPrize = findViewById(R.id.eteventprize);
        eventDescription = findViewById(R.id.eteventdescription);
        eventImage = findViewById(R.id.eteventimage);

        titlepage = findViewById(R.id.titlepage);
        tv1 = findViewById(R.id.tvhostname);
        tv2 = findViewById(R.id.tvhostphone);
        tv3 = findViewById(R.id.tvhostgender);
        tv4 = findViewById(R.id.tvhostid);
        tv5 = findViewById(R.id.tvhostemail);
        tv6 = findViewById(R.id.tveventname);
        tv7 = findViewById(R.id.tvvenue);
        tv8 = findViewById(R.id.tveventfee);
        tv9 = findViewById(R.id.tvpayment);
        tv10 = findViewById(R.id.tvdateselector);
        tv11 = findViewById(R.id.tvtimeselector);
        tv12 = findViewById(R.id.tveventprize);
        tv13 = findViewById(R.id.tveventdescription);
        tv14 = findViewById(R.id.tveventimage);

        //import Font
        Typeface MLight = Typeface.createFromAsset(getAssets(),"fonts/regular.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(),"fonts/light.ttf");

        titlepage.setTypeface(MMedium);
        tv1.setTypeface(MLight);
        tv2.setTypeface(MLight);
        tv3.setTypeface(MLight);
        tv4.setTypeface(MLight);
        tv5.setTypeface(MLight);
        tv6.setTypeface(MLight);
        tv7.setTypeface(MLight);
        tv8.setTypeface(MLight);
        tv9.setTypeface(MLight);
        tv10.setTypeface(MLight);
        tv11.setTypeface(MLight);
        tv12.setTypeface(MLight);
        tv13.setTypeface(MLight);
        tv14.setTypeface(MLight);

        hostBranch = (Spinner) findViewById(R.id.neweventspinnerbranch);
        hostYear = (Spinner) findViewById(R.id.neweventspinneryear);
        category = (Spinner) findViewById(R.id.spinnereventcategory);
        joiningCriteria = (Spinner) findViewById(R.id.spinnerjoiningcriteria);

        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        ivBack = findViewById(R.id.ivBack);
        ivBack.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();

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

        eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_PHOTO);
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
                        eventPayment.getText().toString(),
                        eventPrize.getText().toString(),
                        eventDescription.getText().toString(),
                        downloadUrl
                        )){
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
                event.put("eventprize",eventPrize.getText().toString());
                event.put("eventdescription",eventDescription.getText().toString());
                event.put("eventdate",currentDateString);
                event.put("eventtime",timings);
                event.put("bannerUrl",downloadUrl);
                event.put("userId", FirebaseHelper.getUser().getUid());
                ProgressDialog.show(AddStudent.this);
                mAuth = FirebaseAuth.getInstance();
                String eventId = mFirestore.collection("events").document().getId();
                event.put("eventId",eventId);
                mFirestore.collection("events").document(eventId).set(event).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {

                        String error = e.getMessage();
                        Toast.makeText(AddStudent.this, "Error :" + error, Toast.LENGTH_SHORT).show();
                        ProgressDialog.dismiss();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ProgressDialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                    }
                });

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            Log.d("Uri Dtata",uri.toString());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                Glide.with(this).load(uri).into(eventImage);
                uploadFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        currentDateString = DateFormat.getDateInstance().format(c.getTime());
        TextView textView = findViewById(R.id.dateselector);
        textView.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY,hourOfDay);
        datetime.set(Calendar.MINUTE,minute);

        if(datetime.get(Calendar.AM_PM) == Calendar.AM){
            am_pm = "AM";
        }else{
            am_pm = "PM";
        }

        TextView textView = findViewById(R.id.timeselector);
        timings = hourOfDay + ":" + minute + " " + am_pm;
        textView.setText(timings);
    }

    private String getFileExtension(Uri uri){
        //to get the image extension
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if(uri != null){
            final StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
            +"."+ getFileExtension(uri));
            ProgressDialog.show(this);
            fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    ProgressDialog.dismiss();
                                    downloadUrl =  uri.toString();
                                    Toast.makeText(AddStudent.this,downloadUrl, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStudent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean validation(String name,
                               String phone,
                               String id,
                               String email,
                               String eventname,
                               String eventvenue,
                               View v,
                               String eventfee,
                               String eventpayment,
                               String eventprize,
                               String eventdescription,
                               String downloadUrl
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
        if(uri == null){
            Snackbar.make(v,"Image is Required",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }

        return true;
    }

}
