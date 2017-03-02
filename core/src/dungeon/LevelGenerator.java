/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

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
    private int height;
    private int width;
    private int playerStartX = 2;
    private int playerStartY = 2;
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
        //DungeonTile[][] map = new DungeonTile[width][height];
        DungeonTile[][] map = new DungeonTile[21][21] ;
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
        
        System.out.println(" Starting map " );
        System.out.println("-----------------------------------" );
        printGrid(map) ; //print the map
        
        //declares player start location on map
        map[playerStartX][playerStartY] = new DungeonTile("START", playerStartX, playerStartY);
        
        endLocationX = rand.nextInt((map.length-2) - (map.length/2)) + ((map.length/2));
        endLocationY = rand.nextInt((map[0].length-2) - (map[0].length/2)) + ((map[0].length/2));
        
        System.out.println("LengthX = " + map.length);
        System.out.println("LengthY = " + map[0].length);
        System.out.println("X = " + endLocationX);
        System.out.println("Y = " + endLocationY);
        System.out.println("Room number: "+ roomStyle);
        
        //generate the maze (we can put this under room style later ) 
        map = generateMaze(map,playerStartX,playerStartY) ; 
        
        
        System.out.println(" Ending map " );
        System.out.println("-----------------------------------" );
        printGrid(map) ; //print the map

        
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
		
    	
    	for(int direction:directions){ //loops through the directions 
    		if(direction == 0){ //up 
    			if(!(y+4 >= map[0].length)){
    				if((map[x][y+4].getTileType().equals("empty"))){ //check if we have been here
    					
    					Random randy = new Random();
    					int pathTaken = randy.nextInt(3) + 1;
    					
    					if(pathTaken == 1){
    						
    						map[x][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-1][y+2].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-1][y+3].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-1][y+4].setTileType("floor"); //tile is valid carve path through wall there
                			map[x][y+4].setTileType("floor"); //tile is valid carve path through wall there
                			
    					}

    					if(pathTaken == 2){
    						
    						map[x][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x][y+2].setTileType("floor"); //tile is valid carve path through wall there
                			map[x][y+3].setTileType("floor"); //tile is valid carve path through wall there
                			map[x][y+4].setTileType("floor"); //tile is valid carve path through wall there
    					}
    					
    					if(pathTaken == 3){
    						
    						map[x][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+1][y+2].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+1][y+3].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+1][y+4].setTileType("floor"); //tile is valid carve path through wall there
                			map[x][y+4].setTileType("floor"); //tile is valid carve path through wall there
    					}
            			generateMaze(map,x,y+4) ; //call function again from new square 
            		}
    			}
        		
        	}else if(direction == 1){ //down 
        		if(!(y-4 < 0)){
        			if(map[x][y-4].getTileType().equals("empty")){ //check if we have been here
        				
        				Random randy = new Random();
    					int pathTaken = randy.nextInt(3) + 1;
    					
    					if(pathTaken == 1){
    						
    						map[x][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-1][y-2].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-1][y-3].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-1][y-4].setTileType("floor"); //tile is valid carve path through wall there
                			map[x][y-4].setTileType("floor"); //tile is valid carve path through wall there
    					}

    					if(pathTaken == 2){
    						
    						map[x][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x][y-2].setTileType("floor"); //tile is valid carve path through wall there
                			map[x][y-3].setTileType("floor"); //tile is valid carve path through wall there
                			map[x][y-4].setTileType("floor"); //tile is valid carve path through wall there
    					}
    					
    					if(pathTaken == 3){
    						
    						map[x][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+1][y-2].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+1][y-3].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+1][y-4].setTileType("floor"); //tile is valid carve path through wall there
                			map[x][y-4].setTileType("floor"); //tile is valid carve path through wall there
    					}
            			
            			generateMaze(map,x,y-4) ; //call function again from new square 
            		}
        		}
        		
        	}else if(direction == 2){ //left 
        		if(!(x-4 < 0)){
	        		if(map[x-4][y].getTileType().equals("empty")){ //check if we have been here
	        			
	        			Random randy = new Random();
    					int pathTaken = randy.nextInt(3) + 1;
    					
    					if(pathTaken == 1){
    						
    						map[x-1][y].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-2][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-3][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-4][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-4][y].setTileType("floor"); //tile is valid carve path through wall there
    					}

    					if(pathTaken == 2){
    						
    						map[x-1][y].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-2][y].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-3][y].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-4][y].setTileType("floor"); //tile is valid carve path through wall there
    					}
    					
    					if(pathTaken == 3){
    						
    						map[x-1][y].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-2][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-3][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-4][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x-4][y].setTileType("floor"); //tile is valid carve path through wall there
    					}
	        			
	        			generateMaze(map,x-4,y) ; //call function again from new square
	        		}
        		}
        		
        	}else if(direction == 3){ //right
        		if(!(x+4 >= map.length))
	        		if(map[x+4][y].getTileType().equals("empty")){ //check if we have been here
	        			
	        			Random randy = new Random();
    					int pathTaken = randy.nextInt(3) + 1;
    					
    					if(pathTaken == 1){
    						
    						map[x+1][y].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+1][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+2][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+3][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+4][y-1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+4][y].setTileType("floor"); //tile is valid carve path through wall there
    					}

    					if(pathTaken == 2){
    						
    						map[x+1][y].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+2][y].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+3][y].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+4][y].setTileType("floor"); //tile is valid carve path through wall there
    					}
    					
    					if(pathTaken == 3){
    						
    						map[x+1][y].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+1][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+2][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+3][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+4][y+1].setTileType("floor"); //tile is valid carve path through wall there
                			map[x+4][y].setTileType("floor"); //tile is valid carve path through wall there
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
    	//create a new level 
        Level newLevel = new Level(generateMap());
        
        
        //return the level to the game class 
        return newLevel;
    }
}
