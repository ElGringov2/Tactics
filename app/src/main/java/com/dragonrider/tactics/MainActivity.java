package com.dragonrider.tactics;

import com.dragonrider.tactics.Tilemap.CountrysideMapGenerator;
import com.dragonrider.tactics.Tilemap.Map;
import com.dragonrider.tactics.battle.BattleScene;
import com.dragonrider.tactics.entity.Entity;
import com.dragonrider.tactics.utils.Fonts;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;
import java.util.Random;


public class MainActivity extends SimpleBaseGameActivity {


    Camera mCamera;








    @Override
    public EngineOptions onCreateEngineOptions() {


        int CAMERA_HEIGHT = this.getResources().getDisplayMetrics().heightPixels;
        int CAMERA_WIDTH = this.getResources().getDisplayMetrics().widthPixels;


        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);



        mCamera.setCenter(128f * 32f / 2f, 128f * 32f / 2f);

        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);




    }

    BattleScene scene;

    @Override
    protected void onCreateResources() throws IOException {



        int HEIGHT = this.getResources().getDisplayMetrics().heightPixels;
        int WIDTH = this.getResources().getDisplayMetrics().widthPixels;

        float fElementHeight = (HEIGHT - WIDTH - 32f) / 10f;


        Fonts.Init(fElementHeight, this.getTextureManager(), this.getFontManager(), this.getAssets());



        CountrysideMapGenerator generator = new CountrysideMapGenerator(0);
        Map map = generator.Generate(128);


        map.CreateResources(this.getTextureManager(), this.getAssets());


        scene=new BattleScene(map, mCamera, this.getVertexBufferObjectManager(), this.getTextureManager(), this.getAssets(), WIDTH, HEIGHT );
        Random r = new Random(0);

        for (int i = 1; i < 63; i++) {

            int mX = r.nextInt(62) + 1;
            int mY = r.nextInt(62) + 1;





            Entity entity = new Entity(false,
                    Entity.BodyTypes.values()[r.nextInt(Entity.BodyTypes.values().length)],
                    Entity.HairTypes.values()[r.nextInt(Entity.HairTypes.values().length)],
                    Entity.HairColors.values()[r.nextInt(Entity.HairColors.values().length)]);
            entity.CreateResources(this.getTextureManager(), this.getAssets());
            entity.Animation = Entity.ANIM_STATE.values()[r.nextInt(Entity.ANIM_STATE.values().length)];
            entity.Orientation = Entity.ORIENTATION.values()[r.nextInt(Entity.ORIENTATION.values().length)];

            entity.Name = "Char" + String.valueOf(i);

            int id = scene.AddEntity(entity, this.getVertexBufferObjectManager());
            scene.setEntityPosition(id, mX, mY);
            entity.Animate();
        }




    }



    @Override
    protected Scene onCreateScene() {

        return scene;

    }


}
