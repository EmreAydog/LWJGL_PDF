public class GameEngine implements Runnable {

    // The targeted frames per second for the game
    public static final int TARGET_FPS = 60;

    // The target updates per second for the game (tickrate)
    public static final int TARGET_UPS = 30;

    private final Window window;

    // Define a variable that will hold the main game loop thread
    private final Thread gameLoopThread;

    private final Timer timer;

    private final IGameLogic gameLogic;

    // Constructor for GameEngine
    public GameEngine(String windowTitle, int width, int height, boolean vSync,
                      IGameLogic gameLogic) throws Exception {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vSync);
        this.gameLogic = gameLogic;
        timer = new Timer();
    }

    // Method that provides a way for the GameEngine to start a Thread
    public void start() {
        gameLoopThread.start();
    }

    // Starting point of the game
    @Override
    public void run() {
        try {
            // Initialize
            init();

            // Run the game
            gameLoop();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Initialize all the required elements for our gameloop to function
    protected void init() throws Exception {
        window.init();
        timer.init();
        gameLogic.init();
    }

    protected void gameLoop() {
        float elapsedTime;

        float accumulator = 0f;

        // Set up a variable for the updates per second
        float interval = 1f / TARGET_UPS;

        // This boolean remains true as long as the game is running
        boolean running = true;

        // The game loop runs until the boolean is false and the user/OS
        // has indicated that the window should close
        while(running && !window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            // Handle the user input
            input();

            // While we still have time to update the game (the game hasn't yet surpassed
            // the targeted tickrate) we allow the game to update
            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            // Render the required elements on the screen
            render();

            // Implement a seperate way of syncing if vSync is disabled
            if (!window.isvSync()) {
                sync();
            }
        }
    }

    // In case vSync is disable, provide an alternative to syncing the window
    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while(timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    // Input handler
    protected void input() {
        gameLogic.input(window);
    }

    // Update handler
    protected void update(float interval) {
        gameLogic.update(interval);
    }

    // Render handler
    protected void render() {
        gameLogic.render(window);
        window.update();
    }
}
