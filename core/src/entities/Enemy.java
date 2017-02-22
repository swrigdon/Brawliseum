/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Jason
 */
public class Enemy extends Entity
{
    private float health;
    private float damage;
    private float defense;
    
    public float attack()
    {
        
        return 0;
    }
    
    public float defense()
    {
        return 0;
    }
    
    @Override
    public void move() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
