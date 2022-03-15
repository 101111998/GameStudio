package connectFour.consoleui;

import connectFour.core.Field;
import connectFour.core.GameMode;
import connectFour.core.GameState;
import connectFour.entity.Comment;
import connectFour.entity.Rating;
import connectFour.entity.Score;
import connectFour.service.*;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    private final Field field;
    private GameMode gameMode;
    private final Scanner scanner = new Scanner(System.in);
    private static final Pattern INPUT_PATTERN = Pattern.compile("([P])([C])([1-7])");

    private ScoreService scoreService = new ScoreServiceJDBC();
    private CommentService commentService = new CommentServiceJDBC();
    private RatingService ratingService = new RatingServiceJDBC();
    private String winnerName;

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play(){
        mainMenu();

        do{
            printConsoleInterface();
            processInput();
        } while(field.getGameState() == GameState.PLAYING);

        printConsoleInterface();
        printEndGameInfo();
        addComment();
        setRating();
    }

    private void printEndGameInfo(){
        if(field.getGameState() == GameState.DRAW){
            System.out.println("Hra sa skoncila remizou");
        }else if(field.getGameState() == GameState.REDWIN){
            System.out.println("Gratulujem vyhral cerveny hrac");
            scoreService.addScore(new Score(nameInput(), "connectfour", field.getScore(), new Date()));
        }else if(field.getGameState() == GameState.YELLOWWIN){
            System.out.println("Gratulujem vyhral zlty hrac");
            scoreService.addScore(new Score(nameInput(), "connectfour", field.getScore(), new Date()));
        }
    }

    private void printConsoleInterface() {
        printAverageRating();
        printField();
        printLowerHeader();
    }

    private void printAverageRating(){
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("AVG RATING OF THE GAME: %d\n", ratingService.getAverageRating("connectfour"));
        System.out.println("-----------------------------------------------------------------------");

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
    }

    private void printLowerHeader() {
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
            int column = Integer.parseInt(matcher.group(3)) - 1;
            if(!field.placeToken(column)) System.out.println("WRONG INPUT COLUMN IS FULL");
            if(gameMode.getGameMode().equals("PVB")){
                gameMode.moveOfBot();
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
                gameMode = new GameMode("PVP", field);
                return;
            }else if("B".equals(line)){
                gameMode = new GameMode("PVB", field);
                return;
            }
        }
    }

    private void printTopScore(){
        var scores = scoreService.getTopScores("connectfour");
        System.out.println("----------------------------------RATINGS-----------------------------------");
        for(int i=0; i<scores.size(); i++){
            var score = scores.get(i);
            System.out.printf("%d. %s %d\n", i+1, score.getPlayer(), score.getPoints());
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    private String nameInput(){
        System.out.print("ENTER WINNERS NAME: ");
        Scanner in = new Scanner(System.in);
        winnerName = in.nextLine();
        return winnerName;
    }

    private void addComment(){
        System.out.print("Do you want comment game ? [Y/N]:");
        Scanner in = new Scanner(System.in);
        if(in.nextLine().equals("Y")){
            System.out.print("Enter commentary: ");
            commentService.addComment(new Comment(winnerName, "connectfour", in.nextLine(), new Date()));
        }
    }

    private void setRating(){
        System.out.print("Do you want rate game ? [Y/N]:");
        Scanner in = new Scanner(System.in);
        if(in.nextLine().equals("Y")){
            System.out.print("Enter rating as number [1 - 5]: ");
            ratingService.setRating(new Rating(winnerName, "connectfour", Integer.parseInt(in.nextLine()), new Date()));
        }
    }

    private void printComments(){
        var comments = commentService.getComments("connectfour");
        System.out.println("----------------------------------COMMENTS----------------------------------");
        for(int i=0; i<comments.size(); i++){
            var comment = comments.get(i);
            System.out.printf("%d. %tY-%tm-%td %TT | %s | %s \n", i+1, comment.getCommentedOn(), comment.getCommentedOn(), comment.getCommentedOn(), comment.getCommentedOn(), comment.getPlayer(), comment.getComment());
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    private void printPersonalRating(){
        System.out.println("------------------------------PERSONALRATING--------------------------------");
        System.out.println("ENTER YOUR NAME: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        System.out.printf("%s : %d \n", name,  ratingService.getRating("connectfour", name));
        System.out.println("----------------------------------------------------------------------------");
    }

    private void mainMenu(){
        System.out.println("MAIN MENU OF CONNECT FOUR\n\n");
        System.out.println("ENTER:\n -PLAY FOR START NEW GAME \n -HOF FOR DISPLAYING HALL OF FAME \n -COM FOR LIST COMMENTS \n -CHECK FOR CHECK YOUR SCORE");
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        while(!command.equals("PLAY")) {
            if(command.equals("HOF")) printTopScore();
            if(command.equals("COM")) printComments();
            if(command.equals("CHECK")) printPersonalRating();
            command = in.nextLine();
        }
        pickGameMode();
    }
}
