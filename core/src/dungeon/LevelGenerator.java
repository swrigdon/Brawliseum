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
    private int playerStartX = 1;
    private int playerStartY = 1;
    private int endLocationX;
    private int endLocationY;
    
    public LevelGenerator(int height, int width)
    {
        this.height = height;
        this.width = width;
    }
    
    //UNDER CONSTRUCTION
    public DungeonTile[][] generateMap()
    {
        int roomStyle;
        DungeonTile[][] map = new DungeonTile[width][height];
        
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
        
        endLocationX = rand.nextInt((map.length-2) - (map.length/2)) + ((map.length/2));
        endLocationY = rand.nextInt((map[0].length-2) - (map[0].length/2)) + ((map[0].length/2));
        
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
