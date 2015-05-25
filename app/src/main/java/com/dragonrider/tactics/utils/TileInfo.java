package com.dragonrider.tactics.utils;

/**
 * Created by dragonrider on 25/05/15.
 */
public class TileInfo {

    private int TileID;
    private float TileWeight;

    public TileInfo(int id, float tileWeight) {
        this.TileID = id;
        TileWeight = tileWeight;
    }


    public int getTileID() {
        return TileID;
    }


    public float getTileWeight() {
        return TileWeight;
    }


}
