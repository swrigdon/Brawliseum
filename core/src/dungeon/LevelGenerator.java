/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import com.badlogic.gdx.graphics.Texture;

import constants.GameConstants;
import entities.Enemy;
import entities.GroundItem;
import items.Potion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jason
 */
public class LevelGenerator 
{
    private final int height;
    private final int width;
    private int endLocationX;
    private int endLocationY;
    
    public LevelGenerator(int height, int width)
    {
        this.height = height;
        this.width = width;
    }
    
    //UNDER CONSTRUCTION
    private DungeonTile[][] generateMap()
    {
        int roomStyle;
        DungeonTile[][] map = new DungeonTile[width][height] ;
        //THE hard part
        Random rand = new Random();
        roomStyle = rand.nextInt(2);
        
        //different map start generation to work with the algorithm 
        for(int x = 0; x < map.length; x++)
        {
            for(int y = 0; y < map[0].length; y++)
            {
                if((x%2!=0)&&(y%2!=0)) 
                {
                    map[x][y] = new DungeonTile("empty", x, y);
                }
                
                if((x%3!=0)&&(y%3!=0)) 
                {
                    map[x][y] = new DungeonTile("empty", x, y);
                }
                
                if((x%4!=0)&&(y%4!=0)) 
                {
                    map[x][y] = new DungeonTile("empty", x, y);
                }
                
                else
                {
                    map[x][y] = new DungeonTile("wall", x, y);
                }
            }
        }
        
        /*
        System.out.println(" Starting map " );
        System.out.println("-----------------------------------" );
        printGrid(map) ; //print the map
        */
        
        //declares player start location on map
        map[GameConstants.PLAYER_START_X][GameConstants.PLAYER_START_Y] = new DungeonTile("START", GameConstants.PLAYER_START_X, GameConstants.PLAYER_START_Y);
        
        //generate the maze (we can put this under room style later ) 
        map = generateMaze(map,GameConstants.PLAYER_START_X,GameConstants.PLAYER_START_Y) ; 
        
        //Generates the end location (NEEDS WORK)
        endLocationX = rand.nextInt((map.length-2) - ((map.length*3)/4)) + (((map.length*3)/4));
        endLocationY = rand.nextInt((map[0].length-2) - ((map[0].length*3)/4)) + (((map[0].length*3)/4));

        while(map[(int)endLocationX][(int)endLocationY].getTileType().equals("wall"))
        {
            if(!(map[(int)endLocationX][(int)endLocationY+1].getTileType().equals("wall")))
            {
                endLocationY++;
            }
            else if(!(map[(int)endLocationX][(int)endLocationY-1].getTileType().equals("wall")))
            {
                endLocationY--;
            }
            else if(!(map[(int)endLocationX+1][(int)endLocationY].getTileType().equals("wall")))
            {
                endLocationX++;
            }
            else if(!(map[(int)endLocationX-1][(int)endLocationY].getTileType().equals("wall")))
            {
                endLocationX--;
            }
            else if(!(map[(int)endLocationX+1][(int)endLocationY+1].getTileType().equals("wall")))
            {
                endLocationX++;
                endLocationY++;
            }
            else if(!(map[(int)endLocationX-1][(int)endLocationY-1].getTileType().equals("wall")))
            {
                endLocationX--;
                endLocationY--;
            }
            else if(!(map[(int)endLocationX+1][(int)endLocationY-1].getTileType().equals("wall")))
            {
                endLocationX++;
                endLocationY--;
            }
            else if(!(map[(int)endLocationX-1][(int)endLocationY+1].getTileType().equals("wall")))
            {
                endLocationX--;
                endLocationY++;
            }
            else//FAIL SAFE
            {
                endLocationX = rand.nextInt((map.length-2) - ((map.length*3)/4)) + (((map.length*3)/4));
                endLocationY = rand.nextInt((map[0].length-2) - ((map[0].length*3)/4)) + (((map[0].length*3)/4));
            }
        }
        
        map[endLocationX][endLocationY].setTileType("END");
        
        /*
        System.out.println(" Ending map " );
        System.out.println("-----------------------------------" );
        printGrid(map) ; //print the map
        */
        
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
    
    //recursive method to make the maze 
    private DungeonTile[][] generateMaze(DungeonTile[][] map, int x, int y)
    {
    	//uses an array list of directions because i wanted to randomly loop through them with collections 
    	ArrayList<Integer> directions = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3)) ;
    	 
    	Collections.shuffle(directions); //mixes up the directions 
    	
    	//filling up the 3x3 with floor tiles after each recursive call
    	map[x][y+1].setTileType("floor"); 
        map[x][y-1].setTileType("floor"); 
		map[x+1][y].setTileType("floor"); 
		map[x-1][y].setTileType("floor");
		map[x+1][y+1].setTileType("floor"); 
		map[x+1][y-1].setTileType("floor"); 
		map[x-1][y+1].setTileType("floor"); 
		map[x-1][y-1].setTileType("floor"); 	
    	
    	for(int direction:directions)
        { //loops through the directions 
            if(direction == 0)
            { //up 
    		if(!(y+4 >= map[0].length))
                {
                    if((map[x][y+4].getTileType().equals("empty"))){ //check if we have been here
    					
    			//RNG
    			Random randy = new Random();
    					
    			//Random number for type of path through the wall; 1<x<4 for single path, 3<x<7 for double path, 6<x for triple path
    			int pathType = randy.nextInt(8) + 1;
    			//Random number for direction of path taken for the single path; 1 for side path, 2 for middle path, 3 for other side path
    			int pathTaken = randy.nextInt(3) + 1;
    					
    			//if we rolled for single path
    			if(pathType < 4)
                        {
                            //path through upper left square
                            if(pathTaken == 1)
                            {
                                map[x][y+1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y+2].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y+3].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y+4].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y+4].setTileType("floor"); //tile is valid carve path through wall there

                            }

                            //path through upper middle square
                            if(pathTaken == 2)
                            {			
                                map[x][y+1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y+2].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y+3].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y+4].setTileType("floor"); //tile is valid carve path through wall there
                            }

                            //path through upper right square
                            if(pathTaken == 3)
                            {			
                                map[x][y+1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x+1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x+1][y+2].setTileType("floor"); //tile is valid carve path through wall there
                                map[x+1][y+3].setTileType("floor"); //tile is valid carve path through wall there
                                map[x+1][y+4].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y+4].setTileType("floor"); //tile is valid carve path through wall there
                            }
    			}
    					
                        //if we rolled double path
    			if(pathType < 7 && pathType > 3)
                        {		
                            //Random number that decides which two blocks to carve through. Similar to pathTaken but for the double path
                            int leftOrRight = randy.nextInt(2) + 1;
    						
                            //path through upper left two squares
                            if(leftOrRight == 1)
                            {
	    						
	    			map[x][y+1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y+1].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x-1][y+2].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x-1][y+3].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x-1][y+4].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x][y+4].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x][y+2].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x][y+3].setTileType("floor"); //tile is valid carve path through wall there	                			
                            }
	
                            //path through upper right two squares
                            if(leftOrRight == 2)
                            {				
	    			map[x][y+1].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x+1][y+1].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x+1][y+2].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x+1][y+3].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x+1][y+4].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x][y+4].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x][y+2].setTileType("floor"); //tile is valid carve path through wall there
	                	map[x][y+3].setTileType("floor"); //tile is valid carve path through wall there
                            }
    			}
    					
                        //if we rolled triple path
                        if(pathType > 6)
                        {			
                            map[x][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-1][y+2].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-1][y+3].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-1][y+4].setTileType("floor"); //tile is valid carve path through wall there
                            map[x][y+4].setTileType("floor"); //tile is valid carve path through wall there
                            map[x][y+2].setTileType("floor"); //tile is valid carve path through wall there
                            map[x][y+3].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+1][y+2].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+1][y+3].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+1][y+4].setTileType("floor"); //tile is valid carve path through wall there
    			}
    					
            		generateMaze(map,x,y+4) ; //call function again from new square 
                    }
                }	
            }
            else if(direction == 1)
            { //down 
                if(!(y-4 < 0))
                {
                    if(map[x][y-4].getTileType().equals("empty"))
                    { //check if we have been here

                        //RNG
                        Random randy = new Random();

                        //Random number for type of path through the wall; 1<x<4 for single path, 3<x<7 for double path, 6<x for triple path
                        int pathType = randy.nextInt(8) + 1;
                        //Random number for direction of path taken for the single path; 1 for side path, 2 for middle path, 3 for other side path
                        int pathTaken = randy.nextInt(3) + 1;

                        //if we rolled for single path
                        if(pathType < 4)
                        {
                            //path through lower left square
                            if(pathTaken == 1)
                            {
                                map[x][y-1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y-2].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y-3].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y-4].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y-4].setTileType("floor"); //tile is valid carve path through wall there
                            }

                            //path through lower middle square
                            if(pathTaken == 2)
                            {
                                map[x][y-1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y-2].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y-3].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y-4].setTileType("floor"); //tile is valid carve path through wall there
                            }

                            //path through lower right square
                            if(pathTaken == 3)
                            {
                                map[x][y-1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x+1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x+1][y-2].setTileType("floor"); //tile is valid carve path through wall there
                                map[x+1][y-3].setTileType("floor"); //tile is valid carve path through wall there
                                map[x+1][y-4].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y-4].setTileType("floor"); //tile is valid carve path through wall there
                            }
                        }

                        //if we rolled double path
                        if(pathType < 7 && pathType > 3)
                        {
                            //Random number that decides which two blocks to carve through. Similar to pathTaken but for the double path
                            int leftOrRight = randy.nextInt(2) + 1;

                            //path through lower left two squares
                            if(leftOrRight == 1)
                            {
                                map[x][y-1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y-2].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y-3].setTileType("floor"); //tile is valid carve path through wall there
                                map[x-1][y-4].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y-4].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y-2].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y-3].setTileType("floor"); //tile is valid carve path through wall there

                            }

                            //path through lower right two squares
                            if(leftOrRight == 2)
                            {
                                map[x][y-1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x+1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                                map[x+1][y-2].setTileType("floor"); //tile is valid carve path through wall there
                                map[x+1][y-3].setTileType("floor"); //tile is valid carve path through wall there
                                map[x+1][y-4].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y-4].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y-2].setTileType("floor"); //tile is valid carve path through wall there
                                map[x][y-3].setTileType("floor"); //tile is valid carve path through wall there
                            }
                        }

                        //if we rolled triple path
                        if(pathType > 6)
                        {

                            map[x][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-1][y-2].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-1][y-3].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-1][y-4].setTileType("floor"); //tile is valid carve path through wall there
                            map[x][y-4].setTileType("floor"); //tile is valid carve path through wall there
                            map[x][y-2].setTileType("floor"); //tile is valid carve path through wall there
                            map[x][y-3].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+1][y-2].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+1][y-3].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+1][y-4].setTileType("floor"); //tile is valid carve path through wall there
                        }

                        generateMaze(map,x,y-4) ; //call function again from new square 
                    }
                }
            }
            else if(direction == 2)
            { //left 
                if(!(x-4 < 0))
                {
                    if(map[x-4][y].getTileType().equals("empty"))
                    { //check if we have been here

                    //RNG
                    Random randy = new Random();

                    //Random number for type of path through the wall; 1<x<4 for single path, 3<x<7 for double path, 6<x for triple path
                    int pathType = randy.nextInt(8) + 1;
                    //Random number for direction of path taken for the single path; 1 for side path, 2 for middle path, 3 for other side path
                    int pathTaken = randy.nextInt(3) + 1;

                    //if we rolled single path
                    if(pathType < 4)
                    {
                        //path through lower left square
                        if(pathTaken == 1)
                        {
                            map[x-1][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-2][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-3][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-4][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-4][y].setTileType("floor"); //tile is valid carve path through wall there
                        }

                        //path through middle left square
                        if(pathTaken == 2)
                        {
                            map[x-1][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-2][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-3][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-4][y].setTileType("floor"); //tile is valid carve path through wall there
                        }

                        //path through upper left square
                        if(pathTaken == 3)
                        {

                            map[x-1][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-2][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-3][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-4][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-4][y].setTileType("floor"); //tile is valid carve path through wall there
                        }
                    }

                    //if we rolled double path 
                    if(pathType < 7 && pathType > 3)
                    {

                        //Random number that decides which two blocks to carve through. Similar to pathTaken but for the double path
                        int leftOrRight = randy.nextInt(2) + 1;

                        //path through lower left two squares
                        if(leftOrRight == 1)
                        {
                            map[x-1][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-2][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-3][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-4][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-4][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-2][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-3][y].setTileType("floor"); //tile is valid carve path through wall there
                        }

                        //path through upper left two squares
                        if(leftOrRight == 2)
                        {
                            map[x-1][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-2][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-3][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-4][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-4][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-2][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x-3][y].setTileType("floor"); //tile is valid carve path through wall there
                        }
                    }

                    //if we rolled triple path
                    if(pathType > 6)
                    {
                        map[x-1][y].setTileType("floor"); //tile is valid carve path through wall there
                        map[x-1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x-2][y+1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x-3][y+1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x-4][y+1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x-4][y].setTileType("floor"); //tile is valid carve path through wall there
                        map[x-2][y].setTileType("floor"); //tile is valid carve path through wall there
                        map[x-3][y].setTileType("floor"); //tile is valid carve path through wall there
                        map[x-1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x-2][y-1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x-3][y-1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x-4][y-1].setTileType("floor"); //tile is valid carve path through wall there
                    }

                    generateMaze(map,x-4,y) ; //call function again from new square
                }
            }
        }
            
        else if(direction == 3)
        { //right
            if(!(x+4 >= map.length))
                if(map[x+4][y].getTileType().equals("empty"))
                { //check if we have been here

                    //RNG
                    Random randy = new Random();

                    //Random number for type of path through the wall; 1<x<4 for single path, 3<x<7 for double path, 6<x for triple path
                    int pathType = randy.nextInt(8) + 1;
                    //Random number for direction of path taken for the single path; 1 for side path, 2 for middle path, 3 for other side path
                    int pathTaken = randy.nextInt(3) + 1;

                    //if we rolled single path
                    if(pathType < 4)
                    {
                        //path through lower right square
                        if(pathTaken == 1)
                        {
                            map[x+1][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+2][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+3][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+4][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+4][y].setTileType("floor"); //tile is valid carve path through wall there
                        }

                        //path through middle right square
                        if(pathTaken == 2)
                        {
                            map[x+1][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+2][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+3][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+4][y].setTileType("floor"); //tile is valid carve path through wall there
                        }

                        //path through upper right square
                        if(pathTaken == 3){

                            map[x+1][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+2][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+3][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+4][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+4][y].setTileType("floor"); //tile is valid carve path through wall there
                        }
                    }

                    //if we rolled double path
                    if(pathType < 7 && pathType > 3)
                    {
                        //Random number that decides which two blocks to carve through. Similar to pathTaken but for the double path
                        int leftOrRight = randy.nextInt(2) + 1;

                        //path through lower right two squares
                        if(leftOrRight == 1)
                        {
                            map[x+1][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+2][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+3][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+4][y-1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+4][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+2][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+3][y].setTileType("floor"); //tile is valid carve path through wall there
                        }

                        //path through upper right two squares
                        if(leftOrRight == 2)
                        {
                            map[x+1][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+2][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+3][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+4][y+1].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+4][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+2][y].setTileType("floor"); //tile is valid carve path through wall there
                            map[x+3][y].setTileType("floor"); //tile is valid carve path through wall there
                        }
                    }
    					
                    //if we rolled triple path
                    if(pathType > 6)
                    {    						
                        map[x+1][y].setTileType("floor"); //tile is valid carve path through wall there
                        map[x+1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x+2][y+1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x+3][y+1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x+4][y+1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x+4][y].setTileType("floor"); //tile is valid carve path through wall there
                        map[x+2][y].setTileType("floor"); //tile is valid carve path through wall there
                        map[x+3][y].setTileType("floor"); //tile is valid carve path through wall there
                        map[x+1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x+2][y-1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x+3][y-1].setTileType("floor"); //tile is valid carve path through wall there
                        map[x+4][y-1].setTileType("floor"); //tile is valid carve path through wall there
                    }

                    generateMaze(map,x+4,y) ; //call function again from new square 
                }
            }	
        }

        return map ; //backtracking 
    }
    
    //helper function to print the grid to the console. we should delete this in final solution but it will be usefull to check for now 
    private void printGrid(DungeonTile [][] grid)
    {
       for(int i = 0; i < grid.length; i++)
       {
          for(int j = 0; j < grid[0].length; j++)
          {
             System.out.printf("%5s ", grid[i][j].getTileType());
          }
          System.out.println();
       }
    }
    
    
    public Level generateLevel(int levelNumber)
    {
        DungeonTile[][] map = generateMap();
        ArrayList<Enemy> enemies = generateEnemies(levelNumber, map);
        ArrayList<GroundItem> groundItems = generateItems(levelNumber, map);
        
    	//create a new level 
        Level newLevel = new Level(map, enemies, groundItems,levelNumber);
        
        
        //return the level to the game class 
        return newLevel;
    }
    
    private ArrayList<GroundItem> generateItems(int levelNum, DungeonTile[][] map)
    {
        ArrayList<GroundItem> groundItems = new ArrayList<GroundItem>();
        GroundItem holderItem;
        Potion holderPotion;
        int numItems = GameConstants.NUM_GROUND_ITEMS;
        Texture itemTexture = GameConstants.ITEM_HEALTH;
        
        
        float itemLocationX;
        float itemLocationY;
        
        for(int i = 0; i < numItems; i++)
        {           
            Random rand = new Random();
            int pickItem = rand.nextInt(4-1)+1;
            
            if(pickItem == 1)
            {
                holderPotion = new Potion("health", (GameConstants.BASE_HEALTH_POTION) + (GameConstants.SCALE_FACTOR_POTION * levelNum));
                itemTexture = GameConstants.ITEM_HEALTH;
            }
            else if(pickItem == 2)
            {
                holderPotion = new Potion("attack", (GameConstants.BASE_ATTACK_POTION));
                itemTexture = GameConstants.ITEM_ATTACK;
            }
            else
            {
                //Move Speed
                holderPotion = new Potion("move", (GameConstants.BASE_MOVE_POTION));
                itemTexture = GameConstants.ITEM_MOVE;
            }
            
            
            holderItem = new GroundItem(1,1, itemTexture, holderPotion);
            holderItem.setSpeed(0);
            
            
            itemLocationX = rand.nextInt((map.length-2) - 1)+1;
            itemLocationY = rand.nextInt((map[0].length-2 - 1))+1;
            
            while(map[(int)itemLocationX][(int)itemLocationY].getTileType().equals("wall"))
            {
                if(!(map[(int)itemLocationX][(int)itemLocationY+1].getTileType().equals("wall")))
                {
                    itemLocationY++;
                }
                else if(!(map[(int)itemLocationX][(int)itemLocationY-1].getTileType().equals("wall")))
                {
                    itemLocationY--;
                }
                else if(!(map[(int)itemLocationX+1][(int)itemLocationY].getTileType().equals("wall")))
                {
                    itemLocationX++;
                }
                else if(!(map[(int)itemLocationX-1][(int)itemLocationY].getTileType().equals("wall")))
                {
                    itemLocationX--;
                }
                else if(!(map[(int)itemLocationX+1][(int)itemLocationY+1].getTileType().equals("wall")))
                {
                    itemLocationX++;
                    itemLocationY++;
                }
                else if(!(map[(int)itemLocationX-1][(int)itemLocationY-1].getTileType().equals("wall")))
                {
                    itemLocationX--;
                    itemLocationY--;
                }
                else if(!(map[(int)itemLocationX+1][(int)itemLocationY-1].getTileType().equals("wall")))
                {
                    itemLocationX++;
                    itemLocationY--;
                }
                else if(!(map[(int)itemLocationX-1][(int)itemLocationY+1].getTileType().equals("wall")))
                {
                    itemLocationX--;
                    itemLocationY++;
                }
                else//FAIL SAFE
                {
                    itemLocationX = rand.nextInt((map.length-2) - 1)+1;
                    itemLocationY = rand.nextInt((map[0].length-2 - 1))+1;
                }
            }
            
            holderItem.setxLocation(itemLocationX+((float)(1)-((float)(itemTexture.getHeight())/32))/2);
            holderItem.setyLocation(itemLocationY+((float)(1)-((float)(itemTexture.getHeight())/32))/2);

            groundItems.add(holderItem);
        }
        
        
        return groundItems;
    }
    
    private ArrayList<Enemy> generateEnemies(int levelNumber, DungeonTile[][] map)
    {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        Enemy holderEnemy;
        int enemyNumber = 0;
        
        Texture enemyTexture = new Texture("enemyTest.png");
        
        float enemyLocationX;
        float enemyLocationY;
        
        Random rand = new Random();
        
        if(levelNumber == 0)
        {
            enemyNumber = 2;
        }
        else if(levelNumber % 5 == 0)
        {
            enemyNumber = 1;
        }
        else
        {
            enemyNumber = GameConstants.BASE_NUM_ENEMIES + levelNumber/2;
            if(enemyNumber > GameConstants.CAP_NUM_ENEMIES)
            {
                enemyNumber = GameConstants.CAP_NUM_ENEMIES;
            }
        }
        
        for(int i = 0; i < enemyNumber; i++)
        {
            holderEnemy = new Enemy(map, enemyTexture, levelNumber);
            holderEnemy.setHealth(GameConstants.ENEMY_STARTING_HEALTH);
            holderEnemy.setDamage(GameConstants.ENEMY_STARTING_DAMAGE);
            holderEnemy.setDefense(100);
            holderEnemy.setSpeed(GameConstants.ENEMY_BASE_SPEED);
            
            enemyLocationX = rand.nextInt((map.length-2) - 6)+7;
            enemyLocationY = rand.nextInt((map[0].length-2 - 6))+7;
            
            while(map[(int)enemyLocationX][(int)enemyLocationY].getTileType().equals("wall"))
            {
                if(!(map[(int)enemyLocationX][(int)enemyLocationY+1].getTileType().equals("wall")))
                {
                    enemyLocationY++;
                }
                else if(!(map[(int)enemyLocationX][(int)enemyLocationY-1].getTileType().equals("wall")))
                {
                    enemyLocationY--;
                }
                else if(!(map[(int)enemyLocationX+1][(int)enemyLocationY].getTileType().equals("wall")))
                {
                    enemyLocationX++;
                }
                else if(!(map[(int)enemyLocationX-1][(int)enemyLocationY].getTileType().equals("wall")))
                {
                    enemyLocationX--;
                }
                else if(!(map[(int)enemyLocationX+1][(int)enemyLocationY+1].getTileType().equals("wall")))
                {
                    enemyLocationX++;
                    enemyLocationY++;
                }
                else if(!(map[(int)enemyLocationX-1][(int)enemyLocationY-1].getTileType().equals("wall")))
                {
                    enemyLocationX--;
                    enemyLocationY--;
                }
                else if(!(map[(int)enemyLocationX+1][(int)enemyLocationY-1].getTileType().equals("wall")))
                {
                    enemyLocationX++;
                    enemyLocationY--;
                }
                else if(!(map[(int)enemyLocationX-1][(int)enemyLocationY+1].getTileType().equals("wall")))
                {
                    enemyLocationX--;
                    enemyLocationY++;
                }
                else//FAIL SAFE
                {
                    enemyLocationX = rand.nextInt((map.length-2) - 1)+1;
                    enemyLocationY = rand.nextInt((map[0].length-2 - 1))+1;
                }
            }
            
            holderEnemy.setxLocation(enemyLocationX+((float)(1)-((float)(enemyTexture.getHeight())/32))/2);
            holderEnemy.setyLocation(enemyLocationY+((float)(1)-((float)(enemyTexture.getHeight())/32))/2);
            
            map[(int)enemyLocationX][(int)enemyLocationY].setOccupied(true);
            
            holderEnemy.setEndX(2);
            holderEnemy.setEndY(2);
            
            holderEnemy.setPath();
            map[(int)enemyLocationX][(int)enemyLocationY].setEnemyOnTile(holderEnemy);
            
            enemies.add(holderEnemy);
        }
        
        return enemies;
    }
}
