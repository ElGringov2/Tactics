package com.dragonrider.tactics;

import android.graphics.Typeface;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;


import com.dragonrider.tactics.Tilemap.CountrysideMapGenerator;
import com.dragonrider.tactics.Tilemap.Map;
import com.dragonrider.tactics.entity.Entity;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.controller.BaseTouchController;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.IFont;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOException;


public class MainActivity extends SimpleBaseGameActivity {

    //Set the camera Width and Height
    private static int CAMERA_WIDTH = 768;
    private static int CAMERA_HEIGHT = 1280;
    Camera mCamera;


    Scene mScene;


    Entity entity;


    @Override
    public EngineOptions onCreateEngineOptions() {
        Display display = getWindowManager().getDefaultDisplay();
        CAMERA_WIDTH = display.getWidth();  // deprecated
        CAMERA_HEIGHT = display.getHeight();  // deprecated



        mCamera = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, 1000, 1000, 1);



        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);




    }


    Map map;

    @Override
    protected void onCreateResources() throws IOException {

//        Random r = new Random();
//
//        for (int i = 0; i <= 47; i++) {
//
//
//            Entity entity = new Entity(false,
//                    Entity.BodyTypes.values()[r.nextInt(Entity.BodyTypes.values().length)],
//                    Entity.HairTypes.values()[r.nextInt(Entity.HairTypes.values().length)],
//                    Entity.HairColors.values()[r.nextInt(Entity.HairColors.values().length)]);
//            entity.CreateResources(this.getTextureManager(), this.getAssets());
//            entity.Animation = Entity.ANIM_STATE.values()[r.nextInt(Entity.ANIM_STATE.values().length)];
//            entity.Orientation = Entity.ORIENTATION.values()[r.nextInt(Entity.ORIENTATION.values().length)];
//
//            Entities.add(entity);
//        }
//



        CountrysideMapGenerator generator = new CountrysideMapGenerator(0);
        map = generator.Generate(512);


        map.CreateResources(this.getTextureManager(), this.getAssets());




        entity = new Entity(false, Entity.BodyTypes.TANNED, Entity.HairTypes.BEDHEAD, Entity.HairColors.BLACK);
        entity.CreateResources(this.getTextureManager(), this.getAssets());
    }


    @Override
    protected Scene onCreateScene() {





        mScene = new Scene();
        mScene.setBackground(new Background(Color.WHITE));



        map.AttachToScene(mScene, this.getVertexBufferObjectManager());



        entity.AttachToScene(this.mScene, this.getVertexBufferObjectManager());

        entity.Animation = Entity.ANIM_STATE.WALKING;
        entity.Orientation = Entity.ORIENTATION.SOUTH;
        entity.Animate();


        mScene.setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
                mCamera.setCenter(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                return true;
            }
        });

        return mScene;
    }


}
