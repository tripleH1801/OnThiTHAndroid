package com.example.onthith;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecycleViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private ArrayList<User> arrayList;

    private Button btnAdd;

    private final String url = "https://60ada21980a61f0017331577.mockapi.io/api/customers/customers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        recyclerView = findViewById(R.id.rcv_Main);
        arrayList = new ArrayList<User>();

        getListUser();

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecycleViewActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getListUser(){
        RequestQueue requestQueue = Volley.newRequestQueue(RecycleViewActivity.this);

        JsonArrayRequest request =
                new JsonArrayRequest(
                        url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                recyclerView.setVisibility(View.VISIBLE);
                                for(int i = 0; i < response.length(); i++){
                                    try {
                                        JSONObject object = (JSONObject)response.get(i);

                                        String id = object.getString("id");
                                        String name = object.getString("name");
                                        int age = object.getInt("age");
                                        boolean gender = object.getBoolean("gender");
                                        arrayList.add(new User(id, name, age, gender));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                buildRecyclerView();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RecycleViewActivity.this, "Error by get Json Array!", Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(request);
    }

    private void buildRecyclerView() {

        adapter = new UserAdapter(RecycleViewActivity.this, arrayList);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);
    }

}