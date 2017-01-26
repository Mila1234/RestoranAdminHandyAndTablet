package com.example.marijaradisavljevic.restoranadminmarija.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marija on 24.1.17.
 */
public class Content {
    /**
     * An array of sample  items.
     */
    public static final List<MainItem> ITEMSADMIN = new ArrayList<MainItem>();

    /**
     * A map of sample) items, by ID.
     */
    public static final Map<String, MainItem> ITEM_MAP_ADMIN = new HashMap<String, MainItem>();

    /**
     * An array of sample  items.
     */
    public static final List<MainItem> ITEMSKONOBAR = new ArrayList<MainItem>();

    /**
     * A map of sample) items, by ID.
     */
    public static final Map<String, MainItem> ITEM_MAP_KONOBAR = new HashMap<String, MainItem>();

    static {
        // Add some items.

        addItem(createMainItem("1", "licne informacije"));
        addItem(createMainItem("2","kreiraj novog korisnika" ));
        addItem(createMainItem("3", "lista korisnika"));
        addItem(createMainItem("4", "lista naridzbina"));
        addItem(createMainItem("5","dodaj stavku" ));
        addItem(createMainItem("6", "lista stavki"));
        addItem(createMainItem("7","odjavi se" ));

        addItemKOnobar(createMainItem("1", "licne informacije"));
        addItemKOnobar(createMainItem("2","lista narudzbina" ));
        addItemKOnobar(createMainItem("3", "nova narudzbina"));
        addItemKOnobar(createMainItem("4", "odjavi se"));


    }

    private static void addItem(MainItem item) {
        ITEMSADMIN.add(item);
        ITEM_MAP_ADMIN.put(item.id, item);
    }

    private static void addItemKOnobar(MainItem item) {
        ITEMSKONOBAR.add(item);
        ITEM_MAP_KONOBAR.put(item.id, item);
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
