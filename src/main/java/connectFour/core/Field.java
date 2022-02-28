package connectFour.core;

import java.util.Random;

public class Field {
    private final Tile[][] tiles;
    private final int rowCount;
    private final int columnCount;
    private GameState state = GameState.PLAYING;
    private final int tilesCount;
    private int tokenCount;
    private String actualColor;
    private GameMode gameMode;

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.gameMode = GameMode.INITMODE;

        tilesCount = rowCount * columnCount;
        tokenCount = 0;
        tiles = new Tile[rowCount][columnCount];
        generateField();
        pickWhoStart();
    }

    private void pickWhoStart() {
        Random random = new Random();
        int startingPlayer = random.nextInt(2);
        if(startingPlayer == 0) actualColor = "R";
        else actualColor = "Y";
    }

    private void generateField(){
        for(int row = 0; row < getRowCount(); row++){
            for(int column = 0; column < getColumnCount(); column++){
                tiles[row][column] = new Tile(getActualColor());
            }
        }
    }

    private void switchPlayer(){
        if(actualColor == "R"){
            actualColor = "Y";
        }else {
            actualColor = "R";
        }
    }

    public int generateColumn(){
        Random random = new Random();
        int pickColumn = random.nextInt(7);
        return pickColumn;
    }

    public Boolean placeToken(int column){
        Tile tile = checkForTile(column);
        if(tile != null){
            if(actualColor == "R"){
                tile.setState(TileState.REDTOKEN);
            }else {
                tile.setState(TileState.YELLOWTOKEN);
            }
            switchPlayer();
            tokenCount++;
            //correct input token was placed
            return true;
        }
        //wrong input
        return false;
    }

    Tile checkForTile(int column){
        for(int row = getRowCount() - 1; row >= 0; row-- ){
            Tile tile = getTile(row, column);
            if(tile.getState() == TileState.EMPTY){
                return tile;
            }
        }
        return null;
    }

    //SETTERS
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    //GETTERS
    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getTokenCount() {
        return tokenCount;
    }

    public String getActualColor() {
        return actualColor;
    }

    public Tile getTile(int row, int column){
        return tiles[row][column];
    }

    public GameState getState() {
        return state;
    }

    public GameMode getGameMode() {
        return gameMode;
    }
}

