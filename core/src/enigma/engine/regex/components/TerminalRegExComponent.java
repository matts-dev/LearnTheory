package enigma.engine.regex.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import enigma.engine.DrawableString;

public class TerminalRegExComponent extends RegExComponent {
	public DrawableString terminalValue;

	public TerminalRegExComponent(char terminal) {
		this.terminalValue = new DrawableString("" + terminal);
	}

	public TerminalRegExComponent(String concatTerminals) {
		this.terminalValue = new DrawableString(concatTerminals);
	}

	public String getRepresentingString() {
		return this.terminalValue.getText();
	}

	public void draw(SpriteBatch batch) {
		terminalValue.draw(batch);
	}

	public void dispose() {
		terminalValue.dispose();
	}

	@Override
	public void setXY(float x, float y) {
		// this.x = x;
		// this.y = y;
		terminalValue.setXY(x, y);
	}

	@Override
	public boolean touched(Vector3 touchPnt) {
		return terminalValue.touched(touchPnt);
	}

	@Override
	public float getLength() {
		return terminalValue.width();
	}

	@Override
	public void translateX(float amount) {
		// this.x = terminalValue.getX() + amount;
		terminalValue.setXY(terminalValue.getX() + amount, terminalValue.getY());
	}

	@Override
	public void translateY(float amount) {
		// this.y = terminalValue.getY() + amount;
		terminalValue.setXY(terminalValue.getX(), terminalValue.getY() + amount);
	}

	@Override
	public float getX() {
		return terminalValue.getX();
	}

	@Override
	public float getY() {
		return terminalValue.getY();
	}
	
	@Override
	public void highlightActiveComponent(){
		if(showLetterHighlighting){
			terminalValue.highlight();
		}
	}
	
	@Override
	public void unhighlightActiveComponent(){
		if(showLetterHighlighting){
			terminalValue.unhighlight();
		}
	}

	@Override
	public void reset() {
		super.resetActive();
		
		for(RegExComponent component : components){
			component.reset();
		}
	}

}
