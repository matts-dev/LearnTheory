package enigma.engine.floatingpoint;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enigma.engine.DrawableString;
import enigma.engine.TextureLookup;

public class AcknowledgeButton {
	private char charOnBtn;
	private DrawableString letter;
	private Sprite button;
	private float btnScale = 1.0f;
	private long flashDelayMs = 250;
	private long lastFlashTimeStamp = System.currentTimeMillis();
	private boolean drawButton = true;

	public AcknowledgeButton(char characterOnButton) {
		this.charOnBtn = characterOnButton;
		letter = new DrawableString("" + charOnBtn);

		button = new Sprite(TextureLookup.buttonBlack);
		btnScale = (letter.height() * 1.5f)/ button.getHeight();
		button.setScale(btnScale);
		button.setOrigin(0, 0);

		setPosition(0, 0);

	}

	public void draw(SpriteBatch batch) 
	{
		if(drawButton)
		{
			button.draw(batch);
			letter.draw(batch);
		}
	}

	public void logic() 
	{
		long currentTime = System.currentTimeMillis();
		if(currentTime > lastFlashTimeStamp + flashDelayMs) 
		{
			lastFlashTimeStamp = currentTime;
			if(button != null)
			{
				Texture newTexture = button.getTexture() == TextureLookup.buttonBlack ? TextureLookup.buttonGrey : TextureLookup.buttonBlack;
				button.setTexture(newTexture);
			}
		}
	}

	public void showButton(boolean value)
	{
		drawButton = value;
	}
	
	public void setPosition(float x, float y) {
		//set position based on center of button
		button.setPosition(x - button.getWidth() * btnScale / 2, y - button.getHeight() * btnScale / 2);
		letter.setXY(x, y);
	}

	public float getScaleHeight() {
		return button.getHeight() * btnScale;
	}

	public float getScaleWidth() {
		return button.getWidth() * btnScale;
	}

}
