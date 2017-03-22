/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.graphics.Texture;
import dungeon.DungeonTile;
import dungeon.Level;
import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class Enemy extends Entity
{
    private final int MAX_VISION = 10;
    private float health;
    private float damage;
    private float defense;
    private float endX;
    private float endY;
    private DungeonTile[][] map;
    
    public Enemy(DungeonTile[][] map)
    {
        this.map  = map;
    }
    
    public float attack()
    {
        
        return 0;
    }
    
    public float defense()
    {
        return 0;
    }
    
    private void aStar()
    {
        map[(int)this.getxLocation()][(int)this.getyLocation()].setDepth(0);
        
        ArrayList<DungeonTile> closed = new ArrayList<DungeonTile>();
        ArrayList<DungeonTile> open = new ArrayList<DungeonTile>();
        
        open.add(map[(int)this.getxLocation()][(int)this.getyLocation()]);
        
        map[(int)endX][(int)endY].setParent(null);
        
        int maxDepth = 0;
        
        while(maxDepth < MAX_VISION && open.size() != 0)
        {
            DungeonTile current = open.get(0);
            
            //This means that the current location is already at the player
            if(current == map[(int)endX][(int)endY])
            {
                break;
            }
            
            open.remove(current);
            closed.add(current);
            
            for(int x = -1; x < 2; x++)
            {
                for(int y = -1; y < 2; y++)
                {
                    if( x == 0 && y == 0)
                    {
                        continue;
                    }
                    
                    if(x != 0 && y !=0)
                    {
                        continue;
                    }
                    
                    int neighborX = x + current.getX();
                    int neighborY = y + current.getY();
                    
                    if(!map[neighborX][neighborY].getTileType().equals("wall"))
                    {
                        
                    }
                }
            }
        }      
    }
    
    //public movementCost(neighborX, neighborY, )
    
    @Override
    public void move() 
    {
        /*
        System.out.println("Enemy EndX: " + this.getEndX());
        System.out.println("Enemy EndY: " + this.getEndY());
        */
        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the health
     */
    public float getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(float health) {
        this.health = health;
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
     * @return the defense
     */
    public float getDefense() {
        return defense;
    }

    /**
     * @param defense the defense to set
     */
    public void setDefense(float defense) {
        this.defense = defense;
    }

    /**
     * @return the endX
     */
    public float getEndX() {
        return endX;
    }

    /**
     * @param endX the endX to set
     */
    public void setEndX(float endX) {
        this.endX = endX;
    }

    /**
     * @return the endY
     */
    public float getEndY() {
        return endY;
    }

    /**
     * @param endY the endY to set
     */
    public void setEndY(float endY) {
        this.endY = endY;
    }
    
}
