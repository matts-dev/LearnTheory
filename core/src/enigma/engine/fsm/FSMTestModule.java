package enigma.engine.fsm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import enigma.engine.CourseModule;
import enigma.engine.Tools;
import enigma.engine.fsm.components.FiniteStateMachine;

public class FSMTestModule extends CourseModule {
	/** a vector to hold converted touch coordinates into game world coordinates */
	private Vector3 convVect = new Vector3(0, 0, 0);
	private boolean devMode = true;
	private FiniteStateMachine fsm = new FiniteStateMachine();

	/**
	 * Constructor
	 * 
	 * @param camera the Orthographic camera. This is used to convert points
	 */
	public FSMTestModule(OrthographicCamera camera) {
		super(camera);

		fsm.addNode("", 30, 30);
	}

	@Override
	public void logic() {
		super.logic();
		if (fsm != null) {
			fsm.logic();
		}
	}

	@Override
	public void IO() {
		// do sub module IO
		super.IO();

		if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT) && Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
		}

		if (devMode && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {

			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {

			}
		}

	}

	@Override
	public void draw(SpriteBatch batch) {
		// draw any sub modules (super call)
		super.draw(batch);
		if (fsm != null) {
			fsm.draw(batch);
		}

	}

	@Override
	public void dispose() {
		// dispose any sub-modules
		super.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Tools.convertMousePointsIntoGameCoordinates(camera, convVect);
		fsm.touchDown(convVect, pointer, button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Tools.convertMousePointsIntoGameCoordinates(camera, convVect);
		fsm.touchUp(convVect, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Tools.convertMousePointsIntoGameCoordinates(camera, convVect);
		fsm.touchDragged(convVect, pointer);
		return false;
	}

}
