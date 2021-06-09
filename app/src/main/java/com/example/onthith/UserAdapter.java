package com.example.onthith;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private Context context;
    private final ArrayList<User> array;
    private LayoutInflater inflater;

    public UserAdapter(Context context, ArrayList<User> array) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.array = array;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = array.get(position);
        holder.tvId.setText(user.getId());
        holder.tvName.setText(user.getName());
        holder.tvGender.setText(user.isGender() ? "Male" : "Female");
        holder.tvAge.setText(String.valueOf(user.getAge()));
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvId, tvName, tvGender, tvAge;
        private Button btnDelete, btnUpdate;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvId = itemView.findViewById(R.id.tvId);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvGender = itemView.findViewById(R.id.tvGender);
            this.tvAge = itemView.findViewById(R.id.tvAge);
            this.btnDelete = itemView.findViewById(R.id.btnDelete);
            this.btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete.setOnClickListener(this);
            btnUpdate.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnDelete:{
                    String url = "https://60ad9f9180a61f001733151f.mockapi.io/Users/" + array.get(getLayoutPosition()).getId();
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.DELETE, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Error by Post data!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);

                    Intent intent = new Intent(context, RecycleViewActivity.class);
                    context.startActivity(intent);
                    break;
                }case R.id.btnUpdate:{
                    User user = array.get(getLayoutPosition());

                    Intent intent = new Intent(context, UpdateUserActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putParcelable("user", user);
                    intent.putExtra("bundle", bundle);

                    ((Activity)context).startActivity(intent);
                    break;
                }
            }
        }
    }
}