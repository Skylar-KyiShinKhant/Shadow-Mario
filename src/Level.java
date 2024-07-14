import bagel.Input;
import java.util.ArrayList;

public abstract class Level {

    private int levelNum;
    private boolean finished;

    /**
     * Constructs a game entity object.
     * @param levelNum The level number.
     * @param finished The status to flag game level progress.
     */
    public Level(int levelNum, boolean finished) {
        this.levelNum = levelNum;
        this.finished = finished;
    }

    /**
     * Method to check existence of enemy boss
     * @param player the player entity
     */
    public abstract void enemyBossExistence(Player player);

    /**
     * Level-specific method that updates the game objects each frame, when the game is running.
     * @param input            The input received from the user.
     * @param flyingPlatforms  The list of flying platforms in the level.
     * @param doubleScores     The list of double score power-ups in the level.
     * @param invinciblePowers The list of invincible power-ups in the level.
     * @param enemyBoss        The enemy boss object in the level.
     * @param player           The player object.
     * @param platform         The platform object in the level.
     * @param enemies          The list of enemies in the level.
     * @param endFlag          The end flag object in the level.
     */
    public abstract void updateLevelGameObjects(Input input, ArrayList<FlyingPlatform> flyingPlatforms,
                                       ArrayList<DoubleScore> doubleScores, ArrayList<InvinciblePower> invinciblePowers,
                                       EnemyBoss enemyBoss, Player player, Platform platform, ArrayList<Enemy> enemies,
                                       EndFlag endFlag);

    /**
     * Level-specific overloading method that updates the game objects each frame, when the game is running.
     * @param input            The input received from the user.
     * @param enemyBoss        The enemy boss object in the level.
     * @param player           The player object.
     * @param platform         The platform object in the level.
     * @param enemies          The list of enemies in the level.
     * @param endFlag          The end flag object in the level.
     */
    public void updateLevelGameObjects(Input input, EnemyBoss enemyBoss, Player player, Platform platform,
                                       ArrayList<Enemy> enemies, EndFlag endFlag) {
        // Update platform
        platform.updateWithTarget(input, null);

        // Update enemies
        for(Enemy e: enemies) {
            e.updateWithTarget(input, player);
        }

        // Update player
        player.updateWithTarget(input, enemyBoss);

        // Update end flag
        endFlag.updateWithTarget(input, player);

        // Check if end flag is collided
        if(endFlag.isCollided()) {
            finished = true;
        }
    }

    // Getters for level properties
    /**
     * Retrieves the current level number of the game.
     * @return The level number of the game.
     */
    public int getLevelNum() {
        return levelNum;
    }

    /**
     * Checks if the current level is finished playing.
     * @return true if it is finished, false otherwise.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Reset the level status
     * @param finished the new updated status
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
