package com.dragonrider.tactics.utils;

import android.graphics.Point;

import com.dragonrider.tactics.Tilemap.Map;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dragonrider on 25/05/15.
 */
public class AStar {


    public class AStarEntry {
        public int x;
        public int y;
        public float weight;
        public float Score;



        @Override
        public boolean equals(Object o) {
            return o instanceof AStarEntry && o != null && x == ((AStarEntry) o).x && y == ((AStarEntry) o).y;


        }
    }

    private ArrayList<AStarEntry> OpenList = new ArrayList<>();
    private ArrayList<AStarEntry> ClosedList = new ArrayList<>();


    private AStarEntry start;

    public AStar(final int pStartingX, final int pStartingY) {
        start = new AStarEntry() {{ x = pStartingX; y = pStartingY; weight = 0; Score = 0; }};

    }


    public List<AStarEntry> GetAllPath (float pMoveScore, final Map map) {


        OpenList = new ArrayList<>();
        OpenList.add(start);

        ClosedList.clear();

        while (OpenList.size() > 0) {

            final AStarEntry actual = OpenList.get(0);


            AStarEntry north = new AStarEntry() {{ x = actual.x; y = actual.y + 1; Score = map.getMapWeight(actual.x, actual.y + 1) + actual.Score + 1;}};
            if (north.Score <= pMoveScore)
                checkEntry(north);

            AStarEntry south = new AStarEntry() {{ x = actual.x; y = actual.y - 1; Score = map.getMapWeight(actual.x, actual.y - 1) + actual.Score + 1;}};
            if (south.Score <= pMoveScore)
                checkEntry(south);

            AStarEntry east = new AStarEntry() {{ x = actual.x + 1; y = actual.y; Score = map.getMapWeight(actual.x + 1, actual.y) + actual.Score + 1;}};
            if (east.Score <= pMoveScore)
                checkEntry(east);

            AStarEntry west = new AStarEntry() {{ x = actual.x - 1; y = actual.y; Score = map.getMapWeight(actual.x - 1, actual.y) + actual.Score + 1;}};
            if (west.Score <= pMoveScore)
                checkEntry(west);


            OpenList.remove(actual);

            AStarEntry existing = closedListFirstOrDefault(actual.x, actual.y);
            if (existing != null) {
                if (existing.Score > actual.Score) {
                    ClosedList.add(actual);
                    ClosedList.remove(existing);
                }
            }
            else
                ClosedList.add(actual);

        }

        return ClosedList;

    }

    private void checkEntry(AStarEntry entry) {
        AStarEntry existing = closedListFirstOrDefault(entry.x, entry.y);
        if (existing != null)
        {
            if (existing.Score > entry.Score) {
                ClosedList.remove(existing);
                OpenList.add(entry);
            }

        }
        else
            OpenList.add(entry);
    }


    private AStarEntry closedListFirstOrDefault(int x, int y) {
        for (int i = 0; i < ClosedList.size(); i++) {
            if (ClosedList.get(i).x == x && ClosedList.get(i).y == y) return ClosedList.get(i);
        }

        return null;
    }

}
