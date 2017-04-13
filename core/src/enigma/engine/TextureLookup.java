package enigma.engine;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TextureLookup {

	private static ArrayList<Texture> allTextures = new ArrayList<Texture>();
	public static Texture kButton;
	public static Texture kButtonPressed;
	public static Texture buttonBlack;
	public static Texture buttonGrey;
	public static Texture lambdaTexture;

	public static BitmapFont whiteBMFont;
	public static BitmapFont yellowBMFont;
	public static BitmapFont redBMFont;
	public static BitmapFont blueBMFont;
	public static BitmapFont ignoreBmInversionWhite;
	
	public static ShapeRenderer shapeRenderer;
	public static String fontName = "prada.fnt";

	// Warning: be careful when using these colors; properly copy them without changing reference
	public static Color foregroundColor = Color.WHITE;
	public static Color backgroundColor = Color.BLACK;

	public static void initTextures() {
		// check if textures have already been initialized
		if (allTextures.size() > 0) {
			return;
		}
		
		createTextures();
		createBmFonts();

		// initialize the shape renderer
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(Color.WHITE);
	}
	
	private static void createTextures() {
		kButton = new Texture(Gdx.files.internal("KButton.png"));
		allTextures.add(kButton);

		kButtonPressed = new Texture(Gdx.files.internal("KButtonPressed.png"));
		allTextures.add(kButtonPressed);

		lambdaTexture = new Texture(Gdx.files.internal("Lambda.png"));
		allTextures.add(lambdaTexture);
		
		buttonBlack = new Texture(Gdx.files.internal("ButtonBlack.png"));
		allTextures.add(buttonBlack);
		
		buttonGrey = new Texture(Gdx.files.internal("ButtonGrey.png"));
		allTextures.add(buttonGrey);		
	}

	private static void createBmFonts(){
		// initialize the bit map font
		whiteBMFont = new BitmapFont(Gdx.files.internal(fontName));

		yellowBMFont = new BitmapFont(Gdx.files.internal(fontName));
		yellowBMFont.setColor(Color.YELLOW);
		
		redBMFont = new BitmapFont(Gdx.files.internal(fontName));
		redBMFont.setColor(Color.RED);
		
		blueBMFont = new BitmapFont(Gdx.files.internal(fontName));
		blueBMFont.setColor(Color.BLUE);
		
		ignoreBmInversionWhite = new BitmapFont(Gdx.files.internal(fontName));
		ignoreBmInversionWhite.setColor(Color.WHITE);
	}

	public static void dispose() {
		for (Texture tex : allTextures) {
			tex.dispose();
		}

		if (whiteBMFont != null) whiteBMFont.dispose();
		if (yellowBMFont != null) whiteBMFont.dispose();
		if (redBMFont != null) redBMFont.dispose();
		if (blueBMFont != null) blueBMFont.dispose();
		if (shapeRenderer != null) shapeRenderer.dispose();
	}

	public static void swapColorScheme() {
		Color temp = backgroundColor;
		backgroundColor = foregroundColor;
		foregroundColor = temp;
		swapWhiteFont();
	}
	
	private static boolean whiteFont = true;
	private static void swapWhiteFont(){
		if(whiteFont){
			whiteFont = false;
			whiteBMFont.setColor(Color.BLACK);			
		}else {
			whiteFont = true;
			whiteBMFont.setColor(Color.WHITE);
		}
	}
	
	
}
