package enigma.engine.floatingpoint;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enigma.engine.DrawableString;

public class AcknlowedgedInstruction {
	private AcknowledgeButton ackBtn;
	private DrawableString instruction;
	
	public AcknlowedgedInstruction(char characterOnButton)
	{
		instruction = new DrawableString("");
		ackBtn = new AcknowledgeButton(characterOnButton);
		ackBtn.showButton(false);
	}
	
	public void setXY(float x, float y)
	{
		instruction.setXY(x, y);
		updateButtonPosition();
	}
	
	public void animateLogic()
	{
		instruction.animateLogic();
		ackBtn.logic();
		if(instruction.isAnimating())
		{
			ackBtn.showButton(false);
		}
		else
		{
			ackBtn.showButton(true);
		}
	}
	
	public void setText(String newText)
	{
		instruction.setText(newText);
		updateButtonPosition();
	}

	private void updateButtonPosition() 
	{
		ackBtn.setPosition(
				instruction.getX() + instruction.width() /2 + ackBtn.getScaleWidth() / 2,
				instruction.getY()); 
	}
	
	public void draw(SpriteBatch batch)
	{
		instruction.draw(batch);
		ackBtn.draw(batch);
	}

	public void startAnimation() {
		instruction.startAnimation();
	}
}
