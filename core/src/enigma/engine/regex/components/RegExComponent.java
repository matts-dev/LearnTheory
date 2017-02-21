package enigma.engine.regex.components;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class RegExComponent {
	protected float x = 0.0f;
	protected float y = 0.0f;
	protected RegExComponent lastTouchedComponent = null;
	public boolean showLetterHighlighting = false;
	protected boolean locked = false;

	/**
	 * @WARNING: may return null if a call to touched did not return true. The method touched will
	 *           set the returned string for some RegExComponents (e.g. the "Or" component)
	 * @return a string that represents the component.
	 */
	public abstract String getRepresentingString();

	public void setLock(boolean value) {
		this.locked = value;
	}

	public RegExComponent getLastTouchedComponent() {
		return null;
	}

	public abstract void draw(SpriteBatch batch);

	/**
	 * Calls dispose on components and sets their internal links to null
	 * 
	 * @note make sure references are set to null in addition to calling dispose. This will throw a
	 *       null pointer exception if they are used and will make debugging easier.
	 */
	public abstract void dispose();

	public abstract void setXY(float x, float y);

	public abstract boolean touched(Vector3 touchPnt);

	public abstract float getLength();

	public abstract void translateX(float amount);

	public abstract void translateY(float amount);

	public abstract float getX();

	public abstract float getY();

	/** Resets the regular expression and all sub components to initial state */
	public abstract void reset();

	// Handling targeting components
	protected ArrayList<RegExComponent> components = new ArrayList<RegExComponent>();
	private RegExComponent activeComponent;
	private int activeComponentIndex = -1;

	public RegExComponent nextComponent() {
		if (activeComponentIndex + 1 < components.size() && components.size() > 0) {
			// remove highlighting on old components
			if (showLetterHighlighting) {
				unhighlightActiveComponent();
			}

			activeComponentIndex++;
			activeComponent = components.get(activeComponentIndex);

			// highlight the new active components
			if (showLetterHighlighting) {
				highlightActiveComponent();
			}

			return activeComponent;
		} else {
			// no more components left
			if (showLetterHighlighting) {
				unhighlightActiveComponent();
			}

			activeComponent = null;
			return null;
		}
	}

	protected void initActiveComponent() {
		nextComponent();
	}

	public RegExComponent getActiveComponent() {
		return activeComponent;
	}

	public void resetActive() {
		// REMOVE ALL HIGHLIGHTS
		unhighlightActiveComponent();
		for (RegExComponent component : components) {
			component.unhighlightActiveComponent();
		}

		lastTouchedComponent = null;
		activeComponentIndex = -1;
		initActiveComponent();

		// correctly highlight the new active component
		// highlightActiveComponent();
		// for(RegExComponent component : components){
		// //component.highlightActiveComponent(); //TODO testing the removal of this line
		// }

	}

	/**
	 * Updates the active component index to be the the index of component parameter The effect of
	 * this function is basically: Make the active component the parameter component.
	 * 
	 * This either is successful, or doesn't change the component index
	 * 
	 * @param component the component to search for an index
	 * @return returns the component that was set (or null if no component was set)
	 */
	public RegExComponent fastforwardActiveComponentTo(RegExComponent component) {
		for (int i = 0; i < components.size(); ++i) {
			// check if found component that component after is requested
			if (components.get(i) == component) {
				// this is the current component, so return the next component in loop
				activeComponentIndex = i;
				activeComponent = component;
				return components.get(i);
			}
		}

		// either there is no componet after the target, or the component could not be found
		return null;
	}

	public RegExComponent peakNextComponent(RegExComponent componentToFind) {
		// O(n) linear search - could improve this by using hashmap instead - but data will always
		// be small
		boolean returnNextComponent = false;

		for (int i = 0; i < components.size(); ++i) {
			// check if this "next" component should be returned (flag set below)
			if (returnNextComponent) {
				// previously, the flat to return this component was set, so return this component
				return components.get(i);
			}

			// check if found component that component after is requested
			if (components.get(i) == componentToFind) {
				// this is the current component, so return the next component in loop
				returnNextComponent = true;
			}
		}

		// returns null if no component was found or there was not a next component
		return null;
	}

	/**
	 * Tells whether the active components after this component should also be checked. This is
	 * useful for things like kleene star where the current component may be skipped if 0 terminals
	 * are taken. By default, this shouldn't happen so the function returns false. However, this
	 * function should be overridden if needed.
	 * 
	 * @return
	 */
	public boolean shouldPeakNextComponent() {
		return false;
	}

	public boolean hasMoreActiveComponents() {
		if (activeComponentIndex < (components.size() - 1) && components.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean hasIncompleteSubModule() {
		return false;
	}

	public void highlightActiveComponent() {
		if (activeComponent != null && showLetterHighlighting) {
			activeComponent.highlightActiveComponent();
		}
	}

	public void unhighlightActiveComponent() {
		if (activeComponent != null && showLetterHighlighting) {
			activeComponent.unhighlightActiveComponent();
		}
	}

	public void cancelLastTouchedComponentIfOnFirstActive() {
		if (lastTouchedComponent != null) {
			if (lastTouchedComponent.activeComponentIndex == 0) {
				lastTouchedComponent = null;
			}
		}
	}

	public void disableHighlightForSubComponents() {
		for (RegExComponent component : components) {
			component.disableHighlightForSubComponents();
		}
		showLetterHighlighting = false;
	}

	public void enableHighlightForSubComponents() {
		showLetterHighlighting = true;
		for (RegExComponent component : components) {
			component.enableHighlightForSubComponents();
		}
	}

	public boolean targetIsKleeneStar() {

		// Select to use either the last target, or the active target
		RegExComponent targetInQuestion = null;
		if (activeComponent == null && lastTouchedComponent != null) {
			// user has not found a active target yet within the RegEx
			targetInQuestion = lastTouchedComponent;
		} else {
			// user has already started working on a target within the RegEx
			targetInQuestion = activeComponent;
		}

		if (targetInQuestion == null) {
			return false;
		}

		if (targetInQuestion instanceof KleeneStarRegExComponent) {
			return true;
		} else {
			return targetInQuestion.targetIsKleeneStar();
		}
	}
}
