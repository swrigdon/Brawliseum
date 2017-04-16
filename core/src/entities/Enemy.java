/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dungeon.DungeonTile;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import constants.GameConstants;

import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Sebastian
 */
public class Enemy extends Entity
{
    private final int MAX_VISION = 600;
    private final int MAX_RANGE = 10 ; 
    private float health;
    private float damage;
    private float defense;
    private float endX;
    private float endY;
    private DungeonTile[][] map;
    private DungeonTile newLocation;
    private int direction ; 
    
    private long lastAttack ; 
    private long attackSpeed ; 
    
    private int levelNum;
    
    private Player player;
    
    public boolean visited = false;


    private boolean pathFind = false;
    
    private boolean attacking;
    
    ArrayList<DungeonTile> path = new ArrayList<DungeonTile>();
    
    Animation<TextureRegion> enemyUp;
    Animation<TextureRegion> enemyDown;
    Animation<TextureRegion> enemyLeft;
    Animation<TextureRegion> enemyRight;
    Animation<TextureRegion> enemyShootUp;
    Animation<TextureRegion> enemyShootDown;
    Animation<TextureRegion> enemyShootLeft;
    Animation<TextureRegion> enemyShootRight;
    TextureRegion[] enemyUpAni;
    TextureRegion[] enemyDownAni;
    TextureRegion[] enemyLeftAni;
    TextureRegion[] enemyRightAni;
    TextureRegion[] enemyShootUpAni;
    TextureRegion[] enemyShootDownAni;
    TextureRegion[] enemyShootLeftAni;
    TextureRegion[] enemyShootRightAni;
    
    Sound swordHit2 = Gdx.audio.newSound(Gdx.files.internal("sword hit2.wav"));
    
    //private boolean finished = true;
    
    public Enemy(DungeonTile[][] map, Texture enemyTexture, int levelNum)
    {
        this.map  = map;
        newLocation = null;
        
        attackSpeed = GameConstants.ENEMY_BASE_ATTACK_SPEED;
        
        this.levelNum = levelNum;

        this.attacking = false;
        
        this.setEntityTexture(enemyTexture);
        
        if(levelNum %5 == 0)
        {
            this.set(this.getxLocation(), this.getyLocation(), 1, 1);
        }
        else
        {
            this.set(this.getxLocation(), this.getyLocation(), (float)enemyTexture.getWidth()/32, (float)enemyTexture.getHeight()/32);
        }
        
        direction = GameConstants.ENEMY_STARTING_DIRECTION ; 
        
        this.setMovingX(false);
        this.setMovingY(false);
        this.setMovingNX(false);
        this.setMovingNY(false);
        
        createEnemyTexture();
    }
    
    private void createEnemyTexture()
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
        
        if(levelNum %5 == 0)
        {
            tmpFrame = TextureRegion.split(GameConstants.BOSS_UP, 64, 51);
            tmp2 = TextureRegion.split(GameConstants.BOSS_DOWN, 64, 51);
            tmp3 = TextureRegion.split(GameConstants.BOSS_LEFT, 64, 51);
            tmp4 = TextureRegion.split(GameConstants.BOSS_RIGHT, 64, 51);

            tmp5 = TextureRegion.split(GameConstants.BOSS_SHOOT_UP, 64, 51);
            tmp6 = TextureRegion.split(GameConstants.BOSS_SHOOT_DOWN, 64, 51);
            tmp7 = TextureRegion.split(GameConstants.BOSS_SHOOT_LEFT, 64, 51);
            tmp8 = TextureRegion.split(GameConstants.BOSS_SHOOT_RIGHT, 64, 51);
        }
        else 
        {
            tmpFrame = TextureRegion.split(GameConstants.ENEMY_UP, 32, 27);
            tmp2 = TextureRegion.split(GameConstants.ENEMY_DOWN, 32, 27);
            tmp3 = TextureRegion.split(GameConstants.ENEMY_LEFT, 32, 27);
            tmp4 = TextureRegion.split(GameConstants.ENEMY_RIGHT, 32, 27);

            tmp5 = TextureRegion.split(GameConstants.ENEMY_SHOOT_UP, 32, 27);
            tmp6 = TextureRegion.split(GameConstants.ENEMY_SHOOT_DOWN, 32, 27);
            tmp7 = TextureRegion.split(GameConstants.ENEMY_SHOOT_LEFT, 32, 27);
            tmp8 = TextureRegion.split(GameConstants.ENEMY_SHOOT_RIGHT, 32, 27);
        }

        classSpeed = 8;

        
        enemyUpAni = new TextureRegion[9];
        enemyDownAni = new TextureRegion[9];
        enemyLeftAni = new TextureRegion[9];
        enemyRightAni = new TextureRegion[9];
        

        enemyShootUpAni = new TextureRegion[6];
        enemyShootDownAni = new TextureRegion[6];
        enemyShootLeftAni = new TextureRegion[6];
        enemyShootRightAni = new TextureRegion[6];

        
        for(int i = 0; i < 9; i++)
        {
            enemyUpAni[i] = tmpFrame[0][i]; 
            enemyDownAni[i] = tmp2[0][i]; 
            enemyLeftAni[i] = tmp3[0][i]; 
            enemyRightAni[i] = tmp4[0][i]; 
        }
        
        for(int i = 0; i < 6; i++)
        {
            enemyShootUpAni[i] = tmp5[0][i];
            enemyShootDownAni[i] = tmp6[0][i];
            enemyShootLeftAni[i] = tmp7[0][i];
            enemyShootRightAni[i] = tmp8[0][i];
        }
        
        enemyUp = new Animation<TextureRegion>((float)1/15, enemyUpAni);
        enemyDown = new Animation<TextureRegion>((float)1/15, enemyDownAni);
        enemyLeft = new Animation<TextureRegion>((float)1/15, enemyLeftAni);
        enemyRight = new Animation<TextureRegion>((float)1/15, enemyRightAni);
        
        enemyShootUp = new Animation<TextureRegion>((float)1/classSpeed, enemyShootUpAni);
        enemyShootDown = new Animation<TextureRegion>((float)1/classSpeed, enemyShootDownAni);
        enemyShootLeft = new Animation<TextureRegion>((float)1/classSpeed, enemyShootLeftAni);
        enemyShootRight = new Animation<TextureRegion>((float)1/classSpeed, enemyShootRightAni);
    }
    
    public void drawEnemy(SpriteBatch batch, float elapsedTime)
    {
        //MESSED UP ON TEXTURES
        float fixingDis = (float).29;
        float fixingShootDis = (float).29;
        
        if(direction == GameConstants.UP) 
        {
            
            if(attacking)
            {
                batch.draw(enemyShootUp.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingShootDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                           this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else if(this.isMovingY())
            {
                batch.draw(enemyUp.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                           this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else
            {
                batch.draw(enemyUpAni[0], (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                           this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
        }
        else if(direction == GameConstants.DOWN)
        {
            
            if(attacking)
            {
                batch.draw(enemyShootDown.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingShootDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                       this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else if(this.isMovingNY())
            {
                batch.draw(enemyDown.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                       this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else
            {
                batch.draw(enemyDownAni[0], (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                           this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
        }
        else if(direction == GameConstants.LEFT)
        {
            
            if(attacking)
            {
                batch.draw(enemyShootLeft.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingShootDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                       this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else  if(this.isMovingNX())
            {
                batch.draw(enemyLeft.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                       this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else
            {
                batch.draw(enemyLeftAni[0], (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                           this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
        }
        else if(direction == GameConstants.RIGHT)
        {
            
            if(attacking)
            {
                batch.draw(enemyShootRight.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingShootDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                       this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else if(this.isMovingX())
            {
                batch.draw(enemyRight.getKeyFrame(elapsedTime, true), (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                       this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
            else
            {
                batch.draw(enemyRightAni[0], (this.getxLocation()-fixingDis) * GameConstants.FLOOR_TEXTURE.getWidth(), 
                           this.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
            }
        }
    }
    
    public void setPlayer(Player player)
    {
        this.player = player;
    }
    
    public void setPath()
    {
        path = aStar();
    }
    
    public float attack()
    {
        
        return 0;
    }
    
    public float defense()
    {
        return 0;
    }
    
    private ArrayList<DungeonTile> aStar()
    {
        map[(int)this.getxLocation()][(int)this.getyLocation()].setDepth(0);
        
        ArrayList<DungeonTile> closed = new ArrayList<DungeonTile>();
        ArrayList<DungeonTile> open = new ArrayList<DungeonTile>();
        
        open.add(map[(int)this.getxLocation()][(int)this.getyLocation()]);
        
        //map[(int)endX][(int)endY].setParent(null);
        
        int maxDepth = 0;
        
        while(maxDepth < MAX_VISION && open.size() != 0)
        {
            DungeonTile current = open.get(0);
            
            //This means that the current location is already at the player
            if(current == map[(int)endX][(int)endY])
            {
                break;
            }
            
            open.remove(current);
            closed.add(current);
            
            for(int x = -1; x < 2; x++)
            {
                for(int y = -1; y < 2; y++)
                {
                    if( x == 0 && y == 0)
                    {
                        continue;
                    }
                    
                    if(x != 0 && y !=0)
                    {
                        continue;
                    }
                    
                    int neighborX = x + current.getX();
                    int neighborY = y + current.getY();
                    
                    if(!(map[neighborX][neighborY].getTileType().equals("wall")))
                    {
                        DungeonTile neighbor= map[neighborX][neighborY]; 
                        if(!open.contains(neighbor) && !closed.contains(neighbor)){
                        	neighbor.getHeuristic();
                        	maxDepth= Math.max(maxDepth, neighbor.setParent(current));
                        	open.add(neighbor);
                        }
                    }
                }
            }
        }      
        
        if(map[(int)endX][(int)endY].getParent()==null){
        	return null;
        }
        
        
        
        ArrayList<DungeonTile> path = new ArrayList<DungeonTile>();
        DungeonTile target = map[(int)endX][(int)endY];
        while(target!= map[(int)this.getxLocation()][(int)this.getyLocation()]){
        	path.add(target);
        	target=target.getParent();
        }
        return path;
    }
    
    //public movementCost(neighborX, neighborY, )
    //test comment
    
    private int calcDistance(){
    	return (int) Math.sqrt(Math.pow(endX-this.getxLocation(), 2)+Math.pow(endY-this.getyLocation(), 2)) ; 
    }
    
    private float hitDistance()
    {
        return Math.round(Math.sqrt(Math.pow(endX-this.getxLocation(), 2)+Math.pow(endY-this.getyLocation(), 2)));
    }
            
    private void attackPlayer(Player player)
    {
        if((com.badlogic.gdx.utils.TimeUtils.nanoTime() - lastAttack > attackSpeed))
        {
            player.getHit(GameConstants.ENEMY_STARTING_DAMAGE + (GameConstants.ENEMY_DAMAGE_SCALE*this.levelNum));
            lastAttack = com.badlogic.gdx.utils.TimeUtils.nanoTime();
        }
    }
    
    public void move(DungeonTile[][] map) 
    {
    	if((int)this.getxLocation()==(int)endX && (int)this.getyLocation()==(int)endY)
        {
            return;
    	}
        
    	if(hitDistance() <= 1)
        {
            attacking = true;
            attackPlayer(player);
        }
        else
        {
            attacking = false;
        }
    	//outside the range of pathfinding 
    	if(calcDistance() >= MAX_RANGE)
    	{
          
    	}
        else
        {
            if(path!= null){
                if(path.size() > 1)
                {
                    changeLocation(path.get(path.size()-1)) ;

                    if(!(Math.abs(this.getxLocation() - ((float)path.get(path.size()-1).getX()+((float)(1)-((float)(this.getEntityTexture().getWidth())/32))/2)) 
                            != 0 ||
                         Math.abs(this.getyLocation() - ((float)path.get(path.size()-1).getY()+((float)(1)-((float)(this.getEntityTexture().getWidth())/32))/2)) 
                            != 0))
                    {
                        if(map[(int)this.getxLocation()][(int)this.getyLocation()+1].getEnemyOnTile() == this)
                        {
                            map[(int)this.getxLocation()][(int)this.getyLocation()+1].setOccupied(false);
                            map[(int)this.getxLocation()][(int)this.getyLocation()+1].setEnemyOnTile(null);
                        }
                        else if(map[(int)this.getxLocation()+1][(int)this.getyLocation()].getEnemyOnTile() == this)
                        {
                            map[(int)this.getxLocation()+1][(int)this.getyLocation()].setOccupied(false);
                            map[(int)this.getxLocation()+1][(int)this.getyLocation()].setEnemyOnTile(null);
                        }
                        else if(map[(int)this.getxLocation()][(int)this.getyLocation()-1].getEnemyOnTile() == this)
                        {
                            map[(int)this.getxLocation()][(int)this.getyLocation()-1].setOccupied(false);
                            map[(int)this.getxLocation()][(int)this.getyLocation()-1].setEnemyOnTile(null);
                        }
                        else if(map[(int)this.getxLocation()-1][(int)this.getyLocation()].getEnemyOnTile() == this)
                        {
                            map[(int)this.getxLocation()-1][(int)this.getyLocation()].setOccupied(false);
                            map[(int)this.getxLocation()-1][(int)this.getyLocation()].setEnemyOnTile(null);
                        }
                        
                        map[(int)this.getxLocation()][(int)this.getyLocation()].setOccupied(true);
                        map[(int)this.getxLocation()][(int)this.getyLocation()].setEnemyOnTile(this);
                        path = aStar();
                    }
                }
                else 
                {
                    path = aStar();
                }
            }
    	}
    }
    
    private void changeLocation(DungeonTile newLocation)
    {
     
        if(((float)newLocation.getX()+((float)(1)-((float)(this.getEntityTexture().getWidth())/32))/2) < this.getxLocation())
        {
            direction = 3 ; 
            this.setMovingNX(true);

            this.setMovingX(false);
            this.setMovingNY(false);
            this.setMovingY(false);

            this.setxLocation(this.getxLocation() - this.getSpeed()*Gdx.graphics.getDeltaTime());

            if((this.getxLocation() < (float)newLocation.getX()+((float)(1)-((float)(this.getEntityTexture().getWidth())/32))/2))
            {
                this.setxLocation((float)newLocation.getX()+((float)(1)-((float)(this.getEntityTexture().getWidth())/32))/2);
            }
        }
        else if((float)newLocation.getX()+((float)(1)-((float)(this.getEntityTexture().getWidth())/32))/2 > (this.getxLocation()))
        {
            direction = 1 ; 
            this.setMovingY(false);

            this.setMovingX(true);
            this.setMovingNY(false);
            this.setMovingNX(false);

            this.setxLocation(this.getxLocation() + this.getSpeed()*Gdx.graphics.getDeltaTime());

            if((this.getxLocation() > (float)newLocation.getX()+((float)(1)-((float)(this.getEntityTexture().getWidth())/32))/2))
            {
                this.setxLocation((float)newLocation.getX()+((float)(1)-((float)(this.getEntityTexture().getWidth())/32))/2);
            }
        }
        else if((float)newLocation.getY()+((float)(1)-((float)(this.getEntityTexture().getHeight())/32))/2 < this.getyLocation())
        {
            direction = 2 ; 
            this.setMovingY(false);
            this.setMovingX(false);
            this.setMovingNY(true);
            this.setMovingNX(false);

            this.setyLocation(this.getyLocation() - this.getSpeed()*Gdx.graphics.getDeltaTime());

            if((this.getyLocation() < (float)newLocation.getY()+((float)(1)-((float)(this.getEntityTexture().getHeight())/32))/2))
            {
                this.setyLocation((float)newLocation.getY()+((float)(1)-((float)(this.getEntityTexture().getHeight())/32))/2);
            }
        }
        else if((float)newLocation.getY()+((float)(1)-((float)(this.getEntityTexture().getHeight())/32))/2 > this.getyLocation())
        {
            direction = 0 ;
            this.setMovingY(true);
            this.setMovingX(false);
            this.setMovingNY(false);
            this.setMovingNX(false);

            this.setyLocation(this.getyLocation() + this.getSpeed()*Gdx.graphics.getDeltaTime());

            if((this.getyLocation() > (float)newLocation.getY()+((float)(1)-((float)(this.getEntityTexture().getHeight())/32))/2))
            {
                this.setyLocation((float)newLocation.getY()+((float)(1)-((float)(this.getEntityTexture().getHeight())/32))/2);
            }
        }   
        else
        {
            this.setMovingY(false);

            this.setMovingX(false);
            this.setMovingNY(false);
            this.setMovingNX(false);
        }
    }
    
    public void getHit(float damage)
    {
        this.setHealth(this.getHealth()-damage);
        swordHit2.play(.75f);
    }

    /**
     * @return the health
     */
    public float getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(float health) {
        this.health = health;
    }

    /**
     * @return the damage
     */
    public float getDamage() {
        return damage;
    }

    /**
     * @param damage the damage to set
     */
    public void setDamage(float damage) {
        this.damage = damage;
    }

    /**
     * @return the defense
     */
    public float getDefense() {
        return defense;
    }

    /**
     * @param defense the defense to set
     */
    public void setDefense(float defense) {
        this.defense = defense;
    }

    /**
     * @return the endX
     */
    public float getEndX() {
        return endX;
    }

    /**
     * @param endX the endX to set
     */
    public void setEndX(float endX) {
        this.endX = endX;
    }

    /**
     * @return the endY
     */
    public float getEndY() {
        return endY;
    }

    /**
     * @param endY the endY to set
     */
    public void setEndY(float endY) {
        this.endY = endY;
    }
    
    
    public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}  
}
