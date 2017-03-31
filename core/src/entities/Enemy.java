/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dungeon.DungeonTile;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 *
 * @author Sebastian
 */
public class Enemy extends Entity
{
    private final int MAX_VISION = 600;
    private final int MAX_RANGE = 10 ; 
    private float health;
    private float damage;
    private float defense;
    private float endX;
    private float endY;
    private DungeonTile[][] map;
    
    ArrayList<DungeonTile> path = new ArrayList<DungeonTile>();
    
    //private boolean finished = true;
    
    public Enemy(DungeonTile[][] map)
    {
        this.map  = map;
        
    }
    
    public void setPath()
    {
        path = aStar();
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
            	//System.out.println("WE'RE BREAKING FREEE");
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
                        DungeonTile neighbor= map[neighborX][neighborY]; 
                        if(!open.contains(neighbor) && !closed.contains(neighbor)){
                        	//System.out.println("THIS IS A TEST");
                        	neighbor.getHeuristic();
                        	maxDepth= Math.max(maxDepth, neighbor.setParent(current));
                        	open.add(neighbor);
                        	//System.out.println(open.size());
                        }
                    }
                }
            }
        }      
        if(map[(int)endX][(int)endY].getParent()==null){
        	return null;
        }
        
        
        
        ArrayList<DungeonTile> path = new ArrayList<DungeonTile>();
        DungeonTile target = map[(int)endX][(int)endY];
        while(target!= map[(int)this.getxLocation()][(int)this.getyLocation()]){
        	//System.out.println("Tile X: "+ target.getX());
    		//System.out.println("Tile Y: "+ target.getY());
        	path.add(target);
        	target=target.getParent();
        }
        return path;
    }
    
    //public movementCost(neighborX, neighborY, )
    //test comment
    
    private int calcDistance(){
    	return (int) Math.sqrt(Math.pow(endX-this.getxLocation(), 2)+Math.pow(endY-this.getyLocation(), 2)) ; 
    }
    
    @Override
    public void move() 
    {
    	if((int)this.getxLocation()==(int)endX && (int)this.getyLocation()==(int)endY)
        {
    		return;
    	}
    	
    	
    	//outside the range of pathfinding 
    	if(calcDistance() >= MAX_RANGE)
    	{

    	}
        else
        {
            if(path.size() > 1)
            {
                System.out.println("Heading to: (" + path.get(path.size()-1).getX() + ", " + path.get(path.size()-1).getY() + ")");
                System.out.println("from: (" + (int)this.getxLocation() + ", " + (int)this.getyLocation() + ")");
                changeLocation(path.get(path.size()-1)) ;

                if(!(Math.abs(this.getxLocation() - path.get(path.size()-1).getX()) != 0 ||
                     Math.abs(this.getyLocation() - path.get(path.size()-1).getY()) != 0))
                {
                    path = aStar();
                }
            }
            else 
            {
                path = aStar();
            }
    	}
    }
    
    private void changeLocation(DungeonTile newLocation)
    {
        /*
        System.out.println("(int) X Location " + (int)this.getxLocation());
    	System.out.println("(int) Y Location: " + (int)this.getyLocation() + "\n");
        System.out.println("(float) X Location " + this.getxLocation());
    	System.out.println("(float) Y Location: " + this.getyLocation() + "\n");
    	*/
        
        if(newLocation.getX() < this.getxLocation())
        {
            this.setxLocation(this.getxLocation() - this.getSpeed()*Gdx.graphics.getDeltaTime());

            if((this.getxLocation() < newLocation.getX()))
            {
                this.setxLocation(newLocation.getX());
            }
        }
        else if(newLocation.getX() > (this.getxLocation()))
        {
            this.setxLocation(this.getxLocation() + this.getSpeed()*Gdx.graphics.getDeltaTime());

            if((this.getxLocation() > newLocation.getX()))
            {
                this.setxLocation(newLocation.getX());
            }
        }
        else if(newLocation.getY() < this.getyLocation())
        {
            this.setyLocation(this.getyLocation() - this.getSpeed()*Gdx.graphics.getDeltaTime());

            if((this.getyLocation() < newLocation.getY()))
            {
                this.setyLocation(newLocation.getY());
            }
        }
        else if(newLocation.getY() > this.getyLocation())
        {
            this.setyLocation(this.getyLocation() + this.getSpeed()*Gdx.graphics.getDeltaTime());

            if((this.getyLocation() > newLocation.getY()))
            {
                this.setyLocation(newLocation.getY());
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
