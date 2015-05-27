package com.dragonrider.tactics.characterui;

import android.graphics.Color;

import com.dragonrider.tactics.entity.Entity;
import com.dragonrider.tactics.gear.Item;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.List;

/**
 * Created by dragonrider on 25/05/15.
 */
public class InventoryScene extends Scene {

    private final int maxCol;
    private final int maxRow;
    private final VertexBufferObjectManager refVBOM;
    private final float decalWidth;

    private Entity entity;
    private float fEntitySize;
    private float fEntityPositionX;
    private float fEntityPositionY;

    private List<Item> mItems;


    public InventoryScene(VertexBufferObjectManager vertexBufferObjectManager, List<Item> items, int ScreenWidth, int ScreenHeight, Entity selectedEntity) {
        this.refVBOM = vertexBufferObjectManager;
        mItems = items;

        maxCol =  (ScreenWidth / 64) - 1;

        maxRow = (ScreenHeight - ScreenWidth) / 64;

        decalWidth = (ScreenWidth - (maxCol * 64)) / 2f;

        entity = selectedEntity;

        fEntitySize = (float)ScreenWidth;
        fEntityPositionX = ScreenWidth / 4f;
        fEntityPositionY = ScreenHeight + (ScreenWidth / 2f) + (ScreenWidth / 16f);

        updateEntity();

        RearrangeIcons();
    }

    private void updateEntity() {
        entity.AttachToScene(this, this, refVBOM);
        entity.setSize(fEntitySize, fEntitySize);
        entity.setAnchorCenter(0f, 1f);
        entity.UpdatePosition(fEntityPositionX, fEntityPositionY);
        entity.Animation = Entity.ANIM_STATE.IDLE;
        entity.Orientation = Entity.ORIENTATION.WEST;
        entity.Animate();
    }


    private void RearrangeIcons() {




        float posX = 0;
        float posY = 48f + (maxRow * 64f);


        for (Item item : mItems) {

            Rectangle rectangle = new Rectangle(posX * 64 + decalWidth, posY, 62, 62, refVBOM) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                }
            };
            rectangle.setColor(Color.GRAY);



            item.getIcon().setPosition(32f, 32f);
            rectangle.attachChild(item.getIcon());
            this.registerTouchArea(rectangle);

            this.attachChild(rectangle);

            posX += 1;
            if (posX > maxCol ) {
                posX = 0;
                posY -= 64;
                if (posY <= 0)
                    break;
            }
        }


    }





}
