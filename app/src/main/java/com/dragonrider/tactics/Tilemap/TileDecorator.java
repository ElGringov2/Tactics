package com.dragonrider.tactics.Tilemap;


import com.dragonrider.tactics.utils.HeightMap;



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
                         int upperRightInsideCornerID) {
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
                {SecondaryTileID, SecondaryTileID, -1},
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

    private int[][] getMatrix(HeightMap map, int x, int y) {
        return new int[][]  {
                { (int)map.get(x-1, y+1), (int)map.get(x, y+1), (int)map.get(x+1, y+1) },
                { (int)map.get(x-1, y), (int)map.get(x, y), (int)map.get(x+1, y) },
                { (int)map.get(x-1, y-1), (int)map.get(x, y-1), (int)map.get(x+1, y-1) }
        };
    }


    private boolean Check(int[][] MapMatrix, int[][] ToCheckMatrix) {
        for( int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                if (ToCheckMatrix[x][y] != -1 && ToCheckMatrix[x][y] != MapMatrix[x][y]) return false;

        return true;
    }

    public void Decorate(HeightMap map) {


        HeightMap decoration = new HeightMap(map.getSize());




        for (int x = 1; x < map.getSize() - 1; x++)
            for (int y = 1; y < map.getSize() - 1; y++) {
                int[][] mat = getMatrix(map, x, y);

                if (Check(mat, mUndecorable1) || Check(mat, mUndecorable2)) {
                    map.set(MainTileID, x, y);


                    for (int x2 = x - 1; x2 < x + 1; x2++) {
                        for (int y2 = y - 1; y2 < y + 1; y2++) {
                            decoration.set(DecorateCell(map, x2, y2), x2, y2);
                        }
                    }
                }

                decoration.set(DecorateCell(map, x, y), x, y);

            }


        int mod = map.Update(decoration);

        map.setFullSize(map.getFullSize() + mod);
    }


    private int DecorateCell(HeightMap map, int x, int y) {
        if (x <= 0 || y <= 0 || x >= map.getSize() - 1 || y >= map.getSize() - 1) return -1;

        int[][] mat = getMatrix(map, x, y);
        if (Check(mat, mLeftCorner))
            return LeftCornerID;
        else if (Check(mat, mRightCorner))
            return RightCornerID;
        else if (Check(mat, mLowerCorner))
            return LowerCornerID;
        else if (Check(mat, mUpperCorner))
            return UpperCornerID;
        else if (Check(mat, mRightUpperCorner))
            return UpperRightCornerID;
        else if (Check(mat, mRightLowerCorner))
            return LowerRightCornerID;
        else if (Check(mat, mLeftUpperCorner))
            return UpperLeftCornerID;
        else if (Check(mat, mLeftLowerCorner))
            return LowerLeftCornerID;
        else if (Check(mat, mRightUpperInsideCorner))
            return UpperRightInsideCornerID;
        else if (Check(mat, mRightLowerInsideCorner))
            return LowerRightInsideCornerID;
        else if (Check(mat, mLeftUpperInsideCorner))
            return UpperLeftInsideCornerID;
        else if (Check(mat, mLeftLowerInsideCorner))
            return LowerLeftInsideCornerID;

        return -1;

    }
}
