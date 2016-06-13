package com.example.marijaradisavljevic.restoranadminmarija.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
 * Created by marija.radisavljevic on 6/9/2016.
 */
public class ActivityAddUser  extends AppCompatActivity {
    private EditText username;
    private EditText name ;
    private EditText surname ;
    private EditText number ;
    private EditText email;
    private EditText password;

    Bundle extras;
    String  usernameString;
    String passordSrting;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.manu_gui, menu);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_info_layout);
         extras = getIntent().getExtras();
        if(extras != null){
           // setTitle(R.string.title_edtiUsername);
        }else{
            //setTitle(R.string.fragmentUserInfo);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setNavigationIcon(R.drawable.back);
        //toolbar.setNavigationContentDescription(getResources().getString(R.string.nameOfApp));
        // toolbar.setLogo(R.drawable.help);
        toolbar.setLogoDescription(getResources().getString(R.string.Logo_description));


        ///////////////////////////////////////////////////////////////////////
        username = (EditText) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        number = (EditText) findViewById(R.id.number);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        Button button_ok = (Button) findViewById(R.id.ok_button);

        if (extras!= null) {
            String usernameString = extras.getString("username");
            String passordSrting = extras.getString("password");
            UserInfo ui = Servis.getInstance().getUserInfo(usernameString, passordSrting);
            username.setText(ui.getUsername());
            name.setText(ui.getName());
            surname.setText(ui.getSurname());
            number.setText(ui.getNumber());
            email.setText(ui.getEmail());
            password.setText(ui.getPassword());
        }


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
                ui.setPassword(password.getText().toString());

                if (extras!= null) {
                    String usernameString = extras.getString("username");
                    String passordSrting = extras.getString("password");
                   Servis.getInstance().updateUserame (ui, usernameString,passordSrting);
                }else{
                    Servis.getInstance().makeuser(ui);
                }


                Intent intent = new Intent(getApplicationContext(), ActivityMainList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

    }
}
