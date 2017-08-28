package enigma.engine.floatingpoint;

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
//	private ArrayList<String> instructionList = new ArrayList<String>();

	private int signInstructionIndex = -1;
	private int exponentInstructionIndex = -1;
	private int mantissaInstructionIndex = -1;
	private int currentInstructionPointer = 0;
	private boolean drawSignBit = false;
	private boolean drawExponentBits = false;
	private boolean drawMantissaBits = false;
	

	/**
	 * Constructor
	 * 
	 * @param camera
	 *            the Orthographic camera. This is used to convert points
	 * @param animatingInstruction 
	 */
	public FP1StartModule(OrthographicCamera camera, AcknlowedgedInstruction animatingInstruction) {
		super(camera, animatingInstruction);

		setUpUnderscores();
		setUpInstructions();
		
		updateInstructionSpatialPosition();
	}

	protected void setUpInstructions() {
		
		instructionList.add("The 16 bits of the number are divided into 3 sections");
		
		//explain the first bit
		signInstructionIndex = instructionList.size();
		instructionList.add("This is the first bit position.");
//		instructions.add("A 0 here means the number is positive.");
//		instructions.add("A 1 here means the number is negative!");
		instructionList.add("It represents the sign of the number.");
		exponentInstructionIndex = instructionList.size();
		
		//explain the middle 5 bits
		instructionList.add("These 5 bits represent the exponent.");
//		instructions.add("The number 15 represents an exponent of 0!");
		mantissaInstructionIndex = instructionList.size();

		//explain
		instructionList.add("These 10 bits represent the number.");
		instructionList.add("It is a binary number in scientific notation form.");
		
		//do optional instructions?
		//explain why 15 represents 0 (because of negative exponents)

		//set up the displaying instruction
		updateInstructionSpatialPosition();
//		instruction.setText(instructions.get(currentInstructionPointer));
//		instruction.startAnimation();
	}
	
	private void updateInstructionSpatialPosition(){
		float instX = (Gdx.graphics.getWidth() / 2);
		float instY = Gdx.graphics.getHeight() * 0.90f;
		instruction.setXY(instX, instY);		
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

	}

	@Override
	public void logic() {
		super.logic();
		if (lastComponentDone()) {
			instruction.animateLogic();
			
			// do logic if last component is complete
			signUnderScore.animateLogic();
	
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
			currentInstructionPointer++;
			handleStateChange(currentInstructionPointer);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
			currentInstructionPointer--;
			handleStateChange(currentInstructionPointer);
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

	private void handleStateChange(int newInstructionPointer) {
		
		if(newInstructionPointer == signInstructionIndex)
		{
			drawSignBit = true;
			signUnderScore.startAnimation();
		}
		else if (newInstructionPointer == exponentInstructionIndex)
		{
			drawExponentBits = true;
			exponentUnderScore.startAnimation();
		}
		else if (newInstructionPointer == mantissaInstructionIndex)
		{
			drawMantissaBits = true;
			mantissaUnderScore.startAnimation();
		}
		
		//correct instruction pointer for edge cases (note: zero check should come second in case instructions.size == 0)
		newInstructionPointer = newInstructionPointer >= instructionList.size() ? instructionList.size() - 1 : newInstructionPointer;
		newInstructionPointer = newInstructionPointer < 0 ? 0 : newInstructionPointer; 
		
		instruction.setText(instructionList.get(newInstructionPointer));
		instruction.startAnimation();
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
			
			if(drawSignBit) signUnderScore.draw(batch);
			if(drawExponentBits) exponentUnderScore.draw(batch);
			if(drawMantissaBits) mantissaUnderScore.draw(batch);
		}
	}
	
	@Override
	public float getFractionDone() {
		return currentInstructionPointer / (float) instructionList.size();
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
