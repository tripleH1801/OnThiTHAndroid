package com.example.onthith;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtEmail, txtPassword;
    private Button btnLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        if(email.isEmpty()){
            txtEmail.setError("Không được để trống");
            txtEmail.requestFocus();
        }
        if(password.isEmpty()){
            txtPassword.setError("Không được để trống");
            txtPassword.requestFocus();
        }
        progressBar.setVisibility(View.VISIBLE);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Success!!!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, RecycleViewActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}