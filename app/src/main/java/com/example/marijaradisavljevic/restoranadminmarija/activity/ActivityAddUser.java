package com.example.marijaradisavljevic.restoranadminmarija.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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
 * Created by marija.radisavljevic on 6/9/2016.
 */
public class ActivityAddUser  extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{
    public static final String ARG_ITEM_ID = "item_id";
    private EditText username;
    private EditText name ;
    private EditText surname ;
    private EditText number ;
    private EditText email;
    private EditText password;

    private Spinner type;

    private Bundle extras;





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
        toolbar.setTitle(getResources().getString(R.string.Logo_description));
        toolbar.setSubtitle(Servis.getInstance().toolBarTypeNameSurnameString());

////////////////////spinner//////////////////////////
        type = (Spinner) findViewById(R.id.typeSpiner);
        // value = getResources().getStringArray(R.array.kategory_array);
        final ArrayAdapter<String> adapter_type = new MySpinnerAdapter(false,getBaseContext(),
                android.R.layout.simple_spinner_item,Servis.getInstance().strignListTypeOFUsers() );

        // Specify the layout to use when the list of choices appears
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        type.setAdapter(adapter_type);
        type.setSelection(((MySpinnerAdapter)adapter_type).getStartPosition());
        type.setOnItemSelectedListener(this);

 //////////////////////text filds/////////////////////////////////////////////////

        username = (EditText) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        number = (EditText) findViewById(R.id.number);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        final Button button_ok = (Button) findViewById(R.id.ok_button);

        if (extras!= null) {//edit user
            String usernameString = extras.getString("username");
            String passordSrting = extras.getString("password");
            UserInfo ui = Servis.getInstance().getUserInfofromList(usernameString, passordSrting);
            username.setText(ui.getUsername());
            name.setText(ui.getName());
            surname.setText(ui.getSurname());
            number.setText(ui.getNumber());
            email.setText(ui.getEmail());
            password.setText(ui.getPassword());
            int position = adapter_type.getPosition(ui.getType());
            type.setSelection(position);
        }


        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Toast.makeText(getApplicationContext(), getString(R.string.snimljeno), Toast.LENGTH_LONG).show();
                button_ok.setEnabled(false);
                UserInfo ui = new UserInfo();
                ui.setUsername(username.getText().toString());
                ui.setName(name.getText().toString());
                ui.setSurname(surname.getText().toString());
                ui.setNumber(number.getText().toString());
                ui.setEmail(email.getText().toString());
                ui.setPassword(password.getText().toString());
                ui.setType((String) type.getSelectedItem());

                if(ui.getUsername().length()==0 || ui.getPassword().length()==0 || type.getSelectedItemPosition()==((MySpinnerAdapter)adapter_type).getStartPosition()){
                    Toast.makeText(getApplicationContext(), getString(R.string.obavezniparametri), Toast.LENGTH_LONG).show();
                    return;
                }

                if (extras!= null) {//edit user
                    String usernameString = extras.getString("username");
                    String passwordString = extras.getString("password");
                   Servis.getInstance().updateUserInfoFromList(ui, usernameString,passwordString);
                }else{
                    Servis.getInstance().makeuserinfoIntoList(ui);
                }


                Intent intent = new Intent(getApplicationContext(), ActivityMainList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);

                button_ok.setEnabled(true);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.typeSpiner:
                if(position == 0 ){

                }else {
                     }
                break;

            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
