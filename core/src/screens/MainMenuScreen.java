package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Application;

import constants.GameConstants;
import screens.GameScreen.State;

public class MainMenuScreen extends ScreenAdapter {
	final Application game  ;
    OrthographicCamera camera;
    private int mainSwitch ; 
    private SpriteBatch batch ; 

	
	public MainMenuScreen(Application game)
	{
		this.game = game ; 
		this.camera = new OrthographicCamera() ; 
		this.camera.setToOrtho(false, 1920, 1080);		
		
		mainSwitch = 0 ; 
		
		batch = new SpriteBatch() ; 
	}
	
	public enum State
    {
        MAIN,
        CLASS,
    }
	
	private State state = State.MAIN;
	
	public void render (float delta)
    {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.camera.update();
		
		batch.begin();
		
		switch(state)
		{
		case MAIN:
			batch.draw(GameConstants.MAIN_MENU_SCREEN, 0.0F, 0.0F);
			break ; 
		case CLASS:
			batch.draw(GameConstants.CLASS_SELECT_SCREEN, 0.0F, 0.0F);
			break ; 
		}
		
		batch.end();
		
		checkInput() ; 
    }
	
	public void checkInput()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
		{
			if(state == State.MAIN)
			{
				state = State.CLASS ; 
			}
		}
		
		if(Gdx.input.isTouched())
		{
			float mouseX = Gdx.input.getX() ;
			float mouseY = Gdx.input.getY() ;
			
			if(state==State.CLASS)
			{
				System.out.println("MouseX: " + mouseX);
				System.out.println("MouseY: " + mouseY);
				
				if(mouseX<320 && mouseX>162 && mouseY>430 && mouseY<600){
					game.setScreen(new GameScreen(game));
				}
			}
			
		}
	}
	
	public void show ()
    {

    }
	
	public void dispose(){
		
	}
}
