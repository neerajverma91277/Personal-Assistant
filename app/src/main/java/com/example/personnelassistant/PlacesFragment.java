package com.example.personnelassistant;

import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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


public class PlacesFragment extends Fragment {
    private RecyclerView rec_view3;
    private ArrayList<model> data3;
    private DatabaseReference databaseReference3;
    private Button button_addPlaces;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PlacesFragment() {

    }

    public static PlacesFragment newInstance(String param1, String param2) {
        PlacesFragment fragment = new PlacesFragment();
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
        View view3 = inflater.inflate(R.layout.fragment_places, container, false);
        button_addPlaces = view3.findViewById(R.id.button_add_places);
        button_addPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("root" , "Places");
                Add_data_dailogFragment dialog_places = new Add_data_dailogFragment();
                dialog_places.setArguments(bundle);
                dialog_places.show(getFragmentManager() , "add_place");

            }
        });
        rec_view3 = view3.findViewById(R.id.rec_view3);
        rec_view3.setLayoutManager(new LinearLayoutManager(getContext()));
        data3 = new ArrayList<>();
        databaseReference3 = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Places");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data3.clear();
                for (DataSnapshot ds : snapshot.getChildren() ){
                    HashMap<String , String> map = (HashMap<String , String>)ds.getValue();
                    map.forEach( (key , value ) -> data3.add(new model(key , value)));
                }

                rec_view3.setAdapter(new recview_adapter(data3));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view3;
    }
}