package com.rafaqatmehmood.adminpannel1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button sign;
    EditText email,password;
    TextView NotAccount;
    Intent intent;
    FirebaseAuth auth;
    ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        sign=findViewById(R.id.btnLogin);
        auth=FirebaseAuth.getInstance();

        loadingbar=new ProgressDialog(this);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allowUserToLogin();
            }
        });

        NotAccount=findViewById(R.id.donotAccount);
        NotAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToRegisterActivity();
            }
        });
    }

    private void allowUserToLogin()
    {
        String field11,field22;
        field11=email.getText().toString();
        field22=password.getText().toString();
        if (TextUtils.isEmpty(field11))
        {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(field22))
        {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingbar.setTitle("Please Wait");
            loadingbar.setMessage("While Check login detail please wait.... ");
            loadingbar.show();
            loadingbar.setCanceledOnTouchOutside(true);

  //This line taken by oyher youtubewr
            auth.signInWithEmailAndPassword(field11,field22).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        sendUserToMainActivity();
                        Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();


                    }
                    else
                    {
                        String ErrorMessage=task.getException().getMessage();
                        Toast.makeText(LoginActivity.this, "Error Occur: "+ErrorMessage, Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }
    }

    private void sendUserToMainActivity()
    {
        Intent mainActivity=new Intent(LoginActivity.this,SetupActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivity);
        finish();
    }

    private void sendUserToRegisterActivity()
    {
        intent=new Intent(LoginActivity.this,RegistrationActivity.class);
        startActivity(intent);

    }
}