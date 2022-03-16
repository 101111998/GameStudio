import connectFour.service.ScoreServiceJDBC;
import org.junit.Before;
import org.junit.Test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static connectFour.service.ServiceConfig.*;

public class ScoreServiceJDBCTest extends ScoreServiceTest {

    private static final String DELETE = "DELETE FROM score";

    public ScoreServiceJDBCTest() {
        super.scoreService = new ScoreServiceJDBC();
    }

    @Before
    public void setUp() throws Exception {
        Connection c = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        Statement s = c.createStatement();
        s.execute(DELETE);
    }

    @Test
    public void testDbInit() throws Exception {
        super.testDbInit();
    }

    @Test
    public void testAddScore() throws Exception {
        super.testAddScore();
    }

    @Test
    public void testGetBestScores() throws Exception {
        super.testGetBestScores();
    }
}
