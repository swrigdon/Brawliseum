package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Application;

/**
 * Created by steph on 2/8/2017.
 */

public class GameScreen extends ScreenAdapter {

        Application game;
        private final SpriteBatch batch;
        private final OrthographicCamera camera;

        public GameScreen(Application game)
        {
            this.game = game;
            camera = new OrthographicCamera(Gdx.graphics.getHeight(), Gdx.graphics.getWidth());
            batch = new SpriteBatch();

        }

        public void render (float delta)
        {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.setProjectionMatrix(camera.combined);
            batch.begin();

            batch.end();
        }

        public void show ()
        {

        }

        public void resize (int width, int height)
        {
            camera.viewportWidth = width;
            camera.viewportHeight = height;

            camera.update();
        }

        public void dispose ()
        {
            batch.dispose();
        }
}
