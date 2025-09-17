<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CHAEKCHECK</title>

<style>
	.container{
	    display: flex;
	    flex-direction: column;
	    align-items: center;
	    width: 100%;
	    min-height: 100vh;
	    box-sizing: border-box;
	}
	
	.myPageMain{
		margin: 0;
	    padding: 0;
	    border: 0;
	    width : 100%;
	    font-size: 100%;
	    font: inherit;
	    vertical-align: baseline;
	    box-sizing: border-box;
	}
	
	.proPic{
		width: 200px;
		height: 200px;
	}
	
	li{
		list-style-type : none;
		display : inline-block;
		border : 1px solid black;
	}
	
	.readBook-wrap, .myProfile-wrap{
		margin : 10px;
		padding : 10px;
		text-align : center;
	}
	
	.myPic, .myInfo, .updateInfo{
		display : inline-block;
	}
	
</style>

</head>
<body>
<%@ include file="../../common/cNav.jsp" %>
<div class="container">
	<div class="myPageMain">
		<!-- 마이페이지 타이틀 -->
		<div class="">
			<h2>마이 책첵</h2>
		</div>
		
		<!-- 프로필 -->
		<div class="myProfile-wrap">
			<div class="myPic"><img src="${pageContext.request.contextPath}/image/userProfile/profile1.jpeg" class="proPic" alt="사용자 프로필"></div>
			<div class="myInfo">
				<div class="myNick">초코럽</div>
	<%-- 					<div class="myNick">${userNck}</div> --%>
			</div>
			<button class="updateInfo" onclick="location.href='updateProfile.log'">프로필 수정</button>
		</div>
		
		<div class="readBook">
			<ul class="readBook-wrap">
				<li>
					<dl>
						<dt>읽은 책</dt>
						<dd>3권</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>작성한 리뷰</dt>
						<dd>4개</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>읽고 싶은 책</dt>
						<dd>궤도</dd>
					</dl>
				</li>
			</ul>
		
		</div>
	</div>
</div>
</body>
</html>