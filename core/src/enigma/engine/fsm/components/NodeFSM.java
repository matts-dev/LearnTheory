package enigma.engine.fsm.components;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class NodeFSM extends ComponentFSM {
	private Circle node;
	private HashMap<Integer, EdgeFSM> edgesOut = new HashMap<Integer, EdgeFSM>();
	private HashMap<Integer, EdgeFSM> inwardEdges = new HashMap<Integer, EdgeFSM>();


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

	public void addOutEdge(EdgeFSM edgeOutward) {
		edgesOut.put(edgeOutward.hashCode(), edgeOutward);
	}

	public void addInEdge(EdgeFSM inwardEdge) {
		inwardEdges.put(inwardEdges.hashCode(), inwardEdge);
	}
	
	public void removeEdge(EdgeFSM removeEdge){
		//search and remove outward edge
		edgesOut.remove(removeEdge.hashCode());
		
		//scan inward edges
		inwardEdges.remove(removeEdge.hashCode());
	}
	
	public void play(){

	}

	public ArrayList<EdgeFSM> getAllEdges() {
		ArrayList<EdgeFSM> ret = new ArrayList<EdgeFSM>(10);
		for(EdgeFSM edge : inwardEdges.values()){
			ret.add(edge);
		}
		for(EdgeFSM edge : edgesOut.values()){
			ret.add(edge);
		}
		return ret;
	}
	
}
