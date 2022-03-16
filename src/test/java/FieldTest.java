import connectFour.core.Field;
import connectFour.core.GameState;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FieldTest {
    private Field field;
    private final int  rowCount = 6;
    private final int columnCount = 7;

    @Test
    public void testIsDraw() throws Exception{
        field = new Field(rowCount, columnCount);
        field.setTestTokenCount(field.getTilesCount());
        assertEquals(rowCount * columnCount, field.getTokenCount());
        assertEquals(GameState.PLAYING, field.getGameState());
    }

    @Test
    public void testIsConnectedDiagonal() throws Exception{
        field = new Field(rowCount, columnCount);
        field.placeToken(1);
        field.placeToken(2);
        field.placeToken(2);
        field.placeToken(3);
        field.placeToken(4);
        field.placeToken(3);
        field.placeToken(3);
        field.placeToken(4);
        field.placeToken(4);
        field.placeToken(5);
        String tmp = field.getCurrentColor();
        field.placeToken(4);
        if(tmp.equals("R"))
            assertEquals(GameState.REDWIN, field.getGameState());
        else if(tmp.equals("Y"))
            assertEquals(GameState.YELLOWWIN, field.getGameState());
    }

    @Test
    public void testIsConnectedColumn() throws Exception{
        field = new Field(rowCount, columnCount);
        field.placeToken(1);
        field.placeToken(2);
        field.placeToken(1);
        field.placeToken(2);
        field.placeToken(1);
        field.placeToken(2);
        String tmp = field.getCurrentColor();
        field.placeToken(1);
        if(tmp.equals("R"))
            assertEquals(GameState.REDWIN, field.getGameState());
        else if(tmp.equals("Y"))
            assertEquals(GameState.YELLOWWIN, field.getGameState());
    }

    @Test
    public void testIsConnectedRow() throws Exception{
        field = new Field(rowCount, columnCount);
        field.placeToken(1);
        field.placeToken(1);
        field.placeToken(2);
        field.placeToken(2);
        field.placeToken(3);
        field.placeToken(3);
        String tmp = field.getCurrentColor();
        field.placeToken(4);
        if(tmp.equals("R"))
            assertEquals(GameState.REDWIN, field.getGameState());
        else if(tmp.equals("Y"))
            assertEquals(GameState.YELLOWWIN, field.getGameState());
    }
}
