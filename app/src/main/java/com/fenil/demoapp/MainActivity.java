package com.fenil.demoapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    //This variable is a request code to get calling permission from Android system.
    private static final int CALL_PERMISSION_CODE = 1;

    //Declaring the variables that link with the user interface components
    Button youtube;
    Button instagram;
    Button whatsapp;
    Button twitter;
    Button call;
    EditText phoneNumber;

    //We store the input phone number provided by user in this variable
    String number;

    PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Links the UI of MainActivity to resource file activity_main.xml

        youtube = findViewById(R.id.youtube);
        instagram = findViewById(R.id.instagram);
        whatsapp = findViewById(R.id.whatsapp);
        twitter = findViewById(R.id.twitter);
        call = findViewById(R.id.call);
        phoneNumber = findViewById(R.id.phone_number);
        packageManager = getApplicationContext().getPackageManager();

        // Attaching an onClickListener to Youtube button for handling touch/click events
        youtube.setOnClickListener(new View.OnClickListener() {
            Intent youtubeIntent;
            @Override
            public void onClick(View v) {

                /* We are using a try-catch block here. A try-catch block helps in execution of code that may produce error.
                * If errors are not handled, our app may crash.
                * Here in try block, we first check if the app user is trying to open is installed on the phone.
                * If the target app is not installed, a NullPointerException occurs. We handle this exception in the catch block*/

                try{
                    /* We pass the package name of target app as an argument of getLaunchIntentForPackage().
                    * If the app is installed, getLaunchIntentForPackage() will return a non-null intent.
                    * We store the returned intent in a local variable and pass it to startActivity() as argument.
                    * By calling startActivity(), the target app will be started through intent without any errors.*/
                    youtubeIntent = packageManager.getLaunchIntentForPackage("com.google.android.youtube");
                    startActivity(youtubeIntent);
                }
                catch (NullPointerException e)
                {
                    /* In the catch block, we are going to handle scenario where target app is not installed on the phone.
                    * We are going to show the target app's respective website to the user in a browser.
                    * First we parse the URL of the website as a Uri. We call Uri.parse() method and pass the string URL as argument
                    * We set the mode of our intent as Intent.ACTION_VIEW. This mode is used when we want to show user some data outside of our app.
                    * When user clicks on the button, Android will show a list of compatible apps that can display the data we parsed as Uri.
                    * As we are giving argument a website URL, Android will display a list of browsers.*/
                    youtubeIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com"));
                    startActivity(youtubeIntent);
                }

            }
        });

        // Attaching an onClickListener to Instagram button for handling touch/click events
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instagramIntent;
                try {
                    instagramIntent = packageManager.getLaunchIntentForPackage("com.instagram.android");
                    startActivity(instagramIntent);
                }
                catch (NullPointerException e)
                {
                    instagramIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com"));
                    startActivity(instagramIntent);
                }

            }
        });

        // Attaching an onClickListener to Whatsapp button for handling touch/click events
        whatsapp.setOnClickListener(new View.OnClickListener() {
            Intent whatsappIntent;
            @Override
            public void onClick(View v) {
                try{
                    whatsappIntent = packageManager.getLaunchIntentForPackage("com.whatsapp");
                    startActivity(whatsappIntent);
                }
                catch (NullPointerException e)
                {
                    whatsappIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.whatsapp.com/"));
                    startActivity(whatsappIntent);
                }

            }
        });

        // Attaching an onClickListener to Twitter button for handling touch/click events
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterIntent;
                try{
                     twitterIntent = packageManager.getLaunchIntentForPackage("com.twitter.android");
                    startActivity(twitterIntent);
                }
                catch (NullPointerException e)
                {
                    twitterIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.twitter.com/"));
                    startActivity(twitterIntent);
                }

            }
        });

        // Attaching an onClickListener to Call button for handling touch/click events
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Stores the input phone number obtained from EditText.
                number = phoneNumber.getText().toString();

                //Condition to check if any input was provided
                if(number.isEmpty())
                {
                    //Display toast asking user to enter phone number before calling.
                    phoneNumber.setError("Please enter phone number");
                    phoneNumber.requestFocus();
                }
                else
                {
                    /* Case where user has provided input\
                    * The calling permission is a dangerous permission.
                    * Dangerous permissions need consent of user at runtime.
                    * Here we check if calling permission is granted or not by the user*/
                    checkPermission(Manifest.permission.CALL_PHONE, CALL_PERMISSION_CODE);
                }
            }
        });
    }

    public void checkPermission(String permission, int callPermissionCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED)
        {
            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, callPermissionCode);
        }
        else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MainActivity.this, "Call Permission Granted", Toast.LENGTH_SHORT).show();

                // Intent.ACTION_CALL indicates calling mode of the intent
                Intent callIntent = new Intent(Intent.ACTION_CALL);

                //We parse the phone number in a format recognized by calling intent
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);
            }
            else {
                Toast.makeText(MainActivity.this, "Call Permission Denied", Toast.LENGTH_SHORT).show();
            }
    }
}