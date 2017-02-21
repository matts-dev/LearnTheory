package enigma.engine;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Keybinds {
	private ArrayList<Integer> upKeys = new ArrayList<Integer>();
	private ArrayList<Integer> downKeys = new ArrayList<Integer>();
	private ArrayList<Integer> leftKeys = new ArrayList<Integer>();
	private ArrayList<Integer> rightKeys = new ArrayList<Integer>();

	public Keybinds() {
		generateUpKeys();
		generateDownKeys();
		generateLeftKeys();
		generateRightKeys();
	}

	private void generateRightKeys() {
		rightKeys.add(Input.Keys.RIGHT);
		rightKeys.add(Input.Keys.D);
	}

	private void generateLeftKeys() {
		leftKeys.add(Input.Keys.LEFT);
		leftKeys.add(Input.Keys.A);
	}

	private void generateDownKeys() {
		downKeys.add(Input.Keys.DOWN);
		downKeys.add(Input.Keys.S);
	}

	private void generateUpKeys() {
		upKeys.add(Input.Keys.UP);
		upKeys.add(Input.Keys.W);
	}

	private boolean pollKeyOnce(ArrayList<Integer> keys) {
		for (Integer key : keys) {
			if (Gdx.input.isKeyJustPressed(key)) {
				return true;
			}
		}
		return false;
	}

	private boolean pollKey(ArrayList<Integer> keys) {
		for (Integer key : keys) {
			if (Gdx.input.isKeyPressed(key)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean pollUpKey(){
		return pollKey(upKeys);
	}
	
	public boolean pollUpKeyOnce(){
		return pollKeyOnce(upKeys);
	}
	
	public boolean pollDownKey(){
		return pollKey(downKeys);
	}
	
	public boolean pollDownKeyOnce(){
		return pollKeyOnce(downKeys);
	}
	
	public boolean pollRightKey(){
		return pollKey(rightKeys);
	}
	
	public boolean pollLeftKeyOnce(){
		return pollKeyOnce(leftKeys);
	}
}
