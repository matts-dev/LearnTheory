package enigma.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter implements InputProcessor {
	// graphics fields
	private SpriteBatch batch;
	private OrthographicCamera camera;

	// theory fields
	private CourseModule game;

	// system fields
	boolean enableSystemDevKeybinds = true;

	// test and debug fields
	BitmapFont bmFont;

	@Override
	public void create() {
		// set up graphics
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
		createCamera();
		
		//create static textures for use 
		TextureLookup.initTextures();

		// set up game modules
		setUpGameModules();

	}

	public void createCamera() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.x = Gdx.graphics.getWidth() / 2;
		camera.position.y = Gdx.graphics.getHeight() / 2;
	}

	private void setUpGameModules() {
		game = new TheoryGameMainModule(camera);
	}

	@Override
	public void render() {
		// SYSTEM LEVEL IO AND LOGIC
		camera.update();
		IO();
		logic();

		// CLEAR SCREEN
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// DRAWING STARTS AFTER
		batch.begin();
		if (game != null) {
			game.IO();
			game.logic();
			game.draw(batch);
		}

		// sprite.draw(batch);

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();

		TextureLookup.dispose();
		
		if (game != null) {
			game.dispose();
		}
		
		
	}

	private void IO() {
		if (enableSystemDevKeybinds) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				Gdx.app.exit();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
			}
		}
	}

	private void logic() {

	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(game != null){
			return game.touchDown(screenX, screenY, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(game != null){
			return game.touchUp(screenX, screenY, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(game != null){
			return game.touchDragged(screenX, screenY, pointer);
		}
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
