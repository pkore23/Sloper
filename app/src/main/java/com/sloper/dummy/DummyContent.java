package com.sloper.dummy;

import android.content.Context;

import com.sloper.MainActivity;
import com.sloper.Parser;
import com.sloper.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private int COUNT;
    private Context mContext = MainActivity.mContext;

    /*static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }*/

    public DummyContent() {
    }


    public void addItems(String category) {
        if(category.equalsIgnoreCase("all"))
        {
            ITEMS=Parser.addAll();
            COUNT=ITEMS.size();
        }
        else if(category.equalsIgnoreCase("category1"))
        {
            ITEMS=Parser.addCategory2();
            COUNT=ITEMS.size();
        }
        else if(category.equalsIgnoreCase("category2"))
        {
            ITEMS=Parser.addCategory1();
            COUNT=ITEMS.size();
        }
        else if(category.equalsIgnoreCase("category3"))
        {
            ITEMS=Parser.addCategory3();
            COUNT=ITEMS.size();
        }
        else if(category.equalsIgnoreCase("category4"))
        {
            ITEMS=Parser.addCategory4();
            COUNT=ITEMS.size();
        }
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String title;
        public final String price;
        public final int imgRes;
        public final String colors;
        public final String sizes;
        public final String type;
        public final String desc;

        public DummyItem(String id, String content, String type, String details, String colors, String sizes, String description) {
            this.id = id;
            this.title = content;
            this.type = type;
            this.price = details;
            this.imgRes = R.drawable.wait;
            this.colors = colors;
            this.sizes = sizes;
            this.desc = description;
        }



        @Override
        public String toString() {
            return id;
        }
    }
}
