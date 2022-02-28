package connectFour.consoleui;

import connectFour.core.Field;
import connectFour.core.GameMode;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    private final Field field;
    private final Scanner scanner = new Scanner(System.in);
    private static final Pattern INPUT_PATTERN = Pattern.compile("([P])([C])([1-7])");
    private boolean endGame = false;

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play(){
        pickGameMode();
        while(!endGame) {
            printField();
            processInput();
        }
    }

    private void printField() {
        System.out.println();
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
        for(int column = 0; column < field.getColumnCount(); column++){
            System.out.print(" ");
            System.out.print(column + 1);
        }
        System.out.println();
        System.out.println();
    }

    private void processInput() {
        System.out.print("Enter command (X - exit, PC1 - pick column for token)");
        var line = scanner.nextLine().toUpperCase();
        if("X".equals(line)) System.exit(0);

        var matcher = INPUT_PATTERN.matcher(line);
        if(matcher.matches()){
            if(field.getGameMode() == GameMode.PVP) {
                int column = Integer.parseInt(matcher.group(3)) - 1;
                boolean isValid = field.placeToken(column);
                if (!isValid) System.out.println("WRONG INPUT COLUMN IS FULL");
            }else if(field.getGameMode() == GameMode.PVB) {
                int column = Integer.parseInt(matcher.group(3)) - 1;
                boolean isValid = field.placeToken(column);
                if (!isValid) System.out.println("WRONG INPUT COLUMN IS FULL");
                column = field.generateColumn();
                isValid = field.placeToken(column);
                if (!isValid){
                    while(!isValid){
                        column = field.generateColumn();
                        isValid = field.placeToken(column);
                    }
                }
            }
        }else{
            System.out.println("WRONG INPUT COMMAND");
        }
    }

    private void pickGameMode(){
        System.out.print("Press P for pvp mode or press B for pvb mode: ");
        var line = scanner.nextLine().toUpperCase();
        while(true){
            if("P".equals(line)){
                //set PVP mode player vs player
                field.setGameMode(GameMode.PVP);
                return;
            }else if("B".equals(line)){
                //set PVB mode player vs bot
                field.setGameMode(GameMode.PVB);
                return;
            }
        }
    }
}
