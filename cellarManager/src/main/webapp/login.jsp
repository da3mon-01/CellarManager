<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>

 
<html>
  <head>
    <title>CellarManager - Login</title>
    <style>
    	body{
    		background : rgb(249, 249, 249);
    		text-align: center;
    	}
    	
    	#center{
			margin-left: auto;
			margin-right: auto;
			width: 250px;
			
		}
    </style>
  </head>
 
  <body>
    <h1>Cellar Manager</h1>
 
 	<div id="center">
    <c:if test="${not empty param.login_error}">
      <font color="red">
        Your login attempt was not successful, try again.<br/><br/>
        Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
      </font>
    </c:if>
 
    <form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
      <table>
        <tr><td>User:</td><td><input type='text' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/></td></tr>
        <tr><td>Password:</td><td><input type='password' name='j_password'></td></tr>
 
        <tr><td colspan='2'><input name="submit" type="submit" value="Login"></td></tr>
      </table>
    </form>
	</div>
  </body>
  
</html>