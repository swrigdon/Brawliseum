package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Application;

public class MainMenuScreen extends ScreenAdapter {
	final Application game  ;
    OrthographicCamera camera;
    private int mainSwitch ; 

	
	public MainMenuScreen(Application game)
	{
		this.game = game ; 
		this.camera = new OrthographicCamera() ; 
		this.camera.setToOrtho(false, 1920, 1080);		
		
		mainSwitch = 0 ; 
	}
	
	public void render (float delta)
    {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.camera.update();
		
		switch(mainSwitch)
		{
		case 0:
			
			break ; 
		case 1:
			break ; 
		}
		
		checkInput() ; 
    }
	
	public void checkInput()
	{
		
	}
	
	public void show ()
    {

    }
	
	public void dispose(){
		
	}
}
