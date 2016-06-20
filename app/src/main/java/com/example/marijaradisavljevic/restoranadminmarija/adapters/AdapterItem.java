package com.example.marijaradisavljevic.restoranadminmarija.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marija.radisavljevic on 5/19/2016.
 */
public interface AdapterItem {

    View getView(Context var1, View var2, ViewGroup var3);

    boolean isEnabled();
}
