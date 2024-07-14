/**
 * Interface representing entities that can move randomly in the game.
 * Classes that implement this interface can move randomly within the game world.
 */
public interface RandomlyMovable extends Movable {

    /**
     * Method to move the entity randomly within the game world.
     */
    public void moveRandomly();
}
