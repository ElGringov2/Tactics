package com.dragonrider.tactics.battle;

import android.content.res.AssetManager;

import com.dragonrider.tactics.Tilemap.Map;

import com.dragonrider.tactics.entity.Entity;
import com.dragonrider.tactics.ui.hud.CharacterHUD;
import com.dragonrider.tactics.ui.hud.CustomHUD;
import com.dragonrider.tactics.utils.Fonts;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dragonrider on 14/05/15.
 * Gere les scenes de combat
 */
public class BattleScene extends Scene {


    private Map mMap;
    private float mTouchPositionX;
    private float mTouchPositionY;

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

        mCharhud = new CharacterHUD(vbom, textureManager, assetManager, screenWidth, screenHeight, Fonts.NormalBlackFont, Fonts.SmallBlackFont);
        mNeutralHud = new CustomHUD(vbom, textureManager, assetManager, screenWidth, screenHeight);

        mCamera.setHUD(mNeutralHud);


        mMap.AttachToScene(this, vbom);



        selectionRectangle = new Rectangle(0, 0, 64, 64, vbom);
        selectionRectangle.setColor(1.0f, 1.0f, 1.0f, 0.25f);


        this.gridLayer = new org.andengine.entity.Entity();
        this.attachChild(gridLayer);


        entityLayer = new org.andengine.entity.Entity();
        this.attachChild(entityLayer);



        this.attachChild(selectionRectangle);

    }

    private org.andengine.entity.Entity entityLayer;

    private List<Entity> entities = new ArrayList<>();

    private org.andengine.entity.Entity gridLayer;

    public int AddEntity(Entity entity, VertexBufferObjectManager vertexBufferObjectManager)  {



        int iCount = entities.size();
        entities.add(entity);
        entity.AttachToScene(entityLayer, this, vertexBufferObjectManager);
        entity.OnTouch = entityTouch;
        return iCount;
    }

    public void setEntityPosition(int ID, int pX, int pY) {
        entities.get(ID).UpdatePosition(pX * 64 + 32, pY * 64 + 64);
    }





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

    private Rectangle selectionRectangle;

    private void SelectEntity(Entity entity) {

        entity.Select(0.0f, 0.5f, 0.0f, 1.0f);
    }

    private void UnSelectEntity (Entity entity) {

        entity.Unselect();
    }
}
