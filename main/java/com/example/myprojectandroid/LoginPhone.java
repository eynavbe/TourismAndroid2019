package com.example.myprojectandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

public class LoginPhone extends Activity {
    EditText etNumberPhone,etCode;
    Button btnSendCode,btnCheckCode;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        etNumberPhone = findViewById(R.id.etNumberPhone);
        etCode = findViewById(R.id.etCode);
        btnSendCode = findViewById(R.id.btnSendCode);
        btnCheckCode = findViewById(R.id.btnCheckCode);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        btnSendCode.setOnClickListener((v)->{
            String phoneNumber =  "+972"+etNumberPhone.getText().toString();
            if (TextUtils.isEmpty(phoneNumber)){
                Toast.makeText(this, "נא להקליד את מספר הטלפון שלך לפני", Toast.LENGTH_SHORT).show();
            }else {
                progressDialog.setTitle("אימות טלפון");
                progressDialog.setMessage("נא לחכות בזמן שאנחנו מאמתים את הטלפון שלך");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        this,               // Activity (for callback binding)
                        callbacks);        // OnVerificationStateChangedCallbacks
            }
        });

        btnCheckCode.setOnClickListener((v)->{
            btnSendCode.setVisibility(View.INVISIBLE);
            etNumberPhone.setVisibility(View.INVISIBLE);
            String verificationCode = etCode.getText().toString();
            if (TextUtils.isEmpty(verificationCode)){
                Toast.makeText(this, "נא תקליד את הקוד לפני", Toast.LENGTH_SHORT).show();
            }else {
                progressDialog.setTitle("אימות קוד");
                progressDialog.setMessage("נא לחכות בזמן שאנחנו מאמתים את הקוד שלך");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                signInWithPhoneAuthCredential(credential);
            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressDialog.dismiss();
                Toast.makeText(LoginPhone.this, "שגיאה במספר הטלפון, נא לרשום מספר טלפון תקין", Toast.LENGTH_SHORT).show();
                btnSendCode.setVisibility(View.VISIBLE);
                etNumberPhone.setVisibility(View.VISIBLE);
                etCode.setVisibility(View.INVISIBLE);
                btnCheckCode.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {


                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                progressDialog.dismiss();
                Toast.makeText(LoginPhone.this, "קוד נשלח אליך לטלפון", Toast.LENGTH_SHORT).show();
                btnSendCode.setVisibility(View.INVISIBLE);
                etNumberPhone.setVisibility(View.INVISIBLE);
                etCode.setVisibility(View.VISIBLE);
                btnCheckCode.setVisibility(View.VISIBLE);
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            Toast.makeText(LoginPhone.this, "ההתחברות הצליחה", Toast.LENGTH_SHORT).show();
                            SendUserToMainActivity();

                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(LoginPhone.this,  "Error: "+message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    
                });
        
    }

    private void SendUserToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
