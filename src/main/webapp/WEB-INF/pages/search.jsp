<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Search</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
	</head>
	<body>
	   <jsp:include page="_header.jsp" />
	   <jsp:include page="_menu.jsp" />

	   <div class="page-title">Search Products</div>
	   <form:form action="doSearch" method="POST">
	   		<br/><br/>
	      <input type="text" class="submit_view_search" name="searchText" style="width: 485px; height: 30px" /><br/><br/>
	      <input type="submit" class="submit_view_search" value="Search" /> <input type="reset" class="submit_view_search" /><br/><br/><br/><br/>
		</form:form>
	   <jsp:include page="_footer.jsp" />
	</body>
</html>
