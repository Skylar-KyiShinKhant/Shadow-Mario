import bagel.Input;

/**
 * Interface representing movable entities in the game.
 * Classes that implement this interface can move in the game world.
 */
public interface Movable {

    /**
     * Method to update the movement of the entity based on player input.
     * @param input The input object representing user input.
     */
    public void move(Input input);
}
