package com.dragonrider.tactics.entity;


import android.content.res.AssetManager;

import com.dragonrider.tactics.gear.Wearable;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dragonrider on 10/05/2015.jhjhj
 */
public class Entity {


    public static String[] sBodyTypes = {"dark", "dark2", "darkelf", "darkelf2", "light", "orc", "red_orc", "tanned", "tanned2"};
    public static String[] sHairTypes = {"bangs","bangslong","bangslong2","bangsshort","bedhead","bunches","jewfro","long","longhawk","longknot","loose","messy1","messy2","mohawk","page","page2","parted","pixie","plain","ponytail","ponytail2","princess","shorthawk","shortknot","shoulderl","shoulderr","swoop","unkempt","xlong","xlongknot"};
    public static String[] sHairColors = {"black","blonde","blonde2","blue","blue2","brown","brown2","brunette","brunette2","dark-blonde","gold","gray","gray2","green","green2","light-blonde","light-blonde2","pink","pink2","purple","raven","raven2","redhead","redhead2","ruby-red","white-blonde","white-blonde2","white-cyan","white"};

    public enum BodyTypes {DARK, DARK2, DARKELF, DARKELF2, LIGHT, ORC, RED_ORC, TANNED, TANNED2}
    public enum HairTypes {BANGS,BANGSLONG,BANGSLONG2,BANGSSHORT,BEDHEAD,BUNCHES,JEWFRO,LONG,LONGHAWK,LONGKNOT,LOOSE,MESSY1,MESSY2,MOHAWK,PAGE,PAGE2,PARTED,PIXIE,PLAIN,PONYTAIL,PONYTAIL2,PRINCESS,SHORTHAWK,SHORTKNOT,SHOULDERL,SHOULDERR,SWOOP,UNKEMPT,XLONG,XLONGKNOT}
    public enum HairColors {BLACK,BLONDE,BLONDE2,BLUE,BLUE2,BROWN,BROWN2,BRUNETTE,BRUNETTE2,DARKBLONDE,GOLD,GRAY,GRAY2,GREEN,GREEN2,LIGHTBLONDE,LIGHTBLONDE2,PINK,PINK2,PURPLE,RAVEN,RAVEN2,REDHEAD,REDHEAD2,RUBYRED,WHITEBLONDE,WHITEBLONDE2,WHITECYAN,WHITE}

    private TiledTextureRegion mBodyTextureRegion;
    private TiledTextureRegion mHairTextureRegion;
    private AnimatedSprite mBodySprite;
    private AnimatedSprite mHairSprite;



    private String mSex = "female";
    private String mBodyType = "tanned";
    private String mHairString = "long/blonde";



    private List<Wearable> Wear = new ArrayList<>();



    public float PositionX = 100;
    public float PositionY = 100;

    public ORIENTATION Orientation = ORIENTATION.SOUTH;
    public ANIM_STATE Animation = ANIM_STATE.IDLE;

    public Entity(boolean IsMale, BodyTypes BodyType, HairTypes HairType, HairColors HairColor) {
        this.mSex = IsMale ? "male" : "female";
        this.mBodyType = sBodyTypes[BodyType.ordinal()];
        this.mHairString = sHairTypes[HairType.ordinal()] + "/" + sHairColors[HairColor.ordinal()];

    }

    public void CreateResources(TextureManager textureManager, AssetManager assetManager) {
        BitmapTextureAtlas mBodyTexture = new BitmapTextureAtlas(textureManager,  832, 1344, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        mBodyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBodyTexture, assetManager, "gfx/char/body/" + mSex + "/" + mBodyType + ".png", 0, 0, 13, 21);
        mBodyTexture.load();

        BitmapTextureAtlas mHairTexture = new BitmapTextureAtlas(textureManager,  832, 1344, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        mHairTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mHairTexture, assetManager, "gfx/char/hair/" + mSex + "/" + mHairString + ".png", 0, 0, 13, 21);
        mHairTexture.load();



        for (int i = 0; i < Wear.size(); i++)
            Wear.get(i).CreateResources(textureManager, assetManager);

    }

    public String Name = "";




    public interface IOnTouch {
        void OnTouch(Entity entity, TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY);
    }

    public IOnTouch OnTouch = null;

    private void RaiseOnTouch(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

        OnTouch.OnTouch(this, pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
    }

    public void AttachToScene (org.andengine.entity.Entity Layer, Scene mScene, VertexBufferObjectManager vertexBufferObjectManager) {

        mBodySprite = new AnimatedSprite(PositionX, PositionY, mBodyTextureRegion, vertexBufferObjectManager) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (OnTouch == null)
                    return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                else
                    RaiseOnTouch(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                return true;
            }
        };

        mHairSprite =  new AnimatedSprite(PositionX, PositionY, mHairTextureRegion, vertexBufferObjectManager);


        mScene.registerTouchArea(mBodySprite);

        Collections.sort(Wear, new Comparator<Wearable>() {
            @Override
            public int compare(Wearable w1, Wearable w2) {
                return w1.DrawOrder > w2.DrawOrder ? -1 : (w1.DrawOrder == w2.DrawOrder ? 0 : 1);
            }
        });

        for (int i = 0; i < Wear.size(); i++) {
            if (Wear.get(i).DrawOrder < 2)
                Layer.attachChild(Wear.get(i).CreateSprite(PositionX, PositionY, vertexBufferObjectManager));
        }

        Layer.attachChild(mBodySprite);
        Layer.attachChild(mHairSprite);

        for (int i = 0; i < Wear.size(); i++) {
            if (Wear.get(i).DrawOrder > 2)
                Layer.attachChild(Wear.get(i).CreateSprite(PositionX, PositionY, vertexBufferObjectManager));
        }


    }

    public void Animate() {



        mBodySprite.animate(GetFramesDuration(), GetFramesID());
        mHairSprite.animate(GetFramesDuration(), GetFramesID());
        for (int i = 0; i < Wear.size(); i++)
            Wear.get(i).Animate(GetFramesDuration(), GetFramesID());//Wear.get(i).Animate(this.Animation, this.Orientation);

    }

//    public void Idle() {
//        this.Animation = ANIM_STATE.IDLE;
//        this.Animate();
//    }

    public enum ORIENTATION {
        NORTH,
        WEST,
        SOUTH,
        EAST

    }

    public enum ANIM_STATE {
        CASTING,
        ATTACK_SPEAR,
        WALKING,
        ATTACK_SLASH,
        ATTACK_BOW,
        WAKE_UP,
        DEAD,
        IDLE

    }


    public int[] GetFramesID() {

        int[] mReturnArray = new int[] {};

        if (this.Animation == ANIM_STATE.CASTING) mReturnArray = new int[] {0, 1, 2, 3, 4, 5, 6};
        if (this.Animation == ANIM_STATE.ATTACK_SPEAR) mReturnArray = new int[] {52, 52, 54, 55, 56, 57, 58};
        if (this.Animation == ANIM_STATE.WALKING) mReturnArray = new int[] {105, 106, 107, 108, 109, 110, 111, 112};
        if (this.Animation == ANIM_STATE.ATTACK_SLASH) mReturnArray = new int[] {156, 157, 158, 159, 160, 161};
        if (this.Animation == ANIM_STATE.ATTACK_BOW) mReturnArray = new int[] {208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220};

        if (this.Animation == ANIM_STATE.IDLE) mReturnArray = new int[] {104};

        if (this.Animation == ANIM_STATE.WAKE_UP) return new int[]{265, 264, 263, 262, 261, 260};
        if (this.Animation == ANIM_STATE.DEAD) return new int[] {265};


        for (int i = 0; i < mReturnArray.length; i++) {
            mReturnArray[i] = mReturnArray[i] + (this.Orientation.ordinal() * 13);
        }



        return mReturnArray;
    }

    public long[] GetFramesDuration() {
        int[] frames = GetFramesID();

        long[] mReturnArray = new long[frames.length];

        for (int i = 0; i < frames.length; i++) {
            mReturnArray[i] = 100;
        }

        return mReturnArray;
    }



    public void UpdatePosition(float NewPosX, float NewPosY) {
        this.PositionX = NewPosX;
        this.PositionY = NewPosY;

        this.mBodySprite.setPosition(NewPosX, NewPosY);
        this.mHairSprite.setPosition(NewPosX, NewPosY);
        for (int i = 0; i < Wear.size(); i++)
            Wear.get(i).setPosition(NewPosX, NewPosY);
    }



    public void AddWearable(Wearable wear) {
        this.Wear.add(wear);

    }


    public void Select(float pRed, float pGreen, float pBlue, float pAlpha) {
        mHairSprite.setColor(pRed, pGreen, pBlue, pAlpha);
        mBodySprite.setColor(pRed, pGreen, pBlue, pAlpha);

    }
    public void Unselect() {
        mHairSprite.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        mBodySprite.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
