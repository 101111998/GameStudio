package connectFour.service;

import connectFour.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score);
    List<Score> getTopScores(String game);
    void reset();
}