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

   <div class="page-title">Review List</div>

   <c:forEach items="${paginationReviews.list}" var="reviewInfo">
       <div class="product-preview-container-review">
   			<!-- <div class="product-preview-container-inner"> -->
           <ul>
               <%-- <li><img class="product-image" src="${pageContext.request.contextPath}/productImage?code=${reviewInfo.code}" /></li> --%>
               <li>Code: ${reviewInfo.code}</li>
               <li>Name: ${reviewInfo.name}</li>
               <li>Review Date: ${reviewInfo.reviewCreateDate}</li>
               <li>Review: ${reviewInfo.review}</li>
               <%-- <li>Publisher: ${reviewInfo.publisher}</li>
               <li>Price: <fmt:formatNumber value="${reviewInfo.price}" type="currency"/></li>
               <li>Tags: ${reviewInfo.tags}</li>
               <li><a href="${pageContext.request.contextPath}/buyProduct?code=${reviewInfo.code}">Buy Now</a></li>

               <!-- For Manager edit Product -->
               <security:authorize  access="hasRole('ROLE_MANAGER')">
                 <li><a style="color:red;" href="${pageContext.request.contextPath}/product?code=${reviewInfo.code}">Edit Product</a></li>
                 <li><a style="color:blue;" href="${pageContext.request.contextPath}/review?code=${reviewInfo.code}">Review</a></li>
               </security:authorize> --%>
           </ul>
           <!-- </div> -->
       </div>

   </c:forEach>
   <br/>


   <c:if test="${paginationReviews.totalPages > 1}">
       <div class="page-navigator">
          <c:forEach items="${paginationReviews.navigationPages}" var = "page">
              <c:if test="${page != -1 }">
                <a href="reviewList?page=${page}" class="nav-item">${page}</a>
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
