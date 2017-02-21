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
	public static Texture lambdaTexture;
	
	public static BitmapFont whiteBMFont;
	public static BitmapFont yellowBMFont;
	public static ShapeRenderer shapeRenderer;
	public static String fontName = "prada.fnt";
	
	public static void initTextures(){
		//check if textures have already been initialized 
		if(allTextures.size() > 0){
			return;
		}
		
		kButton = new Texture(Gdx.files.internal("KButton.png"));
		allTextures.add(kButton);
		
		kButtonPressed = new Texture(Gdx.files.internal("KButtonPressed.png"));
		allTextures.add(kButtonPressed);
		
		lambdaTexture = new Texture(Gdx.files.internal("Lambda.png"));
		allTextures.add(lambdaTexture);
		
		//initialize the bit map font
		whiteBMFont = new BitmapFont(Gdx.files.internal(fontName));
		
		yellowBMFont = new BitmapFont(Gdx.files.internal(fontName));
		yellowBMFont.setColor(Color.YELLOW);
		
		//initialize the shape renderer
		shapeRenderer = new ShapeRenderer();
	}
	
	
	public static void dispose(){
		for(Texture tex : allTextures){
			tex.dispose();
		}
	
		if(whiteBMFont != null) whiteBMFont.dispose();
		if(yellowBMFont != null) whiteBMFont.dispose();
		if(shapeRenderer != null) shapeRenderer.dispose();
	}
}
