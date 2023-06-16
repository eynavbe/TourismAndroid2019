package com.example.myprojectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button btnRegister,btnLogin;
    EditText etPassword,etEmail;
    SignInButton sibLoginGoogle;
    Button sibLoginAnonymously;
    Button sibLoginPhone;
//    Button sibLoginEmail;
    private static final int RC_SIGN_IN = 123;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        sibLoginGoogle = findViewById(R.id.sibLoginGoogle);
        sibLoginAnonymously = findViewById(R.id.sibLoginAnonymously);
        sibLoginPhone = findViewById(R.id.sibLoginPhone);
//        sibLoginEmail = findViewById(R.id.sibLoginEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
//        sibLoginEmail.setOnClickListener((v)->{
//            Intent intent = new Intent(this,LoginEmail.class);
//            startActivity(intent);
//        });

        sibLoginGoogle.setOnClickListener((v)->{
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);

        });
        sibLoginPhone.setOnClickListener((v -> {
            Intent intent = new Intent(this,LoginPhone.class);
            startActivity(intent);
        }));
        sibLoginAnonymously.setOnClickListener((v)->{
            firebaseAuthWithAnonymously();
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        btnLogin.setOnClickListener((v)->{
            login();
        });
        btnRegister.setOnClickListener((v)->{
            register();
        });
    }
    private OnSuccessListener<AuthResult> mSuccessListenerLogin = authResult -> {
        checkIfEmailVerified();

    };
    private OnSuccessListener<AuthResult> mSuccessListenerRegister = authResult -> {
        sendVerificationEmail();
    };
    private OnFailureListener mFailureListener = (Exception e) -> {
        System.out.println(e.getLocalizedMessage());
        new AlertDialog.Builder(this).setTitle("שגיאה").setMessage(e.getLocalizedMessage()).
                setPositiveButton("בסדר", (dialog, which) -> { }).show();
    };
    private void login() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if ((!email.equals("")) && (!password.equals(""))){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).
                    addOnSuccessListener(this,mSuccessListenerLogin).addOnFailureListener(this,mFailureListener);
        }
    }
    private void register() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if ((!email.equals("")) && (!password.equals(""))){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).
                    addOnSuccessListener(this,mSuccessListenerRegister).addOnFailureListener(this,mFailureListener);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                System.out.println("ssss");

            } catch (ApiException e) {
                System.out.println("error");
//                IdpResponse response = IdpResponse.fromResultIntent(data);
//                System.out.println("Error code "+ response.getError().getErrorCode());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(),"המשתמש נכנס",Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(),"לא יכול להיכנס עם זה משתמש",Toast.LENGTH_SHORT).show();
                    }

                }
            });
    }
    private void firebaseAuthWithAnonymously() {

        firebaseAuth.signInAnonymously()
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"המשתמש נכנס",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"לא יכול להיכנס עם זה משתמש",Toast.LENGTH_SHORT).show();

                    }
                }
            });
    }
    private void sendVerificationEmail()
    {
        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        btnRegister.setEnabled(true);
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "נשלח מייל אימות", Toast.LENGTH_SHORT).show();
                            checkIfEmailVerified();

                        }else
                            Toast.makeText(LoginActivity.this, "לא הצליח לשלוח מייל אימות", Toast.LENGTH_SHORT).show();
                    }
                });


    }
    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "לא אושר מייל האימות", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
        }
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if ((requestCode == RC_SIGN_IN) && (resultCode == RESULT_OK)){
//            System.out.println("success");
//        }else {
//            System.out.println("error");
////            IdpResponse response = IdpResponse.fromResultIntent(data);
////            System.out.println("Error code "+ response.getError().getErrorCode());
//        }
//    }
}
