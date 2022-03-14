package connectFour;


import connectFour.entity.Comment;
import connectFour.entity.Rating;
import connectFour.entity.Score;
import connectFour.service.*;

import java.util.Date;

public class TestJDBC {

    public static void main(String[] args) throws Exception {
        /*
        ScoreService service = new ScoreServiceJDBC();
        service.reset();
        service.addScore(new Score("jaro", "connectfour", 110, new Date()));

        CommentService service = new CommentServiceJDBC();
        service.reset();
        service.addComment(new Comment("jaro", "connectfour", "prvy koment", new Date()));
        service.addComment(new Comment("jaro", "connectfour", "prvy koment", new Date()));
        */

        RatingService service = new RatingServiceJDBC();
        service.reset();
        service.setRating(new Rating("jaro", "connectfour", 5, new Date()));
        service.setRating(new Rating("jaro", "connectfour", 2, new Date()));
        service.setRating(new Rating("anna", "connectfour", 1, new Date()));
        service.setRating(new Rating("fero", "connectfour", 1, new Date()));
        service.setRating(new Rating("janko", "connectfour", 2, new Date()));
        service.setRating(new Rating("emil", "connectfour", 3, new Date()));
        System.out.printf("Average game rating: %d", service.getAverageRating("connectfour"));
        //System.out.printf("Fero rating: %d emil r: %d peter r: %d", service.getRating("connectfour", "fero"), service.getRating("connectfour", "emil"), service.getRating("connectfour", "peter"));

    }
}
