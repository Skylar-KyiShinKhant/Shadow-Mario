import bagel.Image;
import bagel.Input;

public abstract class GameEntity {
    private int x;
    private int y;
    private Image image;

    /**
     * Constructs a game entity object.
     * @param x The x-coordinate of the entity.
     * @param y The y-coordinate of the entity.
     * @param image the image of game entity.
     */
    public GameEntity(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    /**
     * Updates the game entities' movement, status, and draws it.
     * @param input The input object.
     * @param target The player or enemy boss object.
     */
    public abstract void updateWithTarget(Input input, Target target);

    // Getters and setters for gameEntity properties
    /**
     * Retrieves the current x-coordinate of the game entity.
     * @return The x-coordinate of the game entity.
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the current y-coordinate of the game entity.
     * @return The y-coordinate of the game entity.
     */
    public int getY() {
        return y;
    }

    /**
     * Changes the x-coordinate of the game entity.
     * @param x new x value
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Changes the y-coordinate of the game entity.
     * @param y new y value
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retrieves the current image of the game entity.
     * @return The image of the game entity.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Changes the image of the game entity.
     * @param image new image value
     */
    public void setImage(Image image) {
        this.image = image;
    }
}
