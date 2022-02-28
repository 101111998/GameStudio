package connectFour.core;

public class Tile {
    private TileState state;
    private String color;

    public Tile(String color) {
        this.color = color;
        state = TileState.EMPTY;
    }

    public String getColor() {
        return color;
    }

    public TileState getState() {
        return state;
    }

    void setState(TileState state) {
        this.state = state;
    }
}
