package enigma.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * @author Matt Stone
 * 
 *         A utility class for common shared functions.
 */
public class Tools {
	/**
	 * Converts a vector of screen coordinates to game world coordinates if the camera has been
	 * properly set up.
	 * 
	 * @param camera
	 *            the camera responsible for un-projection of points.
	 * @param storage
	 *            the vector to contain the final converted points.
	 */
	public static void convertMousePointsIntoGameCoordinates(OrthographicCamera camera, Vector3 storage) {
		float rawX = Gdx.input.getX();
		float rawY = Gdx.input.getY();
		camera.unproject(storage.set(rawX, rawY, 0));
	}

	/**
	 * This method returns whether an (x, y) coordinate pair is within a rectangular range defined
	 * by the last four parameters. Follows a standard Cartesian system where the direction of y is
	 * up.
	 * 
	 * @param x
	 * @param y
	 * @param minX
	 * @param maxX
	 * @param minY
	 * @param maxY
	 * @return
	 */
	public static boolean pointWithinBounds(float x, float y, float minX, float maxX, float minY, float maxY) {
		return x > minX && x < maxX && y > minY && y < maxY;
	}

	public static boolean rectangleCollisionForCenteredPoint(Vector3 pnt1, float pnt1Wid, float pnt1Hgt, Vector3 pnt2, float pnt2Wid, float pnt2Hgt, float threshold) {
		//calculate values needed to determine objects four corners (TopLeft = (objMinX, objMaxY), TR = (objMaxX, objMaxY), BL, BR)
		float objMaxY = pnt1.y + pnt1Hgt / 2;
		float objMinY = pnt1.y - pnt1Hgt / 2;
		float objMaxX = pnt1.x + pnt1Wid / 2;
		float objMinX = pnt1.x - pnt1Hgt / 2;

		// create bounds (get center coordinate, modify by half size, then add tolerance)
		float maxY = pnt2.y + (pnt2Hgt / 2) + threshold;
		float minY = pnt2.y - (pnt2Hgt / 2) - threshold;
		float maxX = pnt2.x + (pnt2Wid / 2) + threshold;
		float minX = pnt2.x - (pnt2Wid / 2) - threshold;

		// check four corner points (TODO: this is wasteful, change so that it only calculates points if previous calculation failed?)
		boolean bottomLeftCollides = Tools.pointWithinBounds(objMinX, objMinY, minX, maxX, minY, maxY);
		boolean bottomRightCollides = Tools.pointWithinBounds(objMaxX, objMinY, minX, maxX, minY, maxY);
		boolean topLeftCollides = Tools.pointWithinBounds(objMinX, objMaxY, minX, maxX, minY, maxY);
		boolean topRightCollides = Tools.pointWithinBounds(objMaxX, objMaxY, minX, maxX, minY, maxY);

		// determine if none of the points collided
		return bottomLeftCollides || bottomRightCollides || topLeftCollides || topRightCollides;
	}
}
