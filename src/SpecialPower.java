import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

/**
 * Class representing a special power entity in the game.
 */
public abstract class SpecialPower extends GameEntity implements Movable{
    private final int MAX_FRAMES;
    private final double RADIUS;
    private final int SPEED_X;
    private final int COLLISION_SPEED = -10;
    private int speedY = 0;
    private boolean isCollided = false;
    private int activeFrameLeft = 0;

    /**
     * Constructs a special power object.
     * @param x The x-coordinate of the special power.
     * @param y The y-coordinate of the special power.
     * @param props The properties containing game object settings.
     * @param key The key to retrieve specific properties for the special power.
     */
    public SpecialPower(int x, int y, Properties props, String key) {
        super(x, y, new Image(props.getProperty("gameObjects." + key + ".image")));
        this.RADIUS = Double.parseDouble(props.getProperty("gameObjects." + key + ".radius"));
        this.SPEED_X = Integer.parseInt(props.getProperty("gameObjects." + key + ".speed"));
        this.MAX_FRAMES = Integer.parseInt(props.getProperty("gameObjects." + key + ".maxFrames"));
    }

    /**
     * Updates the movement and state of the coin.
     * @param input  The input object used for movement.
     * @param target The player object used for collision detection.
     */
    public void updateWithTarget(Input input, Target target) {
        move(input);
        super.getImage().draw(super.getX(), super.getY());

        if (CollisionAndDistanceDetector.isCollided(target.getX(), target.getY(), target.getRADIUS(),
                super.getX(), super.getY(), this.RADIUS) && !isCollided) {
            isCollided = true;
            speedY = COLLISION_SPEED;
            activatePower();
            collect((Player)target);
        }
        disablePower((Player)target);
    }

    /**
     * Moves the special power based on the player's input.
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

    /***
     * Activates the special power when it is collected.
     * @param player The player entity who collects the special power.
     */
    public abstract void collect(Player player);

    /***
     * Deactivates the special power after a certain period.
     * @param player The player entity who collects the special power.
     */
    public abstract void disablePower(Player player);

    /**
     * Activates the special power.
     */
    private void activatePower() {
        activeFrameLeft = MAX_FRAMES;
    }

    /**
     * Gets the number of frames the special power remains active.
     * @return The number of frames the special power remains active.
     */
    public int getActiveFrameLeft() {
        return activeFrameLeft;
    }

    /**
     * Sets the number of frames the special power remains active.
     * @param activeFrameLeft The number of frames the special power remains active.
     */
    public void setActiveFrameLeft(int activeFrameLeft) {
        this.activeFrameLeft = activeFrameLeft;
    }
}
