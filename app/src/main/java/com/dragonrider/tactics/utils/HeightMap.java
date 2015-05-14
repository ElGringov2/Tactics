package com.dragonrider.tactics.utils;

/**
 * Created by mge637 on 13/05/2015.
 * Gere les heightmap
 */
public class HeightMap {

    private float[] Heights;
    private int Size = 128;


    public int getSize() {
        return Size;
    }

    public HeightMap(int size)
    {
        Size = size;
        Heights = new float[Size * Size];
    }

    public float get(int X, int Y) {
        return Heights[Y * Size + X];
    }

    public void set(float Value, int X, int Y) {
        Heights[Y * Size + X] = Value;
    }

}
