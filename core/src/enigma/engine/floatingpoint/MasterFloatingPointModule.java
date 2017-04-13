package enigma.engine.floatingpoint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import enigma.engine.CourseModule;
import enigma.engine.Tools;

public class MasterFloatingPointModule extends CourseModule {
	/** a vector to hold converted touch coordinates into game world coordinates */
	private Vector3 convVect = new Vector3(0, 0, 0);
	private boolean devMode = true;

	private FP0ExplaningModule explainModule;
	private FP1StartModule startModule;
	private FP2WholeNumberBinaryModule wholeNumberBinaryModule;
	private FP3FractionalNumberBinaryModule fractionalNumberBinaryModule;
	private FP4ScientificNotationModule scientificNotationModule;
	private FP5SignModule signModule;
	private FP6ExponentModule2 exponentModule;
	private FP7MantissaModule mantissaModule;

	// currently selected(targeted) module;
	private CourseModule targetModule;

	/**
	 * Constructor
	 * 
	 * @param camera
	 *            the Orthographic camera. This is used to convert points
	 */
	public MasterFloatingPointModule(OrthographicCamera camera) {
		super(camera);

		explainModule = new FP0ExplaningModule(camera);
		startModule = new FP1StartModule(camera);
		wholeNumberBinaryModule = new FP2WholeNumberBinaryModule(camera);
		fractionalNumberBinaryModule = new FP3FractionalNumberBinaryModule(camera);
		scientificNotationModule = new FP4ScientificNotationModule(camera);
		signModule = new FP5SignModule(camera);
		exponentModule = new FP6ExponentModule2(camera);
		mantissaModule = new FP7MantissaModule(camera);

		// the target module get's its logic() and IO() method called.
		targetModule = startModule;
	}

	@Override
	public void logic() {
		super.logic();

		targetModule.logic();
	}

	@Override
	public void IO() {
		// do sub module IO
		super.IO();

		targetModule.IO();

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

		// Draw modules once the previous module is complete.
		if (explainModule != null) explainModule.draw(batch);
		if (startModule != null) startModule.draw(batch, explainModule.getFractionDone());
		if (wholeNumberBinaryModule != null) wholeNumberBinaryModule.draw(batch, startModule.getFractionDone());
		if (fractionalNumberBinaryModule != null) fractionalNumberBinaryModule.draw(batch, wholeNumberBinaryModule.getFractionDone());
		if (scientificNotationModule != null) scientificNotationModule.draw(batch, fractionalNumberBinaryModule.getFractionDone());
		if (signModule != null) signModule.draw(batch, scientificNotationModule.getFractionDone());
		if (exponentModule != null) exponentModule.draw(batch, signModule.getFractionDone());
		if (mantissaModule != null) mantissaModule.draw(batch, exponentModule.getFractionDone());

	}

	@Override
	public void dispose() {
		// dispose any sub-modules
		super.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Tools.convertMousePointsIntoGameCoordinates(camera, convVect);

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Tools.convertMousePointsIntoGameCoordinates(camera, convVect);

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Tools.convertMousePointsIntoGameCoordinates(camera, convVect);

		return false;
	}

}
