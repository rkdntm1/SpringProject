<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>



	<c:forEach items="${boardList}" var="board">
			<a href="/post/list?boardId=${board.id}">${board.name}</a>
			<br>
	</c:forEach>


</body>
</html>
