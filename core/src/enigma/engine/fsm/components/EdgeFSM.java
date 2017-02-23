package enigma.engine.fsm.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class EdgeFSM extends ComponentFSM {
	NodeFSM source;
	NodeFSM sink;
	ArrowFSM arrow;

	EdgeFSM(NodeFSM source, NodeFSM sink) {
		this.source = source;
		this.sink = sink;
	}

	/* (non-Javadoc) Render must be started. */
	@Override
	public void draw(ShapeRenderer render, SpriteBatch batch) {
		if (render.isDrawing()) {
			render.line(source.getX(), source.getY(), sink.getX(), sink.getY());
			ArrowFSM.drawArrow(render, source.getX(), source.getY(), sink);
		}
	}

	@Override
	public void translate(float x, float y) {

	}

	@Override
	public boolean isTouched(Vector3 touchCoordinates) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub

	}

}
