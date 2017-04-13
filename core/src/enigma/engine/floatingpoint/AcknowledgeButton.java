package enigma.engine.floatingpoint;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enigma.engine.DrawableString;
import enigma.engine.TextureLookup;

public class AcknowledgeButton {
	private char charOnBtn;
	private DrawableString letter;
	private Sprite button;
	private float btnScale = 1.0f;

	public AcknowledgeButton(char characterOnButton) {
		this.charOnBtn = characterOnButton;
		letter = new DrawableString("" + charOnBtn);

		button = new Sprite(TextureLookup.buttonBlack);
		btnScale = (letter.height() * 1.5f)/ button.getHeight();
		button.setScale(btnScale);
		button.setOrigin(0, 0);

		setPosition(0, 0);

	}

	public void draw(SpriteBatch batch) {
		button.draw(batch);
		letter.draw(batch);
	}

	public void logic() {

	}

	public void setPosition(float x, float y) {
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
