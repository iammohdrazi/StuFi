package com.zaph.loginsignupdesign.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.Image;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zaph.loginsignupdesign.DatePickerFragment;
import com.zaph.loginsignupdesign.EditTaskDesk;
import com.zaph.loginsignupdesign.R;
import com.zaph.loginsignupdesign.TimePickerFragment;
import com.zaph.loginsignupdesign.firebase.FirebaseHelper;
import com.zaph.loginsignupdesign.models.Event;
import com.zaph.loginsignupdesign.models.Student;
import com.zaph.loginsignupdesign.ui.DatabaseHelperClass;
import com.zaph.loginsignupdesign.utils.ProgressDialog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class StudentEditor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener{

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

    private ImageView edteventimage;
    int SELECT_PHOTO = 1;
    Uri uri;
    String downloadUrl;
    private StorageReference mStorageReference;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String image;

    private TextView edtdateselector;
    private TextView edttimeselector;
    String currentDateString = "";
    String am_pm = "";
    String timings;

    private Button editEvent;
    private Button removeEvent;

    private DatabaseHelperClass myDb ;

    private TextView toolbarHeading;
    private ImageView ivBack;
    private ImageView ivDelete;
    private MainAdapter mainAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_editor);

        titlepage = findViewById(R.id.edttitlepage);
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
        editEvent = (Button) findViewById(R.id.editEvent);
        removeEvent = (Button) findViewById(R.id.removeevent);

        edttimeselector = findViewById(R.id.edttimeselector);
        edtdateselector = findViewById(R.id.edtdateselector);
        edteventimage = findViewById(R.id.edteventimage);

        hostBranch = (Spinner)findViewById(R.id.editeventspinnerbranch);
        hostYear = (Spinner)findViewById(R.id.editeventspinneryear);
        category = (Spinner)findViewById(R.id.edtspinnereventcategory);
        joiningCriteria = (Spinner)findViewById(R.id.edtspinnerjoiningcriteria);

        eventEditorRadioGender = (RadioGroup)findViewById(R.id.event_editor_radio_gender);
        eventEditorRadioFemale = (RadioButton)findViewById(R.id.event_editor_radio_female);
        eventEditorRadioMale = (RadioButton)findViewById(R.id.event_editor_radio_male);

        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");

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
        edttimeselector.setText(event.getEventtime());
        edtdateselector.setText(event.getEventdate());

        image = event.getBannerUrl();
        if(image != null) {
            Toast.makeText(this, "Image Loading May Take Time..", Toast.LENGTH_SHORT).show();
            Glide.with(this).load(image).into(edteventImage);
        }
        else{
            Toast.makeText(this, "No Image Found", Toast.LENGTH_SHORT).show();
        }

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

        edttimeselector.setText(event.getEventtime());
        edttimeselector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        edtdateselector.setText(event.getEventdate());
        edtdateselector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        edteventimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_PHOTO);
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

        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog.show(StudentEditor.this);
                downloadUrl = event.getBannerUrl();
                DocumentReference editor = db.collection("events").document(event.getEventId());
                editor.update("hostname", edthostName.getText().toString());
                editor.update("hostphone", edthostPhone.getText().toString());
                editor.update("hostgender", edtgender);
                editor.update("hostid", edthostId.getText().toString());
                editor.update("hostemail", edthostEmail.getText().toString());
                editor.update("hostcourse", branchName);
                editor.update("hostyear", currentYear);
                editor.update("eventcategory", categoryName);
                editor.update("eventname", edteventName.getText().toString());
                editor.update("eventvenue", edteventVenue.getText().toString());
                editor.update("eventfee", edteventFee.getText().toString());
                editor.update("eventpayment", edteventPayment.getText().toString());
                editor.update("joiningcriteria", joinType);
                editor.update("eventprize", edteventPrize.getText().toString());
                editor.update("eventdescription", edteventDescription.getText().toString());
                editor.update("eventdate", currentDateString);
                editor.update("eventtime", timings);
                editor.update("bannerUrl", image)
                        .addOnSuccessListener(new OnSuccessListener < Void > () {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(StudentEditor.this, "Updated Successfully",
                                        Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                ProgressDialog.dismiss();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StudentEditor.this, "Error !@#", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        removeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(StudentEditor.this);
                builder.setTitle("Delete this Event");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProgressDialog.show(StudentEditor.this);
                        db.collection("events").document(event.getEventId()).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(StudentEditor.this, "Event Deleted Successfully", Toast.LENGTH_SHORT).show();
                                        ProgressDialog.dismiss();
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(StudentEditor.this, "Event Not Deleted", Toast.LENGTH_SHORT).show();
                                ProgressDialog.dismiss();
                            }
                        });
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
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
        if(uri == null){
            Snackbar.make(v,"Image is Required",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        return true;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        currentDateString = DateFormat.getDateInstance().format(c.getTime());
        edtdateselector.setText(currentDateString);
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

        timings = hourOfDay + ":" + minute + " " + am_pm;
        edttimeselector.setText(timings);
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
                                    image =  uri.toString();
                                    Toast.makeText(StudentEditor.this,image, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StudentEditor.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else{
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri){
        //to get the image extension
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            Log.d("Uri Dtata",uri.toString());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                Glide.with(this).load(uri).into(edteventImage);
                uploadFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
