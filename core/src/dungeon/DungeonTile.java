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
public class DungeonTile 
{
    private String tileType;
    private int x;
    private int y;
    
    DungeonTile(String tileType, int x, int y)
    {
        this.tileType = tileType;
        this.x = x;
        this.y = y;
    }
    
}
