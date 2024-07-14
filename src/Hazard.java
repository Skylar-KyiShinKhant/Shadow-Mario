/**
 * Interface representing hazards in the game.
 * Classes that implement this interface are capable of causing damage to target objects.
 */
public interface Hazard {
    /**
     * Method to inflict damage on a target object.
     * @param target The target object to be damaged.
     */
    public void damageTarget(Target target);
}
