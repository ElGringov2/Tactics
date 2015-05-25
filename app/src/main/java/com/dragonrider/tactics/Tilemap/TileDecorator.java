package com.dragonrider.tactics.Tilemap;


import com.dragonrider.tactics.utils.LayerMap;
import com.dragonrider.tactics.utils.TileInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mge637 on 14/05/2015.
 * Permet de décorer une map
 */
public class TileDecorator {
    private int MainTileID;
    private int SecondaryTileID;
    private int LowerLeftCornerID;
    private int LowerRightCornerID;
    private int UpperLeftCornerID;
    private int UpperRightCornerID;
    private int LowerLeftInsideCornerID;
    private int LowerRightInsideCornerID;
    private int UpperLeftInsideCornerID;
    private int UpperRightInsideCornerID;

    private int RightCornerID;
    private int LeftCornerID;
    private int UpperCornerID;
    private int LowerCornerID;
    
    private float DecorationWeight;

    public TileDecorator(int mainTileID,
                         int secondaryTileID,
                         int lowerLeftCornerID,
                         int lowerRightCornerID,
                         int upperLeftCornerID,
                         int upperRightCornerID,
                         int rightCornerID,
                         int leftCornerID,
                         int upperCornerID,
                         int lowerCornerID,
                         int lowerLeftInsideCornerID,
                         int lowerRightInsideCornerID,
                         int upperLeftInsideCornerID,
                         int upperRightInsideCornerID, float decorationWeight) {
        this.MainTileID = mainTileID;
        this.SecondaryTileID = secondaryTileID;
        this.LowerLeftCornerID = lowerLeftCornerID;
        this.LowerRightCornerID = lowerRightCornerID;
        this.UpperLeftCornerID = upperLeftCornerID;
        this.UpperRightCornerID = upperRightCornerID;
        LowerLeftInsideCornerID = lowerLeftInsideCornerID;
        LowerRightInsideCornerID = lowerRightInsideCornerID;
        UpperLeftInsideCornerID = upperLeftInsideCornerID;
        UpperRightInsideCornerID = upperRightInsideCornerID;
        RightCornerID = rightCornerID;
        LeftCornerID = leftCornerID;
        UpperCornerID = upperCornerID;
        LowerCornerID = lowerCornerID;
        DecorationWeight = decorationWeight;

        InitMatrix();
    }

    private int[][] mUndecorable1;
    private int[][] mUndecorable2;

    int[][] mRightCorner;
    int[][] mLeftCorner;
    int[][] mUpperCorner;
    int[][] mLowerCorner;

    int[][] mRightLowerCorner;
    int[][] mLeftLowerCorner;
    int[][] mRightUpperCorner;
    int[][] mLeftUpperCorner;

    int[][] mRightLowerInsideCorner;
    int[][] mLeftLowerInsideCorner;
    int[][] mRightUpperInsideCorner;
    int[][] mLeftUpperInsideCorner;


    private void InitMatrix() {


        mUndecorable1 = new int[][] {
                {-1, -1, -1},
                { MainTileID, SecondaryTileID, MainTileID   },
                {-1, -1, -1}};
        mUndecorable2 = new int[][] {
                {-1, MainTileID, -1},
                { -1, SecondaryTileID, -1   },
                {-1, MainTileID, -1}};

        mRightCorner = new int[][]{
                {-1, SecondaryTileID, -1},
                {SecondaryTileID, SecondaryTileID, MainTileID},
                {-1, SecondaryTileID, -1}};
        mLeftCorner = new int[][] {
                {-1, SecondaryTileID, -1},
                {MainTileID, SecondaryTileID, SecondaryTileID},
                {-1, SecondaryTileID, -1}};
        mLowerCorner = new int[][] {
                {-1, SecondaryTileID, -1},
                {SecondaryTileID, SecondaryTileID, SecondaryTileID},
                {-1, MainTileID, -1}};
        mUpperCorner = new int[][] {
                {-1, MainTileID, -1},
                {SecondaryTileID, SecondaryTileID, SecondaryTileID},
                {-1, SecondaryTileID, -1}};

        mRightUpperCorner = new int[][] {
                {-1, MainTileID, -1},
                {SecondaryTileID, SecondaryTileID, MainTileID},
                {-1, SecondaryTileID, -1}};
        mRightLowerCorner = new int[][] {
                {-1, SecondaryTileID, -1},
                {SecondaryTileID, SecondaryTileID, MainTileID},
                {-1, MainTileID, -1}};
        mLeftLowerCorner = new int[][] {
                {-1, SecondaryTileID, -1},
                {MainTileID, SecondaryTileID, SecondaryTileID},
                {-1, MainTileID, -1}};
        mLeftUpperCorner = new int[][] {
                {-1, MainTileID, -1},
                {MainTileID, SecondaryTileID, SecondaryTileID},
                {-1, SecondaryTileID, -1}};

        mLeftLowerInsideCorner = new int[][] {
                {-1, SecondaryTileID, MainTileID},
                {-1, SecondaryTileID, SecondaryTileID},
                {-1, -1, -1}};
        mLeftUpperInsideCorner = new int[][] {
                {-1, -1, -1},
                {-1, SecondaryTileID, SecondaryTileID},
                {-1, SecondaryTileID, MainTileID}};
        mRightUpperInsideCorner = new int[][] {
                {-1, -1, -1},
                {SecondaryTileID, SecondaryTileID, -1},
                {MainTileID, SecondaryTileID, -1}};
        mRightLowerInsideCorner = new int[][] {
                {MainTileID, SecondaryTileID, -1},
                {SecondaryTileID, SecondaryTileID, -1},
                {-1, -1, -1}};




    }

    private int[][] getMatrix(LayerMap map, int x, int y) {
        return new int[][]  {
                { map.getFirst(x - 1, y + 1), map.getFirst(x, y + 1), map.getFirst(x + 1, y + 1) },
                { map.getFirst(x - 1, y), map.getFirst(x, y), map.getFirst(x + 1, y) },
                { map.getFirst(x - 1, y-1), map.getFirst(x, y-1), map.getFirst(x + 1, y - 1) }
        };
    }


    private boolean Check(int[][] MapMatrix, int[][] ToCheckMatrix) {
        for( int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                if (ToCheckMatrix[x][y] != -1 && ToCheckMatrix[x][y] != MapMatrix[x][y]) return false;

        return true;
    }

    public void Decorate(LayerMap map) {





        for (int x = 1; x < map.getSize() - 1; x++)
            for (int y = 1; y < map.getSize() - 1; y++) {
                int[][] mat = getMatrix(map, x, y);

                if (Check(mat, mUndecorable1) || Check(mat, mUndecorable2)) {
                    map.set(new int[] { MainTileID }, new float[] { DecorationWeight }, x, y);


                    for (int x2 = x - 1; x2 < x + 1; x2++) {
                        for (int y2 = y - 1; y2 < y + 1; y2++) {
                            List<TileInfo> decoration = DecorateCell(map, x2, y2);
                            if (decoration != null)
                                map.set(decoration, x2, y2);
                        }
                    }
                }
                List<TileInfo> decoration = DecorateCell(map, x, y);
                if (decoration != null)
                    map.set(decoration, x, y);

            }





    }


    private List<TileInfo> DecorateCell(LayerMap map, final int x, final int y) {
        if (x <= 0 || y <= 0 || x >= map.getSize() - 1 || y >= map.getSize() - 1) return null;


        ArrayList<TileInfo> tiles = new ArrayList<>();
        tiles.add(new TileInfo(map.getFirst(x, y), DecorationWeight));



        int[][] mat = getMatrix(map, x, y);
        if (Check(mat, mLeftCorner))
            tiles.add(new TileInfo(LeftCornerID, DecorationWeight));
        if (Check(mat, mRightCorner))
            tiles.add(new TileInfo(RightCornerID, DecorationWeight));
        if (Check(mat, mLowerCorner))
            tiles.add(new TileInfo(LowerCornerID, DecorationWeight));
        if (Check(mat, mUpperCorner))
            tiles.add(new TileInfo(UpperCornerID, DecorationWeight));
        if (Check(mat, mRightUpperCorner))
            tiles.add(new TileInfo(UpperRightCornerID, DecorationWeight));
        if (Check(mat, mRightLowerCorner))
            tiles.add(new TileInfo(LowerRightCornerID, DecorationWeight));
        if (Check(mat, mLeftUpperCorner))
            tiles.add(new TileInfo(UpperLeftCornerID, DecorationWeight));
        if (Check(mat, mLeftLowerCorner))
            tiles.add(new TileInfo(LowerLeftCornerID, DecorationWeight));
        if (Check(mat, mRightUpperInsideCorner))
            tiles.add(new TileInfo(UpperRightInsideCornerID, DecorationWeight));
        if (Check(mat, mRightLowerInsideCorner))
            tiles.add(new TileInfo(LowerRightInsideCornerID, DecorationWeight));
        if (Check(mat, mLeftUpperInsideCorner))
            tiles.add(new TileInfo(UpperLeftInsideCornerID, DecorationWeight));
        if (Check(mat, mLeftLowerInsideCorner))
            tiles.add(new TileInfo(LowerLeftInsideCornerID, DecorationWeight));

        return tiles;




    }
}
