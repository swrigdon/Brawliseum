package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Application;

import dungeon.DungeonTile;
import dungeon.Level;
import dungeon.LevelGenerator;

/**
 * Created by steph on 2/8/2017.
 */

public class GameScreen extends ScreenAdapter {

        Application game;
        private final SpriteBatch batch;
        private final OrthographicCamera camera;
        
        //testing variables need to move to separate class 
        public Texture floorTest,wallTest ; 
        public DungeonTile[][] levelMatrix ;
        
        public LevelGenerator generator;
        public Level currentLevel  ; 

        public GameScreen(Application game)
        {
            generator = new LevelGenerator(Gdx.graphics.getWidth()/32, Gdx.graphics.getHeight()/32);
            
            currentLevel = generator.generateLevel(0);
            
        	
            float w = Gdx.graphics.getWidth();
            float h = Gdx.graphics.getHeight();
            this.game = game;
            camera = new OrthographicCamera(w, h);
            camera.setToOrtho(false, 320, 320); //numbers are pixels player can see 
            batch = new SpriteBatch();
            
            //test temp textures
            floorTest = new Texture("floorTest.png"); 
            wallTest = new Texture("wallTest.png"); 
            
            //creating test matrix
            levelMatrix = currentLevel.getMap() ; 
            

        }

        public void render (float delta)
        {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            //testing drawing code
            for(int x=0;x<levelMatrix.length;x++){
            	for(int y=0;y<levelMatrix[0].length;y++){
            		if(levelMatrix[x][y].getTileType().equals("wall")){
            			batch.draw(wallTest, x*wallTest.getWidth(), y*wallTest.getHeight());
            		}else if(levelMatrix[x][y].getTileType().equals("floor")||levelMatrix[x][y].getTileType().equals("START")){
            			batch.draw(floorTest, x*floorTest.getWidth(), y*floorTest.getHeight());
            		}
            	}
            }
            batch.end();
            
            //update checks
            handleInput() ; 
            camera.update();
        }
        
        //temp handling input method
        private void handleInput(){
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
        }
        
        //temp function to check if viewport is out of the screen. returns true if out of bounds
        //the 160 comes from half the viewport because the camera will over shoot boundery if checked at max and 0
        // the 159 is because the camera starts at 160 and thus will be locked out of moving if its at 159
        //the 175 because the height 720 isn't divisible by 32 (the tile size) and thus has a weird overlap, need to find a mathematical calculation for this gap
        private boolean checkOut(){
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
