package com.zaph.loginsignupdesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zaph.loginsignupdesign.ui.MyDoes;
import com.zaph.loginsignupdesign.ui.assignment.AssignmentFragment;
import com.zaph.loginsignupdesign.utils.ProgressDialog;

import java.util.Random;

public class NewTaskActivity extends AppCompatActivity {

    TextView titlepage, addtitle , tvdesc , tvtime;
    EditText titleDoes , etdesc , ettime;

    ImageView noteBack;
    Button createDoes;

    DatabaseReference reference;
    Integer doesNum = new Random().nextInt();

    String keydoes = Integer.toString(doesNum);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        titlepage = findViewById(R.id.titlepage);
        addtitle = findViewById(R.id.addtitle);
        titleDoes = findViewById(R.id.titleDoes);
        tvdesc = findViewById(R.id.tvdesc);
        tvtime = findViewById(R.id.tvtime);
        etdesc = findViewById(R.id.etdesc);
        ettime = findViewById(R.id.ettime);
        noteBack = findViewById(R.id.note_back);
        createDoes = findViewById(R.id.createdoes);

        //import Font
        Typeface MLight = Typeface.createFromAsset(getAssets(),"fonts/regular.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(),"fonts/light.ttf");

        //customize font here
        titlepage.setTypeface(MMedium);
        addtitle.setTypeface(MLight);
        titleDoes.setTypeface(MLight);
        tvdesc.setTypeface(MLight);
        tvtime.setTypeface(MLight);
        etdesc.setTypeface(MLight);
        ettime.setTypeface(MLight);

        noteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createDoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Button is clicked here
                //insert data to database
                if(!validation(titleDoes.getText().toString(),ettime.getText().toString(),etdesc.getText().toString(),v)){
                    return;
                }
                ProgressDialog.show(NewTaskActivity.this);
                reference = FirebaseDatabase.getInstance().getReference().child("StuFi").child("Does"+ doesNum );
                reference.setValue(new MyDoes(titleDoes.getText().toString(),ettime.getText().toString(),etdesc.getText().toString(),keydoes))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ProgressDialog.dismiss();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(NewTaskActivity.this, "Failed to Get Data", Toast.LENGTH_SHORT).show();
                                ProgressDialog.dismiss();
                            }
                        });


               /* reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("titledoes").setValue(titleDoes.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(etdesc.getText().toString());
                        dataSnapshot.getRef().child("datedoes").setValue(ettime.getText().toString());
                        dataSnapshot.getRef().child("keydoes").setValue(keydoes);

                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(NewTaskActivity.this, "Check Your NetWork Connection!", Toast.LENGTH_SHORT).show();
                    }
                });*/

            }
        });

    }

    public Boolean validation(String title,String time,String description,View v){
        if(title.isEmpty()&&time.isEmpty()&&description.isEmpty()){
            Snackbar.make(v,"No Data Inserted",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
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
