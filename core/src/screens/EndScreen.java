/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Application;

import constants.GameConstants;

/**
 *
 * @author Jason
 */
public class EndScreen implements Screen
{
    final Application game  ;
    OrthographicCamera camera;
    private int mainSwitch ;
    private int[] scores;
    private final SpriteBatch batch;
    private final BitmapFont font ; 
    private String level ; 
    private String enemiesKilled ; 
    private String time; 
    private String finalScore ; 
    private int score ; 
    
    Preferences prefs;
    
    private int bestLevel;
    private int bestEnemy;
    private int bestTime;
    private int bestScore ; 
    private String bestLevelS;
    private String bestEnemyS;
    private String bestTimeS;
    private String bestScoreS ; 
    
    public EndScreen(Application game, int[] scores)
    {
        this.batch = new SpriteBatch();
        this.game = game ; 
        this.camera = new OrthographicCamera() ; 
        this.camera.setToOrtho(false, 1920, 1080);
        this.scores = scores;
        
        font = new BitmapFont() ; 
        font.getData().setScale(3);
        font.setColor(com.badlogic.gdx.graphics.Color.BLACK);
		
        mainSwitch = 0 ; 
        
        level = "" +scores[0] ;
        enemiesKilled = "" + scores[1] ; 
        time = scores[2] + " s" ; 
        
        score = ((scores[0]*3)+scores[1])*10 ;
        finalScore = "" + score ; 
        
        prefs = Gdx.app.getPreferences("myPreference");
        bestLevel = prefs.getInteger("Level", Integer.MAX_VALUE);
        bestEnemy = prefs.getInteger("Enemy", Integer.MAX_VALUE);
        bestTime = prefs.getInteger("Time", Integer.MAX_VALUE);
        bestScore = prefs.getInteger("Final Score", Integer.MAX_VALUE);
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) 
    { 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        
        batch.draw(GameConstants.END_SCREEN, 0.0F, 0.0F);
        
        font.draw(batch, level, 500, 680); 
        font.draw(batch, enemiesKilled, 500, 580) ; 
        font.draw(batch, time, 500, 485) ; 
        font.draw(batch, finalScore, 500, 385) ; 

        if((bestScore < score) || bestLevel == Integer.MAX_VALUE)
        {
            prefs.putInteger("Level", scores[0]);
            prefs.putInteger("Enemy", scores[1]);
            prefs.putInteger("Time", scores[2]);
            prefs.putInteger("Final Score", score); 
            
             bestLevel = prefs.getInteger("Level", Integer.MAX_VALUE);
             bestEnemy = prefs.getInteger("Enemy", Integer.MAX_VALUE);
             bestTime = prefs.getInteger("Time", Integer.MAX_VALUE);
             bestScore = prefs.getInteger("Final Score", Integer.MAX_VALUE);
             
             prefs.flush();
        }
        
        font.draw(batch, ""+prefs.getInteger("Level"), 1650, 695); 
        font.draw(batch, ""+prefs.getInteger("Enemy"), 1650, 595) ; 
        font.draw(batch, prefs.getInteger("Time")+ " s", 1650, 500) ; 
        font.draw(batch, ""+prefs.getInteger("Final Score"), 1650, 400) ; 
        
        inputHandler();
        
        batch.end();
        
        this.camera.update();
        
    }
    
    private void inputHandler()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
        {
            game.setScreen(new MainMenuScreen(game));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
        {
            System.exit(0);
        }
    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        
    }
    
}
