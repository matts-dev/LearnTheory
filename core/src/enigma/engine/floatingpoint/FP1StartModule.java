package enigma.engine.floatingpoint;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import enigma.engine.DrawableString;
import enigma.engine.Tools;

public class FP1StartModule extends FPComponentModule {
	/** a vector to hold converted touch coordinates into game world coordinates */
	private Vector3 convVect = new Vector3(0, 0, 0);
	private boolean devMode = true;
	private DrawableString signUnderScore;
	private DrawableString exponentUnderScore;
	private DrawableString mantissaUnderScore;
	private DrawableString instruction;
	private ArrayList<String> instructions = new ArrayList<String>();
	private AcknowledgeButton ackBtn;
	
	private enum State {
		SIGN1, EXPONENT1, MANTISSA1
	}
	private State currentState = State.SIGN1; 
	int signInstructionIndex = 0;
	int exponentInstructionIndex = -1;
	int mantissaInstructionIndex = -1;

	/**
	 * Constructor
	 * 
	 * @param camera
	 *            the Orthographic camera. This is used to convert points
	 */
	public FP1StartModule(OrthographicCamera camera) {
		super(camera);

		setUpAckBtn();
		setUpUnderscores();
		setUpInstructions();
		
		
		updateInstructionPosition();
		updateButtonPositionToInstruction();

	}

	private void setUpAckBtn() {
		ackBtn = new AcknowledgeButton('k');
		ackBtn.setPosition(0, 0);
	}

	private void setUpInstructions() {
		//explain the first bit
		instructions.add("This is the first bit position.");
		instructions.add("A 0 here means the number is positive.");
		instructions.add("A 1 here means the number is negative!");
		instructions.add("Thus, It represents the sign of the number.");
		exponentInstructionIndex = instructions.size();
		
		//explain the middle 5 bits
		instructions.add("These 5 bits represent the exponent.");
		instructions.add("The number 15 represents an exponent of 0!.");
		mantissaInstructionIndex = instructions.size();

		//explain
		instructions.add("These 10 bits represent the number.");
		instructions.add("It is a binary number in scientific notation form.");
		
		//do optional instructions?
		//explain why 15 represents 0 (because of negative exponents)
		
		//set up the drawable instruction
		instruction = new DrawableString(instructions.get(signInstructionIndex));
		instruction.startAnimation();
		updateInstructionPosition();
	}
	
	private void updateInstructionPosition(){
		float instX = (Gdx.graphics.getWidth() / 2);
		float instY = Gdx.graphics.getHeight() * 0.90f;
		instruction.setXY(instX, instY);		
	}
	
	private void updateButtonPositionToInstruction(){
		float x = instruction.getX() + instruction.width() / 2 + ackBtn.getScaleWidth() / 2;
		float y = instruction.getY() - instruction.height() /2 + ackBtn.getScaleHeight() / 2;
		ackBtn.setPosition(x, y);
	}

	private void setUpUnderscores() {
		signUnderScore = new DrawableString("_ ");
		exponentUnderScore = new DrawableString("_ _ _ _ _ ");
		mantissaUnderScore = new DrawableString("_ _ _ _ _ _ _ _ _ _ ");

		// shared information
		float height = Gdx.graphics.getHeight() * 0.80f;

		// set up sign bar
		float totalWidth = signUnderScore.width() + exponentUnderScore.width() + mantissaUnderScore.width();
		float signX = (Gdx.graphics.getWidth() / 2) - (totalWidth / 2) + signUnderScore.width() / 2;
		signUnderScore.setXY(signX, height);

		// set up exponent bar
		float exponentX = signUnderScore.getX() + signUnderScore.width() / 2 + exponentUnderScore.width() / 2;
		exponentUnderScore.setXY(exponentX, height);
		exponentUnderScore.makeRed();

		// set up mantissa bar
		float mantissaX = exponentX + exponentUnderScore.width() / 2 + mantissaUnderScore.width() / 2;
		mantissaUnderScore.setXY(mantissaX, height);
		mantissaUnderScore.makeBlue();

		signUnderScore.startAnimation();
		exponentUnderScore.startAnimation();
		mantissaUnderScore.startAnimation();

	}

	@Override
	public void logic() {
		super.logic();
		if (lastComponentDone()) {
			instruction.animateLogic();
			
			// do logic if last component is complete
			signUnderScore.animateLogic();
			
			ackBtn.logic();

			if (!signUnderScore.isAnimating()) {
				exponentUnderScore.animateLogic();
			}
			if (!exponentUnderScore.isAnimating()) {
				mantissaUnderScore.animateLogic();
			}
		}
	}

	@Override
	public void IO() {
		// do sub module IO
		super.IO();

		if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT) && Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {

			System.out.println("Pressed k in FP1 start module");
			signUnderScore.startAnimation();
			exponentUnderScore.startAnimation();
			mantissaUnderScore.startAnimation();
			
			
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

	public void draw(SpriteBatch batch, float lastModuleFraction) {
		// draw any sub modules (super call)
		super.draw(batch);
		lastComponentFractionDone = lastModuleFraction;

		// only draw if last module is complete
		if (lastComponentDone()) {
			if(drawInstruction){
				instruction.draw(batch);
			}
			
			if(ackBtn != null) ackBtn.draw(batch);
			
			signUnderScore.draw(batch);
			exponentUnderScore.draw(batch);
			mantissaUnderScore.draw(batch);
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
