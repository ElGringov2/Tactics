package com.dragonrider.tactics.ui;

import android.content.res.AssetManager;

import com.dragonrider.tactics.utils.Fonts;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

/**
 * Created by dragonrider on 15/05/15. ze
 */
public class TacticsButton extends Entity {

    public interface OnClickInterface {
        void OnClick();
    }

    private OnClickInterface onClickInterface;

    TiledTextureRegion region;
    public TacticsButton(String Content, float pX, float pY, TextureManager textureManager, AssetManager assetManager, VertexBufferObjectManager pVertexBufferObjectManager, OnClickInterface OnClick) {

        BitmapTextureAtlas textureAtlas = new BitmapTextureAtlas(textureManager, 544, 320, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, assetManager, "ui/ui.png", 0, 0, 17, 10);
        textureAtlas.load();



        mSpriteBatch = new SpriteBatch(pX, pY, textureAtlas, 10, pVertexBufferObjectManager);

        resetButton();
        this.attachChild(mSpriteBatch);


        mText = new Text(pX, pY, Fonts.NormalWhiteFont, "ABCDEFGHIJKLMNOPQRSTUVWXYZ ", pVertexBufferObjectManager);
        mText.setText(Content);


        this.attachChild(mText);

        onClickInterface = OnClick;





    }


    private void pressButton() {
        mSpriteBatch.draw(region.getTextureRegion(153), 0, 0, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(154), 32, 0, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(154), 64, 0, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(154), 96, 0, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(155), 128, 0, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);

        mSpriteBatch.draw(region.getTextureRegion(119), 0, 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(120), 32, 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(120), 64, 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(120), 96, 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(121), 128, 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);

        mSpriteBatch.submit();

    }

    private void resetButton() {
        mSpriteBatch.draw(region.getTextureRegion(102), 0, 0, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(103), 32, 0, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(103), 64, 0, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(103), 96, 0, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(104), 128, 0, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);

        mSpriteBatch.draw(region.getTextureRegion(68), 0, 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(69), 32, 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(69), 64, 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(69), 96, 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
        mSpriteBatch.draw(region.getTextureRegion(70), 128, 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);


        mSpriteBatch.submit();
    }


    public TacticsButton setText(String content) {
        mText.setText(content);
        return this;
    }

    public void attachToScene(Scene pScene) {
        pScene.attachChild(this);
        pScene.registerTouchArea(this);
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

        Debug.d("hopla", pSceneTouchEvent.toString());

        if (pSceneTouchEvent.isActionDown())
            pressButton();
        if (pSceneTouchEvent.isActionUp()) {
            resetButton();
            onClickInterface.OnClick();
        }
        if (pSceneTouchEvent.isActionOutside())
            resetButton();


        return true;
    }


    private SpriteBatch mSpriteBatch;
    private Text mText;

}
