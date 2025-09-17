<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang = "ko">
<head>
<title>Chaekcheck</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.js"></script>

	<style>
		body{
			background-color : white;
		}
		
		.container{
			max-width : 100%;
			border : 1px solid black;
			display : flex;
			justify-content: center;
		}
		
		.main-container{
			margin : 50px;
		}
	
	</style>

</head>
<body>
<%@ include file="../common/cNav.jsp" %>
	<div class = "container">
		<div class="main-container">
			<p>이 곳은 책첵 메인 페이지 입니다.<br>
			홈페이지 제작 중이니 조금만 기다려주세요!</p>
		</div>
	</div>
</body>
</html>