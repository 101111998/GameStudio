package connectFour.server.controller;

import connectFour.core.Field;
import connectFour.core.GameMode;
import connectFour.core.GameState;
import connectFour.core.Tile;
import connectFour.entity.Comment;
import connectFour.entity.Rating;
import connectFour.entity.Score;
import connectFour.service.CommentService;
import connectFour.service.RatingService;
import connectFour.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

@Controller
@RequestMapping("/connectfour")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ConnectFourController {
    private Field field = new Field(6, 7);
    private GameMode gameMode = null;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserController userController;

    @RequestMapping
    public String connectFour(@RequestParam(required = false) Integer column, Model model) {
        fillModel(model);
        if(column != null && gameMode != null){
            if(field.getGameState() == GameState.PLAYING){
                field.placeToken(column);
                if(gameMode.getGameMode().equals("PVB")){
                    gameMode.moveOfBot();
                }
            }
            if(field.getGameState() != GameState.PLAYING && field.getGameState() != GameState.DRAW && userController.isLogged()){
                scoreService.addScore(new Score(userController.getLoggedUser().getUsrName(), "connectfour", field.getScore(), new Date()));
            }
        }
        return "connectfour";
    }

    private void fillModel(Model model) {
        model.addAttribute("scores", scoreService.getTopScores("connectfour"));
        model.addAttribute("comments", commentService.getComments("connectfour"));
        model.addAttribute("rate", ratingService.getAverageRating("connectfour"));
        if(userController.isLogged())
            model.addAttribute("getrate", ratingService.getRating("connectfour", userController.getLoggedUser().getUsrName()));
    }

    public String getHtmlField(){
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        System.out.println();
        for(int row = 0; row < field.getRowCount(); row++){
            sb.append("<tr>\n");
            for(int column = 0; column < field.getColumnCount(); column++){
                var tile  = field.getTile(row, column);
                sb.append("<td>\n");
                sb.append("<a href='/connectfour?column=" + column + "'>\n");
                sb.append("<img src='/images/connectfour/" + getImageName(tile) + ".png'>");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("<table>\n");
        return sb.toString();
    }

    private String getImageName(Tile tile) {
            switch (tile.getState()) {
                case EMPTY:
                    return "empty";
                case REDTOKEN:
                    return "redtoken";
                case YELLOWTOKEN:
                    return "yellowtoken";
                default:
                    throw new RuntimeException("Unexpected tile state");
            }
    }

    public String getState(){
        return field.getGameState().toString();
    }
    @RequestMapping("/addrating")
    public String addRate(int addrating, Model model) {
        ratingService.setRating(new Rating(userController.getLoggedUser().getUsrName(), "connectfour", addrating, new Date()));
        fillModel(model);
        return "redirect:/connectfour";
    }
    @RequestMapping("/addcomment")
    public String addComentary(String addcomment, Model model) {
        commentService.addComment(new Comment(userController.getLoggedUser().getUsrName(), "connectfour", addcomment, new Date()));
        fillModel(model);
        return "redirect:/connectfour";
    }

    @RequestMapping("/newpvp")
    public String newGamePvp(Model model){
        field = new Field(6, 7);
        gameMode = new GameMode("PVP", field);
        fillModel(model);
        return "connectfour";
    }

    @RequestMapping("/newpvb")
    public String newGamePvb(Model model){
        field = new Field(6, 7);
        gameMode = new GameMode("PVB", field);
        fillModel(model);
        return "connectfour";
    }

    public boolean getModeSet(){
        return gameMode != null;
    }
}
