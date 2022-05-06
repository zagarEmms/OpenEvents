package com.example.openevents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.openevents.api.APIClient;
import com.example.openevents.business.Event;
import com.example.openevents.business.Token;
import com.example.openevents.business.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create_Event_Activity extends AppCompatActivity {

    private ImageView imageUpload;
    private Button createButton;
    private EditText title;
    private EditText description;
    private EditText category;
    private EditText startDate;
    private EditText startTime;
    private EditText endDate;
    private EditText endTime;
    private EditText location;
    private String image;
    private int n_participators;
    private String eventStart_date;
    private String eventEnd_date;

    public void changeActivity () {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void connectApi () {

        /*Event event = new Event();
        Token token = new Token();

        APIClient.getInstance().createEvent(token, event, new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (response.body() == null) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "INCORRECT SIGNUP!", Toast.LENGTH_SHORT);

                    toast1.setGravity(Gravity.TOP, 0, 0);
                    toast1.show();
                } else {
                    Log.i("GET","GET WENT WELL!\n" + response.body().getName());
                    changeActivity();
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.i("GET","KO!");
                Toast toast2 =
                        Toast.makeText(getApplicationContext(),
                                "CONNECTION ERROR!", Toast.LENGTH_SHORT);

                toast2.setGravity(Gravity.TOP, 0, 0);
                toast2.show();
            }
        });
*/
    }

    public void setImageUpload () {

        imageUpload = (ImageView) findViewById(R.id.imageUpload);
        imageUpload.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   //startActivityForResult();

               }
           }
        );
    }

    public void setButton () {

        createButton = (Button) findViewById(R.id.createEventButton);
        createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    connectApi();

                }
            }
        );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        setImageUpload();
        setButton();


    }
}