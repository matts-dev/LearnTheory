package enigma.engine.regex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import enigma.engine.Button;
import enigma.engine.CourseModule;
import enigma.engine.DrawableString;
import enigma.engine.TextureLookup;
import enigma.engine.Tools;
import enigma.engine.regex.components.RegularExpression;

public class RegularExpressionIntroModule extends CourseModule {
	/** a regular expression to work with */
	private RegularExpression regex;

	/** a vector to hold converted touch coordinates into game world coordinates */
	private Vector3 convVect = new Vector3(0, 0, 0);

	/** represents the string the player is holding */
	private DrawableString holdingString;

	/** represents a string that the player is building */
	private DrawableString buildingString;

	/** */
	private Button kButton;

	/** */
	private DrawableString instructions;

	/** */
	private RegExIntroGenerator dataSource;

	private Sprite lambda;
	// private boolean drawLambda = false;

	private boolean devMode = true;

	/**
	 * Constructor
	 * 
	 * @param camera the Orthographic camera. This is used to convert points
	 */
	public RegularExpressionIntroModule(OrthographicCamera camera) {
		super(camera);

		//@formatter:off
//		regex = new RegularExpression(
//				new OrComponent(
//						new TerminalRegExComponent("a"),
//						new TerminalRegExComponent("b"),
//						new TerminalRegExComponent("a")
//						),
//				new TerminalRegExComponent("aaaaa"),
//				new KleeneStarRegExComponent(
//						new OrComponent(
//								new TerminalRegExComponent("a"),
//								new TerminalRegExComponent("b")
//								)
//						),
//				new TerminalRegExComponent("k"),
//				new TerminalRegExComponent("z")
//				);
		//@formatter:on
		dataSource = new RegExIntroGenerator();
		regex = dataSource.getCurrentData().regex;

		// regex.enableHighlightForSubComponents();
		// regex.highlightActiveComponent();

		// position regex at 1/4 x and 1/2 y
		regex.setXY(Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() / 2.0f);

		// set up instruction variables
		instructions = new DrawableString("test instructions");
		instructions.setText(dataSource.getCurrentData().currentInstruction());
		instructions.setXY(Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.75f);
		instructions.startAnimation();
		// drawLambda = dataSource.getCurrentData().drawLambda;

		// set up button
		kButton = new Button();
		kButton.setScale(instructions.height() / kButton.heightScaled());
		kButton.setCenterXY(0.75f * Gdx.graphics.getWidth(), 0.75f * Gdx.graphics.getHeight());

		// set up lambda sprite
		lambda = new Sprite(TextureLookup.lambdaTexture);
		lambda.setScale(instructions.height() / lambda.getHeight());
		lambda.setOrigin(lambda.getScaleX(), lambda.getScaleY()); // correct texture center so it is
																	// drawn at bottom left corner

		float halfLambdaSize = lambda.getHeight() * lambda.getScaleY() * 0.50f;
		lambda.setPosition(Gdx.graphics.getWidth() * 0.75f, (Gdx.graphics.getHeight() * 0.50f) - halfLambdaSize);
	}

	@Override
	public void logic() {
		super.logic();

		// handle button flashing
		kButton.logic();

		// animate instruction and show button after animation is complete
		if (!instructions.isAnimating()) {
			kButton.setHiddenTo(false);
			kButton.setCenterXY(instructions.getX() + instructions.width() / 2.0f + kButton.widthScaled() / 1.0f, instructions.getY());
		} else {
			instructions.animateLogic();
		}

	}

	@Override
	public void IO() {
		// do sub module IO
		super.IO();

		if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT) && Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			generateRandomRegex();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
			handleKPressed();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			resetRegex();
		}

		if (devMode && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
				if (dataSource != null) {
					skipToNextDataSet();
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
				if (regex != null) {
					boolean reHighlight = regex.isHighlight();
					regex.reset();

					if (reHighlight) {
						regex.enableHighlightForSubComponents();
						regex.highlightActiveComponent();
					}
				}
			}
		}

	}

	private void generateRandomRegex() {
		// generate a new regular expression
		if (buildingString != null) {
			buildingString.dispose();
			buildingString = null;
		}

		regex.dispose();
		regex = null;

		regex = RegularExpression.generateRandomExpression();

		regex.enableHighlightForSubComponents();
		regex.highlightActiveComponent();

		// position regex at 1/4 x and 1/2 y
		regex.setXY(Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() / 2.0f);
	}

	private void handleKPressed() {
		// get the next instruction to print
		InstructionData currentData = dataSource.getCurrentData();
		
		// String nextInstruction = dataSource.getCurrentData().nextInstruction();
		String nextInstruction = currentData.nextInstruction();
		
		//quick and dirty implementation to break up the loading (javascript is slow)
		//away from all at the start of the program.
		if(currentData.loadNext){
			dataSource.addReview();
			currentData.loadNext = false;
		}

		// if there are no more instructions in the sub module, get the next sub module ready
		if (nextInstruction.equals("END")) {
			// update the data source
			dataSource.nextDataSet();

			// get the new instruction
			nextInstruction = dataSource.getCurrentData().currentInstruction();

			// drawLambda = dataSource.getCurrentData().drawLambda;

			// get the new regex
			regex = dataSource.getCurrentData().regex;
			regex.setXY(Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() / 2.0f);

			// clear the string the user is making
			if (buildingString != null) {
				buildingString.dispose();
				buildingString = null;
			}
			// reset the regular expression
			if (regex != null) {
				boolean reHighlight = regex.isHighlight();

				regex.reset();

				if (reHighlight) {
					regex.enableHighlightForSubComponents();
					regex.highlightActiveComponent();
				}
			}
		}

		// start instruction playing
		instructions.setText(nextInstruction);
		instructions.startAnimation();

		kButton.setHiddenTo(true);
	}

	private void skipToNextDataSet() {
		// update the data source
		dataSource.nextDataSet();

		// get the new instruction
		String nextInstrunction = dataSource.getCurrentData().currentInstruction();

		// drawLambda = dataSource.getCurrentData().drawLambda;

		// get the new regex
		regex = dataSource.getCurrentData().regex;
		regex.setXY(Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() / 2.0f);

		// clear the string the user is making
		if (buildingString != null) {
			buildingString.dispose();
			buildingString = null;
		}
		// reset the regular expression
		if (regex != null) {
			// regex.reset();
		}

		// start instruction playing
		instructions.setText(nextInstrunction);
		instructions.startAnimation();

		kButton.setHiddenTo(true);
	}

	@Override
	public void draw(SpriteBatch batch) {
		// draw any sub modules (super call)
		super.draw(batch);

		if (regex != null) {
			regex.draw(batch);
		}

		if (holdingString != null) {
			holdingString.draw(batch);
		}

		if (buildingString != null) {
			buildingString.draw(batch);
		}

		if (kButton != null) {
			if (dataSource != null && dataSource.getCurrentData().showKButton) {
				kButton.draw(batch);
			}
		}

		if (instructions != null) {
			instructions.draw(batch);
		}

		if (lambda != null && buildingString == null) {
			if (dataSource != null) {
				InstructionData current = dataSource.getCurrentData();
				if (current != null && current.drawLambda) {
					lambda.draw(batch);
				}
			}
		}

	}

	@Override
	public void dispose() {
		// dispose any sub-modules
		super.dispose();
		if (holdingString != null) {
			holdingString.dispose();
		}

		if (regex != null) {
			regex.dispose();
		}

	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Tools.convertMousePointsIntoGameCoordinates(camera, convVect);

		if (holdingString == null && regex != null) {
			if (regex.touched(convVect)) {
				holdingString = new DrawableString(regex.getRepresentingString());
				holdingString.setXY(convVect);
			} else if (buildingString != null && buildingString.touched(convVect)) {
				holdingString = buildingString;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Tools.convertMousePointsIntoGameCoordinates(camera, convVect);

		// null checks and check to see if holdingString does not collide with regex
		if (regex != null && holdingString != null && regex.pointsOutOfOwnedRegion(convVect, holdingString.width(), holdingString.height())) {
			// append to string that is being drawn or make a new drawing string
			if (buildingString == null) {
				startBuildingString();
			} else {
				handleDroppingHoldingString();
			}
		} else {
			if (holdingString != null) {
				holdingString.dispose();
				holdingString = null;
			}
		}

		return false;
	}

	private void resetRegex() {
		if (regex != null) {
			boolean isHighlighted = regex.isHighlight();

			regex.reset();

			if (isHighlighted) {
				regex.enableHighlightForSubComponents();
				regex.highlightActiveComponent();
			}
		}

		if (buildingString != null) {
			buildingString.dispose();
			buildingString = null;
		}

		if (dataSource != null) {
			dataSource.getCurrentData().setBuildingString(null);
		}
	}

	private void startBuildingString() {
		buildingString = holdingString;
		regex.nextComponent();
		holdingString = null;
		if (dataSource != null && dataSource.getCurrentData() != null) {
			dataSource.getCurrentData().setBuildingString(this.buildingString);

		}
	}

	/**
	 * invarant1: regex and holding string are not null
	 */
	private void handleDroppingHoldingString() {
		// check if player was holding the building string
		if (holdingString == buildingString) {
			holdingString = null;
			// do not dispose because we want to keep the building string alive
		}
		// check if holding string is close enough to building string, or simply return it
		else if (buildingString.otherObjectIsWithinRange(holdingString, 2)) {
			buildingString.appendToRight(holdingString);
			regex.nextComponent();
			holdingString.dispose();
			holdingString = null;
		} else {
			holdingString.dispose();
			holdingString = null;
		}
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (holdingString != null) {
			Tools.convertMousePointsIntoGameCoordinates(camera, convVect);
			holdingString.setXY(convVect);

			return true;
		}

		return false;
	}

}
