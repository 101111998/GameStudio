package connectFour.service;

import connectFour.entity.Rating;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import static connectFour.service.ServiceConfigJDBC.*;

public class RatingServiceJDBC implements RatingService{

    public static final String DELETE_STATEMENT = "DELETE FROM rating";
    public static final String INSERT_STATEMENT = "INSERT INTO rating (player, game, rating, ratedOn) VALUES (?, ?, ?, ?)";
    public static final String SELECT_STATEMENT_GET_AVERAGE_RATING = "SELECT player, game, rating, ratedOn FROM rating WHERE game = ?";
    public static final String SELECT_STATEMENT_GET_RATING = "SELECT player, game, rating, ratedOn FROM rating WHERE game = ? AND player = ?";
    public static final String DELETE_ROW_STATEMENT = "DELETE FROM rating WHERE game = ? AND player = ?";

    @Override
    public void setRating(Rating rating) throws RatingException {
        checkForDuplicate(rating);
        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.prepareStatement(INSERT_STATEMENT)
        ) {

            statement.setString(1, rating.getPlayer());
            statement.setString(2, rating.getGame());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();

        } catch (SQLException e){
            throw new RatingException(e);
        }
    }

    private void checkForDuplicate(Rating rating) {
        if(getRating(rating.getGame(), rating.getPlayer()) != -1){
            try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.prepareStatement(DELETE_ROW_STATEMENT)
            ) {
                statement.setString(1, rating.getGame());
                statement.setString(2, rating.getPlayer());
                statement.executeUpdate();
            } catch (SQLException e){
                throw new RatingException(e);
            }
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        int averageRating = 0;
        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.prepareStatement(SELECT_STATEMENT_GET_AVERAGE_RATING)
        ) {
            statement.setString(1, game);
            try(var rs = statement.executeQuery()) {
                var ratings = new ArrayList<Integer>();
                while(rs.next()){
                    ratings.add(rs.getInt(3));
                }
                for (Integer rating : ratings) {
                    averageRating += rating;
                }
                if(ratings.size() == 0) return 0;
                return averageRating / ratings.size();
            }

        } catch (SQLException e){
            throw new RatingException(e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.prepareStatement(SELECT_STATEMENT_GET_RATING)
        ) {
            statement.setString(1, game);
            statement.setString(2, player);
            int rating = -1;
            try(var rs = statement.executeQuery()) {
                while(rs.next())
                    rating = rs.getInt(3);
            }
            return rating;
        } catch (SQLException e){
            throw new RatingException(e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e){
            throw new RatingException(e);
        }
    }
}
