package enigma.engine.regex.components;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import enigma.engine.DrawableString;
import enigma.engine.Tools;

public class RegularExpression extends RegExComponent {
	private static Random rng = new Random();
	private DrawableString strRep;
	private StringBuffer sb = null;
	private static StringBuffer ssb = null;
	private RegExComponent lastTouchedComponent = null;
	private boolean lastTouchInActiveModule = true;

	public RegularExpression(RegExComponent... args) {
		strRep = new DrawableString("");

		for (int i = 0; i < args.length; ++i) {
			if (args[i] != null) {
				components.add(args[i]);
			}
		}
		organizeComponents();
		initActiveComponent();
		// strRep.setText(this.getRepresentingString());
		strRep.setText(this.rawText());
	}
	
	
	public RegularExpression(boolean showParenthesis, RegExComponent... args) {
		strRep = new DrawableString("");

		for (int i = 0; i < args.length; ++i) {
			if (args[i] != null) {
				components.add(args[i]);
			}
		}
		organizeComponents();
		initActiveComponent();
		// strRep.setText(this.getRepresentingString());
		strRep.setText(this.rawText());
	}

	private String rawText() {
		// set up string buffer (lazy loading)
		if (sb == null) {
			sb = new StringBuffer();
		}
		sb.setLength(0);

		// load all strings into string buffer
		for (RegExComponent component : components) {
			sb.append(component.getRepresentingString());
		}

		// throw new RuntimeException();
		return sb.toString();
	}	

	private void organizeComponents() {
		float totalLength = 0.0f;

		// Align all the components
		for (RegExComponent component : components) {
			component.setXY(this.x, this.y);

			// grows the regex from the center to the right (needs relalign by 1/2 total length)
			component.translateX(totalLength + (component.getLength() / 2));
			totalLength += component.getLength();
		}

		// Translate all components into the correct location
		for (RegExComponent component : components) {
			component.translateX(-totalLength / 2);
			// float xDiff = component.getX() - this.x;
			// float yDiff = component.getY() - this.y;
			// component.translateX(xDiff);
			// component.translateY(yDiff);
		}
	}

	public void draw(SpriteBatch batch) {
		for (RegExComponent component : components) {
			component.draw(batch);
		}
	}

	@Override
	public void dispose() {
		if (strRep != null) {
			strRep.dispose();
		}

		for (RegExComponent component : components) {
			component.dispose();
		}

		// above dispose should clear any active components
		RegExComponent activeComponent = getActiveComponent();
		if (activeComponent != null) {
			// leaving this code here for a note: the active component has already been disposed
			// (all components have been)
			// activeComponent.dispose();
		}
	}

	/**
	 * @WARNING the result of this function MOST be captured and .dispose must be run on it if it is
	 *          to be discarded
	 * @incomplete
	 * @param convVect
	 * @return
	 */
	public DrawableString getStringFromCoordinates(Vector3 touchPnt) {
		RegExComponent activeComponent = getActiveComponent();
		if (activeComponent != null) {
			if (activeComponent.touched(touchPnt)) {
				return new DrawableString(activeComponent.getRepresentingString());
			}
		}

		return null;
	}

	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
		organizeComponents();

		if (strRep != null) {
			strRep.setXY(x, y);
		}
	}

	/**
	 * Assumes point is at the middle of the obj (like sprites)
	 * 
	 * @param point
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean pointsOutOfOwnedRegion(Vector3 point, float width, float height) {
		// strRep.setText(this.getRepresentingString());
		strRep.setText(this.rawText());

		if (strRep != null) {
			float minDiff = strRep.height() * 2.0f;

			// object bounds
			float objMaxY = point.y + height / 2;
			float objMinY = point.y;
			float objMaxX = point.x + width / 2;
			float objMinX = point.x;

			// this bounds (get center coordinate, modify by half size, then add tolerance)
			float thisMaxY = (strRep.height() / 2) + strRep.getY() + minDiff;
			float thisMinY = -(strRep.height() / 2) + strRep.getY() - minDiff;
			float thisMaxX = (strRep.width() / 2) + strRep.getX() + minDiff;
			float thisMinX = -(strRep.width() / 2) + strRep.getX() - minDiff;

			// check four corner points
			boolean bottomLeftCollides = Tools.pointWithinBounds(objMinX, objMinY, thisMinX, thisMaxX, thisMinY, thisMaxY);
			boolean bottomRightCollides = Tools.pointWithinBounds(objMaxX, objMinY, thisMinX, thisMaxX, thisMinY, thisMaxY);
			boolean topLeftCollides = Tools.pointWithinBounds(objMinX, objMaxY, thisMinX, thisMaxX, thisMinY, thisMaxY);
			boolean topRightCollides = Tools.pointWithinBounds(objMaxX, objMaxY, thisMinX, thisMaxX, thisMinY, thisMaxY);

			// determine if none of the points collided
			boolean noPointCollides = !bottomLeftCollides && !bottomRightCollides && !topLeftCollides && !topRightCollides;

			return noPointCollides;
		}
		return false;
	}

	public boolean pointInOwnedArea(Vector3 pnt) {

		RegExComponent actComp = getActiveComponent();
		// if (actComp != null && actComp.touched(pnt)) {
		// System.out.println("active component touched");
		// }

		return actComp != null && actComp.touched(pnt);

		// TODO: remove code below
		// System.out.println("RegularExpression.pointInOwnedArea() needs redefining to use
		// component size");
		//
		// float minDiff = strRep.height() * 2.0f;
		//
		// float thisMaxY = (strRep.height() / 2) + strRep.getY() + minDiff;
		// float thisMinY = -(strRep.height() / 2) + strRep.getY() - minDiff;
		// float thisMaxX = (strRep.width() / 2) + strRep.getX() + minDiff;
		// float thisMinX = -(strRep.width() / 2) + strRep.getX() - minDiff;
		//
		// return Tools.pointWithinBounds(pnt.x, pnt.y, thisMinX, thisMaxX, thisMinY, thisMaxY);
	}

	@Override
	public String getRepresentingString() {
		// // set up string buffer (lazy loading)
		// if (sb == null) {
		// sb = new StringBuffer();
		// }
		// sb.setLength(0);
		//
		// // load all strings into string buffer
		// for (RegExComponent component : components) {
		// sb.append(component.getRepresentingString());
		// }
		//
		// // throw new RuntimeException();
		// return sb.toString();

		// new way
		if (lastTouchedComponent != null) {
			return lastTouchedComponent.getRepresentingString();
		}
		return null;

	}

	@Override
	public boolean touched(Vector3 touchPnt) {
		if(this.locked){
			return false;
		}
		
		// since new touch starting, then clear old data fields
		lastTouchedComponent = null;
		lastTouchInActiveModule = true;
		boolean localLastTouchInModule = true;

		RegExComponent activeCmp = getActiveComponent();
		
		while (activeCmp != null) {
			if (activeCmp.touched(touchPnt)) {
				// save the last component that was touched (used for gathering strings)
				lastTouchedComponent = activeCmp;

				// update the field since module was touched (didn't update in case nothing was
				// found)
				lastTouchInActiveModule = localLastTouchInModule;
				return true;
			}
			if (activeCmp.shouldPeakNextComponent()) {
				activeCmp = peakNextComponent(activeCmp);
				localLastTouchInModule = false;
			} else {
				activeCmp = null;
			}
		}
		return false;
	}

	@Override
	public float getLength() {
		float totalLength = 0.0f;
		for (RegExComponent component : components) {
			totalLength += component.getLength();
		}
		return totalLength;
	}

	@Override
	public void translateX(float amount) {
		for (RegExComponent component : components) {
			component.translateX(amount);
		}
	}

	@Override
	public void translateY(float amount) {
		for (RegExComponent component : components) {
			component.translateY(amount);
		}
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public RegExComponent nextComponent() {
		RegExComponent activeComponent = getActiveComponent();

		// highlight component after kleene star (in case user chooses 0 items)
		// doAdditionalHighlightsForUpcomingKStar(activeComponent);

		if (activeComponent != null && activeComponent instanceof KleeneStarRegExComponent) {
			// do not update next component, next component may be accessed using peeks
			// this component may be extracted from again (kleene star is 0 or more)

			// check to make sure the user didn't access a component not contained in kleene star
			if (!lastTouchInActiveModule) {
				// unhighlight components in kleene star
				unhighlightActiveComponent();

				// unhighlight components after kleene star
				RegExComponent next = peakNextComponent(activeComponent);
				//if (next != null) {		//OLD WORKING IF STATEMENT
				//	next.unhighlightActiveComponent();
				//}
				//Unhighlight all the peaked kleene stars (that are not the touched)
				while(next != null && next != lastTouchedComponent){
					next.unhighlightActiveComponent();
				}

				//internally set up .nextComponent to skip //TODO: remove below? also remove code from regex compononent
				fastforwardActiveComponentTo(lastTouchedComponent);// TODO remove this call - it does nothing
				
				//make sure that the next component isn't itself 
				if(lastTouchedComponent instanceof KleeneStarRegExComponent){
					//prevents doing a super.nextComponent until user touches something else
					lastTouchedComponent.highlightActiveComponent();//unhighlighted previously
					return lastTouchedComponent;
				}
				
				return super.nextComponent(); //OLD WAY
				
				
//				//TODO *.*
//				if(activeComponent.hasMoreActiveComponents()){
//					return activeComponent.nextComponent();
//				}
//				return super.nextComponent();
			}
			return activeComponent;
		} else if (activeComponent instanceof OrComponent) {
			// test if the active component has incomplete active module
			if (activeComponent.hasIncompleteSubModule()) {
				activeComponent.nextComponent();
				return activeComponent;
			}
			// highlight component after kleene star (in case user chooses 0 items)
			doAdditionalHighlightsForUpcomingKStar(activeComponent);
			
			return super.nextComponent(); //way before correction for klenee star within regex
			
			
		} else {
			// highlight component after kleene star (in case user chooses 0 items)
			doAdditionalHighlightsForUpcomingKStar(activeComponent);
			return super.nextComponent();
		}

	}

	private void doAdditionalHighlightsForUpcomingKStar(RegExComponent activeComponent) {
		RegExComponent next = peakNextComponent(activeComponent);
		if (next != null && next instanceof KleeneStarRegExComponent) {
			RegExComponent afterNext = peakNextComponent(next);
			if (afterNext != null) {
				afterNext.highlightActiveComponent();
			}
		}

	}

	public static RegularExpression generateRandomExpression() {
		return generateRandomExpression(1);
	}

	private static RegularExpression generateRandomExpression(int tiers) {
		ArrayList<RegExComponent> toAdd = new ArrayList<RegExComponent>();

		if (tiers < 1) {
			return new RegularExpression(new TerminalRegExComponent(generateRandomString()));
		}

		for (int i = 0; i < 3; ++i) {
			int choice = rng.nextInt(4);

			boolean lastWasString = false;
			if ((choice == 0 || choice == 1) && !lastWasString) {
				toAdd.add(new RegularExpression(new TerminalRegExComponent(generateRandomString())));
				lastWasString = true;
			} else if (choice == 2) {
				toAdd.add(new OrComponent(generateRandomExpression(tiers - 1), generateRandomExpression(tiers - 1)));
				lastWasString = false;
			} else {
				toAdd.add(new KleeneStarRegExComponent(generateRandomExpression(tiers - 1)));
				lastWasString = false;
			}
		}

		return new RegularExpression(toAdd.get(0), toAdd.get(1), toAdd.get(2));
	}

	private static String generateRandomString() {
		if (ssb == null) {
			ssb = new StringBuffer();
		}
		ssb.setLength(0);

		// generate the string
		int amount = rng.nextInt(2);

		// generate the string
		for (int i = 0; i <= amount; ++i) {
			if (rng.nextBoolean()) {
				ssb.append('a');
			} else {
				ssb.append('b');
			}
		}

		return ssb.toString();
	}


	@Override
	public void reset() {
		super.resetActive();
		
		for(RegExComponent component : components){
			component.reset();
		}
	}

	/* (non-Javadoc)
	 * This method must be overridden since the nextComponent() method has been overridden.
	 * If this method is not override, then it will use a different logic and the initialization will not work.
	 * 
	 * @see enigma.engine.regex.components.RegExComponent#initActiveComponent()
	 */
	@Override
	protected void initActiveComponent() {
		super.nextComponent();
	}

	public boolean isHighlight() {
		return showLetterHighlighting;
	}

	@Override
	public void highlightActiveComponent(){
		RegExComponent currComponent = getActiveComponent();
		RegExComponent lastComponent = currComponent;
		
		if (currComponent != null && showLetterHighlighting){
			currComponent.highlightActiveComponent();
			
			while(currComponent != null && currComponent instanceof KleeneStarRegExComponent){
				//get the next component in curr component (currently lastComponent represents currComponent)
				currComponent = peakNextComponent(currComponent);
				
				//check if we've reached end of components (peakNext will have returned the previous component)
				if(currComponent != lastComponent && currComponent != null){
					//highlight the component after kleene star
					currComponent.highlightActiveComponent();
					
					//update last component for next iteration
					//set up for sequence of kleene stars should all be highlighted
					lastComponent = currComponent;
				} else {
					//break out of loop because end has been reached
					break;
				}
			}
		}
	}


}
