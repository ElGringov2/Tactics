package com.dragonrider.tactics.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mge637 on 13/05/2015.
 * Gere les heightmap
 */
public class LayerMap {

    private List<List<TileInfo>> Heights;
    private int Size;



    private int FullSize;

    public int getSize() {
        return Size;
    }

    public LayerMap(int size)
    {
        Size = size;



        Heights = new ArrayList<>();

        for (int i = 0; i < Size * Size; i++)
            Heights.add(null);

    }


    public List<TileInfo> get(int X, int Y) {
        if (Heights.get(Y * Size + X) == null)
            return new ArrayList<>();
        return Heights.get(Y * Size + X);
    }

    public void set(int[] pValues , int X, int Y) {
        Heights.set(Y * Size + X, new ArrayList<TileInfo>());
        for (int i : pValues)
            Heights.get(Y * Size + X).add(new TileInfo(i));

    }

    public void set(List<TileInfo> pValues , int X, int Y) {
        if (Heights.get(Y * Size + X) != null)
            this.FullSize -= Heights.get(Y * Size + X).size();
        Heights.set(Y * Size + X, pValues);
        if (pValues != null)
            this.FullSize += pValues.size();
    }

    public void add(int pValue, int X, int Y) {
        if (Heights.get(Y * Size + X) == null)
            Heights.set(Y * Size + X, new ArrayList<TileInfo>());
        Heights.get(Y * Size + X).add(new TileInfo(pValue));
        this.FullSize++;
    }

    public int getFullSize() {
        return this.FullSize;
    }




    public int getFirst(int x, int y) {
        if (this.Heights.get(y * Size + x) != null && this.Heights.get(y * Size + x).size() > 0 )
            return this.Heights.get(y * Size + x).get(0).getTileID();
        else
            return -1;
    }
}
