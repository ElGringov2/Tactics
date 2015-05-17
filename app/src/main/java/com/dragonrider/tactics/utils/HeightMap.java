package com.dragonrider.tactics.utils;

/**
 * Created by mge637 on 13/05/2015.
 * Gere les heightmap
 */
public class HeightMap {

    private float[] Heights;
    private int Size = 128;

    private int FullSize = 128 * 128;

    public int getSize() {
        return Size;
    }

    public HeightMap(int size)
    {
        Size = size;
        FullSize = Size * Size;
        Heights = new float[Size * Size];
    }

    public float get(int X, int Y) {
        return Heights[Y * Size + X];
    }

    public void set(float Value, int X, int Y) {
        Heights[Y * Size + X] = Value;
    }

    public int getFullSize() {
        return FullSize;
    }

    public HeightMap setFullSize(int fullSize) {
        FullSize = fullSize;
        return this;
    }


    public int Update(HeightMap map) {
        int iModifications = 0;
        for (int j = 0; j < Heights.length; j++) {
            if (map.Heights[j] != -1) {
                Heights[j] = map.Heights[j];
                iModifications++;
            }
        }
        return iModifications;
    }
}
