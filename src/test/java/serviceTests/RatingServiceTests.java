package serviceTests;

import connectFour.entity.Rating;
import connectFour.service.RatingService;
import connectFour.service.RatingServiceJDBC;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class RatingServiceTests {

    private RatingService ratingService = new RatingServiceJDBC();

    @Test
    public void testSetRatingAndGetRating(){
        Rating rating = new Rating("ven", "connectfour", 5, new Date());
        ratingService.setRating(rating);
        assertEquals(5, ratingService.getRating("connectfour", "ven"));
        rating.setRating(1);
        ratingService.setRating(rating);
        assertEquals(1, ratingService.getRating("connectfour", "ven"));
    }

    @Test
    public void testReset(){
        ratingService.reset();
        assertEquals(0, ratingService.getAverageRating("connectfour"));
    }

    @Test
    public void testGetAverageRating(){
        ratingService.reset();
        assertEquals(0, ratingService.getAverageRating("connectfour"));
        Rating rating1 = new Rating("ven", "connectfour", 5, new Date());
        Rating rating2 = new Rating("ven1", "connectfour", 1, new Date());
        ratingService.setRating(rating1);
        ratingService.setRating(rating2);
        assertEquals(3, ratingService.getAverageRating("connectfour"));
    }
}
