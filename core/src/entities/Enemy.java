/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.Gdx;
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
    
    private ArrayList<DungeonTile> aStar()
    {
        map[(int)this.getxLocation()][(int)this.getyLocation()].setDepth(0);
        
        ArrayList<DungeonTile> closed = new ArrayList<DungeonTile>();
        ArrayList<DungeonTile> open = new ArrayList<DungeonTile>();
        
        open.add(map[(int)this.getxLocation()][(int)this.getyLocation()]);
        
        //map[(int)endX][(int)endY].setParent(null);
        
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
                    
                    if(!(map[neighborX][neighborY].getTileType().equals("wall")))
                    {
                    	
                        DungeonTile neighbor = map[neighborX][neighborY] ; 
                        System.out.println("Visiting");
                        
                        if(!open.contains(neighbor)&&!(closed.contains(neighbor)))
                        {
                        	neighbor.getHeuristic() ; 
                        	maxDepth = Math.max(maxDepth, neighbor.setParent(current)) ; 
                        	open.add(neighbor) ; 
                        }
                    }
                }
            }
        }  
        
        if(map[(int) endX][(int) endY].getParent()==null)
        {
        	System.out.println("null");
        	return null ; 
        }
        
        ArrayList<DungeonTile> path = new ArrayList<DungeonTile>() ; 
        DungeonTile target = map[(int) endX][(int) endY] ; 
        while(target !=  map[(int)this.getxLocation()][(int)this.getyLocation()])
        {
        	path.add(target) ;
        	target = target.getParent() ; 
        }
        
        return path ; 
    }
    
    //public movementCost(neighborX, neighborY, )
    //test comment
    
    @Override
    public void move() 
    {
        /*
        System.out.println("Enemy EndX: " + this.getEndX());
        System.out.println("Enemy EndY: " + this.getEndY());
        */
        
    	ArrayList<DungeonTile> path = aStar() ; 
    	//System.out.println("Path bool: "+path.size());
    	if(path!=null)
    	{
    		System.out.println("CurrLoc: "+this.getX()+","+this.getY());
    		System.out.println("TargetLoc: "+path.get(0).getX()+","+path.get(0).getX());
	    	if(this.getX()<path.get(0).getX())
	    	{
	    		this.setX(this.getxLocation() + (float)this.getSpeed()*Gdx.graphics.getDeltaTime()) ; 
	    	}else if(this.getY()<path.get(0).getY())
	    	{
	    		this.setY(this.getyLocation() + (float)this.getSpeed()*Gdx.graphics.getDeltaTime()) ; 
	    	}
    	}
    	
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
