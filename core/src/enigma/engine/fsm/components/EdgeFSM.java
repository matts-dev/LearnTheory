package enigma.engine.fsm.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class EdgeFSM extends ComponentFSM {
	protected NodeFSM source;
	protected NodeFSM sink;

	public EdgeFSM(NodeFSM source, NodeFSM sink) {
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
		//Note: this code will change when multiple lines are used
		float m = (sink.getY() - source.getY()) / (sink.getX() - source.getX());
		float b = -m*sink.getX() + sink.getY();
		float lineFuncResult = m * touchCoordinates.x + b;
		
		int epsilon = 20;
		float sourceX = source.getX();
		float sinkX = sink.getX();
		float lowerXBound = (sourceX < sinkX ? sourceX - epsilon : sinkX - epsilon);
		float upperXBound = (sourceX > sinkX ? sourceX + epsilon : sinkX + epsilon);

		//return if x and y are the same as the calculated value for y based on line formula
		//return x in range and if y is same as calculated result
		return Math.abs(lineFuncResult - touchCoordinates.y) < epsilon
				&& touchCoordinates.x >= lowerXBound
				&& touchCoordinates.x <= upperXBound;
	}

	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub

	}

}
