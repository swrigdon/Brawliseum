/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import entities.Enemy;
import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class Level 
{
	private DungeonTile[][] map;
    private ArrayList<Enemy> enemies;
    private int levelNumber ; 
	
	public Level(DungeonTile [][] map, ArrayList<Enemy> enemies, int levelNumber)
    {
		this.setLevelNumber(levelNumber) ; 
		this.enemies = enemies;
        this.setMap(map) ; 
	}

	public DungeonTile[][] getMap(){
		return map;
	}

	public void setMap(DungeonTile[][] map){
		this.map = map;
	}

    /**
     * @return the enemies
     */
    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }

    /**
     * @param enemies the enemies to set
     */
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
}
