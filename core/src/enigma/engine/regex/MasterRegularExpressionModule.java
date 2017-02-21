package enigma.engine.regex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

import enigma.engine.test.TestModule1;
import enigma.engine.test.TestModule2;

public class MasterRegularExpressionModule extends enigma.engine.CourseModule {
	public MasterRegularExpressionModule(OrthographicCamera camera){
		super(camera);
		subModules.add(new RegularExpressionIntroModule(camera));
		subModules.add(new TestModule1(camera));
		subModules.add(new TestModule2(camera));
		loadCurrentModule();
	}
	
	@Override
	public void IO(){
		if(currentSubModule != null){
			currentSubModule.IO();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.N)){
			nextModule();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
			previousModule();
		}
	}

	@Override
	public void logic() {
		super.logic();
	}
}
