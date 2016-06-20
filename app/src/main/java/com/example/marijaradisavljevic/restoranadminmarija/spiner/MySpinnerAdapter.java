package com.example.marijaradisavljevic.restoranadminmarija.spiner;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MySpinnerAdapter extends ArrayAdapter<String> {

  public MySpinnerAdapter(Context context, int textViewResourceId, String[] value) {
  super(context, textViewResourceId,value);
  // TODO Auto-generated constructor stub

 }

  @Override
 public int getCount() {
	  
  // TODO Auto-generated method stub
  int count = super.getCount();
  
  return count>0 ? count-1 : count ;
  
  
 }
 

}