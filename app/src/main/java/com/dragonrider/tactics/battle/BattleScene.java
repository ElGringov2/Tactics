package com.dragonrider.tactics.battle;

import android.content.res.AssetManager;
import android.graphics.Point;

import com.dragonrider.tactics.Tilemap.Map;

import com.dragonrider.tactics.entity.Entity;
import com.dragonrider.tactics.ui.hud.CharacterHUD;
import com.dragonrider.tactics.ui.hud.CustomHUD;
import com.dragonrider.tactics.utils.AStar;
import com.dragonrider.tactics.utils.Fonts;


import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Gradient;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dragonrider on 14/05/15.
 * Gere les scenes de combat
 */
public class BattleScene extends Scene {


    private Map mMap;
    private float mTouchPositionX;
    private float mTouchPositionY;

    private VertexBufferObjectManager refVBOM;

    private Camera mCamera;

    private CharacterHUD mCharhud;
    private CustomHUD mNeutralHud;

    public BattleScene(Map map, Camera refCamera, VertexBufferObjectManager vbom, TextureManager textureManager, AssetManager assetManager, int screenWidth, int screenHeight) {
        mMap = map;
        super.setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
                if (pSceneTouchEvent.isActionDown())  {
                    mTouchPositionX = pSceneTouchEvent.getX();
                    mTouchPositionY = pSceneTouchEvent.getY();
                }
                if (pSceneTouchEvent.isActionMove()) {

                    mCamera.setCenter(mCamera.getCenterX() + (mTouchPositionX - pSceneTouchEvent.getX()), mCamera.getCenterY() + (mTouchPositionY - pSceneTouchEvent.getY()));
                }

                return true;
            }
        });


        mCamera = refCamera;

        refVBOM = vbom;

        mCharhud = new CharacterHUD(vbom, textureManager, assetManager, screenWidth, screenHeight, Fonts.NormalBlackFont, Fonts.SmallBlackFont);
        mNeutralHud = new CustomHUD(vbom, textureManager, assetManager, screenWidth, screenHeight);

        mCamera.setHUD(mNeutralHud);


        mMap.AttachBaseLayerToScene(this, vbom);





        this.gridLayer = new org.andengine.entity.Entity();

        this.attachChild(gridLayer);



        entityLayer = new org.andengine.entity.Entity();
        this.attachChild(entityLayer);
        this.registerTouchArea(entityLayer);



        EntityPosition = new ArrayList<Point>();



        mMap.AttachTopLayerToScene(this, vbom);

    }

    private org.andengine.entity.Entity entityLayer;

    private List<Entity> entities = new ArrayList<>();

    private org.andengine.entity.Entity gridLayer;

    public int AddEntity(Entity entity, VertexBufferObjectManager vertexBufferObjectManager)  {



        int iCount = entities.size();
        entities.add(entity);
        entity.AttachToScene(entityLayer, this, vertexBufferObjectManager);
        entity.OnTouch = entityTouch;
        EntityPosition.add(new Point());
        return iCount;
    }

    public void setEntityPosition(int ID, int pX, int pY) {
        entities.get(ID).UpdatePosition(pX * 64, pY * 64 + 16);
        EntityPosition.set(ID, new Point(pX, pY));
    }


    private ArrayList<Point> EntityPosition;



    Entity SelectedEntity = null;


    private Entity.IOnTouch entityTouch = new Entity.IOnTouch() {
        @Override
        public void OnTouch(Entity entity, TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

            if (pSceneTouchEvent.isActionUp()) {
                if (SelectedEntity != null)
                    UnSelectEntity(SelectedEntity);

                SelectedEntity = entity;
                mCamera.setHUD(mCharhud);

                SelectEntity(SelectedEntity);
            }

        }
    };


    private void SelectEntity(Entity entity) {

        entity.Select(0.0f, 0.5f, 0.0f, 1.0f);
        SelectedEntity = entity;
        UpdateGrid();
    }

    private void UnSelectEntity (Entity entity) {

        entity.Unselect();
        SelectedEntity = null;
        UpdateGrid();

    }

    private void UpdateGrid() {

        for (int i = 0; i < gridLayer.getChildCount(); i++)
        {
            this.unregisterTouchArea(gridLayer.getChildByIndex(i));
        }

        gridLayer.detachChildren();

        if (SelectedEntity == null) return;


        int x = 0;
        int y = 0;

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) == SelectedEntity) {
                x = EntityPosition.get(i).x;
                y = EntityPosition.get(i).y;

                break;

            }
        }


        AStar aStar = new AStar(x, y);
        List<AStar.AStarEntry> path = aStar.GetAllPath(4, mMap);

        for (final AStar.AStarEntry entry : path) {
            if (entry.x <= 0 || entry.y <= 0 || entry.x >= mMap.getGridSize() || entry.y >= mMap.getGridSize()) continue;

            boolean bContinue = false;
            for (int i = 0; i< entities.size(); i++) {
                if (EntityPosition.get(i).x == entry.x && EntityPosition.get(i).y == entry.y)  { bContinue  = true; break;}
            }

            if (bContinue) continue;


            Rectangle rectangle = new Rectangle(entry.x * 64 + 1,
                    entry.y * 64 + 1,
                    62, 62,
                    this.refVBOM) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                    if (!pSceneTouchEvent.isActionUp()) return false;
                    MoveEntity(SelectedEntity, entry.x, entry.y, entry.Score);

                    return true;
                }
            };



            rectangle.setColor(1f, 1f, 0f, 0.25f);
            gridLayer.attachChild(rectangle);

        }


        path = aStar.GetAllPath(6, mMap);

        for (final AStar.AStarEntry entry : path) {
            if (entry.x <= 0 || entry.y <= 0 || entry.x >= mMap.getGridSize() || entry.y >= mMap.getGridSize()) continue;

            boolean bContinue = false;
            for (int i = 0; i< entities.size(); i++) {
                if (EntityPosition.get(i).x == entry.x && EntityPosition.get(i).y == entry.y)  { bContinue  = true; break;}
            }

            if (bContinue) continue;


            Rectangle rectangle = new Rectangle(entry.x * 64 + 1,
                    entry.y * 64 + 1,
                    62, 62,
                    this.refVBOM) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    if (!pSceneTouchEvent.isActionUp()) return false;

                    MoveEntity(SelectedEntity, entry.x, entry.y, entry.Score);

                    return true;
                }
            };



            rectangle.setColor(0f, 0f, 1f, 0.25f);
            gridLayer.attachChild(rectangle);

            this.registerTouchArea(rectangle);


        }



    }


    private void MoveEntity(Entity selectedEntity, int newX, int newY, float MoveDuration) {

        selectedEntity.Move(newX * 64, newY * 64, MoveDuration);

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) == SelectedEntity) {
                EntityPosition.get(i).x = newX;
                EntityPosition.get(i).y = newY;

                break;
            }
        }

                UnSelectEntity(selectedEntity);

    }


}

