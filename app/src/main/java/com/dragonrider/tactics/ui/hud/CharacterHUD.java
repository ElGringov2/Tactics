package com.dragonrider.tactics.ui.hud;

import android.content.res.AssetManager;

import com.dragonrider.tactics.ui.TacticsButton;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

/**
 * Created by dragonrider on 15/05/15. k
 */
public class CharacterHUD extends CustomHUD {
    public CharacterHUD(VertexBufferObjectManager vbom, TextureManager textureManager, AssetManager assetManager, int ScreenWidth, int ScreenHeight, Font mainFont, Font secondaryFont) {
        super(vbom, textureManager, assetManager, ScreenWidth, ScreenHeight);

        float fHudSize = ScreenHeight - ScreenWidth;
        float fElementHeight = (ScreenHeight - ScreenWidth - 32f) / 10f;



        Rectangle rect = new Rectangle(ScreenWidth / 2, fHudSize - 16f - (fElementHeight / 2), ScreenWidth - 32f, fElementHeight, vbom);
        //rect.setAnchorCenter(0, 1.0f);
        rect.setColor(Color.RED);

        this.attachChild(rect);



        mNameText = new Text(ScreenWidth / 2,
                fHudSize - 16f - (fElementHeight  /2),
                mainFont,
                "[] ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890'-",
                vbom);
        mNameText.setText("[Nom du personnage]");
        mNameText.setSize(ScreenWidth - 32f, fElementHeight);
        //mNameText.setAnchorCenter(0f, 1.0f);


        mNameText.setHorizontalAlign(HorizontalAlign.CENTER);


        this.attachChild(mNameText);



        Rectangle rect2 = new Rectangle(16f,
                fHudSize - 16f - (fElementHeight * 2),
                ScreenWidth / 2f - 32f,
                fElementHeight * 2f,
                vbom);
        rect2.setColor(Color.YELLOW);
        rect2.setAnchorCenter(0, 0.5f);
        this.attachChild(rect2);

        mStaminaText = new Text(16f, fHudSize - 16f - (fElementHeight * 2), mainFont, "Endurace: 0123456789Bls", vbom);
        mStaminaText.setAnchorCenter(0, 0.5f);
        mStaminaText.setSize(ScreenWidth / 2f - 32f, fElementHeight * 2f);
        mStaminaText.setText("Endurance: 12\nBlessures:");

        this.attachChild(mStaminaText);


        Rectangle rect3 = new Rectangle(32f,
                fHudSize - 16f - (fElementHeight * 3),
                (ScreenWidth / 2f) - 48f,
                (fElementHeight * 7f) + (fElementHeight / 2) - 32f,
                vbom);
        rect3.setColor(Color.CYAN);
        rect3.setAnchorCenter(0f, 1f);
        this.attachChild(rect3);



        mWoundsText = new Text(32f,
                fHudSize - 16f - (fElementHeight * 3),
                secondaryFont,
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890àêéè'-",
                vbom);
        mWoundsText.setHorizontalAlign(HorizontalAlign.LEFT);
        mWoundsText.setAnchorCenter(0, 1f);
        mWoundsText.setSize((ScreenWidth / 2f) - 48f, (fElementHeight * 7f) + (fElementHeight / 2) - 32f);
        mWoundsText.setText("Légère:\n - Blessure à la tête\n -\n -\n -\n -\nGraves:\n -\n - Bras atrophié");

        this.attachChild(mWoundsText);




        TacticsButton button = new TacticsButton("DEPLACEMENT", 16f + (ScreenWidth * 0.75f), fHudSize - 16f - (fElementHeight * 3), textureManager, assetManager, vbom, new TacticsButton.OnClickInterface() {
            @Override
            public void OnClick() {
                Debug.d("hopla", "Clicked!");
            }
        });



        button.attachToScene(this);

    }

    Text mNameText;
    Text mWoundsText;
    Text mStaminaText;
}
