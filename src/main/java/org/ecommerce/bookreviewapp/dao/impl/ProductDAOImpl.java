package org.ecommerce.bookreviewapp.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.hibernate.Criteria;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.ecommerce.bookreviewapp.dao.AccountDAO;
import org.ecommerce.bookreviewapp.dao.ProductDAO;
import org.ecommerce.bookreviewapp.entity.Account;
import org.ecommerce.bookreviewapp.entity.Product;
import org.ecommerce.bookreviewapp.entity.Review;
import org.ecommerce.bookreviewapp.model.PaginationResult;
import org.ecommerce.bookreviewapp.model.ProductInfo;
import org.ecommerce.bookreviewapp.model.ReviewInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private AccountDAO accountDAO;

    //@Override
    public Product findProduct(String code) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Product.class);
        crit.add(Restrictions.eq("code", code));
        return (Product) crit.uniqueResult();
    }

    public Review findReview(String code) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Review.class);
        crit.add(Restrictions.eq("product", code));
        return (Review) crit.uniqueResult();
    }

    //@Override
    public ProductInfo findProductInfo(String code) {
        Product product = this.findProduct(code);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getCode(), product.getName(), product.getPrice(), product.getTags(), product.getAuthor(), product.getPublisher());
    }

    //@Override
    public ReviewInfo findReviewInfo(String code) {
        Review review = this.findReview(code);
        if (review == null) {
            return null;
        }
        return new ReviewInfo(review.getAccount(), review.getProduct(), review.getReview(), review.getCreateDate().toString());
    }

    //@Override
    public void save(ProductInfo productInfo) {
        String code = productInfo.getCode();

        Product product = null;

        boolean isNew = false;
        if (code != null) {
            product = this.findProduct(code);
        }
        if (product == null) {
            isNew = true;
            product = new Product();
            product.setCreateDate(new Date());
        }
        product.setCode(code);
        product.setName(productInfo.getName());
        product.setPrice(productInfo.getPrice());
        product.setTags(productInfo.getTags());
        product.setAuthor(productInfo.getAuthor());
        product.setPublisher(productInfo.getPublisher());

        if (productInfo.getFileData() != null) {
            byte[] image = productInfo.getFileData().getBytes();
            if (image != null && image.length > 0) {
                product.setImage(image);
            }
        }
        if (isNew) {
            this.sessionFactory.getCurrentSession().persist(product);
        }
        // If error in DB, Exceptions will be thrown out immediately
        this.sessionFactory.getCurrentSession().flush();
    }

    //@Override
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
            String likeName) {
        String sql = "Select new " + ProductInfo.class.getName() //
                + "(p.code, p.name, p.price, p.tags, p.author, p.publisher) " + " from "//
                + Product.class.getName() + " p ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }
        sql += " order by p.createDate desc ";
        //
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery(sql);
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
    }

    //@Override
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
        return queryProducts(page, maxResult, maxNavigationPage, null);
    }

	//@Override
	public PaginationResult<ReviewInfo> queryReviews(int page, int maxResult, int maxNavigationPage) {
		return queryReviews(page, maxResult, maxNavigationPage, null);
	}

	//@Override
	public PaginationResult<ReviewInfo> queryReviews(int page, int maxResult, int maxNavigationPage, String likeName) {
		String sql = "Select new " + ReviewInfo.class.getName() + "(r.product.code, r.account.userName, r.review) from " + Review.class.getName() + " r ";

		if(likeName != null && likeName.length() > 0) {
			sql += " Where lower(r.account.userName) like :likeName ";
		}
		sql += " order by r.createDate desc";

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
		return new PaginationResult<ReviewInfo>(query, page, maxResult, maxNavigationPage);
	}

	//@Override
	public PaginationResult<ReviewInfo> queryReviews(int page, int maxResult, int maxNavigationPage, String likeName, String code) {
		String sql = "Select new " + ReviewInfo.class.getName() + "(r.product, r.account, r.review, r.createDate) from " + Review.class.getName() + " r ";

		if(code != null && code.length() > 0) {
			sql += " Where lower(r.product) like :code ";
		}

		sql += " order by r.createDate desc";

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
		if (code != null && code.length() > 0) {
            query.setParameter("code", "%" + code.toLowerCase() + "%");
        }
		return new PaginationResult<ReviewInfo>(query, page, maxResult, maxNavigationPage);
	}

	//@Override
	public void save(ReviewInfo reviewInfo) {
		String code = reviewInfo.getCode();
		Review review = null;

        boolean isNew = false;
        if (code != null) {
        	//To restrict one review per user
            //review = this.findReview(code);
        }
        if (review == null) {
            isNew = true;
            review = new Review();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

    	Product product = this.findProduct(code);
    	Account account = this.accountDAO.findAccount(currentPrincipalName);

    	if (product == null) {
    		isNew = false;
    	}
    	if (account == null) {
    		isNew = false;
    	}

        review.setId(UUID.randomUUID().toString());
        review.setProduct(reviewInfo.getCode());
        review.setCreateDate(new Date());
        review.setReview(reviewInfo.getReview());
        review.setAccount(currentPrincipalName);

        if (isNew) {
            this.sessionFactory.getCurrentSession().persist(review);
        }
        this.sessionFactory.getCurrentSession().flush();
	}

	public void indexProducts() throws Exception {
		try{
			Session session = sessionFactory.getCurrentSession();
			FullTextSession fullTextSession = Search.getFullTextSession(session);
			fullTextSession.createIndexer().startAndWait();

		} catch(Exception ex) {
			throw ex;
		}
	}

	public List<Product> searchForProduct(String searchText) throws Exception {
		try {
			Session session = sessionFactory.getCurrentSession();
			FullTextSession fullTextSession = Search.getFullTextSession(session);

			QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Product.class).get();

			org.apache.lucene.search.Query luceneQuery = qb
					.keyword().onFields("name", "author", "publisher", "tags")
					.matching(searchText)
					.createQuery();

			/*org.hibernate.Query hibernateQuery = fullTextSession.createFullTextQuery(luceneQuery, Product.class);*/

			org.hibernate.query.Query<Product> newHibernateQuery = fullTextSession.createFullTextQuery(luceneQuery, Product.class);

			List<Product> results = newHibernateQuery.list();/*hibernateQuery.list();*/
			return results;
		} catch(Exception ex) {
			throw ex;
		}
	}
}
