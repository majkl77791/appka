package com.michal.appka.dao;

import com.michal.appka.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserAccountDAO implements IUserAccountDAO {

    private EntityManager entityManager;

    // set up constructor injection
    @Autowired
    public UserAccountDAO(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public List<UserAccount> getUsers() {

        Session currentSession = entityManager.unwrap(Session.class);
        Query<UserAccount> theQuery =
                currentSession.createQuery("from UserAccount",
                        UserAccount.class);

        List<UserAccount> userAccounts = theQuery.getResultList();

        return userAccounts;
    }

    @Override
    public UserAccount saveUser(UserAccount theUser) {

        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.saveOrUpdate(theUser);

        return theUser;

    }

    @Override
    public UserAccount getUser(int theId) {

        Session currentSession = entityManager.unwrap(Session.class);

        UserAccount theCustomer = currentSession.get(UserAccount.class, theId);

        return theCustomer;
    }

    @Override
    public void deleteUser(int theId) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery =
                currentSession.createQuery("delete from UserAccount where id=:userAccountId");
        theQuery.setParameter("userAccountId", theId);

        theQuery.executeUpdate();

    }
}
