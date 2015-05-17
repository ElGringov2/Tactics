package com.dragonrider.tactics.Tilemap;

import com.dragonrider.tactics.utils.HeightMap;
import com.dragonrider.tactics.utils.PerlinNoise;

import org.andengine.util.debug.Debug;

import java.util.Random;

/**
 * Created by mge637 on 13/05/2015.
 */
public class CountrysideMapGenerator extends MapGenerator {
    private int seed;
    public CountrysideMapGenerator(int Seed) {
        this.seed = Seed;

    }




    @Override
    public Map Generate(int Size) {

        int baseTileID = 118;
        int secondaryTileID = 115;

        Map map = new Map(baseTileID, secondaryTileID, Size);
        Random random = new Random(seed);


        PerlinNoise noise = new PerlinNoise(random.nextInt());
        map.BaseLayer = new HeightMap(Size);



        for (int i = 0; i < Size ; i++)
            for (int j = 0; j < Size ; j++) {
                float fValue = noise.Noise(32.0f * i / (float) Size, 32.0f * j / (float) Size, 0);
                map.BaseLayer.set(fValue > -0.1f ? baseTileID : secondaryTileID, i, j);


            }



        TileDecorator decorator = new TileDecorator(baseTileID, secondaryTileID, 54, 55, 22, 23, 117, 119, 150, 86, 149, 151, 85, 87);
        decorator.Decorate(map.BaseLayer);

        Debug.d("hopla", "Taille=" + String.valueOf(map.BaseLayer.getFullSize()));


        return map;
    }


}
