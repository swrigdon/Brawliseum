/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    String level ; 
    String enemiesKilled ; 
    String time; 
    String finalScore ; 
    
    public EndScreen(Application game, int[] scores)
    {
        this.batch = new SpriteBatch();
        this.game = game ; 
        this.camera = new OrthographicCamera() ; 
        this.camera.setToOrtho(false, 1920, 1080);
        this.scores = scores;
        
        font = new BitmapFont() ; 
		
        mainSwitch = 0 ; 
        
        level = "" +scores[0] ;
        enemiesKilled = "" + scores[1] ; 
        time = scores[2] + " s" ; 
        
        int score = ((scores[0]+scores[1]) - (scores[2]/3))*10 ;
        finalScore = "" + score ; 
        
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
        
        font.draw(batch, level, 500, 500) ; 
        font.draw(batch, enemiesKilled, 500, 400) ; 
        font.draw(batch, time, 500, 300) ; 
        font.draw(batch, finalScore, 500, 200) ; 
        
        batch.end();
        
        this.camera.update();
        
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
