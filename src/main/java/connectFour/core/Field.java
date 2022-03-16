package connectFour.core;

import java.util.Random;

public class Field {
    private final Tile[][] tiles;
    private final int rowCount;
    private final int columnCount;
    private GameState gameState = GameState.PLAYING;
    private final int tilesCount;
    private int tokenCount;
    private String currentColor;
    private long startMillis;

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;

        tilesCount = rowCount * columnCount;
        tokenCount = 0;
        tiles = new Tile[rowCount][columnCount];
        generateField();
        pickWhoStart();
    }

    private void generateField(){
        for(int row = 0; row < getRowCount(); row++){
            for(int column = 0; column < getColumnCount(); column++){
                tiles[row][column] = new Tile(row, column);
            }
        }
        startMillis = System.currentTimeMillis();
    }

    private void pickWhoStart() {
        Random random = new Random();
        int startingPlayer = random.nextInt(2);
        if(startingPlayer == 0) currentColor = "R";
        else currentColor = "Y";
    }

    public boolean placeToken(int column){
        Tile tile = checkForTile(column);
        if(tile != null){
            tokenCount++;
            if(currentColor == "R"){
                tile.setColor("R");
                tile.setState(TileState.REDTOKEN);
            }else {
                tile.setColor("Y");
                tile.setState(TileState.YELLOWTOKEN);
            }
            isConnected(tile);
            isDraw();
            switchPlayer();
            //correct input token was placed
            return true;
        }
        //wrong input
        return false;
    }

    public Tile checkForTile(int column){
        for(int row = getRowCount() - 1; row >= 0; row-- ){
            Tile tile = getTile(row, column);
            if(tile.getState() == TileState.EMPTY){
                return tile;
            }
        }
        return null;
    }

    public void isConnected(Tile tile){
        if(checkWinColumn(tile) || checkWinRow(tile) || checkWinDiagonal(tile)) {
            if(currentColor.equals("R")){
                setGameState(GameState.REDWIN);
            }else if(currentColor.equals("Y")){
                setGameState(GameState.YELLOWWIN);
            }
        }
    }

    private void isDraw(){
        if(tokenCount == tilesCount && gameState == GameState.PLAYING) {
            setGameState(GameState.DRAW);
        }
    }

    private void switchPlayer(){
        if(currentColor.equals("R")){
            currentColor = "Y";
        }else {
            currentColor = "R";
        }
    }

    private boolean checkWinColumn(Tile tile){
        int countConnected = 1;
        if(tile.getRow() <= 2) {
            for (int row = tile.getRow() + 1; row <= tile.getRow() + 3; row++) {
                Tile nextTile = getTile(row, tile.getColumn());
                if (nextTile.getColor().equals(currentColor)) {
                    countConnected++;
                } else {
                    return false;
                }
            }
        }
        return countConnected == 4;
    }

    private boolean checkWinRow(Tile tile){
        int countConnected = checkLeftSideInRow(tile, 1);
        if(countConnected == 4) return true;
        int tmp = countConnected;
        for(int i=0; i < 4-tmp; i++ ){
            int column = tile.getColumn() + 1 + i;
            if(column < columnCount) {
                Tile nextTile = getTile(tile.getRow(), column);
                if (nextTile.getColor().equals(currentColor)) {
                    countConnected++;
                    if(countConnected == 4){
                        return true;
                    }
                }else return false;
            }else break;
        }
        return false;
    }

    private int checkLeftSideInRow(Tile tile, int countConnected) {
        for(int i=0; i<3; i++){
            int column = tile.getColumn() - 1 - i;
            if(column >= 0){
                Tile nextTile = getTile(tile.getRow(), column);
                if (nextTile.getColor().equals(currentColor)) {
                    countConnected++;
                } else break;
            }else break;
        }
        return countConnected;
    }

    private boolean checkWinDiagonal(Tile tile){
        int countConnected = 1;

        // left and down
        countConnected = checkDiagonalLeftDown(tile, countConnected);
        if(countConnected == 4) return true;
        // right and up
        countConnected = checkDiagonalRightUp(tile, countConnected);
        if(countConnected == 4) return true;
        
        //reset counting for second diagonal
        countConnected = 1;

        // left and up
        countConnected = checkDiagonalLeftUp(tile, countConnected);
        if(countConnected == 4) return true;
        // right and down
        countConnected = checkDiagonalRightDown(tile, countConnected);
        return countConnected == 4;
    }

    private int checkDiagonalRightDown(Tile tile, int countConnected) {
        int tmp = countConnected;
        for(int i=0; i<4-tmp; i++){
            int row = tile.getRow() + 1 + i;
            int column = tile.getColumn() + 1 + i;
            //check for correct position
            if(row < rowCount && column < columnCount){
                Tile nextTile = getTile(row, column);
                if(nextTile.getColor().equals(currentColor)) {
                    countConnected++;
                }else break;
            }else break;
        }
        return countConnected;
    }

    private int checkDiagonalLeftUp(Tile tile, int countConnected) {
        for(int i=0; i<3; i++){
            int row = tile.getRow() - 1 - i;
            int column = tile.getColumn() - 1 - i;
            //check for correct position
            if(row > -1 && column > -1){
                Tile nextTile = getTile(row, column);
                if (nextTile.getColor().equals(currentColor)) {
                    countConnected++;
                }else break;
            }else break;
        }
        return countConnected;
    }

    private int checkDiagonalRightUp(Tile tile, int countConnected) {
        int tmp = countConnected;
        for(int i = 0; i<4- tmp; i++){
            int row = tile.getRow() - 1 - i;
            int column = tile.getColumn() + 1 + i;
            //check for correct position
            if(row > -1 && column < columnCount){
                Tile nextTile = getTile(row, column);
                if(nextTile.getColor().equals(currentColor)) {
                    countConnected++;
                }else break;
            }else break;
        }
        return countConnected;
    }

    private int checkDiagonalLeftDown(Tile tile, int countConnected) {
        for(int i=0; i<3; i++){
            int row = tile.getRow() + 1 + i;
            int column = tile.getColumn() - 1 - i;
            //check for correct position
            if(row < rowCount && column > -1){
                Tile nextTile = getTile(row, column);
                if (nextTile.getColor().equals(currentColor)) {
                    countConnected++;
                }else break;
            }else break;
        }
        return countConnected;
    }

    public int getScore() {
        return rowCount * columnCount * 3 - (int) (System.currentTimeMillis() - startMillis) / 1000;
    }


    //SETTERS
    public void setTestTokenCount(int tokenCount) { this.tokenCount = tokenCount; }
    public void setGameState(GameState gameState) { this.gameState = gameState; }

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

    public int getTilesCount() { return tilesCount; }

    public String getCurrentColor() {
        return currentColor;
    }

    public Tile getTile(int row, int column){
        return tiles[row][column];
    }

    public GameState getGameState() {
        return gameState;
    }
}