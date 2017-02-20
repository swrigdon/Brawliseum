/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

/**
 *
 * @author Jason
 */
public class Level 
{
	private DungeonTile[][] map; 
	
	public Level(DungeonTile [][] map){
		this.setMap(map) ; 
	}

	public DungeonTile[][] getMap() {
		return map;
	}

	public void setMap(DungeonTile[][] map) {
		this.map = map;
	}
}
