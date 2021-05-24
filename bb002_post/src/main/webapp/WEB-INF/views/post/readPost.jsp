<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title>
</head>
<body>
  	제목<p>${post.title}</p>
  	내용<p>${post.content}</p>
  	작성자<p>${post.writer.name}</p>
</body>
</html>