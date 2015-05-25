package com.dragonrider.tactics.Tilemap;

import com.dragonrider.tactics.utils.LayerMap;

/**
 * Created by dragonrider on 23/05/15. hahaha
 */
public class TreeTileDecorator {

    private int LeftShadowTileID;
    private int RightShadowTileID;

    private int[] TreeLevelIDs;
    private int[] TreeLevelLeftDecorationIDs;
    private int[] TreeLevelRightDecorationIDs;


    public TreeTileDecorator(int leftShadowTileID, int rightShadowTileID, int[] treeLevelIDs, int[] treeLevelLeftDecorationIDs, int[] treeLevelRightDecorationIDs) {
        LeftShadowTileID = leftShadowTileID;
        RightShadowTileID = rightShadowTileID;
        TreeLevelIDs = treeLevelIDs;
        TreeLevelLeftDecorationIDs = treeLevelLeftDecorationIDs;
        TreeLevelRightDecorationIDs = treeLevelRightDecorationIDs;
    }


    public int DecorateUpperLayer(LayerMap TreeLayer, LayerMap UpperLayer) {

        int iCount = 0;


        for (int y = TreeLayer.getSize() - 1; y >= 0; y--)
            for (int x = 0; x < TreeLayer.getSize(); x++)
            {
                if (TreeLayer.getFirst(x, y) == -1) {


                    if (x - 1 >= 0 && TreeLayer.getFirst(x - 1, y) != -1) {

                        if (y + 3 < TreeLayer.getSize() - 1) {
                            UpperLayer.add(TreeLevelRightDecorationIDs[0], 0, x, y + 3);
                            iCount++;
                        }
                        if (y + 2 < TreeLayer.getSize() - 1) {
                            UpperLayer.add(TreeLevelRightDecorationIDs[1], 0, x, y + 2);
                            iCount++;
                        }
                        if (y + 1 < TreeLayer.getSize() - 1) {
                            UpperLayer.add(TreeLevelRightDecorationIDs[2], 0, x, y + 1);
                            iCount++;
                        }
                        UpperLayer.add(RightShadowTileID, 0, x, y);

                        UpperLayer.add(TreeLevelRightDecorationIDs[3], 0, x, y);
                        iCount++;
                    }
                    if (x + 1 < TreeLayer.getSize() && TreeLayer.getFirst(x + 1, y) != -1) {

                        if (y + 3 < TreeLayer.getSize() - 1) {
                            UpperLayer.add(TreeLevelLeftDecorationIDs[0], 0, x, y + 3);
                            iCount++;
                        }
                        if (y + 2 < TreeLayer.getSize() - 1) {
                            UpperLayer.add(TreeLevelLeftDecorationIDs[1], 0, x, y + 2);
                            iCount++;
                        }
                        if (y + 1 < TreeLayer.getSize() - 1) {
                            UpperLayer.add(TreeLevelLeftDecorationIDs[2], 0, x, y + 1);
                            iCount++;
                        }
                        UpperLayer.add(LeftShadowTileID, 0, x, y);
                        UpperLayer.add(TreeLevelLeftDecorationIDs[3], 0, x, y);
                        iCount++;
                    }


                    continue;
                }


                if (y + 3 < TreeLayer.getSize() - 1) {
                    UpperLayer.add(TreeLevelIDs[0], 0, x, y + 3);
                    iCount++;
                }
                if (y + 2 < TreeLayer.getSize() - 1) {
                    UpperLayer.add(TreeLevelIDs[1], 0, x, y + 2);
                    iCount++;
                }
                if (y + 1 < TreeLayer.getSize() - 1) {
                    UpperLayer.add(TreeLevelIDs[2], 0, x, y + 1);
                    iCount++;
                }
                UpperLayer.add(TreeLevelIDs[3], 0, x, y);
                iCount++;



            }


        return iCount;

    }




}
