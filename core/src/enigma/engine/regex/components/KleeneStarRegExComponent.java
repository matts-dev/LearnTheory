package enigma.engine.regex.components;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import enigma.engine.DrawableString;

public class KleeneStarRegExComponent extends RegExComponent {
	private boolean showParenthesis = true;
	private ArrayList<DrawableString> parentheses = new ArrayList<DrawableString>();
	private DrawableString kleeneStar;
	private String lastTouchedComponentString = null;
	private RegExComponent targetComponent = null; // make this a super class functionality?
	private RegExComponent stagingComponent = null; // TODO implement this and I think the bug for
													// moving k will disapear

	public KleeneStarRegExComponent(RegExComponent... args) {
		this(true, args);
	}

	public KleeneStarRegExComponent(boolean parenthesis, RegExComponent... args) {
		showParenthesis = parenthesis;
		for (int i = 0; i < args.length; ++i) {
			if (args[i] != null) {
				components.add(args[i]);
			}
		}

		parentheses.add(new DrawableString("("));
		parentheses.add(new DrawableString(")"));

		kleeneStar = new DrawableString("*");
	}

	@Override
	public String getRepresentingString() {
		return lastTouchedComponentString;
	}

	@Override
	public void draw(SpriteBatch batch) {
		// draw components
		for (RegExComponent component : components) {
			component.draw(batch);
		}

		// draw parenthesis
		if (showParenthesis && parentheses != null) {
			parentheses.get(0).draw(batch);
			parentheses.get(1).draw(batch);
		}

		kleeneStar.draw(batch);
	}

	@Override
	public void dispose() {
		// clean up components
		for (RegExComponent component : components) {
			component.dispose();
		}
		components = null;

		// clean up parenthesis
		for (DrawableString paren : parentheses) {
			if (paren != null) paren.dispose();
		}
		parentheses = null;

		kleeneStar.dispose();
		kleeneStar = null;
	}

	@Override
	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;

		// reorganize the components after the position has been updated
		organizeComponents();
	}

	@Override
	public boolean touched(Vector3 touchPnt) {
		// check if the user has already touched something contained, but it is not done generating
		if (targetComponent != null) {
			return testTargetComponentForTouch(touchPnt);
		}

		// check if user touched another component
		for (RegExComponent component : components) {
			if (component.touched(touchPnt)) {
				lastTouchedComponentString = component.getRepresentingString();
				// targetComponent = component;
				stagingComponent = component;
				return true;
			}
		}
		return false;
	}

	private boolean testTargetComponentForTouch(Vector3 touchPnt) {
		if (targetComponent.touched(touchPnt)) {
			lastTouchedComponentString = targetComponent.getRepresentingString();
			// targetComponent = targetComponent.getLastTouchedComponent(); todo, target needs to
			// swich to submodule

			// check if component has been depleted, and reset it has
			if (!targetComponent.hasMoreActiveComponents()) {
				targetComponent.resetActive();

				// setting target to null means the kleene star will now check all components again
				targetComponent = null;
			} else {
				targetComponent.nextComponent();
			}

			// the component was touched, so return true
			return true;
		}
		return false;

	}

	@Override
	public float getLength() {
		float totalLength = 0;

		for (RegExComponent component : components) {
			totalLength += component.getLength();
		}

		if (showParenthesis) {
			for (DrawableString parenthesis : parentheses) {
				totalLength += parenthesis.width();
			}
		}

		totalLength += kleeneStar.width();

		return totalLength;
	}

	@Override
	public void translateX(float amount) {
		this.x += amount;
		organizeComponents();
	}

	@Override
	public void translateY(float amount) {
		this.y += amount;
		organizeComponents();
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	private void organizeComponents() {
		float totalLength = 0;

		// create an open parenthesis (and account for their length)
		totalLength = openParenthesis(totalLength);

		// build a linear string of components from the center towards the right
		for (RegExComponent component : components) {
			component.setXY(x + totalLength + component.getLength() / 2, y);
			totalLength += component.getLength();
		}

		// create a close parenthesis
		totalLength = closeParenthesis(totalLength);

		kleeneStar.setXY(x + totalLength + kleeneStar.width() / 2, y);
		totalLength += kleeneStar.width();

		// shift the linear string of components (centers on middle)
		for (RegExComponent component : components) {
			component.translateX(-totalLength / 2);
		}
		for (DrawableString parenthesis : parentheses) {
			parenthesis.translateX(-totalLength / 2);
		}
		kleeneStar.translateX(-totalLength / 2);
	}

	/**
	 * Adjusts total length for a opening parenthesis; also sets the opening parenthesis position.
	 * 
	 * @sideeffect this sets the position of the first parenthesis sprite to start at the total
	 *             length
	 * @param totalLength the current total length
	 * @return either a new totalLength if parentheses are enabled, or the original total length if
	 *         note
	 */
	private float openParenthesis(float totalLength) {
		if (showParenthesis && parentheses != null) {
			DrawableString openParen = parentheses.get(0);
			openParen.setXY(x + totalLength + openParen.width() / 2, y);
			totalLength += openParen.width();
		}
		return totalLength;
	}

	/**
	 * Adjusts total length for a closing parenthesis; also sets the closing parenthesis position.
	 * 
	 * @sideeffect this sets the position of the first parenthesis sprite to start at the total
	 *             length
	 * @param totalLength the current total length
	 * @return either a new totalLength if parentheses are enabled, or the original total length if
	 *         note
	 */
	private float closeParenthesis(float totalLength) {
		if (showParenthesis && parentheses != null) {
			DrawableString openParen = parentheses.get(1);
			openParen.setXY(x + totalLength + openParen.width() / 2, y);
			totalLength += openParen.width();
		}
		return totalLength;
	}

	@Override
	public boolean shouldPeakNextComponent() {
		// if selected component has more components
		if (targetComponent != null) {
			// if there exists a target component, then it has not been depleted
			// the moment a target component has no more modules, it is set to null.
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean hasMoreActiveComponents() {
		// @WARNING: TODO below code hasn't been tested
		 if (shouldPeakNextComponent()) {
		 return true;
		 }
		 return super.hasMoreActiveComponents();
	}

	@Override
	public void highlightActiveComponent() {
		for (RegExComponent component : components) {
			component.highlightActiveComponent();
		}
	}

	@Override
	public void unhighlightActiveComponent() {
		for (RegExComponent component : components) {
			component.unhighlightActiveComponent();
		}
	}

	/*
	 * (non-Javadoc) This is a decorator that uses an intermediate staging component
	 * 
	 * @see enigma.engine.RegExComponent#nextComponent()
	 */
	@Override
	public RegExComponent nextComponent() {
		if (stagingComponent != null && targetComponent == null) {
			targetComponent = stagingComponent;
		}

		RegExComponent ret = super.nextComponent();
		return ret;
	}



	@Override
	public void reset() {
		super.resetActive();

		for (RegExComponent component : components) {
			component.reset();
		}

		lastTouchedComponentString = null;
		stagingComponent = null;
		targetComponent = null;
	}
}
