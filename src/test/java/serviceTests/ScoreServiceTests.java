package serviceTests;

import connectFour.entity.Score;
import connectFour.service.ScoreService;
import connectFour.service.ScoreServiceJDBC;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class ScoreServiceTests {
    private ScoreService scoreService = new ScoreServiceJDBC();

    @Test
    public void testAddScore(){
        Score score = new Score("ven", "connectfour", 100, new Date());
        scoreService.addScore(score);
        assertEquals(1, scoreService.getTopScores("connectfour").size());
    }

    @Test
    public void testReset(){
        scoreService.reset();
        assertEquals(0, scoreService.getTopScores("connectfour").size());
    }

    @Test
    public void testGetTopScores(){
        scoreService.reset();
        var date = new Date();
        scoreService.addScore(new Score("Jaro", "connectfour", 120, date));
        scoreService.addScore(new Score("Katka", "connectfour", 150, date));
        scoreService.addScore(new Score("Zuzka", "ctf", 180, date));
        scoreService.addScore(new Score("Jaro", "connectfour", 100, date));

        var scores = scoreService.getTopScores("connectfour");

        assertEquals(3, scores.size());

        assertEquals("connectfour", scores.get(0).getGame());
        assertEquals("Katka", scores.get(0).getPlayer());
        assertEquals(150, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedAt());

        assertEquals("connectfour", scores.get(1).getGame());
        assertEquals("Jaro", scores.get(1).getPlayer());
        assertEquals(120, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedAt());

        assertEquals("connectfour", scores.get(2).getGame());
        assertEquals("Jaro", scores.get(2).getPlayer());
        assertEquals(100, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedAt());

    }
}
