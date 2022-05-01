package connectFour.server.controller;

import connectFour.core.Field;
import connectFour.core.GameState;
import connectFour.core.Tile;
import connectFour.entity.Score;
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
    @Autowired
    private ScoreService scoreService;

    @RequestMapping
    public String connectFour(@RequestParam(required = false) Integer column, Model model) {
        if(column != null){
            field.placeToken(column);
            if(field.getGameState() != GameState.PLAYING && field.getGameState() != GameState.DRAW){
                scoreService.addScore(new Score("martin", "connectfour", field.getScore(), new Date()));
            }
        }
        fillModel(model);
        return "connectfour";
    }

    private void fillModel(Model model){
        model.addAttribute("scores", scoreService.getTopScores("connectfour"));
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

    @RequestMapping("/new")
    public String newGame(Model model){
        field = new Field(6, 7);
        fillModel(model);
        return "connectfour";
    }
}
