package com.softricsolutions.videoplayapplication.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jyoti on 15-Feb-18.
 */

public class Contents {

    public List<Contents> ITEMS = new ArrayList<>();

    /**
     * A map of YouTube videos, by ID.
     */
    private Map<String, Contents> ITEM_MAP = new HashMap<>();

    public String id;
    public String title;

    public Contents(String id, String content) {
        this.id = id;
        this.title = content;
    }

    @Override
    public String toString() {
        return title;
    }

    public void addItem(Contents item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


}
