<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
	<head>
		<title>메인페이지</title>
		<link href="/resources/css/style.css" rel="stylesheet" type="text/css">
		<script	src="https://code.jquery.com/jquery-3.6.0.js"></script>
		<script type="text/javascript">
			$(window).on('scroll', function() {
				if($(window).scrollTop()){
					$('nav').addClass('black');
				}
				else {
					$('nav').removeClass('black');
				}
			})
		</script>		
	</head>
	<body>
		<nav>
			<div class="logo">
				<img src="/resources/img/노른자.png">
			</div>
			<ul>
				<li><a href="#" class="active">Home</a></li>
				<li><a href="#">About</a></li>
				<li><a href="#">Services</a></li>
				<li><a href="#">Portfolio</a></li>
				<li><a href="#">Team</a></li>
				<li><a href="#">Contact</a></li>
			</ul>
		</nav>
		<section class="sec1"></section>
		<section class="content">
		<h1>여기다가는 검색 및 개인화서비스이용 해쉬태그 달기</h1>
		<p>해쉬태그를 사용한 검색시스템 구축과 로그인한 이용자가 가장 이용많이한 해쉬태그 추천</p>
		</section>
		<section class="sec2"></section>
		<section class="sec3"></section>
	</body>
</html>