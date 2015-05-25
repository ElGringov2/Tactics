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
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dragonrider on 14/05/15.
 * Gere les scenes de combat
 */
public class BattleScene extends Scene {


    /**
     * La map
     */
    private Map mMap;

    /**
     * Position temporaire pour le drag de la zone de jeu
     */
    private float mTouchPositionX;
    /**
     * Position temporaire pour le drag de la zone de jeu
     */
    private float mTouchPositionY;

    /**
     * La reference d'un VBOM pour les rectangles de selection de case (entre autres)
     */
    private VertexBufferObjectManager refVBOM;

    /**
     * Une reference de camera pour le HUD
     */
    private Camera mCamera;

    /**
     * Le HUD lors de la selection
     */
    private CharacterHUD mCharhud;

    /**
     * Un HUD neutre, sans rien dessus.
     */
    private CustomHUD mNeutralHud;


    /**
     * CTOR
     * @param map La map généré
     * @param refCamera Une reference pour la caméra (servira pour mettre a jour le HUD)
     * @param vbom La reference du VBOM
     * @param textureManager La reference du TM
     * @param assetManager La reference de l'AM
     * @param screenWidth La taille de l'écran (largeur) pour générér la hauteur de police du HUD
     * @param screenHeight La taille de l'écran (hauteur) pour générér la hauteur de police du HUD
     */
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



        EntityPosition = new ArrayList<>();



        mMap.AttachTopLayerToScene(this, vbom);

    }

    /**
     * Sert de placeholder pour les personnages
     */
    private org.andengine.entity.Entity entityLayer;


    /**
     * La liste des entitées
     */
    private List<Entity> entities = new ArrayList<>();


    /**
     * Sert de placeholder pour la grille
     */
    private org.andengine.entity.Entity gridLayer;


    /**
     * Ajoute une entité
     * @param entity Une entité
     * @param vertexBufferObjectManager la réference du VBOM
     * @return Le nombre d'entitée actuellement présente (celle ci inclus)
     */
    public int AddEntity(Entity entity, VertexBufferObjectManager vertexBufferObjectManager)  {



        int iCount = entities.size();
        entities.add(entity);
        entity.AttachToScene(entityLayer, this, vertexBufferObjectManager);
        entity.OnTouch = entityTouch;
        EntityPosition.add(new Point());
        return iCount;
    }

    /**
     * Téléporte une entitée (aucune modification d'animation effectuée)
     * @param ID l'ID de l'entitée
     * @param pX La nouvelle coordonnée X (en case)
     * @param pY La nouvelle coordinnée Y (en case)
     */
    public void setEntityPosition(int ID, int pX, int pY) {
        entities.get(ID).UpdatePosition(pX * 64, pY * 64 + 16);
        EntityPosition.set(ID, new Point(pX, pY));
    }


    /**
     * La liste des positions des entitées
     */
    private ArrayList<Point> EntityPosition;

    /**
     * L'ID de l'entité sélectionnée
     */
    int SelectedEntityID = -1;

    /**
     * L'entité sélectionnée (null si aucune entitée n'est sélectionnée
     */
    Entity getSelectedEntity() {
        if (SelectedEntityID != -1) return entities.get(SelectedEntityID);
        return null;
    }



    /**
     * Gestion de la selection de selection d'entitée
     */
    private Entity.IOnTouch entityTouch = new Entity.IOnTouch() {
        @Override
        public void OnTouch(Entity entity, TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

            if (pSceneTouchEvent.isActionUp()) {
                if (getSelectedEntity() != null)
                    UnSelectEntity(getSelectedEntity());




                SelectEntity(entity);
            }

        }
    };


    /**
     * Permet de selectionner une entité ( change le code couleur, met a jour le HUD)
     * @param entity L'entité a installer
     */
    private void SelectEntity(Entity entity) {

        entity.Select(0.0f, 0.5f, 0.0f, 1.0f);
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) == entity) {
                SelectedEntityID = i;
                break;

            }
        }


        mCamera.setHUD(mCharhud);
        UpdateGrid();
    }

    /**
     * Annule une selection d'entité
     * @param entity L'entité a déselectionnée (au cas ou elle est difference de getSelectedEntity()
     */
    private void UnSelectEntity (Entity entity) {

        entity.Unselect();
        SelectedEntityID = -1;
        mCamera.setHUD(mNeutralHud);
        UpdateGrid();

    }


    /**
     * Met a jour la grille en fonction de l'entité sélectionnée.
     * La grille est déssinée par rapport a un mouvement de 4 (6)
     */
    private void UpdateGrid() {

        for (int i = 0; i < gridLayer.getChildCount(); i++)
        {
            this.unregisterTouchArea(gridLayer.getChildByIndex(i));
        }

        gridLayer.detachChildren();

        if (getSelectedEntity() == null) return;


        int x = 0;
        int y = 0;

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) == getSelectedEntity()) {
                x = EntityPosition.get(i).x;
                y = EntityPosition.get(i).y;

                break;

            }
        }


        final AStar aStar = new AStar(x, y);
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


                    if (selectedMoveRectangle != null &&
                            selectedMoveRectangle.getX() == this.getX() &&
                            selectedMoveRectangle.getY() == this.getY()) return false;


                    PrepareMoveEntity(SelectedEntityID, entry.x, entry.y, entry.Score, this.getColor(), aStar);

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

                    if (selectedMoveRectangle != null &&
                            selectedMoveRectangle.getX() == this.getX() &&
                            selectedMoveRectangle.getY() == this.getY()) return false;

                    PrepareMoveEntity(SelectedEntityID, entry.x, entry.y, entry.Score, this.getColor(), aStar);
                    return true;
                }
            };



            rectangle.setColor(0f, 0f, 1f, 0.25f);
            gridLayer.attachChild(rectangle);

            this.registerTouchArea(rectangle);


        }



    }


    /**
     * Le rectangle de surselection de mouvement utilisé dans PrepareMoveEntity
     */
    private Rectangle selectedMoveRectangle;

    /**
     * La ligne utilisée dans PrepareMoveEntity
     */
    private Line selectedPathLine;



    /**
     * Prépare le mouvement en déssinant un deuxieme rectangle plus foncé ainsi qu'une ligne
     * @param selectedEntityID L'entité selectionnée
     * @param pX La position X (en case) de la cible
     * @param pY La position Y (en case) de la cible
     * @param pDuration La durée de mouvement (le nombre de case avancé)
     * @param pColor La couleur du rectangle
     */
    private void PrepareMoveEntity(final int selectedEntityID, final int pX, final int pY, final float pDuration, Color pColor, AStar aStar) {

        if (selectedMoveRectangle != null) {
            this.unregisterTouchArea(selectedMoveRectangle);
            this.gridLayer.detachChild(selectedMoveRectangle);
            this.gridLayer.detachChild(selectedPathLine);

        }

        selectedMoveRectangle = new Rectangle(pX * 64 + 1,
                pY * 64 + 1,
                62, 62,
                this.refVBOM) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (!pSceneTouchEvent.isActionUp()) return false;
                MoveEntity(selectedEntityID, pX, pY, pDuration);

                return true;
            }
        };


        selectedMoveRectangle.setColor(pColor);

        this.gridLayer.attachChild(selectedMoveRectangle);
        this.registerTouchArea(selectedMoveRectangle);

        selectedPathLine = new Line(getSelectedEntity().PositionX, getSelectedEntity().PositionY - 16, pX * 64, pY * 64, refVBOM);
        selectedPathLine.setColor(Color.YELLOW);
        selectedPathLine.setLineWidth(3f);

        this.gridLayer.attachChild(selectedPathLine);



    }


    /**
     * Déplace une entité.
     * L'animation ainsi que le déplacement a pied sera lancé.
     * @param selectedEntityID L'entité à deplacer. -1 annule le déplacement.
     * @param newX La position X en coordonée de case
     * @param newY La position Y en coordonée de case
     * @param MoveDuration Le nombre de case avancé
     */
    private void MoveEntity(int selectedEntityID, int newX, int newY, float MoveDuration) {

        if (selectedMoveRectangle != null) {
            this.unregisterTouchArea(selectedMoveRectangle);
            this.gridLayer.detachChild(selectedMoveRectangle);
            this.gridLayer.detachChild(selectedPathLine);

        }


        if (selectedEntityID == -1)
            return;
        
        getSelectedEntity().Move(newX * 64, newY * 64 + 16, MoveDuration);

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) == getSelectedEntity()) {
                EntityPosition.get(i).x = newX;
                EntityPosition.get(i).y = newY;

                break;
            }
        }


        UnSelectEntity(getSelectedEntity());

    }


}

