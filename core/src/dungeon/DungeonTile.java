/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import com.badlogic.gdx.math.Rectangle;
import screens.GameScreen;

/**
 *
 * @author Jason
 */
public class DungeonTile extends Rectangle
{
    private String tileType;
    private int x;
    private int y;
    GameScreen gscreen;
    
    public DungeonTile(String tileType, int x, int y)
    {
        this.tileType = tileType;
        this.x = x;
        this.y = y;
        
        this.set(x, y, 32, 32);
    }

    public String getTileType() 
    {
        return tileType;
    }

    public void setTileType(String tileType)
    {
        this.tileType = tileType;
    }
}
