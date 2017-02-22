package enigma.engine.fsm.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public abstract class ComponentFSM {
	public abstract void draw(ShapeRenderer render, SpriteBatch batch);
	public abstract void translate(float x, float y);
	public abstract boolean isTouched(Vector3 touchCoordinates);
}
