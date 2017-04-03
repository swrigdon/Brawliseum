/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import dungeon.DungeonTile;
import dungeon.Level;

/**
 *
 * @author Jason
 */
public abstract class Entity extends Rectangle
{
   private float xLocation;
   private float yLocation;
   private float speed;
   private Texture entityTexture;
   private Level currentLevel;
   
   private boolean movingY;
   private boolean movingX;
   private boolean movingNY;
   private boolean movingNX;
   
   public abstract void move(DungeonTile[][] map);

    /**
     * @return the entityTexture
     */
    public Texture getEntityTexture() {
        return entityTexture;
    }

    /**
     * @param entityTexture the entityTexture to set
     */
    public void setEntityTexture(Texture entityTexture) {
        this.entityTexture = entityTexture;
    }

    /**
     * @return the xLocation
     */
    public float getxLocation() {
        return xLocation;
    }

    /**
     * @param xLocation the xLocation to set
     */
    public void setxLocation(float xLocation) {
        this.setX(xLocation);
        this.xLocation = xLocation;
    }

    /**
     * @return the yLocation
     */
    public float getyLocation() {
        return yLocation;
    }

    /**
     * @param yLocation the yLocation to set
     */
    public void setyLocation(float yLocation) {
        this.setY(yLocation);
        this.yLocation = yLocation;
    }

    /**
     * @return the currentLevel
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * @param currentLevel the currentLevel to set
     */
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * @return the speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * @return the movingY
     */
    public boolean isMovingY() {
        return movingY;
    }

    /**
     * @param movingY the movingY to set
     */
    public void setMovingY(boolean movingY) {
        this.movingY = movingY;
    }

    /**
     * @return the movingX
     */
    public boolean isMovingX() {
        return movingX;
    }

    /**
     * @param movingX the movingX to set
     */
    public void setMovingX(boolean movingX) {
        this.movingX = movingX;
    }

    /**
     * @return the movingNY
     */
    public boolean isMovingNY() {
        return movingNY;
    }

    /**
     * @param movingNY the movingNY to set
     */
    public void setMovingNY(boolean movingNY) {
        this.movingNY = movingNY;
    }

    /**
     * @return the movingNX
     */
    public boolean isMovingNX() {
        return movingNX;
    }

    /**
     * @param movingNX the movingNX to set
     */
    public void setMovingNX(boolean movingNX) {
        this.movingNX = movingNX;
    }
   
}
