package com.dragonrider.tactics.characterui;

import android.graphics.Color;

import com.dragonrider.tactics.entity.Entity;
import com.dragonrider.tactics.gear.Armor;
import com.dragonrider.tactics.gear.Item;
import com.dragonrider.tactics.gear.Wearable;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import java.util.List;

/**
 * Created by dragonrider on 25/05/15. jkj
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

    private int mScreenWidth;
    private int mScreenHeight;


    public InventoryScene(VertexBufferObjectManager vertexBufferObjectManager, int ScreenWidth, int ScreenHeight, Entity selectedEntity) {
        this.refVBOM = vertexBufferObjectManager;
        mItems = selectedEntity.getCharacter().getInventory();

        maxCol =  (ScreenWidth / 64) - 1;

        maxRow = (ScreenHeight - ScreenWidth) / 64;

        decalWidth = (ScreenWidth - (maxCol * 64)) / 2f;

        entity = selectedEntity;

        fEntitySize = (float)ScreenWidth;
        fEntityPositionX = ScreenWidth / 4f;
        fEntityPositionY = ScreenHeight + (ScreenWidth / 2f) + (ScreenWidth / 16f);

        this.mScreenHeight = ScreenHeight;
        this.mScreenWidth = ScreenWidth;


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


    Rectangle mEquipmentHat;
    Rectangle mEquipmentChest;
    Rectangle mEquipmentArms;
    Rectangle mEquipmentLegs;
    Rectangle mEquipmentFeet;

    private void RearrangeIcons() {

        this.clearTouchAreas();
        this.detachChildren();





        updateEntity();

        //Equipement

        mEquipmentHat.detachChildren();
        mEquipmentHat = new Rectangle(3 * this.mScreenWidth / 4, this.mScreenHeight - (mScreenWidth / 10), 96, 96, refVBOM) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                return entity.getCharacter().getHatArmor() != null && HandleItemMove(entity.getCharacter().getHatArmor(), mEquipmentHat, pSceneTouchEvent);
            }
        };
        if (entity.getCharacter().getHatArmor() != null)
            mEquipmentHat.attachChild(entity.getCharacter().getHatArmor().getIcon());

        this.attachChild(mEquipmentHat);

        mEquipmentChest.detachChildren();
        mEquipmentChest = new Rectangle(3 * this.mScreenWidth / 4, this.mScreenHeight - ( 3 * mScreenWidth / 10), 96, 96, refVBOM) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                return entity.getCharacter().getChestArmor() != null && HandleItemMove(entity.getCharacter().getChestArmor(), mEquipmentChest, pSceneTouchEvent);
            }
        };
        if (entity.getCharacter().getChestArmor() != null)
            mEquipmentChest.attachChild(entity.getCharacter().getChestArmor().getIcon());

        this.attachChild(mEquipmentChest);

        mEquipmentArms.detachChildren();
        mEquipmentArms = new Rectangle(3 * this.mScreenWidth / 4, this.mScreenHeight - (5 * mScreenWidth / 10), 96, 96, refVBOM) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                return entity.getCharacter().getArmArmor() != null && HandleItemMove(entity.getCharacter().getArmArmor(), mEquipmentArms, pSceneTouchEvent);
            }
        };
        if (entity.getCharacter().getArmArmor() != null)
            mEquipmentArms.attachChild(entity.getCharacter().getArmArmor().getIcon());

        this.attachChild(mEquipmentArms);

        mEquipmentLegs.detachChildren();
        mEquipmentLegs = new Rectangle(3 * this.mScreenWidth / 4, this.mScreenHeight - (7 * mScreenWidth / 10), 96, 96, refVBOM) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                return entity.getCharacter().getLegArmor() != null && HandleItemMove(entity.getCharacter().getLegArmor(), mEquipmentLegs, pSceneTouchEvent);
            }
        };
        if (entity.getCharacter().getLegArmor() != null)
            mEquipmentLegs.attachChild(entity.getCharacter().getLegArmor().getIcon());

        this.attachChild(mEquipmentLegs);

        mEquipmentFeet = new Rectangle(3 * this.mScreenWidth / 4, this.mScreenHeight - (9 * mScreenWidth / 10), 96, 96, refVBOM) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                return entity.getCharacter().getFeetArmor() != null && HandleItemMove(entity.getCharacter().getFeetArmor(), mEquipmentFeet, pSceneTouchEvent);
            }
        };
        if (entity.getCharacter().getFeetArmor() != null)
            mEquipmentFeet.attachChild(entity.getCharacter().getFeetArmor().getIcon());

        this.attachChild(mEquipmentFeet);


        float posX = 0;
        float posY = 48f + (maxRow * 64f);


        for (final Item item : mItems) {

            Rectangle rectangle = new Rectangle(posX * 64 + decalWidth, posY, 62, 62, refVBOM) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                    return HandleItemMove(item, this, pSceneTouchEvent);
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




    private float fInitialPositionX;
    private float fInitialPositionY;

    private Item SelectedDragItem;
    private Rectangle SelectedDragRectangle;




    private boolean HandleItemMove(Item item, Rectangle rectangle, TouchEvent pSceneTouchEvent) {

        if (SelectedDragItem != null && SelectedDragItem != item ) return false;



        if (pSceneTouchEvent.isActionUp() || pSceneTouchEvent.isActionCancel())
        {
            item.getIcon().setSize(42, 42);
            rectangle.setScale(1f);

            SelectedDragItem = null;
            SelectedDragRectangle = null;

            if (CheckIfFit(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), item)) {
                entity.getCharacter().Equip((Wearable) item);
                RearrangeIcons();
            }
            else {
                //Annulation du mouvement
                rectangle.setPosition(fInitialPositionX, fInitialPositionY);
            }
        }
        if (pSceneTouchEvent.isActionDown()) {
            item.getIcon().setSize(96, 96);
            rectangle.setScale(3f);
            SelectedDragItem = item;
            SelectedDragRectangle = rectangle;
            fInitialPositionX = rectangle.getX();
            fInitialPositionY = rectangle.getY();

            rectangle.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());


        }
        if (pSceneTouchEvent.isActionMove() || pSceneTouchEvent.isActionOutside()) {
            rectangle.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());

        }



        return true;

    }


    private boolean CheckIfFit(float pX, float pY, Item item) {

        Debug.d("hopla", "pX = " + String.valueOf(pX) + ", pY = " + String.valueOf(pY));

        if (pX >= 3 * mScreenWidth / 4 - 48 && pX <= 3 * mScreenWidth / 4 + 48 &&
                pY >= mScreenHeight - (mScreenWidth / 10) - 48 && pY <= mScreenHeight - (mScreenWidth / 10) + 48
                && item instanceof Armor && ((Armor)item).getArmorType() == Armor.ArmorType.ARMOR_HAT )
            return true;
        else if (pX >= 3 * mScreenWidth / 4 - 48 && pX <= 3 * mScreenWidth / 4 + 48 &&
                pY >= mScreenHeight - (3 * mScreenWidth / 10) - 48 && pY <= mScreenHeight - (3 * mScreenWidth / 10) + 48
                && item instanceof Armor && ((Armor)item).getArmorType() == Armor.ArmorType.ARMOR_CHEST )
            return true;
        else if (pX >= 3 * mScreenWidth / 4 - 48 && pX <= 3 * mScreenWidth / 4 + 48 &&
                pY >= mScreenHeight - (5 * mScreenWidth / 10) - 48 && pY <= mScreenHeight - (5 * mScreenWidth / 10) + 48
                && item instanceof Armor && ((Armor)item).getArmorType() == Armor.ArmorType.ARMOR_ARMS )
            return true;
        else if (pX >= 3 * mScreenWidth / 4 - 48 && pX <= 3 * mScreenWidth / 4 + 48 &&
                pY >= mScreenHeight - (7 * mScreenWidth / 10) - 48 && pY <= mScreenHeight - (7 * mScreenWidth / 10) + 48
                && item instanceof Armor && ((Armor)item).getArmorType() == Armor.ArmorType.ARMOR_LEGS )
            return true;
        else if (pX >= 3 * mScreenWidth / 4 - 48 && pX <= 3 * mScreenWidth / 4 + 48 &&
                pY >= mScreenHeight - (9 * mScreenWidth / 10) - 48 && pY <= mScreenHeight - (9 * mScreenWidth / 10) + 48
                && item instanceof Armor && ((Armor)item).getArmorType() == Armor.ArmorType.ARMOR_FEET )
            return true;

        return false;
    }


}
