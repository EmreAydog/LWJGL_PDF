public class Main {

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new DummyGame();
            GameEngine gameEngine = new GameEngine("Cool Game",
                    600, 480, vSync, gameLogic);
            gameEngine.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}
