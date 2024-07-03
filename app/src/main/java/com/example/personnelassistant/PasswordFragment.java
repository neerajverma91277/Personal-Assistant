package com.example.personnelassistant;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class PasswordFragment extends Fragment {
    private RecyclerView rec_view2;
    private ArrayList<model> data2;
    private DatabaseReference databaseReference2;
    private Button button_addPassword;
    String key;
    String value;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PasswordFragment() {

    }

    public static PasswordFragment newInstance(String param1, String param2) {
        PasswordFragment fragment = new PasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view2 = inflater.inflate(R.layout.fragment_password, container, false);
        button_addPassword = view2.findViewById(R.id.button_add_password);
        button_addPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("root" , "Passwords");
                Add_data_dailogFragment dialog_password = new Add_data_dailogFragment();
                dialog_password.setArguments(bundle);
                dialog_password.show(getFragmentManager() , "add_password");
            }
        });
        rec_view2 = view2.findViewById(R.id.rec_view2);
        rec_view2.setLayoutManager(new LinearLayoutManager(getContext()));
        data2 = new ArrayList<>();
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Passwords");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data2.clear();
                for ( DataSnapshot ds : snapshot.getChildren()){
                    HashMap<String , String> map = (HashMap<String, String>)ds.getValue();
                    map.forEach((key , value)  -> data2.add(new model(key , value)));
                }

                rec_view2.setAdapter(new recview_adapter(data2));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view2;
    }
}
