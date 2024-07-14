import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

/**
 * Class for the platform entity.
 */
public class Platform extends GameEntity implements Movable {
    private final int SPEED_X;
    private final int MAX_COORDINATE = 3000;

    /**
     * Constructs a platform object.
     * @param x The x-coordinate of the platform.
     * @param y The y-coordinate of the platform.
     * @param props The properties containing platform settings.
     * @param key The key to retrieve specific properties for the platform.
     */
    public Platform(int x, int y, Properties props, String key) {
        super(x, y, new Image(props.getProperty("gameObjects." + key + ".image")));
        this.SPEED_X = Integer.parseInt(props.getProperty("gameObjects." + key + ".speed"));
    }

    /**
     * Updates the player's position, image, and actions based on user input.
     * @param input The input from the user.
     * @param target The enemy boss target to attack.
     */
    @Override
    public void updateWithTarget(Input input, Target target) {
        move(input);
        super.getImage().draw(super.getX(), super.getY());
    }

    /***
     * Method that moves the platform based on the player's movement.
     * @param input The input from the user used for movement.
     */
    public void move(Input input){
        if (input.isDown(Keys.RIGHT)){
            super.setX(super.getX() - SPEED_X);
        } else if (input.isDown(Keys.LEFT)){
            if (super.getX() < MAX_COORDINATE){
                super.setX(super.getX() + SPEED_X);
            }
        }
    }

    // Getters for various platform properties
    /**
     * Retrieves the current moving speed in horizontal direction of the platform.
     * @return The moving SPEED_X of the platform.
     */
    public int getSPEED_X() {
        return SPEED_X;
    }

}