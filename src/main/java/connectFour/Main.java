package connectFour;

import connectFour.consoleui.ConsoleUI;
import connectFour.core.Field;
import connectFour.core.GameMode;

public class Main {
    public static void main(String[] args) {
        var field = new Field(6, 7);
        var ui = new ConsoleUI(field);
        ui.play();
    }
}
