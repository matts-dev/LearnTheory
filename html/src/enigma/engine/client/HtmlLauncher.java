package enigma.engine.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import enigma.engine.Game;

//URL TO PLAY:
//http://localhost:8080/html/
public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
        		// config.height = 540; config.width = 960;
                return new GwtApplicationConfiguration(960, 540);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new Game();
        }
}