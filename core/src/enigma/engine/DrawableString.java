package enigma.engine;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class DrawableString {
	private String text;
	private BitmapFont bmFont;
	private GlyphLayout gl;

	private static Random rng = new Random();

	/** variable used to determine when the last character was animated */
	private long timeLastAnimated = 0;
	private boolean currentlyAnimating = false;
	private int animCharacterIterator = 0;
	private StringBuffer buildingString;
	private long delayAmountInMiliSec = 25;
	private boolean addVariationToDelay = true;
	private long variation = 0;

	/** x is centered at the middle of the string */
	private float x;

	/** y is centered at the middle of the string */
	private float y;

	public DrawableString(String text) {
		// bmFont = new BitmapFont(Gdx.files.internal("prada.fnt"));
		bmFont = TextureLookup.whiteBMFont; 

		gl = new GlyphLayout();
		gl.setText(bmFont, text);
		this.setText(text);
	}

	public void draw(SpriteBatch batch) {
		// draw raw

		if (!currentlyAnimating) {
			bmFont.draw(batch, getText(), x - gl.width / 2, y + gl.height / 2);
		} else {
			bmFont.draw(batch, buildingString.toString(), x - gl.width / 2, y + gl.height / 2);
		}

		// draw accounting for scale (untested)
		// bmFont.draw(batch, getText(), x - (gl.width * bmFont.getScaleX()) / 2, y + (gl.height *
		// bmFont.getScaleY()) / 2);

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		gl.setText(bmFont, text);
		currentlyAnimating = false;
	}

	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setXY(Vector3 location) {
		this.x = location.x;
		this.y = location.y;
	}

	public void translateX(float amount) {
		this.x += amount;
	}

	public void translateY(float amount) {
		this.y += amount;
	}

	public void dispose() {

		// bmFont.dispose();
	}

	public void appendToRight(DrawableString toAppend) {
		this.setText(text + toAppend.getText());
	}

	public float width() {
		return gl.width * bmFont.getScaleX();
	}

	public float height() {
		return gl.height * bmFont.getScaleY();
	}

	public float getY() {
		return y;
	}

	public float getX() {
		return x;
	}

	public Vector3 getPoint() {
		return new Vector3(x, y, 0);
	}

	public boolean otherObjectIsWithinRange(DrawableString other, int multipleOfTextHeight) {
		float threshold = multipleOfTextHeight * this.height();

		return Tools.rectangleCollisionForCenteredPoint(other.getPoint(), other.width(), other.height(), this.getPoint(), this.width(), this.height(), threshold);
	}

	public boolean touched(Vector3 touchPnt) {
		return Tools.pointWithinBounds(touchPnt.x, touchPnt.y, x - gl.width, x + gl.width, y - gl.height, y + gl.height);
	}

	public void highlight() {
		// bmFont.setColor(Color.YELLOW);
		bmFont = TextureLookup.yellowBMFont;
	}

	public void unhighlight() {
		// bmFont.setColor(Color.WHITE);
		bmFont = TextureLookup.whiteBMFont;
	}

	public void startAnimation() {
		if (!currentlyAnimating) {
			if (buildingString == null) {
				buildingString = new StringBuffer();
			}

			currentlyAnimating = true;
			buildingString.setLength(text.length());
			for (int i = 0; i < text.length(); ++i) {
				buildingString.setCharAt(i, ' ');
			}
			timeLastAnimated = System.currentTimeMillis();
			animCharacterIterator = 0;

		}
	}

	public void animateLogic() {
		// test if the class is in an animating state
		if (currentlyAnimating) {
			long currTime = System.currentTimeMillis();

			// test if there has been enough time to delay the animation
			if (currTime - delayAmountInMiliSec + variation > timeLastAnimated) {
				timeLastAnimated = currTime;
				buildingString.setCharAt(animCharacterIterator, text.charAt(animCharacterIterator));
				animCharacterIterator++;

				if (addVariationToDelay) {
					int halfDelay = (int) (delayAmountInMiliSec * 0.5);

					// add a maximum variation of 50% the total variation
					variation = rng.nextInt(halfDelay + 1);

					// shift so negative numbers are positive
					variation = variation - (halfDelay);
				}
			}

			// test if iterator has reached last character
			if (animCharacterIterator == text.length() - 1) {
				currentlyAnimating = false;
			}

		}
	}

	public boolean isAnimating() {
		return currentlyAnimating;
	}

	public void setAnimationDelayVariation(boolean value) {
		this.addVariationToDelay = value;
		if (!value) {
			variation = 0;
		}
	}
}
