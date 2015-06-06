<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>
<script src="js/bootstrap.js" type="text/javascript"></script>
<title>Insert title here</title>
<script type="text/javascript">
window.onload = function(){
	var userName = document.getElementById("userName").value;
	/* alert(userName); */
}
</script>
</head>
<body>
<%-- <h1>userName: <input id="userName" type="text" value="${user.userName}"/> </h1>
<br>
<h1>password: ${user.password}</h1> --%>


<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading"><%=new Date() %></div>
  <div class="panel-body">
    <p>测试表格样式</p>
  </div>

  <table class="table table-hover">
  <tbody>
    <tr>
        <th>#</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Username</th>
    </tr>
    <%
    List<String> list = new ArrayList<String>();
    list.add("active");
    list.add("success");
    list.add("warning");
    list.add("danger");
    list.add("info");
    int j = 4;
    for(int i = 0 ; i < 50 ; i ++){ %>
        <tr <%if(i % 2 == 1){
        j--;
        if(j == -1){
            j = 4;
        }
        %> class="<%=list.get(j)%>"<%} %>><td><%=i+1 %></td><td>Mark  <%=i %></td><td>Otto</td><td>@mdo</td></tr>
    <%} %>
    
  </tbody>
</table>
</div>
<div class="progress">
  <div class="progress-bar progress-bar-success" style="width: 35%">
    <span class="sr-only">35% Complete (success)</span>
  </div>
  <div class="progress-bar progress-bar-warning progress-bar-striped" style="width: 20%">
    <span class="sr-only">20% Complete (warning)</span>
  </div>
  <div class="progress-bar progress-bar-danger" style="width: 10%">
    <span class="sr-only">10% Complete (danger)</span>
  </div>
</div>

</body>
</html>