package com.example.personnelassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInDialogFragment  extends DialogFragment {

    private Button sign_in_button;
    private EditText email_user;
    private EditText password_user;
    private FirebaseAuth mAuth;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_page,null);
        builder.setView(view);
        mAuth = FirebaseAuth.getInstance();
        email_user = view.findViewById(R.id.email_user);
        password_user = view.findViewById(R.id.password_user);
        sign_in_button = view.findViewById(R.id.sign_in);
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_user();
            }
        });
        return builder.create();
    }

    private void login_user() {
        String email = email_user.getText().toString();
        String password = password_user.getText().toString();
        if ( email.isEmpty() ){
            Toast.makeText(getActivity(),"Username field is Empty" , Toast.LENGTH_LONG).show();
        }
        else if (password.isEmpty()){
            Toast.makeText(getActivity(),"Password field is Empty" , Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if( !task.isSuccessful()){
                        Toast.makeText(getActivity(), "Username or password is wrong",Toast.LENGTH_LONG).show();
                    }
                    else {
                        startActivity(new Intent(getActivity() , Account.class));
                        getActivity().finish();
                    }
                }
            });
        }
    }

}

