package com.dragonrider.tactics;

import com.dragonrider.tactics.Tilemap.CountrysideMapGenerator;
import com.dragonrider.tactics.Tilemap.Map;
import com.dragonrider.tactics.battle.BattleScene;
import com.dragonrider.tactics.entity.Entity;
import com.dragonrider.tactics.gear.Armor;
import com.dragonrider.tactics.gear.Item;
import com.dragonrider.tactics.gear.Weapon;
import com.dragonrider.tactics.utils.Fonts;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

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


        Item.InitializeTextureRegion(this.getTextureManager(), this.getAssets());


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




            Entity entity = new Entity(r.nextBoolean(),
                    Entity.BodyTypes.values()[r.nextInt(Entity.BodyTypes.values().length)],
                    Entity.HairTypes.values()[r.nextInt(Entity.HairTypes.values().length)],
                    Entity.HairColors.values()[r.nextInt(Entity.HairColors.values().length)]);
            entity.CreateResources(this.getTextureManager(), this.getAssets());
            entity.Animation = Entity.ANIM_STATE.ATTACK_SLASH;
//            entity.Animation = Entity.ANIM_STATE.values()[r.nextInt(Entity.ANIM_STATE.values().length)];
            entity.Orientation = Entity.ORIENTATION.values()[r.nextInt(Entity.ORIENTATION.values().length)];

            entity.Name = "Char" + String.valueOf(i);



            entity.AddWearable(Armor.getItem(entity.getIsMale(),
                    Armor.ArmorClass.values()[r.nextInt(Armor.ArmorClass.values().length)],
                    Armor.ArmorType.ARMOR_CHEST,
                    0,
                    Color.WHITE,
                    this.getVertexBufferObjectManager(),
                    this.getTextureManager(),
                    this.getAssets(),
                    0));

            entity.AddWearable(Armor.getItem(entity.getIsMale(),
                    Armor.ArmorClass.values()[r.nextInt(Armor.ArmorClass.values().length)],
                    Armor.ArmorType.ARMOR_ARMS,
                    0,
                    Color.WHITE,
                    this.getVertexBufferObjectManager(),
                    this.getTextureManager(),
                    this.getAssets(),
                    0));

            entity.AddWearable(Armor.getItem(entity.getIsMale(),
                    Armor.ArmorClass.values()[r.nextInt(Armor.ArmorClass.values().length)],
                    Armor.ArmorType.ARMOR_FEET,
                    0,
                    Color.WHITE,
                    this.getVertexBufferObjectManager(),
                    this.getTextureManager(),
                    this.getAssets(),
                    0));

            entity.AddWearable(Armor.getItem(entity.getIsMale(),
                    Armor.ArmorClass.values()[r.nextInt(Armor.ArmorClass.values().length)],
                    Armor.ArmorType.ARMOR_HAT,
                    0,
                    Color.WHITE,
                    this.getVertexBufferObjectManager(),
                    this.getTextureManager(),
                    this.getAssets(),
                    0));

            entity.AddWearable(Armor.getItem(entity.getIsMale(),
                    Armor.ArmorClass.values()[r.nextInt(Armor.ArmorClass.values().length)],
                    Armor.ArmorType.ARMOR_LEGS,
                    0,
                    Color.WHITE,
                    this.getVertexBufferObjectManager(),
                    this.getTextureManager(),
                    this.getAssets(),
                    0));



            Weapon weapon = Weapon.getItem(
                    entity.getIsMale(),
                    Weapon.WeaponType.values()[r.nextInt(Weapon.WeaponType.values().length)],
                    r.nextInt(5),
                    -r.nextInt(10),
                    r.nextInt(10),
                    this.getVertexBufferObjectManager(),
                    this.getTextureManager(),
                    this.getAssets(),
                    0);


            entity.AddWearable(weapon);


            if (weapon.getWeaponType() == Weapon.WeaponType.WEAPON_SPEAR) entity.Animation = Entity.ANIM_STATE.ATTACK_SPEAR;
            if (weapon.getWeaponType() == Weapon.WeaponType.WEAPON_BOW) {
                entity.Animation = Entity.ANIM_STATE.ATTACK_BOW;
                Weapon arrow = Weapon.getItem(entity.getIsMale(),
                        Weapon.WeaponType.WEAPON_ARROW,
                        0,
                        0,
                        0,
                        this.getVertexBufferObjectManager(),
                        this.getTextureManager(),
                        this.getAssets(),
                        0);
                entity.AddWearable(arrow);
            }




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
