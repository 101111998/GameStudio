package connectFour.service;

import connectFour.entity.Login;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class LoginServiceJPA implements LoginService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(Login usr) throws LoginException {
        entityManager.persist(usr);
    }

    @Override
    public Login getUser(String usrName, String password) throws LoginException {
        List<Login> tmp = entityManager.createQuery("select s from Login s where s.usrName = :usrName and s.password = :passwd")
                .setParameter("usrName", usrName).setParameter("passwd", password).getResultList();
        return tmp.get(0);
    }

    @Override
    public void reset() throws LoginException {
        entityManager.createNativeQuery("DELETE FROM User").executeUpdate();
    }
}
