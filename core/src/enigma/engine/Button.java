package enigma.engine;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {
	private Sprite button;
	private boolean shouldHide = true;
	private long delayToStartDraw = 1000;
	private long callToStartDrawingInitialTime = 0;

	public Button() {
		button = new Sprite(TextureLookup.kButton);
	}

	public void draw(SpriteBatch batch) {
		if (button != null && !shouldHide) {
			boolean shouldDraw = System.currentTimeMillis() - delayToStartDraw > callToStartDrawingInitialTime;
			if (shouldDraw) {
				button.draw(batch);
			}
		}
	}

	private boolean timeDelayedEnough() {
		// below would be more efficient if there were a boolean flag to be set once this is true,
		// thus avoiding the arthmetic everytime this is called
		long currTime = System.currentTimeMillis();
		long correctedTime = currTime - delayToStartDraw;
		// boolean result = (currTime - delayToStartDraw) > callToStartDrawingInitialTime;
		boolean result = correctedTime > callToStartDrawingInitialTime;
		return result;
	}

	public void setXY(float x, float y) {
		button.setX(x);
		button.setY(y);
	}

	public void setCenterXY(float x, float y) {
		button.setX(x - widthScaled() / 2);
		button.setY(y - heightScaled() / 2);
	}

	public void setScale(float xyScale) {
		button.setScale(xyScale);
		button.setOrigin(button.getScaleX(), button.getScaleY());
	}

	public float widthScaled() {
		return button.getWidth() * button.getScaleX();
	}

	public float heightScaled() {
		return button.getHeight() * button.getScaleY();
	}

	private long timeLastUpdated = System.currentTimeMillis();
	private long delay1 = 250; // 1000ms = 1sec

	public void logic() {
		// change the way the image looks to let user know it is a button
		if (timeLastUpdated < System.currentTimeMillis() - delay1) {
			if (button.getTexture() == TextureLookup.kButton) {
				button.setTexture(TextureLookup.kButtonPressed);
			} else {
				button.setTexture(TextureLookup.kButton);
			}
			timeLastUpdated = System.currentTimeMillis();
		}
	}

	public void setHiddenTo(boolean value) {
		if (value != shouldHide) {
			shouldHide = value;
			// second statement prevents updating of time if the value was already set
			if (!value) {
				callToStartDrawingInitialTime = System.currentTimeMillis();
			}
		}
	}

	public boolean buttonActive() {
		return !shouldHide && timeDelayedEnough();
	}
}
