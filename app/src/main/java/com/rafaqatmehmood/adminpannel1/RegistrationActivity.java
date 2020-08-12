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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    EditText U_Email,U_Password,U_ConfirmPassword;
    Button U_RegisterButton;
    TextView HaveAnAccount;
    FirebaseAuth auth;
    ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        U_Email=findViewById(R.id.register_email);
        U_Password=findViewById(R.id.register_password);
        U_ConfirmPassword=findViewById(R.id.register_confirmpassword);
        U_RegisterButton=findViewById(R.id.btnRegister);
        HaveAnAccount=findViewById(R.id.haveanaccount);

        auth=FirebaseAuth.getInstance();
        loadingbar= new ProgressDialog(this);

        HaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });

        U_RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount()
    {
        String field1,field2,field3;
        field1=U_Email.getText().toString();
        field2=U_Password.getText().toString();
        field3=U_ConfirmPassword.getText().toString();

        //Helper Option or sign
        if (TextUtils.isEmpty(field1))
        {

            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(field2))
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(field3))
        {
            Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
        }
        else if (!field2.equals(field3))      //field2 !=field3   this is use for comparing not equal but !field2.equals(field3) this is equal check
        {
            Toast.makeText(this, "Not Match", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingbar.setTitle("Creating New Account");
            loadingbar.setMessage("Please wait, while we are creating new account....");
            loadingbar.show();
            loadingbar.setCanceledOnTouchOutside(true);    //This line mean ager user na screen per touch keya to dialog box nahe jy ga jab tak wo apna process complete nahe karta

            auth.createUserWithEmailAndPassword(field1,field2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        //  userSendToSetupActivity();
                        Toast.makeText(RegistrationActivity.this, "Account Created Successful..", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                    else
                    {
                        String Errormessage=task.getException().getMessage();
                        Toast.makeText(RegistrationActivity.this, "Error Occur :" +Errormessage, Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }
    }

    private void userSendToSetupActivity()
    {
        Intent setupIntent=new Intent(RegistrationActivity.this,MainActivity.class);
        setupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }
}