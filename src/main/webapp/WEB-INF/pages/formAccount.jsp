<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Account</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>

   <jsp:include page="_header.jsp" />
   <jsp:include page="_menu.jsp" />

   <div class="page-title"><h3>Create Account</h3></div>

   <div class="login-container">

   <c:if test="${not empty errorMessage }">
     <div class="error-message">
         ${errorMessage}
     </div>
   </c:if>

   <form:form modelAttribute="accountForm" method="POST" action="saveAccount">
       <table style="text-align:left;">
           <tr>
               <td>Name *</td>
               <td><form:input path="userName" /></td>
               <td><form:errors path="userName" class="error-message" /></td>
           </tr>

           <tr>
               <td>Password:</td>
               <td><form:input path="password" /></td>
               <td><form:errors path="password" class="error-message" /></td>
           </tr>

           <tr>
               <td><form:hidden path="active" value="true"/></td>
               <td><form:hidden path="userRole" value="USER"/></td>
               <td></td>
           </tr>

           <tr>
               <td>&nbsp;</td>
               <td><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></td>
           </tr>
       </table>
   </form:form>
</div>
   <jsp:include page="_footer.jsp" />

</body>
</html>
