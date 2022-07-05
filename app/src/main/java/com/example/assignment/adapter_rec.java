package com.example.assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter_rec extends RecyclerView.Adapter<adapter_rec.ViewHolder> {

    private final List<model> modelList;

    public adapter_rec(List<model> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            holder.name.setText(modelList.get(position).getName());
            holder.username.setText(modelList.get(position).getUsername());
            holder.email.setText(modelList.get(position).getEmail());
            holder.street.setText(modelList.get(position).getStreet());
            holder.city.setText(modelList.get(position).getCity());
            holder.zipcode.setText(modelList.get(position).getZipcode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

            TextView name, username, email, street, city, zipcode;
        public ViewHolder(@NonNull View itemView) {
                super(itemView);
            try {
                name = itemView.findViewById(R.id.name);
                username = itemView.findViewById(R.id.username);
                email = itemView.findViewById(R.id.email);
                street = itemView.findViewById(R.id.street);
                city = itemView.findViewById(R.id.city);
                zipcode = itemView.findViewById(R.id.zipcode);
            }catch (Exception e){
                e.printStackTrace();
            }
        }//ViewHolder
        }//extends
}//main
