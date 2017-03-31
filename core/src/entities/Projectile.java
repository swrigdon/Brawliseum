/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dungeon.DungeonTile;

/**
 *
 * @author Jason
 */
public class Projectile extends Entity
{
    private String projectileType;
    private float damage;

    
    @Override
    public void move(DungeonTile[][] map) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
}
