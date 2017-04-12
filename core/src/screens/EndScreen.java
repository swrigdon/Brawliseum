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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Application;

/**
 *
 * @author Jason
 */
public class EndScreen implements Screen
{
    final Application game  ;
    OrthographicCamera camera;
    private int mainSwitch ;
    private int finalScore;
    private final SpriteBatch batch;
    
    public EndScreen(Application game, int finalScore)
    {
        this.batch = new SpriteBatch();
        this.game = game ; 
        this.camera = new OrthographicCamera() ; 
        this.camera.setToOrtho(false, 1920, 1080);
        this.finalScore = finalScore;
		
        mainSwitch = 0 ; 
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) 
    { 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        System.out.println("FINAL SCORE: " + finalScore*10);
        
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
