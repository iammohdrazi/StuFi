package com.zaph.loginsignupdesign.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.zaph.loginsignupdesign.utils.ProgressDialog;
import com.zaph.loginsignupdesign.R;

public class ForgotPassword extends AppCompatActivity {

    private EditText forgotEmail;
    private Button sendEmail;
    private ImageView forgotPass;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_forgot__password);

        mAuth = FirebaseAuth.getInstance();

        forgotEmail = (EditText)findViewById(R.id.forgotEmail);
        sendEmail = (Button)findViewById(R.id.sendEmail);
        forgotPass = (ImageView)findViewById(R.id.ivForgot);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(forgotEmail.getText().toString().isEmpty()){
                        Snackbar.make(view,"E-Mail should not be empty",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        return;
                    }

                ProgressDialog.show(ForgotPassword.this);

                mAuth.sendPasswordResetEmail(forgotEmail.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(ForgotPassword.this,"email has been sent",Toast.LENGTH_SHORT).show();
                        ProgressDialog.dismiss();
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ForgotPassword.this,"check your email",Toast.LENGTH_SHORT).show();
                        ProgressDialog.dismiss();

                    }
                });

            }
        });

    }
}
