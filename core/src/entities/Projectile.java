/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import constants.GameConstants;
import dungeon.DungeonTile;

/**
 *
 * @author Jason
 */
public class Projectile extends Entity
{
    private String projectileType;
    private float damage;
    
    private int direction;
    int rotation;
    
    
    public Projectile(String projectileType, float damage, float x, float y, float speed, int direction)
    {
        this.setProjectileType(projectileType);
        this.setDamage(damage);
        this.setxLocation(x);
        this.setyLocation(y);
        this.setSpeed(speed);
        
        if(projectileType.equals("bow"))
        {
            this.setEntityTexture(GameConstants.ARROW_UP_TEXTURE);
        }
        else if(projectileType.equals("mage"))
        {
            this.setEntityTexture(GameConstants.FIREBALL_TEXTURE);
        }
        
        this.setDirection(direction);
        
        this.set(x, y, (float)this.getEntityTexture().getWidth()/32, (float)this.getEntityTexture().getHeight()/32);
    }

    public void draw(SpriteBatch batch)
    {
        batch.draw(this.getEntityTexture(), this.getxLocation() * GameConstants.FLOOR_TEXTURE.getWidth(), 
                this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
    }
    
    @Override
    public void move(DungeonTile[][] map) 
    {
        if(direction == GameConstants.UP)
        {
            if(projectileType.equals("bow"))
            {
                this.setEntityTexture(GameConstants.ARROW_UP_TEXTURE);
            }
            else if(projectileType.equals("mage"))
            {
                this.setEntityTexture(GameConstants.FIREBALL_TEXTURE);
            }
            
            this.setyLocation(this.getyLocation() + this.getSpeed()*Gdx.graphics.getDeltaTime());
        }
        else if(direction == GameConstants.DOWN)
        {
            if(projectileType.equals("bow"))
            {
                this.setEntityTexture(GameConstants.ARROW_DOWN_TEXTURE);
            }
            else if(projectileType.equals("mage"))
            {
                this.setEntityTexture(GameConstants.FIREBALL_TEXTURE);
            }
            
            this.setyLocation(this.getyLocation() - this.getSpeed()*Gdx.graphics.getDeltaTime());
        }
        else if(direction == GameConstants.RIGHT)
        {
            if(projectileType.equals("bow"))
            {
                this.setEntityTexture(GameConstants.ARROW_RIGHT_TEXTURE);
            }
            else if(projectileType.equals("mage"))
            {
                this.setEntityTexture(GameConstants.FIREBALL_TEXTURE);   
            }
            
            this.setxLocation(this.getxLocation() + this.getSpeed()*Gdx.graphics.getDeltaTime());
        }
        else if(direction == GameConstants.LEFT)
        {
            if(projectileType.equals("bow"))
            {
                this.setEntityTexture(GameConstants.ARROW_LEFT_TEXTURE);
            }
            else if(projectileType.equals("mage"))
            {
                this.setEntityTexture(GameConstants.FIREBALL_TEXTURE);
            }
            
            this.setxLocation(this.getxLocation() - this.getSpeed()*Gdx.graphics.getDeltaTime());
        }
    }   

    /**
     * @return the projectileType
     */
    public String getProjectileType() {
        return projectileType;
    }

    /**
     * @param projectileType the projectileType to set
     */
    public void setProjectileType(String projectileType) {
        this.projectileType = projectileType;
    }

    /**
     * @return the damage
     */
    public float getDamage() {
        return damage;
    }

    /**
     * @param damage the damage to set
     */
    public void setDamage(float damage) {
        this.damage = damage;
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }
}
