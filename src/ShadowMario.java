import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Colour;

import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Main class representing the Shadow Mario game.
 * Built upon Sample solution for SWEN20003 Project 1, Semester 1, 2024
 * By Dimuthu Kariyawasan & Tharun Dharmawickrema
 *
 * @author Kyi Shin Khant (Skylar)
 */
public class ShadowMario extends AbstractGame {

    // Constants for game properties
    private final int WINDOW_HEIGHT;
    private final String GAME_TITLE;
    private final Image BACKGROUND_IMAGE;
    private final String FONT_FILE;
    private final Font TITLE_FONT;
    private final int TITLE_X;
    private final int TITLE_Y;
    private final String INSTRUCTION;
    private final Font INSTRUCTION_FONT;
    private final int INS_Y;
    private final Font MESSAGE_FONT;
    private final int MESSAGE_Y;
    private final Properties PROPS;
    private final Properties MESSAGE_PROPS;
    private final Font SCORE_FONT;
    private final int SCORE_X;
    private final int SCORE_Y;
    private final Font PLAYER_HEALTH_FONT;
    private final int PLAYER_HEALTH_X;
    private final int PLAYER_HEALTH_Y;
    private final Font ENEMY_BOSS_HEALTH_FONT;
    private final int ENEMY_BOSS_HEALTH_X;
    private final int ENEMY_BOSS_HEALTH_Y;

    // Game variables
    private int score;
    private Player player;
    private Platform platform;
    private ArrayList<Enemy> enemies;
    private ArrayList<Coin> coins;
    private ArrayList<DoubleScore> doubleScores;
    private ArrayList<InvinciblePower> invinciblePowers;
    private ArrayList<FlyingPlatform> flyingPlatforms;
    private EnemyBoss enemyBoss;
    private EndFlag endFlag;
    private boolean started = false;
    private Level level;

    /**
     * Constructs the ShadowMario game.
     *
     * @param game_props    Properties for the game configuration.
     * @param message_props Properties for game messages.
     */
    public ShadowMario(Properties game_props, Properties message_props) {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
              Integer.parseInt(game_props.getProperty("windowHeight")),
              message_props.getProperty("title"));

        WINDOW_HEIGHT = Integer.parseInt(game_props.getProperty("windowHeight"));
        GAME_TITLE = message_props.getProperty("title");
        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));
        FONT_FILE = game_props.getProperty("font");

        TITLE_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("title.fontSize")));
        TITLE_X = Integer.parseInt(game_props.getProperty("title.x"));
        TITLE_Y = Integer.parseInt(game_props.getProperty("title.y"));

        SCORE_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("score.fontSize")));
        SCORE_X = Integer.parseInt(game_props.getProperty("score.x"));
        SCORE_Y = Integer.parseInt(game_props.getProperty("score.y"));

        INSTRUCTION = message_props.getProperty("instruction");
        INSTRUCTION_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("instruction.fontSize")));
        INS_Y = Integer.parseInt(game_props.getProperty("instruction.y"));

        MESSAGE_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("message.fontSize")));
        MESSAGE_Y = Integer.parseInt(game_props.getProperty("message.y"));

        PLAYER_HEALTH_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("playerHealth.fontSize")));
        PLAYER_HEALTH_X = Integer.parseInt(game_props.getProperty("playerHealth.x"));
        PLAYER_HEALTH_Y = Integer.parseInt(game_props.getProperty("playerHealth.y"));

        ENEMY_BOSS_HEALTH_FONT = new Font(FONT_FILE, Integer.parseInt(
                                game_props.getProperty("enemyBossHealth.fontSize")));
        ENEMY_BOSS_HEALTH_X = Integer.parseInt(game_props.getProperty("enemyBossHealth.x"));
        ENEMY_BOSS_HEALTH_Y = Integer.parseInt(game_props.getProperty("enemyBossHealth.y"));

        this.PROPS = game_props;
        this.MESSAGE_PROPS = message_props;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
        ShadowMario game = new ShadowMario(game_props, message_props);
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     * @param input input from user
     */
    @Override
    protected void update(Input input) {

        // close window
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        // Draw background image
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        if (!started) {
            // Display title and instructions
            TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_FONT.drawString(INSTRUCTION,
                    Window.getWidth() / 2 - INSTRUCTION_FONT.getWidth(INSTRUCTION)/2, INS_Y);

            // Start corresponding game level if number keys (1, 2, 3) are pressed
            if (input.wasPressed(Keys.NUM_1)) {
                startGame(new Level1(1, false));
            } else if (input.wasPressed(Keys.NUM_2)) {
                startGame(new Level2(2, false));
            } else if (input.wasPressed(Keys.NUM_3)) {
                startGame(new Level3(3, false));
            }
        } else if (player.isDead() && player.getY() > WINDOW_HEIGHT) {
            // Display game over message
            String message = MESSAGE_PROPS.getProperty("gameOver");
            MESSAGE_FONT.drawString(message,
                    Window.getWidth() / 2 - MESSAGE_FONT.getWidth(message)/2,
                    MESSAGE_Y);
            // Restart game if space key is pressed
            if (input.wasPressed(Keys.SPACE)) {
                started = false;
            }
        } else {
            if (level.isFinished()) {
                // Display game won message
                String message = MESSAGE_PROPS.getProperty("gameWon");
                MESSAGE_FONT.drawString(message,
                        Window.getWidth() / 2 - MESSAGE_FONT.getWidth(message)/2,
                        MESSAGE_Y);
                // Restart game if space key is pressed
                if(input.wasPressed(Keys.SPACE)) {
                    started = false;
                    level.setFinished(false);
                 }
            } else {
                // game is running
                SCORE_FONT.drawString(MESSAGE_PROPS.getProperty("score") + score, SCORE_X, SCORE_Y);
                PLAYER_HEALTH_FONT.drawString(MESSAGE_PROPS.getProperty("health") +
                                Math.round(player.getHealth()*100), PLAYER_HEALTH_X, PLAYER_HEALTH_Y);
                if (level.getLevelNum() == 3) {
                    ENEMY_BOSS_HEALTH_FONT.drawString(MESSAGE_PROPS.getProperty("health") +
                                    Math.round(enemyBoss.getHealth()*100), ENEMY_BOSS_HEALTH_X, ENEMY_BOSS_HEALTH_Y,
                                    new DrawOptions().setBlendColour(Colour.RED));
                }
                updateGameObjects(input);
            }
        }
    }

    /**
     * Method that creates the game objects using the lines read from the CSV file.
     * @param lines collection of lines in level file
     */
    private void populateGameObjects(String[][] lines) {
        coins = new ArrayList<Coin>();
        enemies = new ArrayList<Enemy>();
        flyingPlatforms = new ArrayList<FlyingPlatform>();
        doubleScores = new ArrayList<DoubleScore>();
        invinciblePowers = new ArrayList<InvinciblePower>();

        // Iterate through each line in the CSV file
        for(String[] lineElement: lines) {
            int x = Integer.parseInt(lineElement[1]);
            int y = Integer.parseInt(lineElement[2]);

            if (lineElement[0].equals("PLAYER")) {
                player = new Player(x, y, this.PROPS);
                // Regarding level, determine if enemy boss exist
                level.enemyBossExistence(player);
            } else if (lineElement[0].equals("PLATFORM")) {
                platform = new Platform(x, y, this.PROPS, "platform");
            } else if (lineElement[0].equals("FLYING_PLATFORM")) {
                FlyingPlatform flyingPlatform = new FlyingPlatform(x, y, this.PROPS, "flyingPlatform");
                flyingPlatforms.add(flyingPlatform);
            } else if (lineElement[0].equals("ENEMY")) {
                Enemy enemy = new Enemy(x, y, PROPS);
                enemies.add(enemy);
            } else if (lineElement[0].equals("COIN")) {
                Coin coin = new Coin(x, y, PROPS, "coin");
                coins.add(coin);
            } else if (lineElement[0].equals("DOUBLE_SCORE")) {
                DoubleScore doubleScore = new DoubleScore(x, y, PROPS, "doubleScore");
                doubleScores.add(doubleScore);
            } else if (lineElement[0].equals("INVINCIBLE_POWER")) {
                InvinciblePower invinciblePower = new InvinciblePower(x, y, PROPS, "invinciblePower");
                invinciblePowers.add(invinciblePower);
            } else if (lineElement[0].equals("ENEMY_BOSS")) {
                enemyBoss = new EnemyBoss(x, y, PROPS);
            } else if (lineElement[0].equals("END_FLAG")) {
                endFlag = new EndFlag(x, y, PROPS);
            }
        }
    }

    /**
     * Method that updates the game objects each frame, when the game is running.
     * @param input input from user
     */
    public void updateGameObjects(Input input) {
        // Update coins and score
        for(Coin c: coins) {
            score += c.updateWithPlayer(input, player);
        }

        // Update game objects regarding level
        level.updateLevelGameObjects(input, flyingPlatforms, doubleScores, invinciblePowers, enemyBoss, player,
                platform, enemies, endFlag);
    }

    /**
     * Starts the game with the specified level.
     * @param level The level to start the game with.
     */
    private void startGame(Level level) {
        this.level = level;
        started = true;
        score = 0;

        String[][] lines = IOUtils.readCsv(this.PROPS.getProperty("level" + level.getLevelNum() + "File"));
        populateGameObjects(lines);
    }
}