package com.dragonrider.tactics.Tilemap;

/**
 * Created by mge637 on 13/05/2015.
 */
public abstract class MapGenerator {




    public Map Generate(int Size) {
        return new Map(0, 1, Size);

    }
}
