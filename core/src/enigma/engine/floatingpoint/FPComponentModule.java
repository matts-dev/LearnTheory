package enigma.engine.floatingpoint;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enigma.engine.CourseModule;

/**
 * @author Matt Stone
 *
 */
public abstract class FPComponentModule extends CourseModule {
	protected float lastComponentFractionDone = 0.0f;	
	protected boolean drawInstruction = true;
	
	public FPComponentModule(OrthographicCamera camera) {
		super(camera);
	}

	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}

	public void dispose() {
		super.dispose();
	}

	public void IO() {
		super.IO();
	}

	/**
	 * To be regarded as the main game loop for everything other than rendering and IO.
	 */
	public void logic() {
		super.logic();
	};

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return super.touchDown(screenX, screenY, pointer, button);
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return super.touchUp(screenX, screenY, pointer, button);
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return super.touchDragged(screenX, screenY, pointer);
	}

	/**
	 * Returns the percentage in fractional form of how complete the module is.
	 * 
	 * @return the fraction done with current module. Returns -1.0 if this function hasn't been
	 *         overloaded
	 */
	public float getFractionDone() {
		return -1.0f;
	}

	/**
	 * Skips to the module fraction specified. This is used to skip around within a module. If this
	 * function hasn't been overloaded, then it will simply do nothing by default.
	 * 
	 * @param fractionalPercentage
	 *            the percentage point in the module to skip to; give in fractional form (ie between
	 *            0.0 and 1.0)
	 */
	public void setFractionDone(float fractionalPercentage) {
		// do nothing unless overloaded
	}
	
	public boolean lastComponentDone(){
		float adjustedValue = Math.abs(lastComponentFractionDone) - 1;
		return adjustedValue < 0.001;
	}

}
