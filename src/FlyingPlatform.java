import bagel.Input;
import bagel.Keys;

import java.util.Properties;
import java.util.Random;

/**
 * Class for the flying platform entity in the game.
 */
public class FlyingPlatform extends Platform implements RandomlyMovable {
    private final int HALF_HEIGHT;
    private final int HALF_LENGTH;
    private final int MAX_DISPLACEMENT_X;
    private final int RANDOM_SPEED;
    private boolean moveLeft;
    private int displacement = 0;
    private boolean onThisPlatform = false;

    /**
     * Constructs a FlyingPlatform object.
     * @param x The x-coordinate of the platform.
     * @param y The y-coordinate of the platform.
     * @param props The properties containing platform details.
     * @param key The key associated with the platform.
     */
    public FlyingPlatform(int x, int y, Properties props, String key) {
        super(x, y, props, key);
        this.HALF_HEIGHT = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.halfHeight"));
        this.HALF_LENGTH = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.halfLength"));
        this.MAX_DISPLACEMENT_X = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX"));
        this.RANDOM_SPEED = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.randomSpeed"));
        // Initialize random movement direction
        Random random = new Random();
        this.moveLeft = random.nextBoolean();
    }

    /**
     * Updates the flying platform's movement and draws it.
     * Allows the player to jump onto it.
     * @param input The input object.
     * @param target The player object.
     */
    @Override
    public void updateWithTarget(Input input, Target target) {
        move(input);
        jumpOntoFlyingPlatform((Player)target);
        moveRandomly();
        super.getImage().draw(super.getX(), super.getY());
    }

    /**
     * Moves the flyingPlatform based on the player's input.
     * @param input The input object used for movement.
     */
    @Override
    public void move(Input input) {
        if (input.isDown(Keys.RIGHT)){
            super.setX(super.getX() - super.getSPEED_X());
        } else if (input.isDown(Keys.LEFT)){
            super.setX(super.getX() + super.getSPEED_X());
        }
    }

    /**
     * Moves the flying platform randomly within a set range.
     */
    @Override
    public void moveRandomly() {
        // Random movement within a range of 50 pixels
        if (displacement >= MAX_DISPLACEMENT_X) {
            // Reverse direction
            moveLeft = !moveLeft;
            displacement = 0;
        }

        if (moveLeft) {
            super.setX(super.getX() - RANDOM_SPEED);
        } else {
            super.setX(super.getX() + RANDOM_SPEED);
        }

        displacement++;
    }

    /**
     * Manages player landing onto the flying platform.
     * @param player The player object.
     */
    public void jumpOntoFlyingPlatform(Player player) {
        if (player.getY() < super.getY() && // Stop player from falling onto lower flying platforms
            Math.abs(player.getX() - super.getX()) < HALF_LENGTH &&
            Math.abs(player.getY() - super.getY()) <= HALF_HEIGHT &&
            Math.abs(player.getY() - super.getY()) >= HALF_HEIGHT - 1) {
            // To stop player's vertical movement when it lands on the flying platform
            player.setCurrPlatformY(player.getY());
            onThisPlatform = true;
        } else {
            // Jumping off from the flying platform to the ground
            if (onThisPlatform) {
                player.setCurrPlatformY(player.getINITIAL_Y());
                onThisPlatform = false;
            }
        }
    }
}
