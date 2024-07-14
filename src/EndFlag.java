import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

/**
 * Class for the end flag entity.
 */
public class EndFlag extends GameEntity implements Movable {
    private final double RADIUS;
    private final int SPEED_X;
    private boolean isCollided = false;

    /**
     * Constructor for the EndFlag class.
     * @param x The x-coordinate of the end flag.
     * @param y The y-coordinate of the end flag.
     * @param props The properties containing end flag details.
     */
    public EndFlag(int x, int y, Properties props) {
        super(x, y, new Image(props.getProperty("gameObjects.endFlag.image")));
        this.RADIUS = Double.parseDouble(props.getProperty("gameObjects.endFlag.radius"));
        this.SPEED_X = Integer.parseInt(props.getProperty("gameObjects.endFlag.speed"));
    }

    /**
     * Updates the movement of the end flag while checking collision.
     * @param input  The input object used for movement.
     * @param target The player object used for collision detection.
     */
    public void updateWithTarget(Input input, Target target) {
        move(input);
        super.getImage().draw(super.getX(), super.getY());

        if (CollisionAndDistanceDetector.isCollided(target.getX(), target.getY(), target.getRADIUS(),
                super.getX(), super.getY(), this.RADIUS) &&
                !isCollided && ((Player)target).isKilledEnemyBoss()) {
            isCollided = true;
        }
    }

    /**
     * Moves the end flag based on the player's input.
     * @param input The input object used for movement.
     */
    public void move(Input input) {
        if (input.isDown(Keys.RIGHT)){
            super.setX(super.getX() - SPEED_X);
        } else if (input.isDown(Keys.LEFT)){
            super.setX(super.getX() + SPEED_X);
        }
    }

    // Getters for end flag properties
    /**
     * Check if the end flag is collided.
     * @return True if the end flag is collided, otherwise false.
     */
    public boolean isCollided() {
        return isCollided;
    }
}