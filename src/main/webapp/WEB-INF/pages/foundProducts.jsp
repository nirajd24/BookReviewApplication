<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Found Products</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
	</head>
	<body>
	   <jsp:include page="_header.jsp" />
	   <jsp:include page="_menu.jsp" />

	   <div class="page-title">Found Products</div>
		<c:forEach var="foundProducts" items="${foundProducts}">
		<div class="product-preview-container">
			<div class="user_record_info" style="text-align: left;">
                  <div class="user_thumb_box">
                      <div class="user_thumb">
                          <img class="product-image" src="${pageContext.request.contextPath}/productImage?code=${foundProducts.code}" />
                      </div>
                  </div>
                  <div class="user_record_content">
                      <div class="record_group">
                          <div class="record_title">Name :</div>
                          <div class="record_desc">${foundProducts.getName()}</div>
                      </div>
                      <div class="record_group">
                          <div class="record_title">Author :</div>
                          <div class="record_desc">${foundProducts.getAuthor()}</div>
                      </div>
                      <div class="record_group">
                          <div class="record_title">Publisher :</div>
                          <div class="record_desc">${foundProducts.getPublisher()}</div>
                      </div>
                      <div class="record_group">
                          <div class="record_title">Tags :</div>
                          <div class="record_desc">${foundProducts.getTags()}</div>
                      </div>
                  </div>
              </div>
              </div>
		</c:forEach>
	   <jsp:include page="_footer.jsp" />
	</body>
</html>
