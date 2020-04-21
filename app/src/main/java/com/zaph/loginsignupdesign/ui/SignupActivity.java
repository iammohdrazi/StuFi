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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.zaph.loginsignupdesign.utils.ProgressDialog;
import com.zaph.loginsignupdesign.R;

public class SignupActivity extends AppCompatActivity {

    private EditText etName,etuserPassword,etconfirmPassword,etemail;
    private FirebaseAuth mAuth;
    private Button sign_up;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_signup_form);

        mAuth = FirebaseAuth.getInstance();


        etName=(EditText)findViewById(R.id.name_sign_up);
        etemail=(EditText)findViewById(R.id.email_sign_up);
        etuserPassword=(EditText)findViewById(R.id.password_login);
        etconfirmPassword=(EditText)findViewById(R.id.passwordConfir_login);
        back = (ImageView) findViewById(R.id.signUpBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sign_up=(Button)findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(validate(etuserPassword.getText().toString(),etconfirmPassword.getText().toString(),etName.getText().toString(),etemail.getText().toString())){
                    signUp();
                }
            }
        });
    }

    private boolean validate(String userPassword,String confirmPassword,String name,String e_mail){

        if(userPassword.isEmpty()&&confirmPassword.isEmpty()&&name.isEmpty()&&e_mail.isEmpty()){
            Toast.makeText(SignupActivity.this, getResources().getString(R.string.fill_details),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name.isEmpty()){
            Toast.makeText(SignupActivity.this,"Name is empty",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(e_mail.isEmpty()){
            Toast.makeText(SignupActivity.this,"EMail Should not be empty",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(userPassword.isEmpty()){
            Toast.makeText(SignupActivity.this,"Password is empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(userPassword.length()<6){
            Toast.makeText(SignupActivity.this,"Password should be greater than 6 words",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(confirmPassword.isEmpty()){
            Toast.makeText(SignupActivity.this,"Confirm your Password",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!userPassword.equals(confirmPassword)){
            Toast.makeText(SignupActivity.this,"Password do not match",Toast.LENGTH_SHORT).show();
            return false;
        }

        //All is clear
        return true;

    }

    private void signUp(){

        ProgressDialog.show(this);

        mAuth.createUserWithEmailAndPassword(etemail.getText().toString(),etuserPassword.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(SignupActivity.this,"Sign UP Successful",Toast.LENGTH_SHORT).show();
                        ProgressDialog.dismiss();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this,"SIgn Up unsuccessful",Toast.LENGTH_SHORT).show();
                        ProgressDialog.dismiss();

                    }
                });

    }

}
