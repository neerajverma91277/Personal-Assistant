package com.example.personnelassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Add_data_dailogFragment  extends DialogFragment {

    private Button add_data;
    private EditText editText_header;
    private EditText editText_desc;
    DatabaseReference databaseReference_add;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_data_dialog , container , false );
        editText_header =view.findViewById(R.id.text_header);
        editText_desc = view.findViewById(R.id.text_desc);
        add_data = view.findViewById(R.id.add_data);
        Bundle bundle = this.getArguments();
        String root = bundle.getString("root");
        switch(root){
            case "Places":
                editText_header.setHint("Enter name of place");
                editText_desc.setHint("Enter address of place");
                add_data.setText(R.string.add_places);
                break;
            case "Passwords":
                editText_header.setHint("Enter context of password");
                editText_desc.setHint("Enter your password");
                add_data.setText(R.string.add_password);
                break;
            case "Contacts" :
                editText_header.setHint("Enter person name");
                editText_desc.setHint("Enter Contact no.");
                add_data.setText(R.string.add_contacts);
                break;
        }
        HashMap<String , String> map1 = new HashMap<>();

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference_add = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(root);
                map1.put(editText_header.getText().toString() , editText_desc.getText().toString());
                databaseReference_add.push().setValue(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if( task.isSuccessful() ){
                            Toast.makeText(getContext() , "data added" , Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getContext() , "error occurred!!" , Toast.LENGTH_LONG).show();
                        }
                        getDialog().dismiss();
                    }
                });

            }
        });

        return  view;
    }
}
