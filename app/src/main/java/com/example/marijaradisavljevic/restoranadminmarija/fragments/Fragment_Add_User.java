package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityMainList;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;
import com.example.marijaradisavljevic.restoranadminmarija.spiner.MySpinnerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

/**
 * Created by marija on 24.1.17.
 */

public class Fragment_Add_User extends Fragment  implements  AdapterView.OnItemSelectedListener {
    public static final String ARG_ITEM_ID = "item_id";

    private EditText username;
    private EditText name ;
    private EditText surname ;
    private EditText number ;
    private EditText email;
    private EditText password;

    private Spinner type;



    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
            //mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_user_info_layout,container, false);

        getActivity().setTitle("Dodaj korisnika");
        ////////////////////spinner//////////////////////////
        type = (Spinner) mRoot.findViewById(R.id.typeSpiner);
        // value = getResources().getStringArray(R.array.kategory_array);
        final ArrayAdapter<String> adapter_type = new MySpinnerAdapter(false,getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, FireBase.getInstance().strignListTypeOFUsers() );

        // Specify the layout to use when the list of choices appears
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        type.setAdapter(adapter_type);
        type.setSelection(((MySpinnerAdapter)adapter_type).getStartPosition());
        type.setOnItemSelectedListener(this);

        //////////////////////text filds/////////////////////////////////////////////////

        username = (EditText) mRoot.findViewById(R.id.username);
        name = (EditText) mRoot.findViewById(R.id.name);
        surname = (EditText) mRoot.findViewById(R.id.surname);
        number = (EditText) mRoot.findViewById(R.id.number);
        email = (EditText) mRoot.findViewById(R.id.email);
        password = (EditText) mRoot.findViewById(R.id.password);
        password.setEnabled(true);
        final Button button_ok = (Button) mRoot.findViewById(R.id.ok_button);
        String usernameStringw;


        if (getActivity().getIntent().getExtras()!=null && getActivity().getIntent().getExtras().getString("username")!=null ) {//edit user
            String usernameString = getActivity().getIntent().getExtras().getString("username");
            String passordSrting = getActivity().getIntent().getExtras().getString("password");
            String key = getActivity().getIntent().getExtras().getString("keyForFireBase");

            showProgressDialog();
            //UserInfo ui = FireBase.getInstance().getUserInfofromList(usernameString, passordSrting);
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInfo ui = dataSnapshot.getValue(UserInfo.class);
                    username.setText(ui.getUsername());
                    name.setText(ui.getName());
                    surname.setText(ui.getSurname());
                    number.setText(ui.getNumber());
                    email.setText(ui.getEmail());
                    if (FireBase.getInstance().getUserInfo().getUsername().equals(ui.getUsername())){
                        password.setText(ui.getPassword());
                        password.setEnabled(true);
                    }else{
                        password.setEnabled(false);
                        password.setText(ui.getPassword());
                    }

                    int position = adapter_type.getPosition(ui.getType());
                    type.setSelection(position);
                    hideProgressDialog();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }else{
            if (!getArguments().isEmpty() && getArguments().getString("username")!=null){
                String usernameString = getArguments().getString("username");
                String passordSrting = getArguments().getString("password");
                String key = getArguments().getString("keyForFireBase");
                //UserInfo ui = FireBase.getInstance().getUserInfofromList(usernameString, passordSrting);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                showProgressDialog();
                mDatabase.child("users").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserInfo ui = dataSnapshot.getValue(UserInfo.class);
                        username.setText(ui.getUsername());
                        name.setText(ui.getName());
                        surname.setText(ui.getSurname());
                        number.setText(ui.getNumber());
                        email.setText(ui.getEmail());
                        if (FireBase.getInstance().getUserInfo().getUsername().equals(ui.getUsername())){
                            password.setText(ui.getPassword());
                            password.setEnabled(true);
                        }else{
                            password.setEnabled(false);
                            password.setText(ui.getPassword());
                        }

                        int position = adapter_type.getPosition(ui.getType());
                        type.setSelection(position);
                        hideProgressDialog();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }


        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.snimljeno), Toast.LENGTH_LONG).show();
                button_ok.setEnabled(false);
                final UserInfo ui = new UserInfo();
                ui.setUsername(username.getText().toString());
                ui.setName(name.getText().toString());
                ui.setSurname(surname.getText().toString());
                ui.setNumber(number.getText().toString());
                ui.setEmail(email.getText().toString());
                ui.setPassword(password.getText().toString());
                ui.setType((String) type.getSelectedItem());

                if(ui.getUsername().length()==0 || ui.getPassword().length()==0 ){
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.obavezniparametri), Toast.LENGTH_LONG).show();
                    return;
                }
                if (getActivity().getIntent().getExtras()!=null && getActivity().getIntent().getExtras().getString("username")!=null) {//edit user
                    String usernameString = getActivity().getIntent().getExtras().getString("username");
                    String passwordString = getActivity().getIntent().getExtras().getString("password");
                    String key = getActivity().getIntent().getExtras().getString("keyForFireBase");
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("users").child(key).setValue(ui);
                   // FireBase.getInstance().updateUserInfoFromList(ui, usernameString,passwordString);
                }else{
                    if (!getArguments().isEmpty() && getArguments().getString("username")!=null){
                        String usernameString = getArguments().getString("username");
                        String passwordString = getArguments().getString("password");
                        String key = getArguments().getString("keyForFireBase");
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                        mDatabase.child("users").child(key).setValue(ui);
                        //FireBase.getInstance().updateUserInfoFromList(ui, usernameString,passwordString);
                    }else{
                        //FireBase.getInstance().makeuserinfoIntoList(ui);
                        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(ui.getEmail()  , ui.getPassword())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());


                                        if (task.isSuccessful()) {
                                            String key = mDatabase.child("users").push().getKey();
                                            mDatabase.child("users").child(key).setValue(ui);
                                        } else {
                                            Log.d(TAG, "createUser:onComplete:" + task.getException().getMessage());
                                        }
                                    }
                                });


                    }

                }


                Intent intent = new Intent(getActivity().getApplicationContext(), ActivityMainList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);

                button_ok.setEnabled(true);
            }
        });

        return mRoot;
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
