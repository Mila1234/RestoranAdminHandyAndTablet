package com.example.marijaradisavljevic.restoranadminmarija.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.database.FoodMenuItem;
import com.example.marijaradisavljevic.restoranadminmarija.servis.Servis;
import com.example.marijaradisavljevic.restoranadminmarija.spiner.MySpinnerAdapter;

/**
 * Created by marija.radisavljevic on 6/9/2016.
 */
public class ActivityAddmenuItem  extends AppCompatActivity {

    private EditText newItemName, price;
    private Button okButton;

    private Spinner kategory_spinner;

    private Bundle extras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_menu_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setNavigationIcon(R.drawable.back);
        //toolbar.setNavigationContentDescription(getResources().getString(R.string.nameOfApp));
        // toolbar.setLogo(R.drawable.help);
        toolbar.setTitle(getResources().getString(R.string.Logo_description));
        toolbar.setSubtitle(Servis.getInstance().toolBarTypeNameSurnameString());


        kategory_spinner = (Spinner) findViewById(R.id.kategorySpiner);
        // value = getResources().getStringArray(R.array.kategory_array);
        ArrayAdapter<String> adapter_kategory = new MySpinnerAdapter(false,getBaseContext(),
                android.R.layout.simple_spinner_item,Servis.getInstance().stringListofFoodItems() );

        // Specify the layout to use when the list of choices appears
        adapter_kategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        kategory_spinner.setAdapter(adapter_kategory);
        kategory_spinner.setSelection(((MySpinnerAdapter)adapter_kategory).getStartPosition());
       // kategory_spinner.setOnItemSelectedListener(this);


        newItemName = (EditText) findViewById(R.id.newItme);
        price = (EditText) findViewById(R.id.price);
        okButton = (Button)findViewById(R.id.ok_button);

         extras = getIntent().getExtras();

        if (extras!= null) {//edit user
            String foodItemId = extras.getString("foodItemId");
            FoodMenuItem fmi = Servis.getInstance().getFootMenuItem(foodItemId);
            newItemName.setText(fmi.getFood());
            price.setText(fmi.getPrice().toString());

            int position = adapter_kategory.getPosition(fmi.getNadstavka().getFood());
            kategory_spinner.setSelection(position);

        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String kategoryString = (String) kategory_spinner.getSelectedItem();
                String nameString = newItemName.getText().toString();
                String priceString = price.getText().toString();

                if(kategoryString.length()==0 || nameString.length()==0 || priceString.length()==0){
                    Toast.makeText(getApplicationContext(), getString(R.string.obavezniparametri), Toast.LENGTH_LONG).show();
                    return;
                }
                if (!(priceString.matches("[0-9]+") && (priceString.length() >1 ))) {
                    Toast.makeText(getApplicationContext(), getString(R.string.loseunetparametar), Toast.LENGTH_LONG).show();
                    return;
                }

                if (extras!= null) {//edit user
                    String foodItemId = extras.getString("foodItemId");
                    Servis.getInstance().updateFoodMenuItem( foodItemId , kategoryString , nameString , priceString );
                }else {
                    Servis.getInstance().makeNewFoodItem( kategoryString , nameString , priceString );
                }

                Toast.makeText(getApplicationContext(), " Snimljeno ", Toast.LENGTH_LONG).show();


                Intent intent = new Intent(getApplicationContext(), ActivityMainList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);


            }
        });




    }
}
