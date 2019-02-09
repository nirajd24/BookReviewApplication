package org.ecommerce.bookreviewapp.dao;

import java.util.List;

import org.ecommerce.bookreviewapp.model.CartInfo;
import org.ecommerce.bookreviewapp.model.OrderDetailInfo;
import org.ecommerce.bookreviewapp.model.OrderInfo;
import org.ecommerce.bookreviewapp.model.PaginationResult;

public interface OrderDAO {

    public void saveOrder(CartInfo cartInfo);

    public PaginationResult<OrderInfo> listOrderInfo(int page,
            int maxResult, int maxNavigationPage);
   
    public OrderInfo getOrderInfo(String orderId);
   
    public List<OrderDetailInfo> listOrderDetailInfos(String orderId);

}
