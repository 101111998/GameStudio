package connectFour.server.controller;

import connectFour.core.Field;
import connectFour.core.Tile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

@Controller
@RequestMapping("/connectfour")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ConnectFourController {
    private Field field = new Field(6, 7);

    @RequestMapping
    public String connectFour(@RequestParam(required = false) Integer column) {
        if(column != null) field.placeToken(column);
        return "connectfour";
    }

    // Pridaj img pre stavy
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
                //sb.append("<img src='/images/connectfour/" + getImageName(tile) + ".png'>");
                sb.append("X\n");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("<table>\n");
        return sb.toString();
    }

    private String getImageName(Tile tile) {
        switch (tile.getState()){
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

    @RequestMapping("/new")
    public String newGame(){
        field = new Field(6, 7);
        return "connectfour";
    }
}
