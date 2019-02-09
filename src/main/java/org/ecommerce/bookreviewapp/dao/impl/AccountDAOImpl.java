package org.ecommerce.bookreviewapp.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import org.ecommerce.bookreviewapp.dao.AccountDAO;
import org.ecommerce.bookreviewapp.entity.Account;
import org.ecommerce.bookreviewapp.model.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AccountDAOImpl implements AccountDAO {

    @Autowired
    private SessionFactory sessionFactory;

    //@Override
    public Account findAccount(String userName) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Account.class);
        crit.add(Restrictions.eq("userName", userName));
        return (Account) crit.uniqueResult();
    }

    /*@Autowired
    private EntityManager em;

    //@Override
    public Account findAccount(String userName) {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Account> crit = criteriaBuilder.createQuery(Account.class);
        Root<Account> root = crit.from(Account.class);
        crit.where(criteriaBuilder.equal(root.get("userName"), userName))
            .distinct(true);
        return em.createQuery(crit).getSingleResult();
    }*/

	public void saveAccount(AccountInfo accountInfo) {
		String userName = accountInfo.getUserName();
		Account account = null;

		if(userName != null){
			account = this.findAccount(userName);
		}
		boolean isNew = false;

		if(account == null){
			isNew = true;
			account = new Account();
		}
		account.setUserName(accountInfo.getUserName());
		account.setPassword(accountInfo.getPassword());
		account.setActive(accountInfo.isActive());
		account.setUserRole(accountInfo.getUserRole());

		if(isNew){
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(account);
		}
	}
}
