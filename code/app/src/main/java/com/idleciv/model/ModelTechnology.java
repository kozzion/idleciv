package com.idleciv.model;

import com.idleciv.R;

import java.util.ArrayList;

public class ModelTechnology {

    public static int PrimitiveTools = 0;
    public static int CopperTools = 1;
    public static int ArchesTools = 2;

    public transient ModelGameState mGameState;
    public int mTechnologyIndex;
    public String mName;
    public String mDescription;
    public ArrayList<ModelModifier> mModifiers;

    public ModelTechnology(ModelGameState gameState, int technologyIndex, String name, String description, ArrayList<ModelModifier> modifiers) {
        mGameState = gameState;
        mTechnologyIndex = technologyIndex;
        mName = name;
        mDescription = description;
        mModifiers = modifiers;
    }

    public static int getIcon(int technologyIndex){
        switch (technologyIndex){
            case 0:
                return R.drawable.scythe;
            default:
                return R.drawable.placeholder;
        }
    }

    public void research() {
        //Apply modifiers
        //move to researched
    }

    public static ArrayList<ModelTechnology> getTechnologies(ModelGameState gameState){
        ArrayList<ModelTechnology> technologies = new ArrayList<>();
        ArrayList<ModelModifier> modifier0 = new ArrayList<>();
        technologies.add(new ModelTechnology(gameState, PrimitiveTools,"Primitive tools", "They are very primitive", modifier0));
        ArrayList<ModelModifier> modifier1 = new ArrayList<>();
        technologies.add(new ModelTechnology(gameState, CopperTools,"Copper Tools", "Not the police", modifier1));
        return technologies;
    }

}