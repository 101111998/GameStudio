package connectFour.core;

public class Tile {
    private TileState state;
    private String color;

    public Tile(String color, TileState tileState) {
        this.color = color;
        setState(tileState);
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
