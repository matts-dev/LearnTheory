package enigma.engine.fsm.components;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

import enigma.engine.DeveloperMode;
import enigma.engine.Keybinds;
import enigma.engine.TextureLookup;

public class FiniteStateMachine {
	private ShapeRenderer render = TextureLookup.shapeRenderer;
	private ArrayList<NodeFSM> nodes = new ArrayList<NodeFSM>();
	private ArrayList<EdgeFSM> edges = new ArrayList<EdgeFSM>();
	private float nodeSize = 25;
	
	private NodeFSM holdingNode = null;

	private float devMoveSpeed = 10.0f;

	public FiniteStateMachine() {
	}

	/** Update any logic per frame */
	public void logic() {
		IO();
	}

	public void draw(SpriteBatch batch) {
		// draw solid filled circled
		render.begin(ShapeType.Filled);
		for (ComponentFSM node : nodes) {
			node.draw(render, batch);
		}
		render.end();

		// draw line based
		render.begin(ShapeType.Line);
		for (ComponentFSM edge : nodes) {
			edge.draw(render, batch);
		}
		render.end();
	}

	public void translate(float x, float y) {
		//@formatter:off
		for(ComponentFSM node : nodes){	node.translate(x, y); }
		for(ComponentFSM edge : edges){	edge.translate(x, y); }
		//@formatter:on
	}

	public void addNode(String text, float x, float y) {
		nodes.add(new NodeFSM(text, x, y, nodeSize));
	}

	public void IO() {		
		// Developer control
		if (DeveloperMode.developerModeEnabled) {
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				this.translate(0, devMoveSpeed);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				this.translate(0, -devMoveSpeed);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				this.translate(-devMoveSpeed, 0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				this.translate(devMoveSpeed, 0);
			}
		}
	}
	

	public boolean touchDown(Vector3 touchCords, int pointer, int button) {
		//check if touch was within a node
		for(NodeFSM node : nodes){
			if(node.isTouched(touchCords)){
				//TODO start here and implement a touch and move 
			}
		}
		
		if(Keybinds.pollModKey1() && button == Input.Buttons.LEFT){
			addNode("", touchCords.x, touchCords.y);
		}

		return false;
	}

	public boolean touchUp(Vector3 touchCoords, int pointer, int button) {

		return false;
	}

	public boolean touchDragged(Vector3 touchCoords, int pointer) {

		return false;
	}
}
