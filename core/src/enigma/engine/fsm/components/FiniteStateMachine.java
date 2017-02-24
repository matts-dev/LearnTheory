package enigma.engine.fsm.components;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

import enigma.engine.DeveloperMode;
import enigma.engine.Keybinds;
import enigma.engine.Point;
import enigma.engine.TextureLookup;

public class FiniteStateMachine {
	private ShapeRenderer render = TextureLookup.shapeRenderer;
	private HashMap<Integer, NodeFSM> nodes = new HashMap<Integer, NodeFSM>();
	private HashMap<Integer, EdgeFSM> edges = new HashMap<Integer, EdgeFSM>();
	private float nodeSize = 25;
	private Color fgColor = new Color(TextureLookup.foregroundColor);
	private Color bgColor = new Color(TextureLookup.backgroundColor);

	// Variables to track a node being held
	private NodeFSM holdingNode = null;
	private float holdingX = 0;
	private float holdingY = 0;

	// Variables for edge creation
	private boolean drawTempEdge = false;
	private Point tempEdgePnt1 = new Point(0, 0);
	private Point tempEdgePnt2 = new Point(0, 0);

	// Edge/Node selection
	private ComponentFSM selected = null;
	private ComponentFSM stagedForSelect = null;
	private Color selectColor = Color.RED;
	private float selectDownX = 0;
	private float selectDownY = 0;

	// Highlight current state
	// private ComponentFSM currentState = null;

	private float devMoveSpeed = 10.0f;

	public FiniteStateMachine() {
	}

	/** Update any logic per frame */
	public void logic() {
		IO();
	}

	public void draw(SpriteBatch batch) {
		fgColor.set(TextureLookup.foregroundColor);
		bgColor.set(TextureLookup.backgroundColor);
		drawEdges(batch);
		drawNodes(batch);

	}

	private void drawEdges(SpriteBatch batch) {
		// Draw Edges
		render.begin(ShapeType.Line);
		for (ComponentFSM edge : edges.values()) {
			// draw selected edges differently
			if (edge != selected) {
				edge.draw(render, batch);
			}
		}

		// Draw the temporary edge that the user is creating with mouse click
		if (drawTempEdge) {
			render.line(tempEdgePnt1.x, tempEdgePnt1.y, tempEdgePnt2.x, tempEdgePnt2.y);
		}
		render.end();

		// color the selected edge
		if (selected != null && selected instanceof EdgeFSM) {
			render.setColor(selectColor);
			render.begin(ShapeType.Line);
			selected.draw(render, batch);
			render.end();
			render.setColor(fgColor);
		}

	}

	private void drawNodes(SpriteBatch batch) {
		render.begin(ShapeType.Filled);
		render.setColor(bgColor);
		for (NodeFSM node : nodes.values()) {
			node.draw(render, batch);
		}
		render.end();
		render.setColor(fgColor);

		// Draw node outlines
		render.begin(ShapeType.Line);
		for (NodeFSM node : nodes.values()) {
			if (node != selected) {
				node.draw(render, batch);
			}
		}
		render.end();

		if (selected instanceof NodeFSM) {
			render.setColor(selectColor);
			render.begin(ShapeType.Line);
			selected.draw(render, batch);
			render.end();
			render.setColor(fgColor);
		}
	}

	public void translate(float x, float y) {
		//@formatter:off
		for(ComponentFSM node : nodes.values()){	node.translate(x, y); }
		for(ComponentFSM edge : edges.values()){	edge.translate(x, y); }
		//@formatter:on
	}

	public void addNode(String text, float x, float y) {
		NodeFSM toAdd = new NodeFSM(text, x, y, nodeSize);
		nodes.put(toAdd.hashCode(), toAdd);
	}

	public void IO() {
		if (Keybinds.pollRemoveKeyOnce() || Gdx.input.isKeyJustPressed(Input.Keys.DEL) || Gdx.input.isKeyJustPressed(Input.Keys.FORWARD_DEL)) {
			if (selected != null) {
				if (selected instanceof EdgeFSM) {
					removeEdge((EdgeFSM) selected);
				} else if (selected instanceof NodeFSM){
					removeNode((NodeFSM) selected);
					
				}
				selected = null;
			}
		}

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

	private void removeNode(NodeFSM node) {
		ArrayList<EdgeFSM> allEdges = node.getAllEdges();
		for(EdgeFSM edge : allEdges){
			removeEdge(edge);
		}
		nodes.remove(node.hashCode());
	}

	private void removeEdge(EdgeFSM edgeToRemove) {
		NodeFSM source = edgeToRemove.source;
		NodeFSM sink = edgeToRemove.sink;

		source.removeEdge(edgeToRemove);
		sink.removeEdge(edgeToRemove);
		edges.remove(edgeToRemove.hashCode());
	}

	public void generateTestMachine() {
		NodeFSM toAdd1 = new NodeFSM("", 100, 100, nodeSize);
		NodeFSM toAdd2 = new NodeFSM("", 200, 200, nodeSize);

		nodes.put(toAdd1.hashCode(), toAdd1);
		nodes.put(toAdd2.hashCode(), toAdd2);

		// edges.add(new EdgeFSM(nodes.get(0), nodes.get(1)));
		EdgeFSM edge1 = new EdgeFSM(toAdd1, toAdd2);
		edges.put(edge1.hashCode(), edge1);
		
		toAdd1.addOutEdge(edge1);
		toAdd2.addOutEdge(edge1);

	}

	/**
	 * This method is called during the touchUp event. It checks to see if the mouse is on a node.
	 * 
	 * @param touchCoords the coordiantes of the ending touch.
	 */
	private void attemptEdgeCreation(Vector3 touchCoords) {
		for (NodeFSM node : nodes.values()) {
			if (node.isTouched(touchCoords)) {
				createEdgeBetween(holdingNode, node);
				break;
			}
		}

	}

	private void createEdgeBetween(NodeFSM source, NodeFSM sink) {
		EdgeFSM newEdge = new EdgeFSM(source, sink);
		source.addOutEdge(newEdge);
		sink.addInEdge(newEdge);
		edges.put(newEdge.hashCode(), newEdge);
	}

	public boolean touchDown(Vector3 touchCords, int pointer, int button) {
		// check if touch was within a node
		for (NodeFSM node : nodes.values()) {
			if (node.isTouched(touchCords)) {
				// node touched so stage it to be moved or drawn from
				holdingNode = node;
				// user is holding mod key, probably to draw an edge; release control
				if (Keybinds.pollModKey1()) {
					break;
				} else {
					stagedForSelect = node;
					selectDownX = touchCords.x;
					selectDownY = touchCords.y;

					
					// get relative position that the node was touched
					holdingX = holdingNode.getX() - touchCords.x;
					holdingY = holdingNode.getY() - touchCords.y;
					return false;
				}
			}
		}

		// If no node was selected, see if user tried to select an edge
		if (holdingNode == null) {
			for (EdgeFSM edge : edges.values()) {
				if (edge.isTouched(touchCords)) {
					// test if user is de-selecting an edge
					if (selected == edge) {
						selected = null;
						return false;
					}
					selected = edge;
					return false;
				}
			}
		}

		// If conditions are correct, and there is no holding node, then create a new node.
		if (Keybinds.pollModKey1() && button == Input.Buttons.LEFT && holdingNode == null) {
			addNode("", touchCords.x, touchCords.y);
		}

		return false;
	}

	public boolean touchDragged(Vector3 touchCoords, int pointer) {
		if (holdingNode != null) {
			if (Keybinds.pollModKey1()) {
				drawTempEdge = true;
				tempEdgePnt1.x = holdingNode.getX();
				tempEdgePnt1.y = holdingNode.getY();
				tempEdgePnt2.x = touchCoords.x;
				tempEdgePnt2.y = touchCoords.y;
			} else if (!drawTempEdge) {
				holdingNode.setPosition(touchCoords.x + holdingX, touchCoords.y + holdingY);
			}
		}
		return false;
	}

	public final float epsilon = 5;

	public boolean touchUp(Vector3 touchCoords, int pointer, int button) {
		// release any node that is being held
		if (button == Input.Buttons.LEFT) {
			if (stagedForSelect != null && Math.abs(touchCoords.x - selectDownX) < epsilon && Math.abs(touchCoords.y - selectDownY) < epsilon) {
				if(selected == stagedForSelect){
					selected = null;
				} else {
					selected = stagedForSelect;
				}
			}

			// check for edge creation
			if (drawTempEdge) {
				attemptEdgeCreation(touchCoords);
			}

			holdingNode = null;
			stagedForSelect = null;
			drawTempEdge = false;
		}

		return false;
	}
}
