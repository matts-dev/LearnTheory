package enigma.engine;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class CourseModule {
	protected ArrayList<CourseModule> subModules = new ArrayList<CourseModule>();
	protected int moduleIter = 0;
	protected CourseModule currentSubModule;
	protected OrthographicCamera camera;
	
	public CourseModule(OrthographicCamera camera){
		this.camera = camera;
	}

	public void draw(SpriteBatch batch) {
		if (currentSubModule != null) {
			currentSubModule.draw(batch);
		}
	}

	public void dispose() {
		for (CourseModule module : subModules) {
			if (module != null) {
				module.dispose();
			}
		}
	}

	public void loadCurrentModule() {
		if (subModules.size() > 0) {
			currentSubModule = subModules.get(moduleIter);
		}
	}

	public void nextModule() {
		// prevent going passed last module
		if (moduleIter == subModules.size() - 1) {
			return;
		}
		// currentSubModule.dispose();

		// moduleIter = (moduleIter + 1) % subModules.size();
		moduleIter += 1;
		currentSubModule = subModules.get(moduleIter);
		currentSubModule.loadCurrentModule();
	}

	public void previousModule() {
		// prevent going before first module
		if (moduleIter == 0) {
			return;
		}

		// currentSubModule.dispose();
		// moduleIter = ((moduleIter - 1) + subModules.size()) % subModules.size();
		moduleIter -= 1;
		currentSubModule = subModules.get(moduleIter);
		currentSubModule.loadCurrentModule();
	}

	public void IO() {
		if (currentSubModule != null) {
			currentSubModule.IO();
		}
	}

	/**
	 * To be regarded as the main game loop for everything other than rendering and IO.
	 */
	public void logic(){
		if(currentSubModule != null){
			currentSubModule.logic();
		}
	};
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(currentSubModule != null){
			return currentSubModule.touchDown(screenX, screenY, pointer, button);
		}
		return false;
	}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(currentSubModule != null){
			return currentSubModule.touchUp(screenX, screenY, pointer, button);
		}
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(currentSubModule != null){
			return currentSubModule.touchDragged(screenX, screenY, pointer);
		}
		return false;
	}
}
