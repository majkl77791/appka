package com.michal.appka.dao;

import com.michal.appka.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserAccountDAO implements IUserAccountDAO {

    private EntityManager entityManager;


    @Autowired
    public UserAccountDAO(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public List<UserAccount> getUsers() {

        Session currentSession = entityManager.unwrap(Session.class);
        Query<UserAccount> theQuery =
                currentSession.createQuery("from UserAccount",
                        UserAccount.class);

        List<UserAccount> userAccounts = theQuery.getResultList();

        return userAccounts;
    }

    @Override
    public UserAccount saveUser(UserAccount userAccount) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query<UserAccount> theQuery =
                currentSession.createQuery("from UserAccount where facebook_id=:userAccountId");
        theQuery.setParameter("userAccountId", userAccount.getFacebookId());
        List<UserAccount> userAccounts = theQuery.getResultList();

        if(userAccounts.isEmpty()) {
            currentSession.saveOrUpdate(userAccount);
            return userAccount;
        }
        return null;

    }

    @Override
    public UserAccount getUser(String facebookId) {

        Session currentSession = entityManager.unwrap(Session.class);


        Query<UserAccount> theQuery =
                currentSession.createQuery("from UserAccount where facebook_id=:userAccountId");
        theQuery.setParameter("userAccountId", facebookId);

        List<UserAccount> userAccounts = theQuery.getResultList();

        if(userAccounts.isEmpty()) {
           return null;
        }
        return  userAccounts.get(0);

    }

    @Override
    public void deleteUser(String facebookId) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery =
                currentSession.createQuery("delete from UserAccount where facebook_id=:userAccountId");
        theQuery.setParameter("userAccountId", facebookId);

        theQuery.executeUpdate();

    }
}
