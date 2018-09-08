package com.idleciv.model;

import com.idleciv.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jaapo on 21-1-2018.
 */

public class ResourceType {

    public static int Food = 0;
    public static int Lumber = 1;
    public static int Stone = 2;

    public int mIndex;
    public String mName;
    public int mIcon;

    public ResourceType( int index, String name, int icon)
    {
        mIndex = index;
        mName = name;
        mIcon = icon;
    }

    public static Map<Integer, ResourceType> getResourceMap()
    {
        Map<Integer, ResourceType> map = new HashMap<>();
        map.put(Food, new ResourceType(Food, "Food", R.drawable.wheat));
        map.put(Lumber, new ResourceType(Lumber, "Lumber", R.drawable.lumber));
        map.put(Stone, new ResourceType(Stone, "Stone", R.drawable.stone));

        return map;
    }
    public String getName() {
        return mName;
    }

    public int getIcon() {
        return mIcon;
    }

    public static String getName(int resourceIndex){
        if(getResourceMap().containsKey(resourceIndex)) {
            return getResourceMap().get(resourceIndex).getName();
        } else {
            return "Unknown";
        }
    }

    public static int getIcon(int resourceIndex){
        if(getResourceMap().containsKey(resourceIndex)) {
            return getResourceMap().get(resourceIndex).getIcon();
        } else {
            return R.drawable.placeholder;
        }
    }
}
