package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.Application;

import constants.GameConstants;
import dungeon.BossLevelGenerator;
import dungeon.DungeonTile;
import dungeon.Level;
import dungeon.LevelGenerator;
import entities.Enemy;
import entities.GroundItem;
import entities.Player;
import entities.Projectile;
import java.util.ArrayList;


public class GameScreen extends ScreenAdapter 
{

    Application game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    float elapsedTime;
    
    Animation<TextureRegion> portalOpen;
    Animation<TextureRegion> portalClosed;
    TextureRegion[] portalClosedAnimation;
    TextureRegion[] portalOpenAnimation;
    
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
    
    Music levelMusic = Gdx.audio.newMusic(Gdx.files.internal("Sulphaeus.mp3"));
    Music bossMusic = Gdx.audio.newMusic(Gdx.files.internal("Sensorial.mp3"));
    Sound arrowHit = Gdx.audio.newSound(Gdx.files.internal("arrow hit.wav"));
    Sound spellHit = Gdx.audio.newSound(Gdx.files.internal("spell hit.wav"));
    Sound swordHit2 = Gdx.audio.newSound(Gdx.files.internal("sword hit2.wav"));
    Sound enterPortal = Gdx.audio.newSound(Gdx.files.internal("portal sound.wav"));
    Sound pickupPotion = Gdx.audio.newSound(Gdx.files.internal("Potion_Pickup.wav"));

    private String playerClass;
    
    //Goes to boss level if %5==0, goes to maze level otherwise.
    private int levelNumber = 1;
    
    private long startTime; 


    public GameScreen(Application game, String playerClass)
    {
        if(levelNumber%5==0)
        {
            bossGenerator = new BossLevelGenerator(29, 29);
            currentLevel = bossGenerator.generateLevel(levelNumber);
        }
        else
        {
            levelMusic.setVolume(.5f);
            levelMusic.setLooping(true);
            levelMusic.play();
            generator = new LevelGenerator(29, 29);
            currentLevel = generator.generateLevel(levelNumber);
        }

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        this.game = game;
        camera = new OrthographicCamera(w, h);
        //AFTER TESTING THIS SHOULD BE: camera.setToOrtho(false, 320*(w/h), 320);
        camera.setToOrtho(false, 320*(w/h), 320);
        //camera.setToOrtho(false, GameConstants.PLAYER_VIEW_X, GameConstants.PLAYER_VIEW_Y); //numbers are pixels player can see 
        batch = new SpriteBatch();

        //creating test matrix
        map = currentLevel.getMap() ;

        genWall(collisionMatrix, currentLevel);
        
        this.playerClass = playerClass;

        player = new Player(2 + ((float)(1)-((float)27/32))/2, 
                            2 + ((float)(1)-((float)27/32))/2,
                                currentLevel, playerClass);
        
        //printGrid(map)  ; 
        createPortal();
        
        startTime = com.badlogic.gdx.utils.TimeUtils.nanoTime() ; 
    }
    
    public enum State
    {
        PAUSE,
        RUN,
        RESUME,
        STOPPED
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
    
    private void createPortal()
    {
        Texture portal = new Texture("portalRings2.png");
        Texture portalClose = new Texture("portalRings1.png");
        
        TextureRegion[][] tmpFrame = TextureRegion.split(portal, 32, 32);
        TextureRegion[][] tmp2Frame = TextureRegion.split(portalClose, 32, 32);
    
        portalOpenAnimation = new TextureRegion[5];
        portalClosedAnimation = new TextureRegion[17];
    
        for(int i = 0; i < 5; i++)
        {
            portalOpenAnimation[i] = tmpFrame[0][i];
        }  
        
        int index = 0;
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                if(index >= 17)
                {
                    break;
                }
                portalClosedAnimation[index] = tmp2Frame[i][j];
                
                index++;

            }
        }
        
        
        portalOpen = new Animation<TextureRegion>((float)1/(float)15, portalOpenAnimation);
        portalClosed = new Animation<TextureRegion>((float)1/(float)15, portalClosedAnimation);     
    }
    
    private void drawPortal(SpriteBatch batch, int x, int y)
    {
        if( currentLevel.getEnemies().isEmpty())
        {
            batch.draw(portalOpen.getKeyFrame(elapsedTime, true), x*32, y*32);
        }
        else
        {
            batch.draw(portalClosed.getKeyFrame(elapsedTime, true), x*32, y*32);
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
                    batch.draw(GameConstants.FLOOR_TEXTURE, x*GameConstants.FLOOR_TEXTURE.getWidth(), y*GameConstants.FLOOR_TEXTURE.getHeight());
                    drawPortal(batch, x, y);
                }      
            }
        }
    }
    
    private void drawItems(SpriteBatch batch)
    {
        for(GroundItem groundItem: currentLevel.getGroundItems())
        {
            groundItem.draw(batch);
        }
    }
    
    private void drawEnemies(SpriteBatch batch)
    {
    	for(int i = 0; i < currentLevel.getEnemies().size(); i++)
        {
            currentLevel.getEnemies().get(i).setPlayer(player);
            
            if(currentLevel.getEnemies().get(i).getHealth() <= 0)
            {
                if(!(currentLevel.getEnemies().get(i).getxLocation() > map.length || currentLevel.getEnemies().get(i).getyLocation() > map[0].length))
                {
                    map[(int)currentLevel.getEnemies().get(i).getxLocation()][(int)currentLevel.getEnemies().get(i).getyLocation()].setOccupied(false);
                    map[(int)currentLevel.getEnemies().get(i).getxLocation()][(int)currentLevel.getEnemies().get(i).getyLocation()].setEnemyOnTile(null);
                }
 
                currentLevel.getEnemies().remove(i);

                player.setExperience(player.getExperience()+GameConstants.XP_FROM_ENEMIES);
                player.setEnemiesKilled(player.getEnemiesKilled()+1);
                
                continue;
            }
            currentLevel.getEnemies().get(i).setEndX(player.getxLocation());
            currentLevel.getEnemies().get(i).setEndY(player.getyLocation());
            currentLevel.getEnemies().get(i).move(map);
            
            currentLevel.getEnemies().get(i).drawEnemy(batch, elapsedTime);
        }
    }
    private void drawPausedEnemies(SpriteBatch batch)
    {
        for(int i = 0; i < currentLevel.getEnemies().size(); i++)
        {
            currentLevel.getEnemies().get(i).drawEnemy(batch, elapsedTime);
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
    
    private void createLevelTable(SpriteBatch batch)
    {
        batch.draw(GameConstants.LEVEL_TABLE, 32*player.getxLocation()-GameConstants.TABLE_OFFSET, 32*player.getyLocation()-GameConstants.TABLE_OFFSET);
    }
    
    private void createPauseTable(SpriteBatch batch)
    {
        batch.draw(GameConstants.PAUSE_TABLE, 32*player.getxLocation()-GameConstants.TABLE_OFFSET, 32*player.getyLocation()-GameConstants.TABLE_OFFSET);
    }

    private State state = State.RUN;
    
    public void render (float delta)
    {
        switch(state)
        {
            case RUN: 
                elapsedTime += Gdx.graphics.getDeltaTime();
                
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                batch.setProjectionMatrix(camera.combined);

                //start the batch
                batch.begin();
                
                //draw map 
                drawMap(batch) ;

                drawItems(batch);

                //draw projectile
                drawProjectiles(batch);

                //draw player
                player.draw(batch, elapsedTime);

                //draw enemies
                drawEnemies(batch) ; 
                
                batch.draw(GameConstants.POTION_SLOT, 32*player.getxLocation() - 15,  32*player.getyLocation()-150);
                
                if(player.getPlayerPotion() != null)
                {
                    if(player.getPlayerPotion().getPotionName().equals("health"))
                    {
                        batch.draw(GameConstants.ITEM_HEALTH, 32*player.getxLocation() , 32*player.getyLocation()-135);
                    }
                    else if(player.getPlayerPotion().getPotionName().equals("attack"))
                    {
                        batch.draw(GameConstants.ITEM_ATTACK, 32*player.getxLocation() , 32*player.getyLocation()-135);
                    }
                    else if(player.getPlayerPotion().getPotionName().equals("move"))
                    {
                        batch.draw(GameConstants.ITEM_MOVE, 32*player.getxLocation() , 32*player.getyLocation()-135);
                    }
                }
                
                batch.draw(GameConstants.EMPTY_BAR, 32*player.getxLocation()+125 , 32*player.getyLocation()+130);
                batch.draw(GameConstants.RED_BAR, 32*player.getxLocation()+125 , 32*player.getyLocation()+130
                        , GameConstants.RED_BAR.getWidth()*(player.getHealth()/player.getMaxHealth()), (float) GameConstants.RED_BAR.getHeight());
                
                batch.draw(GameConstants.EMPTY_BAR, 32*player.getxLocation()+125 , 32*player.getyLocation()+105);
                batch.draw(GameConstants.GREEN_BAR, 32*player.getxLocation()+125 , 32*player.getyLocation()+105,
                        GameConstants.GREEN_BAR.getWidth()*(player.getExperience()/GameConstants.LEVEL_UP_XP), GameConstants.GREEN_BAR.getHeight());
                
                batch.draw(GameConstants.EMPTY_BAR, 32*player.getxLocation()+125 , 32*player.getyLocation()+80);
                batch.draw(GameConstants.BLUE_BAR, 32*player.getxLocation()+125 , 32*player.getyLocation()+80,
                       GameConstants.BLUE_BAR.getWidth()*(float)(currentLevel.getEnemies().size())/(currentLevel.getTotalEnemies()) , GameConstants.BLUE_BAR.getHeight());

                //cant draw after this point
                batch.end();

                //checks for enemy collisions with the player and other enemies and projectiles
                checkEnemies() ; 

                //checks player's collision 
                checkPlayer() ; 
                camera.position.set((player.getxLocation()*32), player.getyLocation()*32, 0);
                camera.update();

                //checks projectile and wall collision
                checkProjectiles();
                
                checkPause();

                //check for level up, if player has been defeated, or the level has been beat
                if(player.getExperience() >= GameConstants.LEVEL_UP_XP)
                {
                    
                    this.state = State.PAUSE;
                    player.setExperience(0);
                }
                
                if(levelWin())
                {
                    resetLevel() ; 
                }

                if(levelLose())
                {
                	long timeTaken = com.badlogic.gdx.utils.TimeUtils.nanoTime() - startTime ; 
                	timeTaken = timeTaken / 1000000000 ; 
                	
                	int[] scores = new int[]{player.getHighestLevel(), player.getEnemiesKilled(),(int)timeTaken} ; 
                	
                    game.setScreen(new EndScreen(game, scores));
                }


                break;
                
            case PAUSE:
                batch.begin();

                drawMap(batch) ;

                drawItems(batch);

                //draw projectile
                drawProjectiles(batch);

                //draw player
                player.draw(batch, elapsedTime);

                //draw enemies
                drawPausedEnemies(batch) ;
                
                createLevelTable(batch);
                
                batch.end();
                
                checkLevelMenuInput();
                
                break;
                
            case STOPPED:
                batch.begin();

                drawMap(batch) ;

                drawItems(batch);

                //draw projectile
                drawProjectiles(batch);

                //draw player
                player.draw(batch, elapsedTime);

                //draw enemies
                drawPausedEnemies(batch) ;
                
                createPauseTable(batch);
                batch.end();
                
                checkPause();
                
                checkPauseMenuInput();
                
                
            case RESUME:
                
                break;
            default:
                break;
        }
        
    }
    
    private void checkLevelMenuInput()
    {
        if(Gdx.input.isTouched())
        {
            float mouseX = Gdx.input.getX() ;
            float mouseY = Gdx.input.getY() ;
            
            //Health
            if(mouseX > 850 && mouseX < 1075 && mouseY > 365 && mouseY < 445)
            {    
                player.setMaxHealth(player.getMaxHealth() + ((5*levelNumber) + 10));
            }
            
            //Damage
            else if(mouseX > 850 && mouseX < 1075 && mouseY>490 && mouseY<575)
            {    
                player.setBaseAttack(player.getBaseAttack() + (2*levelNumber) + 10);
            }
            
            //Speed
            else if(mouseX > 850 && mouseX < 1075 && mouseY > 605 && mouseY < 685)
            {         
                player.setSpeed(player.getSpeed() + (float).25);
                player.setNormalSpeed(player.getSpeed());
            }
            
            player.setHealth(player.getMaxHealth());
            state = State.RUN;
        }      
    }
    
    private void checkPauseMenuInput()
    {
        if(Gdx.input.isTouched())
        {
            float mouseX = Gdx.input.getX() ;
            float mouseY = Gdx.input.getY() ;

            //Resume
            if(mouseX > 850 && mouseX < 1075 && mouseY > 365 && mouseY < 445)
            {    
                state = State.RUN;
            }
            
            //Exit
            else if(mouseX > 850 && mouseX < 1075 && mouseY>490 && mouseY<575)
            {    
                long timeTaken = com.badlogic.gdx.utils.TimeUtils.nanoTime() - startTime ; 
                timeTaken = timeTaken / 1000000000 ; 
                	
                int[] scores = new int[]{player.getHighestLevel(), player.getEnemiesKilled(),(int)timeTaken} ;
                
                
                levelMusic.stop();
                levelMusic.dispose();
                bossMusic.stop();
                bossMusic.dispose();	
                game.setScreen(new EndScreen(game, scores));
            }
            
            player.setHealth(player.getMaxHealth());
            
        }      
    }
    
    private void checkPause()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
        {
            if(this.state == State.RUN)
            {
                this.state = State.STOPPED;
            }
        }
    }
    
    private void resetLevel()
    {
    	//make a new level
        if(levelNumber%5==0)
        {
            enterPortal.play(.75f);
            bossMusic.setVolume(.5f);
            bossMusic.setLooping(true);
            levelMusic.stop();
            bossMusic.play();
            
            bossGenerator = new BossLevelGenerator(29, 29);
            currentLevel = bossGenerator.generateLevel(currentLevel.getLevelNumber()+1);
        }
        else
        {
            enterPortal.play(.75f);
            levelMusic.setVolume(.5f);
            levelMusic.setLooping(true);
            bossMusic.stop();
            levelMusic.play();
            
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
        player.setAttackSpeed(player.getBaseAttackSpeed());
        if(player.getPlayerClass().equals("sword"))
        {
            player.setSpeed(player.getNormalSpeed());
        }
        else if(player.getPlayerClass().equals("bow"))
        {
            player.setSpeed(player.getNormalSpeed());
        }
        else
        {
            player.setSpeed(player.getNormalSpeed());
        }
        player.setExperience(player.getExperience()+GameConstants.XP_FROM_LEVEL);
        player.setHighestLevel(player.getHighestLevel()+1);
        
        //reset camera
        camera.position.set(player.getxLocation()*32, player.getyLocation()*32, 0);
    }
    
    private boolean levelWin()
    {
    	if( currentLevel.getEnemies().isEmpty() && map[(int)player.getxLocation()][(int)player.getyLocation()].getTileType().equals("END"))
        {
            levelNumber++;
            return true ; 
    	}else
    	{
            return false ; 
    	}
    }
    
    private boolean levelLose()
    {
        if(player.getHealth() <= 0)
        {
            levelMusic.stop();
            levelMusic.dispose();
            bossMusic.stop();
            bossMusic.dispose();
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private void checkEnemies()
    {
    	for(Enemy enemy : currentLevel.getEnemies())
    	{
            if(enemy.getxLocation() < 0 || enemy.getxLocation() > map.length || enemy.getyLocation() < 0 || enemy.getyLocation() > map[0].length)
            {
                enemy.setHealth(0);
            }
            
            if(enemy.overlaps(player))
            {
                if(enemy.getDirection()== GameConstants.UP)
                {
                    enemy.setyLocation(enemy.getyLocation()-(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                }else if(enemy.getDirection() == GameConstants.RIGHT)
                {
                    enemy.setxLocation(enemy.getxLocation()-(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                }else if(enemy.getDirection() == GameConstants.DOWN)
                {
                    enemy.setyLocation(enemy.getyLocation()+(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                }else if(enemy.getDirection() == GameConstants.LEFT)
                {
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
                    
                   if(enemy.getDirection()== GameConstants.UP)
                   {
                       enemy.setyLocation(enemy.getyLocation()-(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                   }
                   else if(enemy.getDirection() == GameConstants.RIGHT)
                   {
                        enemy.setxLocation(enemy.getxLocation()-(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                   }
                   else if(enemy.getDirection() == GameConstants.DOWN)
                   {
                       enemy.setyLocation(enemy.getyLocation()+(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                   }
                   else if(enemy.getDirection() == GameConstants.LEFT)
                   {
                       enemy.setxLocation(enemy.getxLocation()+(float)enemy.getSpeed()*Gdx.graphics.getDeltaTime());
                   }         
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
                	if(player.getPlayerClass().equals("bow"))
                		arrowHit.play(.75f);
                	
                	else if(player.getPlayerClass().equals("mage"))
                		spellHit.play(.75f);
                	
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
                }
                else if(player.isMovingX())
                {
                    player.setxLocation(player.getxLocation() - (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                }
                else if(player.isMovingNY())
                {
                    player.setyLocation(player.getyLocation() + (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                }
                else if(player.isMovingNX())
                {
                    player.setxLocation(player.getxLocation() + (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                }
            }
        }
        
        for(int i = 0; i < currentLevel.getGroundItems().size(); i++)
        {
            if(player.overlaps(currentLevel.getGroundItems().get(i)))
            {
                if(player.getPlayerPotion() == null)
                {
                    pickupPotion.play(1.0f);
                    player.setPlayerPotion(currentLevel.getGroundItems().get(i).getPotion());
                    currentLevel.getGroundItems().remove(i);
                }
            }
        }
        
        for(int i = 0; i < currentLevel.getEnemies().size(); i++)
        {
            if(player.overlaps(currentLevel.getEnemies().get(i)))
            {
                if(player.isMovingY())
                {	
                    player.setyLocation(player.getyLocation() - (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                }
                else if(player.isMovingX())
                {
                    player.setxLocation(player.getxLocation() - (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                }
                else if(player.isMovingNY())
                {
                    player.setyLocation(player.getyLocation() + (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                }
                else if(player.isMovingNX())
                {
                    player.setxLocation(player.getxLocation() + (float)player.getSpeed()*Gdx.graphics.getDeltaTime());
                }
            }
        }

        camera.update();
    }
    
    private void checkProjectiles()
    {
        if(player.getProjectiles().size() > 0)
        {
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
