package connectFour.service;

import connectFour.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        if(entityManager.createQuery("select s from Rating s where s.game = :game AND s.player = :player")
                .setParameter("game", rating.getGame()).setParameter("player", rating.getPlayer()).getResultList().size() > 0){
            entityManager.createQuery("UPDATE Rating set rating = '"+rating.getRating()+"', ratedOn = '"+rating.getRatedOn()+
                    "' where game ='"+rating.getGame()+"' and player = '"+rating.getPlayer()+"'").executeUpdate();
        }else{
            entityManager.persist(rating);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        List<Rating> tmp = entityManager.createQuery("select s from Rating s where s.game = :game").setParameter("game", game).getResultList();
        if(tmp.size() == 0) return 0;
        int sum = 0;
        for(int i=0; i<tmp.size(); i++){
            sum += tmp.get(i).getRating();
        }
        return sum / tmp.size();
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        List<Rating> tmp = entityManager.createQuery("select s from Rating s where s.player = :player and s.game = :game")
                .setParameter("player", player).setParameter("game", game).getResultList();
        return tmp.get(0).getRating();
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNativeQuery("DELETE FROM Rating").executeUpdate();
    }
}
