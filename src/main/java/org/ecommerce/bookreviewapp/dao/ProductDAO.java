package org.ecommerce.bookreviewapp.dao;

import java.util.List;

import org.ecommerce.bookreviewapp.entity.Product;
import org.ecommerce.bookreviewapp.model.PaginationResult;
import org.ecommerce.bookreviewapp.model.ProductInfo;
import org.ecommerce.bookreviewapp.model.ReviewInfo;

public interface ProductDAO {

    public Product findProduct(String code);

    public ProductInfo findProductInfo(String code) ;

    public ReviewInfo findReviewInfo(String code) ;

    public PaginationResult<ProductInfo> queryProducts(int page,
                       int maxResult, int maxNavigationPage  );

    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult,
                       int maxNavigationPage, String likeName);

    public PaginationResult<ReviewInfo> queryReviews(int page,
            int maxResult, int maxNavigationPage  );

    public PaginationResult<ReviewInfo> queryReviews(int page, int maxResult,
            int maxNavigationPage, String likeName, String code);

    public void save(ProductInfo productInfo);

    public void save(ReviewInfo reviewInfo);

    public void indexProducts() throws Exception;

    public List<Product> searchForProduct(String searchText) throws Exception;

}
