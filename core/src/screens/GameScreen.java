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
import dungeon.DungeonTile;
import dungeon.Level;
import dungeon.LevelGenerator;
import entities.Enemy;
import entities.Player;
import java.util.ArrayList;

/**
 * Created by steph on 2/8/2017.
 */

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
    public Level currentLevel  ; 
    public Player player;
    
    private String playerClass;


    public GameScreen(Application game)
    {
        generator = new LevelGenerator(29, 29);
        currentLevel = generator.generateLevel(6);        


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
        playerClass = "sword";

        player = new Player(2,2, GameConstants.PLAYER_TEXTURE, currentLevel, playerClass);
        
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

    public void render (float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        //testing drawing code
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
        batch.draw(player.getEntityTexture(), player.getxLocation() * GameConstants.FLOOR_TEXTURE.getWidth(), 
                                              player.getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
        
   
        //int i = 0;
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
                
                System.out.println(currentLevel.getEnemies().size());
                continue;
            }
            //System.out.println("i = " + i);
            currentLevel.getEnemies().get(i).setEndX(player.getxLocation());
            currentLevel.getEnemies().get(i).setEndY(player.getyLocation());
            currentLevel.getEnemies().get(i).move(map);
            
            batch.draw(currentLevel.getEnemies().get(i).getEntityTexture(), currentLevel.getEnemies().get(i).getxLocation() * GameConstants.FLOOR_TEXTURE.getWidth(), 
                                              currentLevel.getEnemies().get(i).getyLocation() * GameConstants.FLOOR_TEXTURE.getHeight());
        }
        
        //checks for enemy collisions with the player and other enemies
        checkEnemies() ; 
        
        //Sets the camera position to the "player" so that it will follow it
        //AFTER TESTING, UNCOMMENT
        //camera.position.set(player.getxLocation()*32, player.getyLocation()*32, 0);

        batch.end();

        //update checks
        handleInput() ; 
        camera.update();
    }
    
    private void checkEnemies()
    {
    	for(Enemy enemy : currentLevel.getEnemies())
    	{
    		if(enemy.overlaps(player))
    		{
    			if(enemy.getDirection()==0)
    			{
    				enemy.setyLocation(enemy.getyLocation()-(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
    			}else if(enemy.getDirection() == 1)
    			{
    				enemy.setxLocation(enemy.getxLocation()-(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
    			}else if(enemy.getDirection() == 2)
    			{
    				enemy.setyLocation(enemy.getyLocation()+(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
    			}else if(enemy.getDirection() == 3)
    			{
    				enemy.setxLocation(enemy.getxLocation()+(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
    			}
    		}
    		
    		for(Enemy enemy2 : currentLevel.getEnemies())
    		{
    			if(enemy.overlaps(enemy2))
    			{
    				if(enemy.getDirection()==0)
        			{
        				enemy.setyLocation(enemy.getyLocation()-(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
        			}else if(enemy.getDirection() == 1)
        			{
        				enemy.setxLocation(enemy.getxLocation()-(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
        			}else if(enemy.getDirection() == 2)
        			{
        				enemy.setyLocation(enemy.getyLocation()+(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
        			}else if(enemy.getDirection() == 3)
        			{
        				enemy.setxLocation(enemy.getxLocation()+(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
        			}
    			}
    		}
    	}
    }

    //temp handling input method
    private void handleInput()
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
                    break;
                }
                else if(player.isMovingX())
                {
                    player.setxLocation(player.getxLocation() - (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                    break;
                }
                else if(player.isMovingNY())
                {
                    player.setyLocation(player.getyLocation() + (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                    break;
                }
                else if(player.isMovingNX())
                {
                    player.setxLocation(player.getxLocation() + (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                    break;
                }
            }
        }

        //camera.position.set(player.getxLocation()*32, player.getyLocation()*32, 0);
        camera.update();


        /*
            if(Gdx.input.isKeyPressed(Input.Keys.W))
            {
                    camera.translate(0,3,0);
                    if(checkOut())
                    {
                            camera.translate(0,-3,0);
                    }
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.S))
            {
                    camera.translate(0,-3,0);
                    if(checkOut())
                    {
                            camera.translate(0,3,0);
                    }
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.D))
            {
                    camera.translate(3,0,0);
                    if(checkOut())
                    {
                            camera.translate(-3,0,0);
                    }
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.A))
            {
                    camera.translate(-3,0,0);
                    if(checkOut())
                    {
                            camera.translate(3,0,0);
                    }
            }
            */
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
