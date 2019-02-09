package org.ecommerce.bookreviewapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.ecommerce.bookreviewapp.dao.AccountDAO;
import org.ecommerce.bookreviewapp.dao.OrderDAO;
import org.ecommerce.bookreviewapp.dao.ProductDAO;
import org.ecommerce.bookreviewapp.entity.Product;
import org.ecommerce.bookreviewapp.model.AccountInfo;
import org.ecommerce.bookreviewapp.model.OrderDetailInfo;
import org.ecommerce.bookreviewapp.model.OrderInfo;
import org.ecommerce.bookreviewapp.model.PaginationResult;
import org.ecommerce.bookreviewapp.model.ProductInfo;
import org.ecommerce.bookreviewapp.model.ReviewInfo;
import org.ecommerce.bookreviewapp.validator.AccountValidator;
import org.ecommerce.bookreviewapp.validator.ProductInfoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
@EnableWebMvc
public class AdminController {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductInfoValidator productInfoValidator;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == ProductInfo.class) {
            dataBinder.setValidator(productInfoValidator);
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }

        if (target.getClass() == AccountInfo.class) {
            dataBinder.setValidator(accountValidator);
        }
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = { "/search" }, method = RequestMethod.GET)
    public String searchPage(Model model) throws Exception {
    	productDAO.indexProducts();
        return "search";
    }

    @RequestMapping(value = { "/doSearch" }, method = RequestMethod.POST)
    public String search(Model model, @RequestParam("searchText") String searchText) throws Exception {
    	if ((searchText.length() <= 0) || (searchText.isEmpty())) {
            return "redirect:/search";
        }
    	List<Product> allFound = productDAO.searchForProduct(searchText);
    	List<ProductInfo> productInfo = new ArrayList<ProductInfo>();

    	for(Product product : allFound) {
    		ProductInfo prodInfo = new ProductInfo();

    		prodInfo.setCode(product.getCode());
    		prodInfo.setName(product.getName());
    		prodInfo.setAuthor(product.getAuthor());
    		prodInfo.setPublisher(product.getPublisher());
    		prodInfo.setTags(product.getTags());

    		productInfo.add(prodInfo);
    	}

    	model.addAttribute("foundProducts", productInfo);
        return "foundProducts";
    }

    @RequestMapping(value = { "/createAccount" })
    public String createAccount(Model model) {
    	AccountInfo accountInfo = new AccountInfo();
    	model.addAttribute("accountForm", accountInfo);

    	if(accountInfo.getUserName() == null){
    		model.addAttribute("formTitle", "Create Account");
    	} else {
    		model.addAttribute("formTitle", "Edit Account");
    	}
        return "formAccount";
    }

    @RequestMapping(value = { "/saveAccount" }, method = RequestMethod.POST)
    public String saveAccount(Model model, @ModelAttribute("accountForm") @Validated AccountInfo accountInfo, BindingResult result, final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
        	return "formAccount";
        }
        this.accountDAO.saveAccount(accountInfo);
        redirectAttributes.addFlashAttribute("message", "Account Saved Successfully");
    	return "redirect:/login";
    }

    @RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
    public String accountInfo(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }

    @RequestMapping(value = { "/orderList" }, method = RequestMethod.GET)
    public String orderList(Model model, @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;

        PaginationResult<OrderInfo> paginationResult = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
        model.addAttribute("paginationResult", paginationResult);
        return "orderList";
    }

    @RequestMapping(value = { "/product" }, method = RequestMethod.GET)
    public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        ProductInfo productInfo = null;

        if (code != null && code.length() > 0) {
            productInfo = productDAO.findProductInfo(code);
        }
        if (productInfo == null) {
            productInfo = new ProductInfo();
            productInfo.setNewProduct(true);
        }
        model.addAttribute("productForm", productInfo);
        return "product";
    }

    @RequestMapping(value = { "/product" }, method = RequestMethod.POST)
    @Transactional(propagation = Propagation.NEVER)
    public String productSave(Model model, @ModelAttribute("productForm") @Validated ProductInfo productInfo, BindingResult result, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "product";
        }
        try {
            productDAO.save(productInfo);
        } catch (Exception e) {
            String message = e.getMessage();
            model.addAttribute("message", message);
            return "product";
        }
        return "redirect:/productList";
    }

    @RequestMapping(value = { "/order" }, method = RequestMethod.GET)
    public String orderView(Model model, @RequestParam("orderId") String orderId) {
        OrderInfo orderInfo = null;
        if (orderId != null) {
            orderInfo = this.orderDAO.getOrderInfo(orderId);
        }
        if (orderInfo == null) {
            return "redirect:/orderList";
        }
        List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
        orderInfo.setDetails(details);

        model.addAttribute("orderInfo", orderInfo);
        return "order";
    }

    @RequestMapping(value = { "/review" }, method = RequestMethod.GET)
    public String review(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        ReviewInfo reviewInfo = null;
        String newReview = "redirect:/reviewList?code=" + code;

        ProductInfo productInfo = null;
        if (code != null && code.length() > 0) {
            productInfo = productDAO.findProductInfo(code);
            //If we only allow one review per user.
            /*reviewInfo = productDAO.findReviewInfo(code);*/
        }

        if (reviewInfo == null) {
        	reviewInfo = new ReviewInfo();
        	reviewInfo.setNewProduct(true);
        	reviewInfo.setCode(code);
        	reviewInfo.setAuthor(productInfo.getAuthor());
        	reviewInfo.setName(productInfo.getName());
        	reviewInfo.setPrice(productInfo.getPrice());
        	reviewInfo.setPublisher(productInfo.getPublisher());
        	reviewInfo.setTags(productInfo.getTags());
        }
        model.addAttribute("reviewForm", reviewInfo);
        return "review";
    }

    @RequestMapping(value = { "/review" }, method = RequestMethod.POST)
    @Transactional(propagation = Propagation.NEVER)
    public String productReviewSave(Model model, @ModelAttribute("reviewForm") @Validated ReviewInfo reviewInfo, BindingResult result, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "review";
        }
        try {
            productDAO.save(reviewInfo);
        } catch (Exception e) {
            String message = e.getMessage();
            model.addAttribute("message", message);
            return "review";
        }
        return "redirect:/reviewList?code=" + reviewInfo.getCode();
    }

}
