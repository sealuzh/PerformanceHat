<%-- Skeleton for all pages. --%>
<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@attribute name="head" fragment="true" %>
<%@attribute name="title" required="true" %>
<%@attribute name="scripts" fragment="true"%>

<!DOCTYPE html>
<html>
  <head>
  
    <meta charset="ISO-8859-1">
    <title>${title}</title>
    
    <link href="/static/style/main.css" rel="stylesheet">
    
    <jsp:invoke fragment="head" />
    
  </head>
  
  <body>
  
    <h1>${title}</h1>
    
    <div id="body">
      <jsp:doBody/>
    </div>
    
    <jsp:invoke fragment="scripts" />
    
  </body>
  
</html>
