package enigma.engine.floatingpoint;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import enigma.engine.CourseModule;
import enigma.engine.DrawableString;
import enigma.engine.Tools;

public class MasterFloatingPointModule extends CourseModule
{
	/** a vector to hold converted touch coordinates into game world coordinates */
	private Vector3 convVect = new Vector3(0, 0, 0);
	private boolean devMode = true;

	private ArrayList<FPComponentModule> orderedModules = new ArrayList<FPComponentModule>();
	// private FP0ExplaningModule explainModule;
	// private FP1StartModule startModule;
	// private FP2WholeNumberBinaryModule wholeNumberBinaryModule;
	// private FP3FractionalNumberBinaryModule fractionalNumberBinaryModule;
	// private FP4ScientificNotationModule scientificNotationModule;
	// private FP5SignModule signModule;
	// private FP6ExponentModule exponentModule;
	// private FP7MantissaModule mantissaModule;

	// currently selected(targeted) module;
	private FPComponentModule targetModule;
	private AcknlowedgedInstruction animatingInstruction = new AcknlowedgedInstruction('k');
	private float numerator;
	private float denominator;
	private float fractional = 25.25f;
	private DrawableString numberToSolve;

	/**
	 * Constructor
	 * 
	 * @param camera the Orthographic camera. This is used to convert points
	 */
	public MasterFloatingPointModule(OrthographicCamera camera) {
		super(camera);
		//
		// explainModule = new FP0ExplaningModule(camera, animatingInstruction);
		// startModule = new FP1StartModule(camera, animatingInstruction);
		// wholeNumberBinaryModule = new FP2WholeNumberBinaryModule(camera, animatingInstruction);
		// fractionalNumberBinaryModule = new FP3FractionalNumberBinaryModule(camera,
		// animatingInstruction);
		// scientificNotationModule = new FP4ScientificNotationModule(camera, animatingInstruction);
		// signModule = new FP5SignModule(camera, animatingInstruction);
		// exponentModule = new FP6ExponentModule(camera, animatingInstruction);
		// mantissaModule = new FP7MantissaModule(camera, animatingInstruction);

		// TODO start here, make target module calculated based on fraction done of ordered moduels,
		// TODO also make the draw call with fraction done a part of the superclass
		// FPComponentModule
		orderedModules.add(new FP0ExplaningModule(camera, animatingInstruction));
		orderedModules.add(new FP1StartModule(camera, animatingInstruction));
		orderedModules.add(new FP2WholeNumberBinaryModule(camera, animatingInstruction));
		orderedModules.add(new FP3FractionalNumberBinaryModule(camera, animatingInstruction));
		orderedModules.add(new FP4ScientificNotationModule(camera, animatingInstruction));
		orderedModules.add(new FP5SignModule(camera, animatingInstruction));
		orderedModules.add(new FP6ExponentModule(camera, animatingInstruction));
		orderedModules.add(new FP7MantissaModule(camera, animatingInstruction));

		// the target module get's its logic() and IO() method called.
		// targetModule = startModule;
		targetModule = orderedModules.get(0);

		pickNumberToSolve();
	}

	private void pickNumberToSolve()
	{
		Random random = new Random();
		boolean showNumberLessOne = random.nextInt() % 3 == 0; //1/3 change for number to be less than 0
		if (showNumberLessOne)
		{
			//number will be bet
			numerator = random.nextInt(250);
			denominator = random.nextInt(1000) + 250;
		}
		else
		{
			numerator = random.nextInt(1000) + 250;
			denominator = random.nextInt(250);
		}

		fractional = numerator / denominator;
		
		/* 1/2 chance value is negative */
		fractional *= random.nextBoolean() ? -1 : 1;

		updateNumberToSolveText();
		for (FPComponentModule module : orderedModules)
		{
			module.setValueToConvert(fractional);
			module.setValueToSolveStringReference(numberToSolve);
		}
	}

	private void updateNumberToSolveText()
	{
		if (numberToSolve == null)
		{
			numberToSolve = new DrawableString("");
		}
		
		String wholeNumberStr = FPUtils.getWholeNumberPortionString(fractional);
		String fracNumStr = FPUtils.getFractionalPortionString(fractional);
		
		System.out.println(fractional);
		Float fractionalStrConverter = fractional;
		String numAsStr = fractionalStrConverter.toString();
		numberToSolve.setText(numAsStr.substring(0,
				(fractional < 0 ? "-".length() : 0)
				+ wholeNumberStr.length()
				- "0".length()
				+ fracNumStr.length())
				);
		numberToSolve.setXY(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

	}

	public void updateTargetModule()
	{
		// Update our target module to the last module that isn't complete (if there is one)
		for (int i = 0; i < orderedModules.size(); ++i)
		{
			if (orderedModules.get(i).getFractionDone() < 1.0f)
			{
				FPComponentModule newTarget = orderedModules.get(i);
				if (newTarget != targetModule)
				{
					targetModule = newTarget;
					targetModule.initialize();
				}
				break;
			}
		}
	}

	@Override
	public void logic()
	{
		super.logic();

		updateTargetModule();
		targetModule.logic();
	}

	@Override
	public void IO()
	{
		// do sub module IO
		super.IO();

		targetModule.IO();

		if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT) && Gdx.input.isKeyJustPressed(Input.Keys.Q))
		{
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.K))
		{
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.R))
		{
		}

		if (devMode && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
		{
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0))
			{

			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9))
			{

			}
		}

	}

	@Override
	public void draw(SpriteBatch batch)
	{
		// draw any sub modules (super call)
		super.draw(batch);

		// Draw modules once the previous module is complete.
		// if (explainModule != null) explainModule.draw(batch);
		// if (startModule != null) startModule.draw(batch, explainModule.getFractionDone());
		// if (wholeNumberBinaryModule != null) wholeNumberBinaryModule.draw(batch,
		// startModule.getFractionDone());
		// if (fractionalNumberBinaryModule != null) fractionalNumberBinaryModule.draw(batch,
		// wholeNumberBinaryModule.getFractionDone());
		// if (scientificNotationModule != null) scientificNotationModule.draw(batch,
		// fractionalNumberBinaryModule.getFractionDone());
		// if (signModule != null) signModule.draw(batch,
		// scientificNotationModule.getFractionDone());
		// if (exponentModule != null) exponentModule.draw(batch, signModule.getFractionDone());
		// if (mantissaModule != null) mantissaModule.draw(batch, exponentModule.getFractionDone());

		for (int i = 0; i < orderedModules.size(); ++i)
		{
			if (i == 0)
			{
				// first draw has no preceeding element, just draw it without fraction consideration
				orderedModules.get(i).draw(batch, 1);
			}
			else
			{
				orderedModules.get(i).draw(batch, orderedModules.get(i - 1).getFractionDone());
			}
		}
		numberToSolve.draw(batch);
	}

	@Override
	public void dispose()
	{
		// dispose any sub-modules
		super.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		Tools.convertMousePointsIntoGameCoordinates(camera, convVect);

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		Tools.convertMousePointsIntoGameCoordinates(camera, convVect);

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		Tools.convertMousePointsIntoGameCoordinates(camera, convVect);

		return false;
	}

}
