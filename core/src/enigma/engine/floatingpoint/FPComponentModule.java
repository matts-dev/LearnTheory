package enigma.engine.floatingpoint;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enigma.engine.CourseModule;
import enigma.engine.DrawableString;

/**
 * @author Matt Stone
 *
 */
public abstract class FPComponentModule extends CourseModule {
	protected float lastComponentFractionDone = 0.0f;
	protected boolean drawInstruction = true;
	protected AcknlowedgedInstruction instruction = null;
	protected ArrayList<String> instructionList = new ArrayList<String>();
	protected int currentInstructionPointer = 0;
	protected int instructionIndex = -1;
	protected float questionValue = 25.25f;
	protected DrawableString drawableValueToSolveReference = null;
	
	public FPComponentModule(OrthographicCamera camera, AcknlowedgedInstruction inInstructionPointer) {
		super(camera);
		instruction = inInstructionPointer;
	}

	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
	
	public void draw(SpriteBatch batch, float lastModuleFractionComplete) {
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
		if(instructionList.size() > 0)
		{
			return currentInstructionPointer / (float) instructionList.size();
		}
		else
		{
			return -1.0f;
		}
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

	/**
	 * lastComponentFractionDone represents what portion done the previous component is rated
	 * between 0 and 1. 0 represents nothing in component complete. 1 represents component complete.
	 * 
	 * Determining if component is complete done by subtracting 1 from the fraction complete. If this is
	 * zero, the the previous module is complete (1 - 1 = 0). Taking the abs value allows values like 0.5
	 * (which would be -0.5 after operation) to have a positive output. Only the value of 1 can be zero.
	 * 
	 * Floating point comparison with a threshold is used to determine zero
	 * 
	 * @return true if the previous component has completed
	 */
	public boolean lastComponentDone() {
		final float VALUES_LOWER_ARE_CONSIDERED_ZERO = 0.001f;
		
		float adjustedValue = Math.abs(lastComponentFractionDone - 1);
		return adjustedValue < VALUES_LOWER_ARE_CONSIDERED_ZERO;
	}

	public void initialize() 
	{
		if(instructionList.size() > 0)
		{
			instruction.setText(instructionList.get(0));
			instruction.startAnimation();
		}
	}
	
	protected abstract void setUpInstructions();

	public void setValueToConvert(float newQuestionValue)
	{
		this.questionValue = newQuestionValue;
	}

	public void setValueToSolveStringReference(DrawableString numberToSolve)
	{
		drawableValueToSolveReference = numberToSolve;
	}
}
