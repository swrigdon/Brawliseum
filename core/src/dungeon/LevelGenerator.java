/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import java.util.Random;

/**
 *
 * @author Jason
 */
public class LevelGenerator 
{
    private int height;
    private int width;
    private int playerStartX = 2;
    private int playerStartY = 2;
    private int endLocationX;
    private int endLocationY;
    
    private DungeonTile[][] map;

    public DungeonTile[][] getMap() 
    {
        return map;
    }
    public void setMap(DungeonTile[][] map) 
    {
        this.map = map;
    }
    
    public LevelGenerator(int width, int height)
    {
        this.height = height;
        this.width = width;
    }
    
    //UNDER CONSTRUCTION
    public DungeonTile[][] generateMap()
    {
        int roomStyle;
        map = new DungeonTile[width][height];
        
        int randPathX = playerStartX;
        int randPathY = playerStartY;
        
        //THE hard part
        Random rand = new Random();
        roomStyle = rand.nextInt(2);
        
        //declares the wall perimeter of the map
        for(int x = 0; x < map.length; x++)
        {
            for(int y = 0; y < map[0].length; y++)
            {
                if(x == 0 || y == 0 || x == map.length -1 || y == map[0].length -1)
                {
                    map[x][y] = new DungeonTile("wall", x, y);
                }
                else
                {
                    map[x][y] = new DungeonTile("empty", x, y);
                }
            }
        }
        
        //declares player start location on map
        map[playerStartX][playerStartY] = new DungeonTile("floor", playerStartX, playerStartY);
        
        //declares a random placement of the end location
        endLocationX = rand.nextInt((map.length-2) - (map.length/2)) + ((map.length/2));
        endLocationY = rand.nextInt((map[0].length-2) - (map[0].length/2)) + ((map[0].length/2));
        
        map[endLocationX][endLocationY] = new DungeonTile("floor", endLocationX, endLocationY);
        
        //Makes a random path from player start to the end location
        //Jason - JUST A TEST, NOT FINAL VERSION
        while(randPathX != endLocationX || randPathY != endLocationY)
        {
            int randInt = rand.nextInt(6)+1;
            
            //Chooses a random direction
            if(randInt == 1 || randInt == 2)
            {
                randPathX++;
            }
            else if(randInt == 3)
            {
                randPathX--;
            }
            else if(randInt == 4 || randInt == 5)
            {
                randPathY++;
            }
            else if(randInt == 6)
            {
                randPathY--;
            }
            
            //Sets parameters
            if(randPathX > endLocationX || randPathX > width+1)
            {
                randPathX--;
            }
            if(randPathX < 2)
            {
                randPathX++;
            }
            if(randPathY > endLocationY || randPathY > width+1)
            {
                randPathY--;
            }
            if(randPathY < 2)
            {
                randPathY++;
            }
            
            if(map[randPathX][randPathY].getTileType().equals("empty"))
            {
                map[randPathX][randPathY] = new DungeonTile("floor", randPathX, randPathY);
                
                /*
                map[randPathX+1][randPathY] = new DungeonTile("floor", randPathX+1, randPathY+1);
                map[randPathX][randPathY+1] = new DungeonTile("floor", randPathX+1, randPathY+1);
                map[randPathX+1][randPathY+1] = new DungeonTile("floor", randPathX+1, randPathY+1);
                map[randPathX+1][randPathY-1] = new DungeonTile("floor", randPathX+1, randPathY+1);
                map[randPathX-1][randPathY] = new DungeonTile("floor", randPathX-1, randPathY-1);
                map[randPathX][randPathY-1] = new DungeonTile("floor", randPathX+1, randPathY+1);
                map[randPathX+1][randPathY+1] = new DungeonTile("floor", randPathX+1, randPathY+1);
                map[randPathX-1][randPathY+1] = new DungeonTile("floor", randPathX+1, randPathY+1);
                */
            }
            
        }
        
        //Print statements to test the locations are properly placed
        System.out.println("LengthX = " + map.length);
        System.out.println("LengthY = " + map[0].length);
        System.out.println("X = " + endLocationX);
        System.out.println("Y = " + endLocationY);
        
        //random level style
        switch(roomStyle)
        {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
        }
        
        
                    
        
        return map;
    }
    
    public Level generateLevel(int levelNumber)
    {
        Level newLevel = new Level();
        
        return newLevel;
    }
}
