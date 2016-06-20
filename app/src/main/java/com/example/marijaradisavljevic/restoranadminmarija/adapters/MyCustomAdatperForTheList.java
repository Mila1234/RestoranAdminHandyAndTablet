package com.example.marijaradisavljevic.restoranadminmarija.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by marija.radisavljevic on 5/13/2016.
 */
public class MyCustomAdatperForTheList<T> extends BaseAdapter{


    ArrayList<T> myList = new ArrayList<T>();
   // LayoutInflater inflater;
    Context context;

    public MyCustomAdatperForTheList(Context context) {

        this.context = context;

    }

    public MyCustomAdatperForTheList<T> addItem(T item) {
        this.myList.add(item);
        notifyDataSetChanged();
        return this;
    }

    public MyCustomAdatperForTheList<T> SetList(ArrayList<T> list) {
        this.myList =  list;
        notifyDataSetChanged();
        return this;
    }

public ArrayList<T> getMyList (){
    return myList;
}

    public void deleteItemFromAdapter(T obj){
        myList.remove(obj);
        notifyDataSetChanged();
        /*Iterator<T>  iter = myList.iterator();

        while(iter.hasNext()){
            T t = iter.next();
            if(obj.equals(t)){
                myList.remove(t);
               // iter.remove();
                break;
            }
        }*/


    }

    @Override
    public int getCount() {
        int size  = myList.size();
        return size;
    }

    @Override
    public Object getItem(int position) {
        T t = myList.get(position);
        return t;
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterItem item = (AdapterItem)this.myList.get(position);
        if(item != null) {
            convertView = item.getView(this.context, convertView, parent);
        }
        return convertView;
    }


    public void deleteAll() {

        myList = new ArrayList<T>();
        notifyDataSetChanged();
    }

    public void update() {



    }

    public void deleteItem(T t) {
        myList.remove(t);
        notifyDataSetChanged();
    }
}
