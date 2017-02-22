package enigma.engine;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Keybinds {
	private static ArrayList<Integer> upKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> downKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> leftKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> rightKeys = new ArrayList<Integer>();
	private static int mod1Key;


	public static void generateKeybinds() {
		generateUpKeys();
		generateDownKeys();
		generateLeftKeys();
		generateRightKeys();
		mod1Key = Input.Keys.CONTROL_LEFT;
	}

	private static void generateRightKeys() {
		rightKeys.add(Input.Keys.RIGHT);
		rightKeys.add(Input.Keys.D);
	}

	private static void generateLeftKeys() {
		leftKeys.add(Input.Keys.LEFT);
		leftKeys.add(Input.Keys.A);
	}

	private static void generateDownKeys() {
		downKeys.add(Input.Keys.DOWN);
		downKeys.add(Input.Keys.S);
	}

	private static void generateUpKeys() {
		upKeys.add(Input.Keys.UP);
		upKeys.add(Input.Keys.W);
	}

	private static boolean pollKeyOnce(ArrayList<Integer> keys) {
		for (Integer key : keys) {
			if (Gdx.input.isKeyJustPressed(key)) {
				return true;
			}
		}
		return false;
	}

	private static boolean pollKey(ArrayList<Integer> keys) {
		for (Integer key : keys) {
			if (Gdx.input.isKeyPressed(key)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean pollUpKey(){
		return pollKey(upKeys);
	}
	
	public static boolean pollUpKeyOnce(){
		return pollKeyOnce(upKeys);
	}
	
	public static boolean pollDownKey(){
		return pollKey(downKeys);
	}
	
	public static boolean pollDownKeyOnce(){
		return pollKeyOnce(downKeys);
	}
	
	public static boolean pollRightKey(){
		return pollKey(rightKeys);
	}
	
	public static boolean pollLeftKeyOnce(){
		return pollKeyOnce(leftKeys);
	}
	
	public static boolean pollModKey1(){
		return Gdx.input.isKeyPressed(mod1Key);
	}
}
