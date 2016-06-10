package com.example.marijaradisavljevic.restoranadminmarija.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.data.UserData;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.example.marijaradisavljevic.restoranadminmarija.servis.Servis;

/**
 * Created by marija.radisavljevic on 6/10/2016.
 */
public class ActivityUserInfo extends AppCompatActivity {
    private EditText username;
    private EditText name ;
    private EditText surname ;
    private EditText number ;
    private EditText email;
    private EditText password;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manu_gui, menu);
        return true;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_user_info_layout);

        setTitle(R.string.fragmentUserInfo);

        ///////////////////////////////////////////////////////////////////////
        username = (EditText) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        number = (EditText) findViewById(R.id.number);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        Button button_ok = (Button) findViewById(R.id.ok_button);

        UserInfo ui =  Servis.getInstance().getUserInfo(UserData.getInstance().getUsername(), UserData.getInstance().getPassword());

        username.setText(ui.getUsername());
        name.setText(ui.getName());
        surname.setText(ui.getSurname());
        number.setText(ui.getNumber());
        email.setText(ui.getEmail());
        password.setText(ui.getPassword());

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), " Snimljeno ", Toast.LENGTH_LONG).show();

                UserInfo ui = new UserInfo();
                ui.setUsername(username.getText().toString());
                ui.setName(name.getText().toString());
                ui.setSurname(surname.getText().toString());
                ui.setNumber(number.getText().toString());
                ui.setEmail(email.getText().toString());
                Servis.getInstance().setUserInfo(ui);

                UserData.getInstance().setUsername(ui.getUsername());
                //  UserData.getInstance().setUsername(ui.getPasseord());

                Intent intent = new Intent(getApplicationContext(), ActivityMainList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });



    }




}
