package com.example.marijaradisavljevic.restoranadminmarija.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.marijaradisavljevic.restoranadminmarija.R;

import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_Add_New_Item_Rezerv;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_Log_Out;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_User_Info;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.FreagmentAddOrder;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;


/**
 * Created by marija.radisavljevic on 5/16/2016.
 */
public class ActivityHost extends AppCompatActivity {

    private static boolean firstTime = true;
//    private Fragment fragment;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_fragment_layout);

      toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.setNavigationIcon(R.drawable.back);
        //toolbar.setNavigationContentDescription(getResources().getString(R.string.nameOfApp));
       // toolbar.setLogo(R.drawable.help);
        toolbar.setLogoDescription(getResources().getString(R.string.Logo_description));

        setTitle("Restoran");
      //  getSupportActionBar().hide();
        Bundle extras = getIntent().getExtras();
        String fragmetnName;

        if (extras != null) {
            fragmetnName = extras.getString("name");
            Log.d("extras : ",extras.toString());
            if(fragmetnName !=null && fragmetnName.equals("FreagmentLogOut")) {
                toolbar.setSubtitle(FireBase.getInstance().toolBarTypeNameSurnameString());
                FragmentManager fm = getSupportFragmentManager();
                Fragment_Log_Out fragmentUserInfo = Fragment_Log_Out.getInstance();
                fm.beginTransaction().replace(R.id.container_menu, fragmentUserInfo).commit();
            }else  if(fragmetnName !=null && fragmetnName.equals("FragmentUserInfo")) {
                toolbar.setSubtitle(FireBase.getInstance().toolBarTypeNameSurnameString());
                FragmentManager fm = getSupportFragmentManager();
                Fragment_User_Info fragmentUserInfo = Fragment_User_Info.getInstance();
                fm.beginTransaction().replace(R.id.container_menu, fragmentUserInfo).commit();
            }else if(fragmetnName !=null &&  fragmetnName.equals("FreagmentAddOrder")){
                toolbar.setSubtitle(FireBase.getInstance().toolBarTypeNameSurnameString());
                String action = extras.getString("action");
                if (action !=null &&  action.equals("plusbutton")){
                    FragmentManager fm = getSupportFragmentManager();
                    FreagmentAddOrder freagmentAddOrder = FreagmentAddOrder.getInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("action","plusbutton");
                    freagmentAddOrder.setArguments(bundle);
                    fm.beginTransaction().replace(R.id.container_menu, freagmentAddOrder).commit();
                }else{
                    if (action !=null &&  action.equals("onclick")){
                        FragmentManager fm = getSupportFragmentManager();
                        FreagmentAddOrder freagmentAddOrder = FreagmentAddOrder.getInstance();
                        Bundle bundle = new Bundle();
                        bundle.putString("rezervationId",extras.getString("rezervationId"));
                        bundle.putString("action","onclick");
                        freagmentAddOrder.setArguments(bundle);
                        fm.beginTransaction().replace(R.id.container_menu, freagmentAddOrder).commit();
                    }else{
                       /* FragmentManager fm = getSupportFragmentManager();
                        FragmentLogin fragmentLogin = FragmentLogin.getInstance();
                        fm.beginTransaction().replace(R.id.container_menu, fragmentLogin).commit();*/
                        Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent2);
                    }
                }

            }else if (fragmetnName !=null && fragmetnName.equals("FragmentUserInfo")) {

            }else{
               /* FragmentManager fm = getSupportFragmentManager();
                FragmentLogin fragmentLogin = FragmentLogin.getInstance();
                fm.beginTransaction().replace(R.id.container_menu, fragmentLogin).commit();*/
                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent2);
            }
        }else
        {
            /*FragmentManager fm = getSupportFragmentManager();
            FragmentLogin fragmentLogin = FragmentLogin.getInstance();
            fm.beginTransaction().replace(R.id.container_menu, fragmentLogin).commit();*/
            Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent2);

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manu_gui, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_user_info:
              /*  Intent intent = new Intent(getApplicationContext(), ActivityHost.class);
                intent.putExtra("name","FragmentUserInfo");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);*/

                callFragmentUserInfo();
                return true;
            case R.id.action_logout:
                //call popup win for logout
            //    intent = new Intent(getApplicationContext(), Activity_Log_Out.class);
               // //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // getApplicationContext().startActivity(intent);

                callFragmentLogOut();
                return true;
            case R.id.action_add:

              /*  Intent intent2 = new Intent(getApplicationContext(), ActivityHost.class);
                intent2.putExtra("name", "FreagmentAddOrder");
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent2);
                */
                callAddOrder("bla","bla");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


    public void callFragmentLogIn(){
        //FragmentManager fm = getSupportFragmentManager();
        //FragmentLogin fragmentLogin = FragmentLogin.getInstance();
        //fm.beginTransaction().replace(R.id.container_menu, fragmentLogin).commit();
    }

    public void callFragmentLogOut(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment_Log_Out fragmentLogout = Fragment_Log_Out.getInstance();
        fm.beginTransaction().replace(R.id.container_menu, fragmentLogout).commit();
    }

    public void callFragmentAddMenuItem(String key, String value){
        FragmentManager fm = getSupportFragmentManager();
        Fragment_Add_New_Item_Rezerv fragment = new Fragment_Add_New_Item_Rezerv();
        Bundle arguments = new Bundle();
        arguments.putString(key, value);
        fragment.setArguments(arguments);
        fm.beginTransaction().replace(R.id.container_menu, fragment).commit();
    }

    //action == onCLick treba i drugi parametart rezervationId
    //action == plusButton ne gleda drugi parametar

    public void callAddOrder(String action,String rezervationId){
        toolbar.setSubtitle(FireBase.getInstance().toolBarTypeNameSurnameString());

        if (action !=null &&  action.equals("plusbutton")){
            FragmentManager fm = getSupportFragmentManager();
            FreagmentAddOrder freagmentAddOrder = FreagmentAddOrder.getInstance();
            Bundle bundle = new Bundle();
            bundle.putString("action","plusbutton");
            freagmentAddOrder.setArguments(bundle);
            fm.beginTransaction().replace(R.id.container_menu, freagmentAddOrder).commit();
        }else {
            if (action != null && action.equals("onclick")) {
                FragmentManager fm = getSupportFragmentManager();
                FreagmentAddOrder freagmentAddOrder = FreagmentAddOrder.getInstance();
                Bundle bundle = new Bundle();
                bundle.putString("rezervationId", rezervationId);
                bundle.putString("action", "onclick");
                freagmentAddOrder.setArguments(bundle);
                fm.beginTransaction().replace(R.id.container_menu, freagmentAddOrder).commit();
            } else {
              /*  FragmentManager fm = getSupportFragmentManager();
                FragmentLogin fragmentLogin = FragmentLogin.getInstance();
                fm.beginTransaction().replace(R.id.container_menu, fragmentLogin).commit();*/

                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent2);
            }
        }

    }

    public  void callFragmentUserInfo(){
        toolbar.setSubtitle(FireBase.getInstance().toolBarTypeNameSurnameString());
        FragmentManager fm = getSupportFragmentManager();
        Fragment_User_Info fragmentUserInfo = Fragment_User_Info.getInstance();
        fm.beginTransaction().replace(R.id.container_menu, fragmentUserInfo).commit();
    }


  /*  @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.container_menu);

        if (fragment instanceof FreagmentAddOrder){

        }else{

        }


    }*/


}
