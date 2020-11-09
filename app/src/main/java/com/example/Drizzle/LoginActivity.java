package com.example.Drizzle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {

    private Button btnSignIn;
    private EditText inputPhone;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = findViewById(R.id.btnSignIn);
        inputPhone = findViewById(R.id.inputPhone);

       btnSignIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //No phone number input
               String phoneNumber = inputPhone.getText().toString();
               if(phoneNumber.isEmpty())
                   Toast.makeText(LoginActivity.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
               else {
                   //Verify phone number
                   PhoneAuthProvider.getInstance().verifyPhoneNumber("+1"+phoneNumber, 60, TimeUnit.SECONDS, LoginActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                       @Override
                       public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                           signInUser(phoneAuthCredential);
                       }

                       @Override
                       public void onVerificationFailed(@NonNull FirebaseException e) {
                           Log.d(TAG, "onVerificationFailed:"+e.getLocalizedMessage());
                       }

                       @Override
                       public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                           super.onCodeSent(verificationId, forceResendingToken);
                           //Show enter verification popup
                           Dialog dialog = new Dialog(LoginActivity.this);
                           dialog.setContentView(R.layout.verify_popup);
                           final EditText inputVerify = dialog.findViewById(R.id.inputVerify);
                           EditText btnVerify = dialog.findViewById(R.id.btnVerify);
                           btnVerify.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                String verificationCode = inputVerify.getText().toString();
                                                                if(verificationId.isEmpty()) return;
                                                                //create credential
                                                                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verificationCode);
                                                                signInUser(credential);
                                                            }
                                                        });
                                   dialog.show();
                       }
                   });
               }
           }
       });
    }

    private void signInUser(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else {
                            Log.d(TAG, "onComplete: "+task.getException().getLocalizedMessage());
                        }
                        }
                    });
    }
}