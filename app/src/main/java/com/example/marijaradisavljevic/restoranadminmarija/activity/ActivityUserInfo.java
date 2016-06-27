package com.example.marijaradisavljevic.restoranadminmarija.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.example.marijaradisavljevic.restoranadminmarija.servis.Servis;
import com.example.marijaradisavljevic.restoranadminmarija.spiner.MySpinnerAdapter;

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

    private Spinner type;

    private UserInfo currUI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_user_info_layout);

        //setTitle(R.string.fragmentUserInfo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setNavigationIcon(R.drawable.back);
        //toolbar.setNavigationContentDescription(getResources().getString(R.string.nameOfApp));
        // toolbar.setLogo(R.drawable.help);
        toolbar.setTitle(getResources().getString(R.string.Logo_description));
        toolbar.setSubtitle(Servis.getInstance().toolBarTypeNameSurnameString());
        ///////////////////////////////////////////////////////////////////////
        type = (Spinner) findViewById(R.id.typeSpiner);
        // value = getResources().getStringArray(R.array.kategory_array);
        ArrayAdapter<String> adapter_type = new MySpinnerAdapter(false,getBaseContext(),
                android.R.layout.simple_spinner_item,Servis.getInstance().strignListTypeOFUsers() );

        // Specify the layout to use when the list of choices appears
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        type.setAdapter(adapter_type);
        type.setSelection(((MySpinnerAdapter)adapter_type).getStartPosition());



        ///////////////////////////////////////////////////////////////////////
        username = (EditText) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        number = (EditText) findViewById(R.id.number);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        Button button_ok = (Button) findViewById(R.id.ok_button);

        currUI =  Servis.getInstance().getUserInfo();

        username.setText(currUI.getUsername());
        name.setText(currUI.getName());
        surname.setText(currUI.getSurname());
        number.setText(currUI.getNumber());
        email.setText(currUI.getEmail());
        password.setText(currUI.getPassword());

        int position = adapter_type.getPosition(currUI.getType());
        type.setSelection(position);

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), " Snimljeno ", Toast.LENGTH_LONG).show();

                UserInfo newUI = new UserInfo();
                newUI.setUsername(username.getText().toString());
                newUI.setName(name.getText().toString());
                newUI.setSurname(surname.getText().toString());
                newUI.setNumber(number.getText().toString());
                newUI.setEmail(email.getText().toString());
                newUI.setPassword(password.getText().toString());
                newUI.setType((String)type.getSelectedItem());

                Servis.getInstance().setUserInfo(newUI);

                Intent intent = new Intent(getApplicationContext(), ActivityMainList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });



    }




}
