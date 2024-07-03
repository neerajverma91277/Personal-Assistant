package com.example.personnelassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button sign_in;
    private Button register;
    private FirebaseAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_in = (Button)findViewById(R.id.sign_in_login);
        register = (Button)findViewById(R.id.register_login);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , Register_user.class));
            }
        });
        myAuth = FirebaseAuth.getInstance();
        FirebaseUser User = myAuth.getCurrentUser();
        if ( User!= null){
            Intent intent = new Intent(this , Account.class);
            startActivity(intent);
            finish();
        }
    }

    private void openDialog() {
        SignInDialogFragment Fragment = new SignInDialogFragment();
        Fragment.show(getSupportFragmentManager(),"Sign_in");
    }
}