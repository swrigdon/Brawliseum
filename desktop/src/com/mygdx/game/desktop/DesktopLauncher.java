package com.mygdx.game.desktop;
  
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Application;

public class DesktopLauncher 
{
	public static void main (String[] arg) 
        {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.height = 1080;
            config.width = 1920;
            config.foregroundFPS = 60;
            config.title = "The Brawliseum - A Procedural Death Labyrinth" ;
            new LwjglApplication(new Application(), config);
	}
}
         