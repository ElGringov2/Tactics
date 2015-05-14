package com.dragonrider.tactics.Tilemap;

import com.dragonrider.tactics.utils.HeightMap;

/**
 * Created by mge637 on 14/05/2015.
 */
public class TileDecorator {
    private int MainTileID;
    private int SecondaryTileID;
    private int LowerLeftCornerID;
    private int LowerRightCornerID;
    private int UpperLeftCornerID;
    private int UpperRightCornerID;
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
                         int lowerCornerID) {
        this.MainTileID = mainTileID;
        this.SecondaryTileID = secondaryTileID;
        this.LowerLeftCornerID = lowerLeftCornerID;
        this.LowerRightCornerID = lowerRightCornerID;
        this.UpperLeftCornerID = upperLeftCornerID;
        this.UpperRightCornerID = upperRightCornerID;
        RightCornerID = rightCornerID;
        LeftCornerID = leftCornerID;
        UpperCornerID = upperCornerID;
        LowerCornerID = lowerCornerID;
    }


    public void Decorate(HeightMap map) {
        for (int x = 1; x < map.getSize() - 1; x++)
            for (int y = 1; y < map.getSize() - 1; y++) {
                if (map.get(x, y) == SecondaryTileID &&
                    map.get(x + 1, y + 1) == MainTileID &&
                    map.get(x + 1, y) == MainTileID &&
                    map.get(x + 1, y - 1) == MainTileID)
                    map.set(LeftCornerID, x, y);

            }
    }
}
