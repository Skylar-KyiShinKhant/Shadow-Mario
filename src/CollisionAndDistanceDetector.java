/**
 * Class that handles the collision detection.
 */
public class CollisionAndDistanceDetector {

    /**
     * Checks for a collision between two game entities.
     *
     * @param x1 The x-coordinate of first entity
     * @param y1 The y-coordinate of first entity
     * @param radius1 The radius of first entity
     * @param x2 The x-coordinate of second entity
     * @param y2 The y-coordinate of second entity
     * @param radius2 The radius of second entity
     * @return True if a collision occurs, false otherwise.
     */
    public static boolean isCollided(int x1, int y1, double radius1, int x2, int y2, double radius2) {
        return distanceBetween(x1, y1, x2, y2) <= radius1 + radius2;
    }

    /**
     * Measures the distance between two game entities.
     *
     * @param x1 The x-coordinate of first entity
     * @param y1 The y-coordinate of first entity
     * @param x2 The x-coordinate of second entity
     * @param y2 The y-coordinate of second entity
     * @return The distance between two game entities.
     */
    public static double distanceBetween(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}