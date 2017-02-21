package enigma.engine;

import com.badlogic.gdx.graphics.OrthographicCamera;

import enigma.engine.regex.MasterRegularExpressionModule;

public class TheoryGameMainModule extends CourseModule {
	public TheoryGameMainModule(OrthographicCamera camera) {
		super(camera);

		// add all suported modules
		// subModules.add(new MasterFSMModule(camera));
		subModules.add(new MasterRegularExpressionModule(camera));

		// load the first module
		loadCurrentModule();
	}

	@Override
	public void logic() {
		super.logic();
	}
}
