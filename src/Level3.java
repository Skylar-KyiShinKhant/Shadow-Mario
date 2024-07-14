import bagel.Input;
import java.util.ArrayList;

public class Level3 extends Level {

    /**
     * Constructs a game entity object.
     * @param levelNum The level number.
     * @param finished The status to flag game level progress.
     */
    public Level3(int levelNum, boolean finished) {
        super(levelNum, finished);
    }

    /**
     * Level3-specific method that updates the game objects each frame, when the game is running.
     *
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
    public void updateLevelGameObjects(Input input, ArrayList<FlyingPlatform> flyingPlatforms,
                                       ArrayList<DoubleScore> doubleScores, ArrayList<InvinciblePower> invinciblePowers,
                                       EnemyBoss enemyBoss, Player player, Platform platform, ArrayList<Enemy> enemies,
                                       EndFlag endFlag) {
        super.updateLevelGameObjects(input, enemyBoss, player, platform, enemies, endFlag);

        // Update special powers
        for (FlyingPlatform f : flyingPlatforms) {
            f.updateWithTarget(input, player);
        }
        for (DoubleScore d: doubleScores) {
            d.updateWithTarget(input, player);
        }
        for (InvinciblePower i: invinciblePowers) {
            i.updateWithTarget(input, player);
        }

        // Update enemy boss
        enemyBoss.updateWithTarget(input, player);
    }

    /**
     * Method to check existence of enemy boss
     * @param player the player entity
     */
    @Override
    public void enemyBossExistence(Player player) {
        player.setKilledEnemyBoss(false);
    }
}
