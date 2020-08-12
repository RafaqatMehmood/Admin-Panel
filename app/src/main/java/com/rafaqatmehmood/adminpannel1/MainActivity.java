package com.rafaqatmehmood.adminpannel1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Intent loginIntent;
    DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        userRef= FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override                          //This method check user login or not or always register or not
    protected void onStart()
    {
        FirebaseUser currentUser=auth.getCurrentUser();
        if (currentUser==null)
        {
            sendUserToLoginActivity();
        }
        else
        {
            checkUserExistenceWithRealtimeDatabase();
        }
        super.onStart();
    }


    private void sendUserToLoginActivity()
    {
        loginIntent=new Intent(MainActivity.this,LoginActivity.class);
        //User not back to mainActivity mean check validation login or register
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

    private void checkUserExistenceWithRealtimeDatabase()
    {
        final String currentuser_id=auth.getCurrentUser().getUid();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (!snapshot.hasChild(currentuser_id))
                {
                    //User register but record in database is not save

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}