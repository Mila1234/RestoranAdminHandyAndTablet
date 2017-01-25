package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityMainList;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.example.marijaradisavljevic.restoranadminmarija.servis.Servis;
import com.example.marijaradisavljevic.restoranadminmarija.spiner.MySpinnerAdapter;

/**
 * Created by marija on 24.1.17.
 */

public class Fragment_User_Info extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private EditText username;
    private EditText name ;
    private EditText surname ;
    private EditText number ;
    private EditText email;
    private EditText password;

    private Spinner type;

    private UserInfo currUI;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_user_info_layout,container, false);
        ///////////////////////////////////////////////////////////////////////
        type = (Spinner) mRoot.findViewById(R.id.typeSpiner);
        // value = getResources().getStringArray(R.array.kategory_array);
        ArrayAdapter<String> adapter_type = new MySpinnerAdapter(false,getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, Servis.getInstance().strignListTypeOFUsers() );

        // Specify the layout to use when the list of choices appears
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        type.setAdapter(adapter_type);
        type.setSelection(((MySpinnerAdapter)adapter_type).getStartPosition());



        ///////////////////////////////////////////////////////////////////////
        username = (EditText) mRoot.findViewById(R.id.username);
        name = (EditText) mRoot.findViewById(R.id.name);
        surname = (EditText) mRoot.findViewById(R.id.surname);
        number = (EditText) mRoot.findViewById(R.id.number);
        email = (EditText) mRoot.findViewById(R.id.email);
        password = (EditText) mRoot.findViewById(R.id.password);
        Button button_ok = (Button) mRoot.findViewById(R.id.ok_button);

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

                UserInfo newUI = new UserInfo();
                newUI.setUsername(username.getText().toString());
                newUI.setName(name.getText().toString());
                newUI.setSurname(surname.getText().toString());
                newUI.setNumber(number.getText().toString());
                newUI.setEmail(email.getText().toString());
                newUI.setPassword(password.getText().toString());
                newUI.setType((String)type.getSelectedItem());

                if(newUI.getUsername().length()==0 || newUI.getPassword().length()==0 ){
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.obavezniparametri), Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.snimljeno), Toast.LENGTH_LONG).show();

                Servis.getInstance().setUserInfo(newUI);

                Intent intent = new Intent(getActivity().getApplicationContext(), ActivityMainList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);
            }
        });

        return mRoot;
    }
}
