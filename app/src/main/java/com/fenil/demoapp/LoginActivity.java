package com.fenil.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //Declaring the variables that link with the user interface components
    EditText username;
    EditText password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //Links the UI of LoginActivity to resource file activity_login.xml

        //findViewById() function is used to find components in resource file. We pass the integer ID of component as argument
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        //setOnClickListener() function is used to implement actions that we need to perform when user clicks on a component like button, text view etc.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //We extract the string value of data which the user gave as input in username and password fields.
                String inputUsername = username.getText().toString();
                String inputPassword = password.getText().toString();

                //If either of username and password field is empty, we display a toast message to fill all values before proceeding to login
                if(inputUsername.isEmpty() || inputPassword.isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"Please fill all the values",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //This is an example of implicit intent. We use implicit intents to navigate between different activities
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}