package com.zaph.loginsignupdesign.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.zaph.loginsignupdesign.utils.ProgressDialog;
import com.zaph.loginsignupdesign.R;

import java.util.Calendar;

public class SignInActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private Button login;
    private TextView attempts;
    private int counter = 5;
    private CountDownTimer timer;
    private Button sign_up;
    private FirebaseAuth mAuth;
    private TextView forgotPassword;
    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_in);


        name = (EditText) findViewById(R.id.name_login);
        password = (EditText) findViewById(R.id.password_login);
        attempts = (TextView) findViewById(R.id.attempts_login);
        login = (Button) findViewById(R.id.btn_Submit);
        attempts.setVisibility(View.GONE);
        sign_up = (Button) findViewById(R.id.sign_up);
        /*startImage = (ImageView)findViewById(R.id.time_photo);*/
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);

        // to get the time pics in the front screen
        /*Calendar calender = Calendar.getInstance();
        int currentDate = calender.get(Calendar.HOUR_OF_DAY);
        if(currentDate >= 18){
            startImage.setImageResource(R.drawable.goodn);
        }
        else {
            startImage.setImageResource(R.drawable.goodm);
        }*/

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(SignInActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(name.getText().toString(), password.getText().toString());
            }
        });

        showTimer();

        mAuth = FirebaseAuth.getInstance();

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(SignInActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

    }


    private void validate(String email, String userPassword) {


        if(email.isEmpty()&&userPassword.isEmpty()){
            Toast.makeText(SignInActivity.this,"Enter your email and Password",Toast.LENGTH_SHORT).show();
            return;
        }

        if(email.isEmpty()){
            Toast.makeText(SignInActivity.this,"Email should not be empty!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPassword.isEmpty()){
            Toast.makeText(SignInActivity.this,getResources().getString(R.string.passsword_empty),Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog.show(this);

        mAuth.signInWithEmailAndPassword(email, userPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                        ProgressDialog.dismiss();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ProgressDialog.dismiss();
                        attempts.setVisibility(View.VISIBLE);
                        counter--;
                        attempts.setText("No.of attemps remaining : " + counter);
                        if (counter == 0) {
                            timer.start();
                            login.setEnabled(false);
                            counter=3;
                        }
                    }
                });
    }


    private void showTimer() {
        timer = new CountDownTimer(10000, 1) {
            @Override
            public void onTick(long l) {
                attempts.setText("Wait for " + l / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                login.setEnabled(true);
                attempts.setVisibility(View.GONE);
            }
        };
    }

}
