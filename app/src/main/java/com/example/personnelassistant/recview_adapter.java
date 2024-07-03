package com.example.personnelassistant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recview_adapter extends RecyclerView.Adapter<recview_adapter.viewholder>{
    ArrayList<model> data;

    public recview_adapter(ArrayList<model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.card_layout , parent , false );
        return new viewholder(view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.Header.setText(data.get(position).getHeader());
        holder.Desc.setText(data.get(position).getDesc());

    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView Header , Desc;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            Header = itemView.findViewById(R.id.header);
            Desc = itemView.findViewById(R.id.desc);
        }
    }
}
