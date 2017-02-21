package enigma.engine.regex;

import java.util.ArrayList;

import enigma.engine.DrawableString;
import enigma.engine.regex.components.RegularExpression;

public class InstructionData {
	public RegularExpression regex;
	public DrawableString buildingString;
	public ArrayList<String> correctOutputs = new ArrayList<String>();
	public boolean drawLambda = false;
	public int minSize = 5;
	public boolean loadNext = false;
	// action iterators
	public ArrayList<InstructionAction> actions = new ArrayList<InstructionAction>();
	private int actionIndex = 0;

	// text iterators
	public ArrayList<String> instructionTexts = new ArrayList<String>();
	private int instructionIndex = 0;
	public boolean showKButton = true;

	private void checkActions() {
		if (actionIndex >= actions.size()) {
			return;
		}

		InstructionAction currAction = actions.get(actionIndex);
		while (currAction != null && currAction != InstructionAction.PRINT_INSTRUCTION) {
			// do current action
			switch (currAction) {
			case UNLOCKREGEX:
				if (regex != null) {
					regex.setLock(false);
				}
				break;
			case LOCKREGEX:
				if (regex != null) {
					regex.setLock(true);
				}
				break;
			case DRAW_STRING_MATCHES:
				if (buildingString != null && correctOutputs.size() > 0) {
					if (!checkBuildingStringOutput()) {
						System.out.println("incorrect string");
						// reprint message
						instructionIndex--;
						return;
					}
					break;
				} else {
					// re-print message
					instructionIndex--;
					return;
				}
			case HIGHLIGHT:
				if (regex != null) {
					regex.enableHighlightForSubComponents();
					regex.highlightActiveComponent();
				}
				break;
			case SET_KBUTTON_FLAG_FALSE:
				showKButton = false;
				break;
			case DRAW_LAMBDA:
				drawLambda = true;
				break;
			case STRING_LENGTH_ATLEAST_MINSIZE:
				if (buildingString != null) {
					if (!(buildingString.getText().length() >= minSize)) {
						// string not long enough, redraw the last instruction
						instructionIndex--;
						// showKButton = false;
						return;
					}
					// showKButton = true;
				} else {
					// building string is null
					instructionIndex--;
					// showKButton = false;
					return;
				}
			case LOAD_NEXT:
				loadNext = true;
				break;
			default:
				break;
			}
			// update iterator index
			actionIndex += 1;

			// update for next part of loop
			if (actions.size() > actionIndex) {
				currAction = actions.get(actionIndex);
			} else {
				break;
			}
		}
		actionIndex += 1;
	}

	/**
	 * checks if the building strings output is one of the inputs in correct output
	 * 
	 * @invariant building string is not null and building string text is not null
	 * @return
	 */
	private boolean checkBuildingStringOutput() {
		for (String str : correctOutputs) {
			if (str.equals(buildingString.getText())) {
				return true;
			}
		}
		return false;
	}

	public String currentInstruction() {
		checkActions();
		return instructionTexts.get(instructionIndex);
	}

	public String nextInstruction() {
		checkActions();
		if (instructionIndex + 1 < instructionTexts.size()) {
			instructionIndex++;
			return instructionTexts.get(instructionIndex);
		} else {
			return "END";
		}
	}

	public void reset() {
		instructionIndex = 0;
		actionIndex = 0;
	}

	public void dispose() {
		if (regex != null) {
			regex.dispose();
		}
	}

	/**
	 * Sets a reference to the building string so that it it can be examined
	 * 
	 * @sideeffect Sets the showKButton flag to true as sideeffect
	 * @param buildingStr
	 */
	public void setBuildingString(DrawableString buildingStr) {
		this.buildingString = buildingStr;
		showKButton = true;
	}
	
	

}