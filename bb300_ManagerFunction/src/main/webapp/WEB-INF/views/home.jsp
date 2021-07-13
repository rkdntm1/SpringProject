<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri= "http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>!Hello 배준수 선생님 World!</h1>

	
	<sec:authorize access="isAnonymous()">
		<a href="/party/customLogin">로그인</a>
	</sec:authorize>
	
	<sec:authorize access="isAuthenticated()">
		<c:forEach items="${boardList}" var="board">
			<a href="/post/listBySearch?boardId=${board.id}">${board.name}</a>
			&emsp;
		</c:forEach>
		<br>
		<a href="/party/customLogout">로그아웃</a>
	</sec:authorize>
</body>
</html>
