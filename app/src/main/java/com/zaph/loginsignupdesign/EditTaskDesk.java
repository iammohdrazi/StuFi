package com.zaph.loginsignupdesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zaph.loginsignupdesign.ui.MyDoes;
import com.zaph.loginsignupdesign.ui.StudentEditor;
import com.zaph.loginsignupdesign.utils.ProgressDialog;

public class EditTaskDesk extends AppCompatActivity {

    EditText  titleDoes , etdesc , etdate ;
    Button editbtn , deletebtn;
    ImageView editNoteBack;
    DatabaseReference reference;
    TextView titlePage , addTitle , tvTime , tvDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_desk);

        titleDoes = findViewById(R.id.titleDoes);
        etdesc = findViewById(R.id.etdesc);
        etdate = findViewById(R.id.ettime);
        editbtn = findViewById(R.id.editbtn);
        editNoteBack = findViewById(R.id.edit_note_back);
        deletebtn = findViewById(R.id.btn_delete_note);

        titlePage = findViewById(R.id.titlepage);
        addTitle = findViewById(R.id.addtitle);
        tvTime = findViewById(R.id.tvtime);
        tvDesc = findViewById(R.id.tvdesc);

        //import Font
        Typeface MLight = Typeface.createFromAsset(getAssets(),"fonts/regular.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(),"fonts/light.ttf");

        titlePage.setTypeface(MMedium);
        addTitle.setTypeface(MLight);
        tvTime.setTypeface(MLight);
        tvDesc.setTypeface(MLight);
        titleDoes.setTypeface(MLight);
        etdesc.setTypeface(MLight);
        etdate.setTypeface(MLight);

        editNoteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //get the value of the previous page
        titleDoes.setText(getIntent().getStringExtra("titledoes"));
        etdesc.setText(getIntent().getStringExtra("descdoes"));
        etdate.setText(getIntent().getStringExtra("datedoes"));

        //make an event for button

        final String keykeyDoes = getIntent().getStringExtra("keydoes");

        reference = FirebaseDatabase.getInstance().getReference().child("StuFi").child("Does"+ keykeyDoes );

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditTaskDesk.this);
                builder.setTitle("Delete this Note");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(EditTaskDesk.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(EditTaskDesk.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        finish();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validation(titleDoes.getText().toString(),etdate.getText().toString(),etdesc.getText().toString(),v)){
                    return;
                }

                ProgressDialog.show(EditTaskDesk.this);

                reference.setValue(new MyDoes(titleDoes.getText().toString(),etdate.getText().toString(),etdesc.getText().toString(),keykeyDoes))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                ProgressDialog.dismiss();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditTaskDesk.this, "Fail to Edit", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public Boolean validation(String title,String time,String description,View v){
        if(title.isEmpty()&&time.isEmpty()&&description.isEmpty()){
            Snackbar.make(v,"No Data Inserted",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }
        if(title.isEmpty()){
            Snackbar.make(v,"Title should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if(time.isEmpty()){
            Snackbar.make(v,"Time should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if(description.isEmpty()){
            Snackbar.make(v,"Description should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        return true;
    }

}
