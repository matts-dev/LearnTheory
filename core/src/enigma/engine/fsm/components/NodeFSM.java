package enigma.engine.fsm.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class NodeFSM extends ComponentFSM {
	private Circle node;

	public NodeFSM(String text, float x, float y, float radius) {
		node = new Circle(x, y, radius);
	}

	/* (non-Javadoc) Render must be started. */
	@Override
	public void draw(ShapeRenderer render, SpriteBatch batch) {
		if (render.isDrawing()) {
			render.circle(node.x, node.y, node.radius);
		}
	}

	@Override
	public void translate(float x, float y) {
		node.set(this.node.x + x, this.node.y + y, this.node.radius);
	}

	@Override
	public boolean isTouched(Vector3 touchCoordinates) {
		return node.contains(touchCoordinates.x, touchCoordinates.y);
	}

	@Override
	public void setPosition(float x, float y) {
		node.setPosition(x, y);
	}

	public float getX() {
		return node.x;
	}

	public float getY() {
		return node.y;
	}

	public float getRadius() {
		return node.radius;
	}
}
