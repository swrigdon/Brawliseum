/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import constants.GameConstants;
import dungeon.DungeonTile;
import dungeon.Level;
import items.Potion;
import java.util.ArrayList;
import screens.GameScreen;

/**
 *
 * @author Jason
 */
public class Player extends Entity
{
    private String playerClass;
    private float health;
    private float maxHealth;
    private float energy;
    private float experience;
    private float baseAttack;
    private float baseDefense;
    private int level;    
    private long lastAttack ; 
    private long attackSpeed ;
    private long baseAttackSpeed;
    private float normalSpeed;
    
    private int enemiesKilled ;
    private int highestLevel ; 

    private int playerDirection;
    
    private boolean attacking;
       
    //
    private Potion playerPotion;
    
    private ArrayList<Projectile> projectiles;
    
    Animation<TextureRegion> playerUp;
    Animation<TextureRegion> playerDown;
    Animation<TextureRegion> playerLeft;
    Animation<TextureRegion> playerRight;
    Animation<TextureRegion> playerShootUp;
    Animation<TextureRegion> playerShootDown;
    Animation<TextureRegion> playerShootLeft;
    Animation<TextureRegion> playerShootRight;
    TextureRegion[] playerUpAni;
    TextureRegion[] playerDownAni;
    TextureRegion[] playerLeftAni;
    TextureRegion[] playerRightAni;
    TextureRegion[] playerShootUpAni;
    TextureRegion[] playerShootDownAni;
    TextureRegion[] playerShootLeftAni;
    TextureRegion[] playerShootRightAni;
    
    Sound arrowShoot = Gdx.audio.newSound(Gdx.files.internal("arrow shoot.wav"));
    Sound spellShoot = Gdx.audio.newSound(Gdx.files.internal("spell shoot.wav"));
    Sound swordHit = Gdx.audio.newSound(Gdx.files.internal("sword hit.wav"));
    Sound drinkPotion = Gdx.audio.newSound(Gdx.files.internal("Potion_Use.wav"));

    public Player(float x, float y, Level currentLevel, String playerClass)
    {
        this.setX(x);
        this.setY(y);
        this.setxLocation(x);
        this.setyLocation(y);
        this.setCurrentLevel(currentLevel);
        
        this.setPlayerClass(playerClass);
        this.playerPotion = null;
        this.enemiesKilled = 0 ; 
        this.highestLevel = 1 ; 
        
        this.experience = 0;
             
        
        this.setPlayerDirection(GameConstants.UP); 
        
        this.setMovingX(false);
        this.setMovingY(false);
        this.setMovingNX(false);
        this.setMovingNY(false);
        
        this.attacking = false;
          
        this.set(x, y, (float)18/32, (float)27/32);
        
        projectiles = new ArrayList<Projectile>();
        
        if(playerClass.equals("sword"))
        {
            this.setSpeed(GameConstants.WARRIOR_STARTING_SPEED);
            this.setNormalSpeed(GameConstants.WARRIOR_STARTING_SPEED);
            this.setMaxHealth(GameConstants.WARRIOR_STARTING_HEALTH);
            this.attackSpeed = GameConstants.WARRIOR_BASE_ATTACK_SPEED ; 
            this.baseAttack = GameConstants.WARRIOR_BASE_DAMAGE;
        }
        else if(playerClass.equals("bow"))
        {
            this.setSpeed(GameConstants.ARCHER_STARTING_SPEED);
            this.setNormalSpeed(GameConstants.ARCHER_STARTING_SPEED);
            this.attackSpeed = GameConstants.ARCHER_BASE_ATTACK_SPEED ; 
            this.setMaxHealth(GameConstants.ARCHER_STARTING_HEALTH);
            this.baseAttack = GameConstants.ARCHER_BASE_DAMAGE;
        }
        else
        {
            this.setSpeed(GameConstants.MAGE_STARTING_SPEED);
            this.setNormalSpeed(GameConstants.MAGE_STARTING_SPEED);
            this.attackSpeed = GameConstants.MAGE_BASE_ATTACK_SPEED ; 
            this.setMaxHealth(GameConstants.MAGE_STARTING_HEALTH);
            this.baseAttack = GameConstants.MAGE_BASE_DAMAGE;
        }
        
        this.baseAttackSpeed = this.getAttackSpeed();
        this.health = this.getMaxHealth(); 
        
        createPlayerTexture();
    }
    
    private void createPlayerTexture()
    {
        TextureRegion[][] tmpFrame = null;
        TextureRegion[][] tmp2 = null;
        TextureRegion[][] tmp3 = null;
        TextureRegion[][] tmp4 = null;
        
        TextureRegion[][] tmp5 = null;
        TextureRegion[][] tmp6 = null;
        TextureRegion[][] tmp7 = null;
        TextureRegion[][] tmp8 = null;
         
        int classSpeed = 15;
        
        if(playerClass.equals("bow"))
        {
            tmpFrame = TextureRegion.split(GameConstants.ARCHER_UP, 17, 27);
            tmp2 = TextureRegion.split(GameConstants.ARCHER_DOWN, 18, 30);
            tmp3 = TextureRegion.split(GameConstants.ARCHER_LEFT, 21, 28);
            tmp4 = TextureRegion.split(GameConstants.ARCHER_RIGHT, 21, 27);
            
            tmp5 = TextureRegion.split(GameConstants.ARCHER_SHOOT_UP, 32, 31);
            tmp6 = TextureRegion.split(GameConstants.ARCHER_SHOOT_DOWN, 32, 30);
            tmp7 = TextureRegion.split(GameConstants.ARCHER_SHOOT_LEFT, 32, 30);
            tmp8 = TextureRegion.split(GameConstants.ARCHER_SHOOT_RIGHT, 32, 30);
            
            classSpeed = 23;
        }
        else if(playerClass.equals("sword"))
        {
            
            tmpFrame = TextureRegion.split(GameConstants.WARRIOR_UP, 32, 28);
            tmp2 = TextureRegion.split(GameConstants.WARRIOR_DOWN, 32, 28);
            tmp3 = TextureRegion.split(GameConstants.WARRIOR_LEFT, 32, 28);
            tmp4 = TextureRegion.split(GameConstants.WARRIOR_RIGHT, 32, 28);
            
            tmp5 = TextureRegion.split(GameConstants.WARRIOR_SHOOT_UP, 32, 28);
            tmp6 = TextureRegion.split(GameConstants.WARRIOR_SHOOT_DOWN, 32, 28);
            tmp7 = TextureRegion.split(GameConstants.WARRIOR_SHOOT_LEFT, 32, 28);
            tmp8 = TextureRegion.split(GameConstants.WARRIOR_SHOOT_RIGHT, 32, 28);
            
            classSpeed = 15;
        }
        else
        {
            
            tmpFrame = TextureRegion.split(GameConstants.MAGE_UP, 16, 26);
            tmp2 = TextureRegion.split(GameConstants.MAGE_DOWN, 16, 26);
            tmp3 = TextureRegion.split(GameConstants.MAGE_LEFT, 15, 25);
            tmp4 = TextureRegion.split(GameConstants.MAGE_RIGHT, 15, 25);
            
            tmp5 = TextureRegion.split(GameConstants.MAGE_SHOOT_UP, 32, 28);
            tmp6 = TextureRegion.split(GameConstants.MAGE_SHOOT_DOWN, 32, 28);
            tmp7 = TextureRegion.split(GameConstants.MAGE_SHOOT_LEFT, 32, 28);
            tmp8 = TextureRegion.split(GameConstants.MAGE_SHOOT_RIGHT, 32, 28);
            
            classSpeed = 10;
        }
        
        playerUpAni = new TextureRegion[9];
        playerDownAni = new TextureRegion[9];
        playerLeftAni = new TextureRegion[9];
        playerRightAni = new TextureRegion[9];
        
        if(playerClass.equals("bow"))
        {
            playerShootUpAni = new TextureRegion[13];
            playerShootDownAni = new TextureRegion[13];
            playerShootLeftAni = new TextureRegion[13];
            playerShootRightAni = new TextureRegion[13];
        }
        else if(playerClass.equals("sword"))
        {
            playerShootUpAni = new TextureRegion[8];
            playerShootDownAni = new TextureRegion[8];
            playerShootLeftAni = new TextureRegion[8];
            playerShootRightAni = new TextureRegion[8];
        }
        else
        {
            playerShootUpAni = new TextureRegion[6];
            playerShootDownAni = new TextureRegion[6];
            playerShootLeftAni = new TextureRegion[6];
            playerShootRightAni = new TextureRegion[6];
        }
        
        for(int i = 0; i < 9; i++)
        {
            playerUpAni[i] = tmpFrame[0][i]; 
            playerDownAni[i] = tmp2[0][i]; 
            playerLeftAni[i] = tmp3[0][i]; 
            playerRightAni[i] = tmp4[0][i]; 
        }
        
        for(int i = 0; i < 13; i++)
        {
            if(playerClass.equals("sword") && i == 8)
            {
                break;
            }
            else if(playerClass.equals("mage") && i == 6)
            {
                break;
            }
            else
            {
                playerShootUpAni[i] = tmp5[0][i];
                playerShootDownAni[i] = tmp6[0][i];
                playerShootLeftAni[i] = tmp7[0][i];
                playerShootRightAni[i] = tmp8[0][i];
            }
        }
        
        playerUp = new Animation<TextureRegion>((float)1/15, playerUpAni);
        playerDown = new Animation<TextureRegion>((float)1/15, playerDownAni);
        playerLeft = new Animation<TextureRegion>((float)1/15, playerLeftAni);
        playerRight = new Animation<TextureRegion>((float)1/15, playerRightAni);
        
        playerShootUp = new Animation<TextureRegion>((float)1/classSpeed, playerShootUpAni);
        playerShootDown = new Animation<TextureRegion>((float)1/classSpeed, playerShootDownAni);
        playerShootLeft = new Animation<TextureRegion>((float)1/classSpeed, playerShootLeftAni);
        playerShootRight = new Animation<TextureRegion>((float)1/classSpeed, playerShootRightAni);
    }
    
    public void draw(SpriteBatch batch, float elapsedTime)
    {
        //MESSED UP ON TEXTURES
        float fixingDis = 0;
        float fixingShootDis = 0;
        if(playerClass.equals("bow"))
        {
            fixingDis = 0;
            fixingShootDis = (float).25;
        }
        else if(playerClass.equals("sword"))
        {
            fixingDis = (float).29;
            fixingShootDis = (float).29;
        }
        else
        {
            fixingDis = 0;
            fixingShootDis = (float).29;
        }
        
        if(playerDirection == GameConstants.UP) 
        {
            if(attacking)
            {
                batch.draw(playerShootUp.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingShootDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                           this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else if(this.isMovingY())
            {
                batch.draw(playerUp.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                           this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else
            {
                batch.draw(playerUpAni[0], (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                           this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
        }
        else if(playerDirection == GameConstants.DOWN)
        {
            if(attacking)
            {
                batch.draw(playerShootDown.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingShootDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                       this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else if(this.isMovingNY())
            {
                batch.draw(playerDown.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                       this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else
            {
                batch.draw(playerDownAni[0], (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                           this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
        }
        else if(playerDirection == GameConstants.LEFT)
        {
            if(attacking)
            {
                batch.draw(playerShootLeft.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingShootDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                       this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else if(this.isMovingNX())
            {
                batch.draw(playerLeft.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                       this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else
            {
                batch.draw(playerLeftAni[0], (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                           this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
        }
        else if(playerDirection == GameConstants.RIGHT)
        {
            if(attacking)
            {
                batch.draw(playerShootRight.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingShootDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                       this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else if(this.isMovingX())
            {
                batch.draw(playerRight.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                       this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else
            {
                batch.draw(playerRightAni[0], (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                           this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
        }
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
            if(playerClass.equals("sword"))
            {
                swordAttack(map);
            }
            else if(playerClass.equals("bow") || playerClass.equals("mage"))
            {
            	if(playerClass.equals("mage"))
            		spellShoot.play(.75f);
            	if(playerClass.equals("bow"))
            		arrowShoot.play(.75f);
            	
                rangeAttack(map);
            }
            
            lastAttack = com.badlogic.gdx.utils.TimeUtils.nanoTime();
            
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            this.attacking = true;
        }
        else 
        {
            this.attacking = false;
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.E))
        {
            if(playerPotion != null)
            {
                if(playerPotion.getPotionName().equals("health"))
                {
                	drinkPotion.play(7.5f);
                    health = Math.min(playerPotion.getValue() + health, maxHealth);
                }
                else if(playerPotion.getPotionName().equals("attack"))
                {
                	drinkPotion.play(7.5f);
                    attackSpeed -= playerPotion.getValue();
                }
                else if(playerPotion.getPotionName().equals("move"))
                {
                	drinkPotion.play(7.5f);
                    this.setSpeed(this.getSpeed()+playerPotion.getValue());
                }
                
                playerPotion = null;
            }          
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
                
            }
            else if(map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy at me 0");
                map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
            }
        }
        else if(playerDirection == GameConstants.RIGHT)
        {
            if(map[(int)this.getxLocation()+1][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy to the right of me");
                map[(int)this.getxLocation()+1][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
            }
            else if(map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy at me 1");
                map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
            }
        }
        else if(playerDirection == GameConstants.DOWN)
        {
            if(map[(int)this.getxLocation()][(int)this.getyLocation()-1].getEnemyOnTile() != null)
            {
                System.out.println("Enemy below me");
                map[(int)this.getxLocation()][(int)this.getyLocation()-1].getEnemyOnTile().getHit(attack());
                
            }
            else if(map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy at me 2");
                map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
            }
        }
        else if(playerDirection == GameConstants.LEFT)
        {
            if(map[(int)this.getxLocation()-1][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy to the left of me");
                map[(int)this.getxLocation()-1][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());
                
            }
            else if(map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile() != null)
            {
                System.out.println("Enemy at me 3");
                map[(int)this.getxLocation()][(int)this.getyLocation()].getEnemyOnTile().getHit(attack());

            }
        }
    }
    private void rangeAttack(DungeonTile[][] map)
    {   
        Projectile newProjectile = new Projectile(playerClass, attack(), this.getxLocation(), this.getyLocation(), (12), playerDirection);
        
        projectiles.add(newProjectile);
          
    }
    
    public float attack()
    {
        return getBaseAttack();
    }
    
    public void getHit(float damage)
    {   
    	swordHit.play(1.0f);
        this.health -= damage;
        System.out.println("Health: " + this.health);
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

    /**
     * @return the projectiles
     */
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     * @param projectiles the projectiles to set
     */
    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    /**
     * @return the playerPotion
     */
    public Potion getPlayerPotion() {
        return playerPotion;
    }

    /**
     * @param playerPotion the playerPotion to set
     */
    public void setPlayerPotion(Potion playerPotion) {
        this.playerPotion = playerPotion;
    }

    /**
     * @return the maxHealth
     */
    public float getMaxHealth() {
        return maxHealth;
    }

    /**
     * @param maxHealth the maxHealth to set
     */
    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

	public int getEnemiesKilled() {
		return enemiesKilled;
	}

	public void setEnemiesKilled(int enemiesKilled) {
		this.enemiesKilled = enemiesKilled;
	}

	public int getHighestLevel() {
		return highestLevel;
	}

	public void setHighestLevel(int highestLevel) {
		this.highestLevel = highestLevel;
	}

    /**
     * @return the baseAttackSpeed
     */
    public long getBaseAttackSpeed() {
        return baseAttackSpeed;
    }

    /**
     * @param baseAttackSpeed the baseAttackSpeed to set
     */
    public void setBaseAttackSpeed(long baseAttackSpeed) {
        this.baseAttackSpeed = baseAttackSpeed;
    }

    /**
     * @return the normalSpeed
     */
    public float getNormalSpeed() {
        return normalSpeed;
    }

    /**
     * @param normalSpeed the normalSpeed to set
     */
    public void setNormalSpeed(float normalSpeed) {
        this.normalSpeed = normalSpeed;
    }
}
