package com.example.personnelassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_user extends AppCompatActivity {
    private EditText register_email;
    private EditText register_password;
    private EditText register_ConfirmPassword;
    private EditText register_name;
    private EditText register_phone;
    private Button register_button;
    private FirebaseAuth auth;
    private DatabaseReference data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        auth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance().getReference();

        register_button = findViewById(R.id.Register_button);
        register_name = findViewById(R.id.User_Name);
        register_ConfirmPassword = findViewById(R.id.User_ConfirmPassword);
        register_email = findViewById(R.id.User_emailAddress);
        register_password = findViewById(R.id.User_password);
        register_phone = findViewById(R.id.User_phone);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = register_email.getText().toString();
                String Password = register_password.getText().toString();
                String ConfirmPassword = register_ConfirmPassword.getText().toString();
                String name = register_name.getText().toString();
                String phone = register_phone.getText().toString();
                if ( !Password.isEmpty() && !Email.isEmpty() && !name.isEmpty() && !phone.isEmpty()) {
                    if (Password.equals(ConfirmPassword)) {
                        if (Password.length() > 5) {
                            auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(Register_user.this , "User Created" , Toast.LENGTH_LONG).show();
                                    User user = new User( name , Email , phone );
                                    data.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("User_profile").setValue(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(Register_user.this , "Registration Successful" , Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(Register_user.this , Account.class));
                                                        finish();
                                                    }
                                                    else{
                                                        Toast.makeText(Register_user.this , "Database not Created" , Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                        else{
                            Toast.makeText(Register_user.this , "Length of password can't be less than 6" , Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(Register_user.this , "Password and Confirm password are not same" , Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(Register_user.this , "No fields can be EMPTY!!" , Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}