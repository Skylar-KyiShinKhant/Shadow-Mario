import bagel.Image;
import bagel.Input;

import java.util.Properties;

/**
 * Abstract class representing a target entity in the game.
 */
public abstract class Target extends GameEntity {
    private final double RADIUS;
    private final int FALL_SPEED = 2;
    private final Properties PROPS;
    private int speedY = 0;
    private double health;

    /**
     * Constructs a target object.
     * @param x The x-coordinate of the target.
     * @param y The y-coordinate of the target.
     * @param props The properties containing game object settings.
     * @param key The key to retrieve specific properties for the target.
     */
    public Target(int x, int y, Properties props, String key) {
        super(x, y, new Image(props.getProperty("gameObjects." + key + ".image") == null ?
                props.getProperty("gameObjects." + key + ".imageRight") :
                props.getProperty("gameObjects." + key + ".image")));
        this.RADIUS = Double.parseDouble(props.getProperty("gameObjects." + key + ".radius"));
        this.health = Double.parseDouble(props.getProperty("gameObjects." + key + ".health"));
        this.PROPS = props;
    }

    /**
     * Method to perform an attack on a target.
     * @param input    The input object used for attack decision-making.
     * @param attacker The attacker performing the attack.
     * @param target   The target being attacked.
     */
    public abstract void attack(Input input, Target attacker, Target target);

    /**
     * Method that sets the fall speed if the target's health has reached 0.
     */
    public void dead() {
        this.speedY = FALL_SPEED;
    }

    /**
     * Checks if the target is dead.
     * @return True if the target's health is less than or equal to 0, false otherwise.
     */
    public boolean isDead() {
        return this.health <= 0;
    }

    // Getters and setters for various target properties
    /**
     * Get the properties object containing game configuration.
     * @return The properties object containing game configuration.
     */
    public Properties getPROPS() {
        return PROPS;
    }

    /**
     * Get the vertical speed of the target entity.
     * @return The vertical speed of the target entity.
     */
    public int getSpeedY() {
        return speedY;
    }

    /**
     * Set the vertical speed of the target entity.
     * @param speedY The vertical speed to set.
     */
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    /**
     * Get the radius of the target entity.
     * @return The radius of the target entity.
     */
    public double getRADIUS() {
        return RADIUS;
    }

    /**
     * Get the health of the target entity.
     * @return The health of the target entity.
     */
    public double getHealth() {
        return health;
    }

    /**
     * Set the health of the target entity.
     * @param health The health to set.
     */
    public void setHealth(double health) {
        this.health = health;
    }

}
