package com.example.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    MaterialButton signin;
    ProgressBar pb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signin = findViewById(R.id.loginbtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            navigateToSecondActivity();
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    void signIn(){
        try {
            Intent signInIntent = gsc.getSignInIntent();
            startActivityForResult(signInIntent, 1000);

        }
        catch (Error e){
            Toast.makeText(getApplicationContext(), (CharSequence) e, Toast.LENGTH_SHORT).show();
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

            try {
                String email = null;
                if (acct != null) {
                    email = acct.getEmail();
                }
                String[] split = new String[0];
                if (email != null) {
                    split = email.split("@");
                    String domain = split[1];
                    if (domain.equals("vitstudent.ac.in")) {
                        try {
                            task.getResult(ApiException.class);
                            navigateToSecondActivity();
                        } catch (ApiException e) {
                            // The ApiException status code indicates the detailed failure reason.
                            // Please refer to the GoogleSignInStatusCodes class reference for more information.
                            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                        }
                    } else {
                        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "Sign in using VIT mail ID", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            } catch (NullPointerException e){
                Toast.makeText(getApplicationContext(), "No Email ID found", Toast.LENGTH_SHORT).show();
            }


        }

    }



    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}