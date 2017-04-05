/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import constants.GameConstants;
import dungeon.DungeonTile;
import dungeon.Level;
import screens.GameScreen;

/**
 *
 * @author Jason
 */
public class Player extends Entity
{
    private String playerClass;
    private float health;
    private float energy;
    private float experience;
    private float baseAttack;
    private float baseDefense;
    private int level;    
    private long lastAttack ; 
    private long attackSpeed ; 

	private int playerDirection;
    

    public Player(float x, float y, Texture playerTexture, Level currentLevel, String playerClass)
    {
        this.setEntityTexture(playerTexture);
        this.setX(x);
        this.setY(y);
        this.setxLocation(x);
        this.setyLocation(y);
        this.setCurrentLevel(currentLevel);
        this.setSpeed(GameConstants.PLAYER_BASE_SPEED);
        this.setPlayerClass(playerClass);
        
        this.setPlayerDirection(GameConstants.UP); 
        
        this.setMovingX(false);
        this.setMovingY(false);
        this.setMovingNX(false);
        this.setMovingNY(false);
        
        this.set(x, y, (float)playerTexture.getWidth()/32, (float)playerTexture.getHeight()/32);
        
        attackSpeed = GameConstants.PLAYER_BASE_ATTACK_SPEED ; 
        
        this.setHealth(GameConstants.PLAYER_STARTING_HEALTH);
    }
    
    public void draw(SpriteBatch batch)
    {
    	batch.draw(this.getEntityTexture(), this.getxLocation() * GameConstants.FLOOR_TEXTURE.getWidth(), 
                this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
    }
    
    @Override
    public void move(DungeonTile[][] map) 
    {
        //Added by Jason
        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            this.setyLocation(this.getyLocation() + this.getSpeed()*Gdx.graphics.getDeltaTime());
            this.setMovingY(true);
            this.setPlayerDirection(GameConstants.UP);

            this.setMovingX(false);
            this.setMovingNY(false);
            this.setMovingNX(false);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            this.setyLocation(this.getyLocation() - this.getSpeed()*Gdx.graphics.getDeltaTime());
            this.setMovingNY(true);
            this.setPlayerDirection(GameConstants.DOWN);

            this.setMovingX(false);
            this.setMovingY(false);
            this.setMovingNX(false);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            this.setxLocation(this.getxLocation() + this.getSpeed()*Gdx.graphics.getDeltaTime());
            this.setMovingX(true);
            this.setPlayerDirection(GameConstants.RIGHT);

            this.setMovingY(false);
            this.setMovingNY(false);
            this.setMovingNX(false);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            this.setxLocation(this.getxLocation() - this.getSpeed()*Gdx.graphics.getDeltaTime());
            this.setMovingNX(true);
            this.setPlayerDirection(GameConstants.LEFT);

            this.setMovingY(false);
            this.setMovingX(false);
            this.setMovingNY(false);
        }       
        else
        {
            this.setMovingX(false);
            this.setMovingY(false);
            this.setMovingNY(false);
            this.setMovingNX(false);
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && (com.badlogic.gdx.utils.TimeUtils.nanoTime() - lastAttack > attackSpeed))
        {
        	//System.out.println("Attacking") ; 
        	//System.out.println("time diff: "+(com.badlogic.gdx.utils.TimeUtils.nanoTime() - lastAttack) ) ; 
        	//System.out.println("attack speed: "+(attackSpeed) ) ; 
            if(playerClass.equals("sword"))
            {
                swordAttack(map);
            }
            else if(playerClass.equals("bow"))
            {
                bowAttack();
            }
            
            lastAttack = com.badlogic.gdx.utils.TimeUtils.nanoTime();
            
            //GameScreen.printGrid(map) ; 
        }
    }
    
    private void swordAttack(DungeonTile[][] map)
    {
        if(playerDirection == GameConstants.UP)
        {
            if(map[(int)this.getxLocation()][(int)this.getyLocation()+1].getEnemyOnTile() != null)
            {
                System.out.println("Enemy above me");
                map[(int)this.getxLocation()][(int)this.getyLocation()+1].getEnemyOnTile().getHit(attack());
                
                if(!map[(int)this.getxLocation()][(int)this.getyLocation()+2].getTileType().equals("wall"))
                {
                	//map[(int)this.getxLocation()][(int)this.getyLocation()+1].getEnemyOnTile().setyLocation((int)this.getyLocation()+2);
                }
            }
            else if(map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy at me 0");
                map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
                
                if(!map[(int)this.getxLocation()][(int)this.getyLocation()+1].getTileType().equals("wall"))
                {
                	//map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().setyLocation((int)this.getyLocation()+1);
                }
            }
        }
        else if(playerDirection == GameConstants.RIGHT)
        {
            if(map[(int)this.getxLocation()+1][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy to the right of me");
                map[(int)this.getxLocation()+1][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
                
                if(!map[(int)this.getxLocation()+1][(int)this.getyLocation()].getTileType().equals("wall"))
                {
                	//map[(int)this.getxLocation()+1][(int)this.getyLocation()].getEnemyOnTile().setxLocation((int)this.getxLocation()+2);
                }
            }
            else if(map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy at me 1");
                map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
                
                if(!map[(int)this.getxLocation()+1][(int)this.getyLocation()].getTileType().equals("wall"))
                {
                	//map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().setxLocation((int)this.getxLocation()+1);
                }
            }
        }
        else if(playerDirection == GameConstants.DOWN)
        {
            if(map[(int)this.getxLocation()][(int)this.getyLocation()-1].getEnemyOnTile() != null)
            {
                System.out.println("Enemy below me");
                map[(int)this.getxLocation()][(int)this.getyLocation()-1].getEnemyOnTile().getHit(attack());
                
                if(!map[(int)this.getxLocation()][(int)this.getyLocation()-2].getTileType().equals("wall"))
                {
                	//map[(int)this.getxLocation()][(int)this.getyLocation()-1].getEnemyOnTile().setyLocation((int)this.getyLocation()-2);
                }
            }
            else if(map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy at me 2");
                map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
                
                if(!map[(int)this.getxLocation()][(int)this.getyLocation()-2].getTileType().equals("wall"))
                {
                	//map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().setyLocation((int)this.getyLocation()-2);
                }
            }
        }
        else if(playerDirection == GameConstants.LEFT)
        {
            if(map[(int)this.getxLocation()-1][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy to the left of me");
                map[(int)this.getxLocation()-1][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
                
                if(!map[(int)this.getxLocation()-2][(int)this.getyLocation()].getTileType().equals("wall"))
                {
                	//map[(int)this.getxLocation()-1][(int)this.getyLocation()].getEnemyOnTile().setxLocation((int)this.getxLocation()-2);
                }
            }
            else if(map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy at me 3");
                map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
                
                if(!map[(int)this.getxLocation()][(int)this.getyLocation()-2].getTileType().equals("wall") &&
                        this.getyLocation()-2 > 0)
                {
                	//map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().setxLocation((int)this.getxLocation()-2);
                }
            }
        }
    }
    private void bowAttack()
    {
        
    }
    
    public float attack()
    {
        return 50;
    }
    
    public float defense()
    {
        return 0;
    }

    /**
     * @return the health
     */
    public float getHealth() 
    {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(float health) 
    {
        this.health = health;
    }

    /**
     * @return the energy
     */
    public float getEnergy() 
    {
        return energy;
    }

    /**
     * @param energy the energy to set
     */
    public void setEnergy(float energy) 
    {
        this.energy = energy;
    }

    /**
     * @return the experience
     */
    public float getExperience() 
    {
        return experience;
    }

    /**
     * @param experience the experience to set
     */
    public void setExperience(float experience) 
    {
        this.experience = experience;
    }

    /**
     * @return the baseAttack
     */
    public float getBaseAttack() 
    {
        return baseAttack;
    }

    /**
     * @param baseAttack the baseAttack to set
     */
    public void setBaseAttack(float baseAttack) 
    {
        this.baseAttack = baseAttack;
    }

    /**
     * @return the baseDefense
     */
    public float getBaseDefense() 
    {
        return baseDefense;
    }

    /**
     * @param baseDefense the baseDefense to set
     */
    public void setBaseDefense(float baseDefense) 
    {
        this.baseDefense = baseDefense;
    }

    /**
     * @return the level
     */
    public int getLevel() 
    {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) 
    {
        this.level = level;
    }

    /**
     * @return the playerClass
     */
    public String getPlayerClass() {
        return playerClass;
    }

    /**
     * @param playerClass the playerClass to set
     */
    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    /**
     * @return the playerDirection
     */
    public int getPlayerDirection() {
        return playerDirection;
    }

    /**
     * @param playerDirection the playerDirection to set
     */
    public void setPlayerDirection(int playerDirection) {
        this.playerDirection = playerDirection;
    }
    
    public long getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(long attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
}
