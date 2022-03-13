package connectFour.core;

public class Tile {
    private TileState state;
    private final int  row;
    private final int  column;
    private String color;

    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        color = "E";
        state = TileState.EMPTY;
    }

    public TileState getState() {
        return state;
    }

    void setState(TileState state) {
        this.state = state;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
