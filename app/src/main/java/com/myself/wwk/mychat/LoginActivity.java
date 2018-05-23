package com.myself.wwk.mychat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // SignIn Button
    public void signInExistingUser(View v){

    }

    // Register new user Button
    public void registerNewUser(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        finish();
        startActivity(intent);
    }

}
