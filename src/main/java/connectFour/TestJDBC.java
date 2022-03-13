package connectFour;


import connectFour.entity.Score;
import connectFour.service.ScoreService;
import connectFour.service.ScoreServiceJDBC;

import java.util.Date;

public class TestJDBC {

    public static void main(String[] args) throws Exception {
        ScoreService service = new ScoreServiceJDBC();
        service.reset();
        service.addScore(new Score("jaro", "connectfour", 110, new Date()));
    }
}
