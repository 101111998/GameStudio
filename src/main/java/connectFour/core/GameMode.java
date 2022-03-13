package connectFour.core;

import java.util.Random;

public class GameMode {
    private final Field field;
    private final String gameMode;

    public GameMode(String gameMode, Field field) {
        this.field = field;
        this.gameMode = gameMode;
    }

    public void moveOfBot(){
        boolean validator = field.placeToken(generateColumn());
        while(!validator){
            validator = field.placeToken(generateColumn());
        }
    }

    public int generateColumn(){
        Random random = new Random();
        return random.nextInt(7);
    }

    public String getGameMode() {
        return gameMode;
    }
}
