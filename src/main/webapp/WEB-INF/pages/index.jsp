<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Book Review App</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>

	<div id="fb-root"></div>
	<script>(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = 'https://connect.facebook.net/en_GB/sdk.js#xfbml=1&version=v3.2&appId=427548644677744&autoLogAppEvents=1';
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));</script>


   <jsp:include page="_header.jsp" />
   <jsp:include page="_menu.jsp" />

   <div class="page-title">Spring MVC Application</div>

   <div class="demo-container">
   <h3>Book Review Application</h3>

   <ul>
      <li>Buy Book Online</li>
      <li>Add Book Review</li>
      <li>Order Details</li>
   </ul>
   </div>

   <div class="fb-page" data-href="https://www.facebook.com/My-Application-2108450602580216/" data-tabs="timeline" data-small-header="true" data-adapt-container-width="true" data-hide-cover="false" data-show-facepile="true"><blockquote cite="https://www.facebook.com/My-Application-2108450602580216/" class="fb-xfbml-parse-ignore"><a href="https://www.facebook.com/My-Application-2108450602580216/">My Application</a></blockquote></div>

   <jsp:include page="_footer.jsp" />

</body>
</html>
