package com.idleciv.model;

import android.content.Context;

import com.idleciv.R;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by jaapo on 21-1-2018.
 */

public class ModelResourceStock {

    public static final String TAG = ModelResourceStock.class.getName();
    public static final int Food = 0;
    public static final int Grain = 1;
    public static final int Lumber = 2;
    public static final int Stone = 3;

    public static final int Planks = 4;
    public static final int Coal = 5;
    public static final int Ore = 6;
    public static final int Iron = 7;

    public static final int Fibre = 8;
    public static final int Cloth = 9;
    public static final int Rope = 10;

    public static final int Boat = 11;
    public static final int FishingNet = 12;
    public static final int Ship = 13;

    public static final int Cannon = 14;

    public static final int Hut = 15;
    public static final int Hovel = 16;
    public static final int House = 17;
    public static final int Land = 29;


    public static final int StoneTool = 18;
    public static final int CopperTool = 19;
    public static final int IronTool = 20;

    public static final int Leather = 21;
    public static final int Cow = 22;
    public static final int Meat = 23;

    public static final int LigthArmor = 24;
    public static final int Bow = 25;

    public static final int StoneWeapon = 26;
    public static final int Clothes = 27;




    private transient ModelEpochState mGameState;
    private transient HashSet<Listener> mListenerSet;

    public int mResourceIndex;
    public int mIconResourceId;
    public int mNameResourceId;

    public int mStock;
    public int mCapacity;

    public int mDemand;
    public int mProduction;

    public boolean mIsEnabled;
    public boolean mIsObsolete;
    public boolean mHasChanges;


    public ModelResourceStock(ModelEpochState gameState, int resourceIndex, int stock, int capacity, int iconResourceId, int nameResourceId)
    {
        mGameState = gameState;
        mListenerSet = new HashSet<>();
        mResourceIndex = resourceIndex;
        mStock = stock;
        mCapacity = capacity;
        mDemand = 0;
        mProduction = 0;
        mIsEnabled = false;
        mIconResourceId =  iconResourceId;
        mNameResourceId = nameResourceId;
    }

    public void validate(ModelEpochState gameState) {
        mGameState = gameState;
        mListenerSet = new HashSet<>();
        //Log.e("validate", "updateUI: " + mIndustryIndex);
    }

    public static ArrayList<ModelResourceStock> getResourceList(ModelEpochState gameState) {
        ArrayList<ModelResourceStock> resourceList = new ArrayList<>();
        resourceList.add(new ModelResourceStock(gameState, Food, 0, 160, R.drawable.resource_food, R.string.resource_food));
        resourceList.add(new ModelResourceStock(gameState, Grain, 0, 160, R.drawable.resource_grain, R.string.resource_grain));
        resourceList.add(new ModelResourceStock(gameState, Lumber, 0, 160, R.drawable.resource_lumber, R.string.resource_lumber));
        resourceList.add(new ModelResourceStock(gameState, Stone, 0, 160, R.drawable.resource_stone, R.string.resource_stone));
        resourceList.add(new ModelResourceStock(gameState, Planks, 0, 160, R.drawable.resource_plank, R.string.resource_planks));
        resourceList.add(new ModelResourceStock(gameState, Coal, 0, 160, R.drawable.resource_coal, R.string.resource_charcoal));
        resourceList.add(new ModelResourceStock(gameState, Ore, 0, 160, R.drawable.resource_ore, R.string.resource_ore));
        resourceList.add(new ModelResourceStock(gameState, Iron, 0, 160, R.drawable.resource_iron, R.string.resource_iron));


        resourceList.add(new ModelResourceStock(gameState, Fibre, 0, 100, R.drawable.resource_fibre, R.string.resource_fibre));
        resourceList.add(new ModelResourceStock(gameState, Cloth, 0, 100, R.drawable.resource_cloth, R.string.resource_cloth));
        resourceList.add(new ModelResourceStock(gameState, Rope, 0, 100, R.drawable.resource_rope, R.string.resource_rope));

        resourceList.add(new ModelResourceStock(gameState, Boat, 0, 100, R.drawable.placeholder, R.string.resource_boat));
        resourceList.add(new ModelResourceStock(gameState, FishingNet, 0, 100, R.drawable.placeholder, R.string.resource_fishing_net));
        resourceList.add(new ModelResourceStock(gameState, Ship, 0, 100, R.drawable.placeholder, R.string.resource_ship));

        resourceList.add(new ModelResourceStock(gameState, StoneTool, 0, 100, R.drawable.resource_stone_tools, R.string.resource_stone_tool));
        resourceList.add(new ModelResourceStock(gameState, StoneWeapon, 0, 100, R.drawable.placeholder, R.string.resource_stone_weapon));






        resourceList.add(new ModelResourceStock(gameState, CopperTool, 0, 100, R.drawable.resource_copper_tools, R.string.resource_copper_tool));


/*        public static final int LandPower = 13;
        public static final int NavalPower = 14;
        public static final int AirPower = 15;

        public static final int Hut = 16;
        public static final int Hovel = 17;
        public static final int House = 18;*/
        return resourceList;
    }

    public void clearDemandAndProduction(){
        mDemand = 0;
        mProduction = 0;
    }

    public void addDemand(int amount){
        mDemand +=amount;
    }

    public boolean canSupply(){
        return mDemand <= mStock;
    }


    public void consume(int amount){
        if(mStock < amount){
            throw new RuntimeException("canSupply check failed");
        } else {
            mStock -= amount;
            mHasChanges = true;
        }
    }

    public void produce(int amount){
        mProduction += amount;
        if(mStock != mCapacity) {
            mStock += amount;
            if (mCapacity < mStock ){
                mStock = mCapacity;
            }
            mHasChanges = true;
        }
    }

    public int getIcon() {
        return mIconResourceId;
    }

    public String getName(Context context)
    {
       // Log.e(TAG, "getName: " + mNameResourceId);
        return context.getString(mNameResourceId);
    }




    public void updateUI() {
        if(mHasChanges) {
            for (Listener listener : mListenerSet) {
                listener.updateResourceStockUI();
            }
        }
        mHasChanges = false;
    }

    public void addListener(Listener listener) {
        mListenerSet.add(listener);
        listener.updateResourceStockUI();
    }

    public void removeListener(Listener listener) {
        if(mListenerSet.contains(listener)) {
            mListenerSet.remove(listener);
        }
    }

    public void clear() {
        mListenerSet = new HashSet<>();
    }


    public interface Listener {
        void updateResourceStockUI();
    }
}
