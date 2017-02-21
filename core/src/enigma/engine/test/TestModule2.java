package enigma.engine.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enigma.engine.CourseModule;

public class TestModule2 extends CourseModule {
	BitmapFont bmFont;
	String exampleText = "TestModule2";
	GlyphLayout Glayout4TxtSize;
	
	public TestModule2(OrthographicCamera camera){
		super(camera);
		bmFont = new BitmapFont(Gdx.files.internal("prada.fnt"));
		Glayout4TxtSize = new GlyphLayout();
		Glayout4TxtSize.setText(bmFont, exampleText);
	}
	
	@Override
	public void draw(SpriteBatch batch){
		//draw test text
		bmFont.draw(batch, exampleText,
		Gdx.graphics.getWidth()/ 2.0f - Glayout4TxtSize.width / 2,
		Gdx.graphics.getHeight() / 2.0f + Glayout4TxtSize.height / 2);
	}
	
	@Override
	public void dispose(){
		bmFont.dispose();
	}

	@Override
	public void logic() {
	}
	
}
