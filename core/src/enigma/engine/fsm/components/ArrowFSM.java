package enigma.engine.fsm.components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ArrowFSM {
	/** Utility class - no instance creation allowed*/
	private ArrowFSM() {
	};

	public static void drawArrow(ShapeRenderer render, float srcX, float srcY, NodeFSM sink) {
		if (render.isDrawing()) {
			//angle properties of right triangle; theta = 180 - 90 - knownAngle
			double compAngle = Math.PI / 2 - ArrowFSM.calculateAngle(srcX, srcY, sink.getX(), sink.getY());
			
			//calculate point of arrow using the complementary angle
			double x = sink.getRadius() * Math.sin(compAngle);
			double y = sink.getRadius() * Math.cos(compAngle);
			
			//below is test code, it is so pretty that I thought I would leave it in.
			render.circle( sink.getX() - (float)x, sink.getY() - (float)y, 5);

		}
	}

	public static double calculateAngle(float srcX, float srcY, float destX, float destY) {
		double angle = (Math.atan((destY - srcY) / (destX - srcX)));
		if (destX - srcX <= 0) {
			// provides correction for any angle calculated where x < 0
			return angle + Math.PI;
		} else {
			// x > 0; modulus correction for angle where y < 0; (angle+360)%360
			return (angle + (2 * Math.PI)) % (2 * Math.PI);
		}

	}

}
