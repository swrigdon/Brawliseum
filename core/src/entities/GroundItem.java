/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import constants.GameConstants;
import dungeon.DungeonTile;
import items.Potion;

/**
 *
 * @author Jason
 */
public class GroundItem extends Entity
{
    private Potion potion;
    
    public GroundItem(int x, int y, Texture itemTexture, Potion potion)
    {
        this.potion = potion;
        this.setEntityTexture(itemTexture);
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
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the potion
     */
    public Potion getPotion() {
        return potion;
    }

    /**
     * @param potion the potion to set
     */
    public void setPotion(Potion potion) {
        this.potion = potion;
    }
    
}
