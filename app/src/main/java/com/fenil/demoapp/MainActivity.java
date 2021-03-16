package com.fenil.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CALL_PERMISSION_CODE = 1;
    Button youtube;
    Button instagram;
    Button whatsapp;
    Button twitter;
    Button call;
    EditText phoneNumber;
    String number;
    PackageManager packageManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youtube = findViewById(R.id.youtube);
        instagram = findViewById(R.id.instagram);
        whatsapp = findViewById(R.id.whatsapp);
        twitter = findViewById(R.id.twitter);
        call = findViewById(R.id.call);
        phoneNumber = findViewById(R.id.phone_number);
        packageManager = getApplicationContext().getPackageManager();

        youtube.setOnClickListener(new View.OnClickListener() {
            Intent youtubeIntent;
            @Override
            public void onClick(View v) {
                try{
                    youtubeIntent = packageManager.getLaunchIntentForPackage("com.google.android.youtube");
                    startActivity(youtubeIntent);
                }
                catch (NullPointerException e)
                {
                    youtubeIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com"));
                    startActivity(youtubeIntent);
                }

            }
        });

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

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = phoneNumber.getText().toString();
                if(number.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please enter phone number",Toast.LENGTH_SHORT).show();
                }
                else
                {
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
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);
            }
            else {
                Toast.makeText(MainActivity.this, "Call Permission Denied", Toast.LENGTH_SHORT).show();
            }
    }
}