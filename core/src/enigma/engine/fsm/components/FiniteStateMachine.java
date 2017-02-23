package enigma.engine.fsm.components;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
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

	// Variables to track a node being held
	private NodeFSM holdingNode = null;
	private float holdingX = 0;
	private float holdingY = 0;

	private float devMoveSpeed = 10.0f;

	public FiniteStateMachine() {
	}

	/** Update any logic per frame */
	public void logic() {
		IO();
	}

	public void draw(SpriteBatch batch) {

		// draw solid filled circled (current state machine)
		render.begin(ShapeType.Filled);
		render.setColor(Color.YELLOW);

		render.setColor(Color.WHITE);
		render.end();

		// draw line based shapes (non-current state)
		render.begin(ShapeType.Line);
		for (ComponentFSM node : nodes) {
			node.draw(render, batch);
		}
		for (ComponentFSM edge : edges) {
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

	public void generateTestMachine() {
		nodes.add(new NodeFSM("", 100, 100, nodeSize));
		nodes.add(new NodeFSM("", 200, 200, nodeSize));
		edges.add(new EdgeFSM(nodes.get(0), nodes.get(1)));
	}

	public boolean touchDown(Vector3 touchCords, int pointer, int button) {
		// check if touch was within a node
		for (NodeFSM node : nodes) {
			if (node.isTouched(touchCords)) {
				// node touched so stage it to be moved
				holdingNode = node;

				// get relative position that the node was touched
				holdingX = holdingNode.getX() - touchCords.x;
				holdingY = holdingNode.getY() - touchCords.y;
				break;
			}
		}

		// If conditions are correct, and there is no holding node, then create a new node.
		if (Keybinds.pollModKey1() && button == Input.Buttons.LEFT && holdingNode == null) {
			addNode("", touchCords.x, touchCords.y);
		}

		return false;
	}

	public boolean touchUp(Vector3 touchCoords, int pointer, int button) {
		// release any node that is being held
		if (button == Input.Buttons.LEFT) holdingNode = null;

		return false;
	}

	public boolean touchDragged(Vector3 touchCoords, int pointer) {
		if (holdingNode != null) {
			holdingNode.setPosition(touchCoords.x + holdingX, touchCoords.y + holdingY);
		}
		return false;
	}
}
