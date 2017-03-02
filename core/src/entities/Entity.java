/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
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
   
   public abstract void move();

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
   
}
