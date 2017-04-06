package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Application;

import constants.GameConstants;
import dungeon.BossLevelGenerator;
import dungeon.DungeonTile;
import dungeon.Level;
import dungeon.LevelGenerator;
import entities.Enemy;
import entities.Player;
import entities.Projectile;
import java.util.ArrayList;


public class GameScreen extends ScreenAdapter 
{

    Application game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;

    //testing variables need to move to separate class 
    public Texture endTest; 
    public DungeonTile[][] map ;
    public Rectangle rect;
    ArrayList<Rectangle> collisionMatrix = new ArrayList<Rectangle>();

    public LevelGenerator generator;
    public BossLevelGenerator bossGenerator;
    public Level currentLevel  ; 
    public Player player;
    ArrayList<Projectile> projectiles;
    
    private String playerClass;
    //Goes to boss level if %5==0, goes to maze level otherwise.
    private int levelNumber = 1;


    public GameScreen(Application game)
    {
        if(levelNumber%5==0)
        {
            bossGenerator = new BossLevelGenerator(29, 29);
            currentLevel = bossGenerator.generateLevel(levelNumber);
        }
        else
        {
            generator = new LevelGenerator(29, 29);
            currentLevel = generator.generateLevel(levelNumber);
        }

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        this.game = game;
        camera = new OrthographicCamera(w, h);
        //AFTER TESTING THIS SHOULD BE: camera.setToOrtho(false, 320*(w/h), 320);
        //camera.setToOrtho(false, 320*(w/h), 320);
        camera.setToOrtho(false, GameConstants.PLAYER_VIEW_X, GameConstants.PLAYER_VIEW_Y); //numbers are pixels player can see 
        batch = new SpriteBatch();

        //test temp textures
        endTest = new Texture("endTest.png");

        //creating test matrix
        map = currentLevel.getMap() ;

        genWall(collisionMatrix, currentLevel);
        
        //TEMP
        playerClass = "bow";
        
        System.out.println("........" + ((float)1-(float)(GameConstants.PLAYER_TEXTURE.getWidth())/64));

        player = new Player(2 + ((float)(1)-((float)(GameConstants.PLAYER_TEXTURE.getWidth())/32))/2, 
                            2 + ((float)(1)-((float)(GameConstants.PLAYER_TEXTURE.getHeight())/32))/2,
                GameConstants.PLAYER_TEXTURE, currentLevel, playerClass);
        
        printGrid(map)  ; 
        
    }
    
    public static void printGrid(DungeonTile [][] grid)
    {
    	System.out.println("Starting Grid Printing");
    	System.out.println("------------------------------------");
       for(int i = 0; i < grid.length; i++)
       {
          for(int j = 0; j < grid[0].length; j++)
          {
             System.out.printf("%5s ", grid[i][j].isOccupied());
          }
          System.out.println();
       }
       
   		System.out.println("------------------------------------ \n");

    }

    public final void genWall(ArrayList<Rectangle> collisionMatrix, Level currentLevel)
    {
        for(int x = 0; x < currentLevel.getMap().length; x++)
        {
            for(int y = 0; y < currentLevel.getMap()[0].length; y++)
            {
                 if(currentLevel.getMap()[x][y].getTileType().equals("wall"))
                 {
                    //This makes a new rectangle the size of the wall tile
                    rect = new Rectangle(x,y,1,1);
                    //And this adds that rectangle to the Array List of rectangles
                    collisionMatrix.add(rect);
                 }
            }  
        }
    }
    
    private void drawMap(SpriteBatch batch)
    {
    	for(int x=0;x<map.length;x++)
        {
            for(int y=0;y<map[0].length;y++)
            {
                if(map[x][y].getTileType().equals("wall") || map[x][y].getTileType().equals("empty"))
                {
                    batch.draw(GameConstants.WALL_TEXTURE, x*GameConstants.WALL_TEXTURE.getWidth(), y*GameConstants.WALL_TEXTURE.getHeight());
                }
                else if(map[x][y].getTileType().equals("floor")||map[x][y].getTileType().equals("START"))
                {
                    batch.draw(GameConstants.FLOOR_TEXTURE, x*GameConstants.FLOOR_TEXTURE.getWidth(), y*GameConstants.FLOOR_TEXTURE.getHeight());
                }      
                else if(map[x][y].getTileType().equals("END"))
                {
                    batch.draw(endTest, x*endTest.getWidth(), y*endTest.getHeight());
                }      
            }
        }
    }
    
    private void drawEnemies(SpriteBatch batch)
    {
    	for(int i = 0; i < currentLevel.getEnemies().size(); i++)
        {
            if(currentLevel.getEnemies().get(i).getHealth() <= 0)
            {
                //System.out.println(currentLevel.getEnemies().size());
               // System.out.println("i = " + i);
                map[(int)currentLevel.getEnemies().get(i).getxLocation()][(int)currentLevel.getEnemies().get(i).getyLocation()].setOccupied(false);
                map[(int)currentLevel.getEnemies().get(i).getxLocation()][(int)currentLevel.getEnemies().get(i).getyLocation()].setEnemyOnTile(null);
                
                System.out.println("Enemy removed loc: (" + (int)currentLevel.getEnemies().get(i).getxLocation() + "," +(int)currentLevel.getEnemies().get(i).getyLocation()+")");
                
                
                currentLevel.getEnemies().remove(i);
                
                System.out.println("Enemy List Size:  " + currentLevel.getEnemies().size());
                
                continue;
            }
            currentLevel.getEnemies().get(i).setEndX(player.getxLocation());
            currentLevel.getEnemies().get(i).setEndY(player.getyLocation());
            currentLevel.getEnemies().get(i).move(map);
            
            batch.draw(currentLevel.getEnemies().get(i).getEntityTexture(), currentLevel.getEnemies().get(i).getxLocation() * GameConstants.FLOOR_TEXTURE.getWidth(), 
                                              currentLevel.getEnemies().get(i).getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
        }
    }
    
    private void drawProjectiles(SpriteBatch batch)
    {
        for(Projectile projectile: player.getProjectiles())
        {
            projectile.move(map);
            projectile.draw(batch);
        }
    }

    public void render (float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        
        //start the batch
        batch.begin();

        //draw map 
        drawMap(batch) ; 
        
        //draw projectile
        drawProjectiles(batch);
        
        //draw player
        player.draw(batch);
        
        //draw enemies
        drawEnemies(batch) ; 
        
        //Sets the camera position to the "player" so that it will follow it
        //AFTER TESTING, UNCOMMENT
        //camera.position.set(player.getxLocation()*32, player.getyLocation()*32, 0);
        
        //cant draw after this point
        batch.end();

        //checks for enemy collisions with the player and other enemies and projectiles
        checkEnemies() ; 
        
        //checks player's collision 
        checkPlayer() ; 
        
        //checks projectile and wall collision
        checkProjectiles();
        
        //check for beating the level 
        if(levelWin())
        {
        	resetLevel() ; 
        }
        
        //update camera
        camera.update();
        
    }
    
    private void resetLevel()
    {
    	//make a new level
        if(levelNumber%5==0)
        {
            currentLevel = bossGenerator.generateLevel(currentLevel.getLevelNumber()+1);
        }
        else
        {
            currentLevel = generator.generateLevel(currentLevel.getLevelNumber() + 1);
        }
    	//make a new map 
    	map = currentLevel.getMap() ;
    	
    	//reset collision matrix
    	collisionMatrix.removeAll(collisionMatrix) ; 
    	genWall(collisionMatrix, currentLevel);
    	
    	//reset player
    	player.setxLocation(GameConstants.PLAYER_START_X);
    	player.setyLocation(GameConstants.PLAYER_START_Y);
    }
    
    private boolean levelWin()
    {
    	if(currentLevel.getEnemies().size()==0){
    		return true ; 
    	}else
    	{
    		return false ; 
    	}
    }
    
    private void checkEnemies()
    {
    	for(Enemy enemy : currentLevel.getEnemies())
    	{
            
            if(enemy.overlaps(player))
            {
                if(enemy.getDirection()== GameConstants.UP)
                {
                    //System.out.println("Collision Going Up");
                    enemy.setyLocation(enemy.getyLocation()-(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                }else if(enemy.getDirection() == GameConstants.RIGHT)
                {
                    //System.out.println("Collision Going Right");
                    enemy.setxLocation(enemy.getxLocation()-(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                }else if(enemy.getDirection() == GameConstants.DOWN)
                {
                    //System.out.println("Collision Going Down");
                    enemy.setyLocation(enemy.getyLocation()+(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                }else if(enemy.getDirection() == GameConstants.LEFT)
                {
                    //System.out.println("Collision Going Left");
                    enemy.setxLocation(enemy.getxLocation()+(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                }
            }
    		
            for(Enemy enemy2 : currentLevel.getEnemies())
            {
                if(enemy.equals(enemy2))
                {
                    continue;
                }
                
                
                if(enemy.overlaps(enemy2))
                {
                    System.out.println("Enemy 1 X: " + enemy.getxLocation());
                    System.out.println("Enemy 1 Y: " + enemy.getyLocation());
                    
                    System.out.println("Enemy 2 X: " + enemy2.getxLocation());
                    System.out.println("Enemy 2 Y: " + enemy2.getyLocation());
                    
                    
                   if(enemy.getDirection()== GameConstants.UP)
                   {
                       enemy.setyLocation(enemy.getyLocation()-(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                       //enemy2.setyLocation(enemy2.getyLocation()+(float)enemy2.getSpeed()*Gdx.graphics.getDeltaTime());
                   }
                   else if(enemy.getDirection() == GameConstants.RIGHT)
                   {
                        enemy.setxLocation(enemy.getxLocation()-(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                        //enemy2.setxLocation(enemy2.getxLocation()+(float)enemy2.getSpeed()*Gdx.graphics.getDeltaTime());
                   }
                   else if(enemy.getDirection() == GameConstants.DOWN)
                   {
                       enemy.setyLocation(enemy.getyLocation()+(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                       //enemy2.setyLocation(enemy2.getyLocation()-(float)enemy2.getSpeed()*Gdx.graphics.getDeltaTime());
                   }
                   else if(enemy.getDirection() == GameConstants.LEFT)
                   {
                       enemy.setxLocation(enemy.getxLocation()+(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                       //enemy2.setxLocation(enemy2.getxLocation()-(float)enemy2.getSpeed()*Gdx.graphics.getDeltaTime());
                   }
                   
                   System.out.println("CHECK....... " + enemy.overlaps(enemy2));
               }

            }
            
            for(int i = 0; i < collisionMatrix.size(); i++)
            {
                if(enemy.overlaps(collisionMatrix.get(i)))
                {
                    if(enemy.getDirection() == GameConstants.UP)
                    {	
                        enemy.setyLocation(enemy.getyLocation() - (float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                    }
                    else if(enemy.getDirection() == GameConstants.RIGHT)
                    {
                        enemy.setxLocation(enemy.getxLocation() - (float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                    }
                    else if(enemy.getDirection() == GameConstants.DOWN)
                    {
                        enemy.setyLocation(enemy.getyLocation() + (float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                    }
                    else if(enemy.getDirection() == GameConstants.LEFT)
                    {
                        enemy.setxLocation(enemy.getxLocation() + (float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                    }
                }
            } 
            
            for(int i = 0; i < player.getProjectiles().size(); i++)
            {
                if(enemy.overlaps(player.getProjectiles().get(i)))
                {
                    enemy.getHit(player.getProjectiles().get(i).getDamage());
                    player.getProjectiles().remove(i);
                    break;
                }
            }
                    
                  
    	}
    }

    //method to check player's collision 
    private void checkPlayer()
    { 
        //Added by Jason
        player.move(map);
        player.set(player.getxLocation(), player.getyLocation(), player.getWidth(), player.getHeight());

        //This handles the collision detection for the player and walls, this will probably have to be moved into it's own
        //method, but for now, it is finally working
        for(int i = 0; i < collisionMatrix.size(); i++)
        {
            if(player.overlaps(collisionMatrix.get(i)))
            {
                if(player.isMovingY())
                {	
                    player.setyLocation(player.getyLocation() - (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                    //break;
                }
                else if(player.isMovingX())
                {
                    player.setxLocation(player.getxLocation() - (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                    //break;
                }
                else if(player.isMovingNY())
                {
                    player.setyLocation(player.getyLocation() + (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                    //break;
                }
                else if(player.isMovingNX())
                {
                    player.setxLocation(player.getxLocation() + (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                    //break;
                }
            }
        }
        
        for(int i = 0; i < currentLevel.getEnemies().size(); i++)
        {
            if(player.overlaps(currentLevel.getEnemies().get(i)))
            {
                if(player.isMovingY())
                {	
                    //System.out.println("Player Collision Going Up");
                    player.setyLocation(player.getyLocation() - (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                    //break;
                }
                else if(player.isMovingX())
                {
                    //System.out.println("Player Collision Going Right");
                    player.setxLocation(player.getxLocation() - (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                    //break;
                }
                else if(player.isMovingNY())
                {
                    //System.out.println("Player Collision Going Down");
                    player.setyLocation(player.getyLocation() + (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                    //break;
                }
                else if(player.isMovingNX())
                {
                    //System.out.println("Player Collision Going Left");
                    player.setxLocation(player.getxLocation() + (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                    //break;
                }
            }
        }
                

        //camera.position.set(player.getxLocation()*32, player.getyLocation()*32, 0);
        camera.update();
    }
    
    private void checkProjectiles()
    {
        if(player.getProjectiles().size() > 0)
        {
            System.out.println("Before " +player.getProjectiles().size());
            for(int i = 0; i < player.getProjectiles().size(); i++)
            {
                for(int j = 0; j < collisionMatrix.size(); j++)
                {
                    if(player.getProjectiles().get(i).overlaps(collisionMatrix.get(j)))
                    {
                        player.getProjectiles().remove(i);
                        break;
                    }
                }
            }
            System.out.println("After " +player.getProjectiles().size());
        }
    }

    //temp function to check if viewport is out of the screen. returns true if out of bounds
    //the 160 comes from half the viewport because the camera will over shoot boundery if checked at max and 0
    // the 159 is because the camera starts at 160 and thus will be locked out of moving if its at 159
    //the 175 because the height 720 isn't divisible by 32 (the tile size) and thus has a weird overlap, need to find a mathematical calculation for this gap
    private boolean checkOut()
    {
        if(camera.position.x>=Gdx.graphics.getWidth()-160){
                return true ; 
        }else if(camera.position.x<=159){
                return true ; 
        }else if(camera.position.y<=159){ 
                return true ; 
        }else if(camera.position.y>=Gdx.graphics.getHeight()-175){
                return true ; 
        }else{
                return false ; 
        }
    }

    public void show ()
    {

    }

    public void resize (int width, int height)
    {
       // camera.viewportWidth = width;
    //   camera.viewportHeight = height;

       // camera.update();
    }

    public void dispose ()
    {
        batch.dispose();

    }
}
