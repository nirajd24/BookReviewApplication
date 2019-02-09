<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product List</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>

   <jsp:include page="_header.jsp" />
   <jsp:include page="_menu.jsp" />

   <fmt:setLocale value="en_US" scope="session"/>

   <div class="page-title">Product List</div>



   <c:forEach items="${paginationProducts.list}" var="prodInfo">
       <div class="product-preview-container">
           <ul>
				<div class="user_record_info">
               		<div class="user_thumb_box">
               			<div class="user_thumb">
               				<img class="product-image" src="${pageContext.request.contextPath}/productImage?code=${prodInfo.code}" />
               			</div>
               		</div>
               		<div class="user_record_content">
						<div class="record_group">
							<div class="record_title">Code :</div>
							<div class="record_desc">${prodInfo.code}</div>
						</div>
	                    <div class="record_group">
	                        <div class="record_title">Name :</div>
	                        <div class="record_desc">${prodInfo.name}</div>
	                    </div>
	                    <div class="record_group">
	                        <div class="record_title">Author :</div>
	                        <div class="record_desc">${prodInfo.author}</div>
	                    </div>
	                    <div class="record_group">
	                        <div class="record_title">Publisher :</div>
	                        <div class="record_desc">${prodInfo.publisher}</div>
	                    </div>
	                    <div class="record_group">
	                        <div class="record_title">Price :</div>
	                        <div class="record_desc"><fmt:formatNumber value="${prodInfo.price}" type="currency" currencySymbol="INR."/></div>
	                    </div>
	                    <div class="record_group">
	                        <div class="record_title">Tags :</div>
	                        <div class="record_desc">${prodInfo.tags}</div>
	                    </div>
		               <li><a href="${pageContext.request.contextPath}/buyProduct?code=${prodInfo.code}">Buy Now</a></li>
		               <li><a style="color:blue;" href="${pageContext.request.contextPath}/reviewList?code=${prodInfo.code}">Reviews</a></li>

		               <!-- For Manager edit Product -->
		               <security:authorize  access="hasRole('ROLE_MANAGER')">
		                 <li><a style="color:red;" href="${pageContext.request.contextPath}/product?code=${prodInfo.code}">Edit Product</a></li>
		                 <li><a style="color:blue;" href="${pageContext.request.contextPath}/review?code=${prodInfo.code}">Create Review</a></li>
		               </security:authorize>
		               <security:authorize  access="hasRole('ROLE_EMPLOYEE')">
		                 <li><a style="color:blue;" href="${pageContext.request.contextPath}/review?code=${prodInfo.code}">Create Review</a></li>
		               </security:authorize>
		               <security:authorize  access="hasRole('ROLE_USER')">
		                 <li><a style="color:blue;" href="${pageContext.request.contextPath}/review?code=${prodInfo.code}">Create Review</a></li>
		               </security:authorize>
               		</div>
               </div>
           </ul>
       </div>

   </c:forEach>
   <br/>


   <c:if test="${paginationProducts.totalPages > 1}">
       <div class="page-navigator">
          <c:forEach items="${paginationProducts.navigationPages}" var = "page">
              <c:if test="${page != -1 }">
                <a href="productList?page=${page}" class="nav-item">${page}</a>
              </c:if>
              <c:if test="${page == -1 }">
                <span class="nav-item"> ... </span>
              </c:if>
          </c:forEach>

       </div>
   </c:if>

   <jsp:include page="_footer.jsp" />

</body>
</html>
