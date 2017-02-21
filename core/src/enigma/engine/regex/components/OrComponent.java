package enigma.engine.regex.components;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import enigma.engine.DrawableString;

public class OrComponent extends RegExComponent {
	private ArrayList<DrawableString> orSymbols = new ArrayList<DrawableString>();
	private ArrayList<DrawableString> parentheses = new ArrayList<DrawableString>();
	private boolean showParenthesis = true;
	private RegExComponent stagingSubComponent = null;

	private DrawableString orSymbol = new DrawableString(" + ");
	// private String lastTouchedComponentString = null;

	public OrComponent(RegExComponent... args) {
		this(true, args);
	}

	public OrComponent(boolean showParenthesis, RegExComponent... args) {
		this.showParenthesis = showParenthesis;

		for (int i = 0; i < args.length; ++i) {
			if (args[i] != null) {
				components.add(args[i]);
			}
		}

		// create 1 less or symbol (" + ") than there are arguments
		for (int i = 0; i < args.length - 1; ++i) {
			orSymbols.add(new DrawableString(" + "));
		}

		// set up parenthesis
		parentheses.add(new DrawableString("("));
		parentheses.add(new DrawableString(")"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @invariant1 assumes that a call to touched happened and the result was true (to set the
	 * string)
	 * 
	 * @see enigma.engine.RegExComponent#getRepresentingString()
	 */
	@Override
	public String getRepresentingString() {
		// for the first string pulled from OR, it may not be used. The staging sub component
		// is a temporary component until the actual user commits to using a given component
		if (stagingSubComponent != null && lastTouchedComponent == null) {
			return stagingSubComponent.getRepresentingString();
		}

		if (lastTouchedComponent != null) {
			return lastTouchedComponent.getRepresentingString();
		}
		return null;
		// return lastTouchedComponentString; //old way
	}

	@Override
	public void draw(SpriteBatch batch) {
		for (RegExComponent component : components) {
			component.draw(batch);
		}

		for (DrawableString symbol : orSymbols) {
			symbol.draw(batch);
		}

		if (showParenthesis && parentheses != null) {
			parentheses.get(0).draw(batch);
			parentheses.get(1).draw(batch);
		}
	}

	@Override
	public void dispose() {
		// clean up components
		for (RegExComponent component : components) {
			component.dispose();
		}
		components = null;

		// clean up actual symbols
		for (DrawableString symbol : orSymbols) {
			if (symbol != null) symbol.dispose();
		}
		orSymbols = null;

		// clean up mock symbol
		if (orSymbol != null) {
			orSymbol.dispose();
			orSymbol = null;
		}

		// clean up parenthesis
		for (DrawableString paren : parentheses) {
			if (paren != null) paren.dispose();
		}
		parentheses = null;
	}

	@Override
	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
		organizeComponents();
	}

	@Override
	public boolean touched(Vector3 touchPnt) {
		if (lastTouchedComponent != null) {
			if (lastTouchedComponent.touched(touchPnt)) {
				return true;
			}
			return false;
		}

		// will only execute if return did not happen above
		for (RegExComponent component : components) {
			if (component.touched(touchPnt)) {
				// lastTouchedComponent = component;
				stagingSubComponent = component;

				return true;
			}
		}

		// no component was touched
		lastTouchedComponent = null;
		return false;
	}

	@Override
	public float getLength() {
		float totalLength = 0.0f;
		// float orSymbolLength = 0; //TODO set later or figure out a logic for this
		float orSymbolLength = orSymbol.width();

		for (RegExComponent component : components) {
			totalLength += component.getLength();
			totalLength += orSymbolLength;
		}

		// remove last or symbol length
		totalLength -= orSymbolLength;

		if (showParenthesis) totalLength += 2 * parentheses.get(0).width();

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
		int symbolIter = 0;

		// create an open parenthesis
		if (showParenthesis && parentheses != null) {
			DrawableString openParen = parentheses.get(0);
			openParen.setXY(x + totalLength + openParen.width() / 2, y);
			totalLength += openParen.width();
		}

		// build a linear string of components from the center towards the right
		for (RegExComponent component : components) {
			component.setXY(x + totalLength + component.getLength() / 2, y);
			totalLength += component.getLength();

			// add an or (+) symbol; there should only be enough symbols as set in constructor.
			if (symbolIter < orSymbols.size()) {
				DrawableString symbol = orSymbols.get(symbolIter);
				symbol.setXY(x + totalLength + symbol.width() / 2, y);
				totalLength += orSymbol.width();
				symbolIter++;
			}
		}

		// create a close parenthesis
		if (showParenthesis && parentheses != null) {
			DrawableString openParen = parentheses.get(1);
			openParen.setXY(x + totalLength + openParen.width() / 2, y);
			totalLength += openParen.width();
		}

		// shift the linear string of components
		for (RegExComponent component : components) {
			component.translateX(-totalLength / 2);
		}
		for (DrawableString symbol : orSymbols) {
			symbol.translateX(-totalLength / 2);
		}
		for (DrawableString parenthesis : parentheses) {
			parenthesis.translateX(-totalLength / 2);
		}
	}

	@Override
	public RegExComponent nextComponent() {

		// check to see if the staged subcomponent should become to true subcomponent
		if (stagingSubComponent != null && stagingSubComponent.hasMoreActiveComponents() && lastTouchedComponent == null) {
			lastTouchedComponent = stagingSubComponent;
			this.unhighlightActiveComponent();
			//boolean isHighlighted = lastTouchedComponent.showLetterHighlighting;
			lastTouchedComponent.nextComponent(); //TODO reinstate highlight like below?
			if(lastTouchedComponent instanceof KleeneStarRegExComponent){
				lastTouchedComponent.enableHighlightForSubComponents(); //TODO 2/6/17 - may cause bugs else were - added for #4
				lastTouchedComponent.highlightActiveComponent();//TODO 2/6/17 - may cause bugs else were - added for #4
			}
			return this;
		}

		// check the current sub component (lastTouchedComponent)
		if (lastTouchedComponent != null && lastTouchedComponent.hasMoreActiveComponents()) {
			//boolean isHighlighted = lastTouchedComponent.showLetterHighlighting;
			lastTouchedComponent.nextComponent();	
			if(lastTouchedComponent instanceof KleeneStarRegExComponent){
				lastTouchedComponent.enableHighlightForSubComponents(); //TODO 2/6/17 - may cause bugs else were - added for #4
				lastTouchedComponent.highlightActiveComponent();//TODO 2/6/17 - may cause bugs else were - added for #4
			}
			return this;
		} else {
			//change to target is active component
			//if(actComp instanceof KleeneStarRegExComponent){ //OLD WAY
			if(this.targetIsKleeneStar()){
				return getActiveComponent();
			} else {
				return super.nextComponent();
			}
		}
	}

	@Override
	public boolean hasIncompleteSubModule() {
		// test if there is a lastTouchedComponent (a commited component
		// also test if there is a potentialcomponent to be commited (staged)
		return (lastTouchedComponent != null && lastTouchedComponent.hasMoreActiveComponents()) 
				|| (stagingSubComponent != null 
					&& stagingSubComponent.hasMoreActiveComponents() 
					&& lastTouchedComponent == null)
				//ammendum below that may break logic TODO: start here - change the code below from getActiveComp to somethign that detects if there is a kleene star
				|| (lastTouchedComponent != null && this.targetIsKleeneStar());
	}

	@Override
	public void highlightActiveComponent() {
		for (RegExComponent component : components) {
			component.highlightActiveComponent();
		}
	}

	@Override
	public void unhighlightActiveComponent() {
		if (lastTouchedComponent != null) {
			// lastTouchedComponent.unhighlightActiveComponent();
		}
		for (RegExComponent component : components) {
			component.unhighlightActiveComponent();
		}
	}

	@Override
	public void reset() {
		super.resetActive();

		for (RegExComponent component : components) {
			component.reset();
		}
		
		stagingSubComponent = null;
	}
}
