#-------------------------------------------------------------------------------
# Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#-------------------------------------------------------------------------------
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
