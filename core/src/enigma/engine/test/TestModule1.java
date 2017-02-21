package enigma.engine.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enigma.engine.CourseModule;
import enigma.engine.DrawableString;

public class TestModule1 extends CourseModule {
	BitmapFont bmFont;
	String exampleText = "TEST MODULE 1";
	GlyphLayout Glayout4TxtSize;
	
	DrawableString testStr = new DrawableString("test module 1");
	
	public TestModule1(OrthographicCamera camera){
		super(camera);
		bmFont = new BitmapFont(Gdx.files.internal("prada.fnt"));
		Glayout4TxtSize = new GlyphLayout();
		Glayout4TxtSize.setText(bmFont, exampleText);
		
		testStr.setXY(Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.25f);
	}
	
	@Override
	public void draw(SpriteBatch batch){
		//draw test text
		bmFont.draw(batch, exampleText,
		Gdx.graphics.getWidth()/ 2.0f - Glayout4TxtSize.width / 2,
		Gdx.graphics.getHeight() / 2.0f + Glayout4TxtSize.height / 2);
		
		if (testStr != null){
			testStr.draw(batch);
		}
		
	}
	
	@Override
	public void dispose(){
		bmFont.dispose();
		testStr.dispose();
	}

	@Override
	public void logic() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
			testStr.startAnimation();
		}
		
		testStr.animateLogic();
	}
	
}
