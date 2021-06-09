package com.example.onthith;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddUserActivity extends AppCompatActivity {

    private EditText txtName, txtAge;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnAddUser;

    private final String url = "https://60ada21980a61f0017331577.mockapi.io/api/customers/customers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        txtName = findViewById(R.id.txtName);
        txtAge = findViewById(R.id.txtAge);
        radioGroup = findViewById(R.id.radioGroup);
        btnAddUser = findViewById(R.id.btnAddUser);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String strAge = txtAge.getText().toString();
                int age = 0;
                if (name.isEmpty()) {
                    txtName.setError("Enter name");
                    txtName.requestFocus();
                }
                if (strAge.isEmpty()) {
                    txtAge.setError("Enter age");
                    txtAge.requestFocus();
                }
                try {
                    age = Integer.parseInt(strAge);
                }catch (Exception exception){
                    txtAge.setError("Age must be numbers");
                    txtAge.requestFocus();
                }

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);

                boolean gender = radioButton.getText().equals("Male") ? true : false;

                JSONObject js = new JSONObject();
                try {
                    js.put("id", 0);
                    js.put("name", name);
                    js.put("age", age);
                    js.put("gender", gender);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                RequestQueue requestQueue = Volley.newRequestQueue(AddUserActivity.this);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, js, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        Toast.makeText(AddUserActivity.this, "Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddUserActivity.this, RecycleViewActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("TAG", "Error: " + error.getMessage());
                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }

                };
                requestQueue.add(request);

            }
        });
    }

}