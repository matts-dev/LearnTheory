package enigma.engine.floatingpoint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import enigma.engine.DrawableString;
import enigma.engine.Tools;

public class FP3FractionalNumberBinaryModule extends FPComponentModule
{
	/** a vector to hold converted touch coordinates into game world coordinates */
	private Vector3 convVect = new Vector3(0, 0, 0);
	private boolean devMode = true;
	private Float fractionalPortion;
	private DrawableString fractionalPortionDrawableString = new DrawableString("");
	private boolean bShouldDrawFractionalString = true;

	/**
	 * Constructor
	 * 
	 * @param camera the Orthographic camera. This is used to convert points
	 * @param animatingInstruction
	 */
	public FP3FractionalNumberBinaryModule(OrthographicCamera camera, AcknlowedgedInstruction animatingInstruction) {
		super(camera, animatingInstruction);
		setUpInstructions();
	}

	@Override
	public void logic()
	{
		super.logic();
	}

	@Override
	public void IO()
	{
		// do sub module IO
		super.IO();

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

	public void draw(SpriteBatch batch, float lastModuleFraction)
	{
		// draw any sub modules (super call)
		super.draw(batch);
		if (bShouldDrawFractionalString)
		{
			fractionalPortionDrawableString.draw(batch);
		}
	}

	@Override
	public float getFractionDone() {
		return currentInstructionPointer / (float) instructionList.size();
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

	protected void setUpInstructions()
	{
		instructionList.add("this is the fractional binary module");
	}

	@Override
	public void setValueToConvert(float newNumberToConvert)
	{
		super.setValueToConvert(newNumberToConvert);
//		Integer wholeNumber = (int) Math.floor(Math.abs(newNumberToConvert));
//		fractionalPortion = (Float) (Math.abs(newNumberToConvert) - wholeNumber);
//		String fracStr = fractionalPortion.toString();
//		fracStr = fracStr.substring(0, fracStr.length() > 6 ? 6 : fracStr.length());
		String fracStr = FPUtils.getFractionalPortionString(newNumberToConvert);
		fractionalPortionDrawableString.setText(fracStr);
		fractionalPortionDrawableString.setXY(Gdx.graphics.getWidth() * 0.75f, Gdx.graphics.getHeight() / 2);

	}
}
