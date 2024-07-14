import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

/**
 * Class for the coin entity.
 */
public class Coin extends GameEntity implements Movable {
    private final double RADIUS;
    private final int SPEED_X;
    private final int VALUE;
    private final int COLLISION_SPEED = -10;
    private int speedY = 0;
    private boolean isCollided = false;

    /**
     * Constructs a Coin object.
     *
     * @param x     The initial x-coordinate of the coin.
     * @param y     The initial y-coordinate of the coin.
     * @param props The properties containing configuration values.
     * @param key   The key used to retrieve specific properties for the coin.
     */
    public Coin(int x, int y, Properties props, String key) {
        super(x, y, new Image(props.getProperty("gameObjects.coin.image")));
        this.RADIUS = Double.parseDouble(props.getProperty("gameObjects.coin.radius"));
        this.VALUE = Integer.parseInt(props.getProperty("gameObjects.coin.value"));
        this.SPEED_X = Integer.parseInt(props.getProperty("gameObjects.coin.speed"));
    }

    /**
     * Updates the movement, state of the coin while checking collision.
     * @param input  The input object used for movement.
     * @param player The player object used for collision detection.
     * @return The value of the coin when collected, or 0 if not collected.
     */
    public int updateWithPlayer(Input input, Player player) {
        updateWithTarget(input, null);
        if (CollisionAndDistanceDetector.isCollided(player.getX(), player.getY(), player.getRADIUS(),
                super.getX(), super.getY(), this.RADIUS) && !isCollided) {
            isCollided = true;
            speedY = COLLISION_SPEED;
            return player.isEnableDoubleScore() ? VALUE * 2: VALUE;
        }
        return 0;
    }

    /**
     * Moves the coin based on the player's input.
     * @param input The input object used for movement.
     */
    public void move(Input input) {
        if (input.isDown(Keys.RIGHT)){
            super.setX(super.getX() - SPEED_X);
        } else if (input.isDown(Keys.LEFT)){
            super.setX(super.getX() + SPEED_X);
        }
        super.setY(super.getY() + speedY);
    }

    /**
     * Updates the movement and draw the coin.
     * @param input  The input object used for movement.
     * @param target The player object.
     */
    @Override
    public void updateWithTarget(Input input, Target target) {
        move(input);
        super.getImage().draw(super.getX(), super.getY());
    }
}