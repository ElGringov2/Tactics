package com.dragonrider.tactics.Tilemap;

import com.dragonrider.tactics.utils.LayerMap;
import com.dragonrider.tactics.utils.PerlinNoise;

import java.util.Random;

/**
 * Created by mge637 on 13/05/2015. code code code
 */
public class CountrysideMapGenerator extends MapGenerator {
    private int seed;
    public CountrysideMapGenerator(int Seed) {
        this.seed = Seed;

    }




    @Override
    public Map Generate(int Size) {



        Map map = new Map(Size);
        Random random = new Random(seed);


        PerlinNoise noise = new PerlinNoise(random.nextInt());



        map.BaseLayer = new LayerMap(Size);
        for (int i = 0; i < Size ; i++)
            for (int j = 0; j < Size ; j++) {
                float fValue = noise.Noise(8.0f * i / (float) Size, 8.0f * j / (float) Size, 0);
                map.BaseLayer.add(fValue > -0.2f ? 118 : 115, 0f, i, j);


            }



        TileDecorator decorator = new TileDecorator(118, 115, 54, 55, 22, 23, 117, 119, 150, 86, 149, 151, 85, 87, 0.0f);
        decorator.Decorate(map.BaseLayer);


        noise = new PerlinNoise(random.nextInt());


        float mHeightLevel = 0.25f;
        map.TreeLayer = new LayerMap(Size);
        for (int i = 0; i < Size ; i++)
            for (int j = 0; j < Size ; j++)
                if (noise.Noise(32.0f * i / (float) Size, 32.0f * j / (float) Size, 0) > mHeightLevel)
                    map.TreeLayer.add(634, 0.25f, i, j);



        map.UpperLevelLayer = new LayerMap(Size);


        TreeTileDecorator treeTileDecorator = new TreeTileDecorator(636, 638, new int[] { 508, 540, 572, 604}, new int[] { 507, 539, 571, 603}, new int[] { 509, 541, 573, 605});
        treeTileDecorator.DecorateUpperLayer(map.TreeLayer, map.UpperLevelLayer);



        return map;
    }


}
