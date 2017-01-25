package com.example.marijaradisavljevic.restoranadminmarija.adapters;

import android.content.res.Resources;

import com.example.marijaradisavljevic.restoranadminmarija.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marija on 24.1.17.
 */
public class MainListAdapterContent {
    /**
     * An array of sample  items.
     */
    public static final List<MainItem> ITEMS = new ArrayList<MainItem>();

    /**
     * A map of sample) items, by ID.
     */
    public static final Map<String, MainItem> ITEM_MAP = new HashMap<String, MainItem>();

    private static final int COUNT = 25;

    static {
        // Add some items.

        addItem(createMainItem("1", "licne informacije"));
        addItem(createMainItem("2","kreiraj novog korisnika" ));
        addItem(createMainItem("3", "lista korisnika"));
        addItem(createMainItem("4", "lista naridzbina"));
        addItem(createMainItem("5","dodaj stavku" ));
        addItem(createMainItem("6", "lista stavki"));
        addItem(createMainItem("7","odjavi se" ));


    }

    private static void addItem(MainItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static MainItem createMainItem(String id, String content) {
        return new MainItem(id, content);
    }



    /**
     * A dummy item representing a piece of content.
     */
    public static class MainItem {
        public final String id;
        public final String content;


        public MainItem(String id, String content ) {
            this.id = id;
            this.content = content;

        }

        @Override
        public String toString() {
            return content;
        }
    }
}
