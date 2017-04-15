/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import entities.Enemy;
import entities.GroundItem;
import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class Level 
{
	private DungeonTile[][] map;
    private ArrayList<Enemy> enemies;
    private ArrayList<GroundItem> groundItems;
    private int levelNumber ; 
    private int totalEnemies ; 
	
	public Level(DungeonTile [][] map, ArrayList<Enemy> enemies, ArrayList<GroundItem> groundItems, int levelNumber)
        {
            this.setLevelNumber(levelNumber) ; 
            this.enemies = enemies;
            this.groundItems = groundItems;
            this.setMap(map) ; 
            this.totalEnemies = enemies.size()  ; 
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

    /**
     * @return the groundItems
     */
    public ArrayList<GroundItem> getGroundItems() {
        return groundItems;
    }

    /**
     * @param groundItems the groundItems to set
     */
    public void setGroundItems(ArrayList<GroundItem> groundItems) {
        this.groundItems = groundItems;
    }

    /**
     * @return the totalEnemies
     */
    public int getTotalEnemies() {
        return totalEnemies;
    }

    /**
     * @param totalEnemies the totalEnemies to set
     */
    public void setTotalEnemies(int totalEnemies) {
        this.totalEnemies = totalEnemies;
    }
}
