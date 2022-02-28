package connectFour;

import connectFour.core.Field;

public class Main {
    public static void main(String[] args) {
        var field = new Field(6, 7);
        printfield(field);
        field.placeToken(0);
        System.out.println();
        printfield(field);
        field.placeToken(2);
        System.out.println();
        printfield(field);
    }

    private static void printfield(Field field) {
        for(int row = 0; row < field.getRowCount(); row++){
            for(int column = 0; column < field.getColumnCount(); column++){
                var tile  = field.getTile(row, column);
                System.out.print(" ");
                switch (tile.getState()){
                    case EMPTY:
                        System.out.print("E");
                        break;
                    case REDTOKEN:
                        System.out.print("R");
                        break;
                    case YELLOWTOKEN:
                        System.out.print("Y");
                        break;
                }
            }
            System.out.println();
        }
    }
}
