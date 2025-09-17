<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Chaekcheck Nav</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/validation.js"></script>
	
<style>
	body {
		background-color: white;
	}

	.custom-navbar {
	    background-color: transparent !important;
	    margin: 0 50px;
	    border: none !important;
	}

	.search-form {
		display: flex;
		align-items: center;
		margin-bottom: 0;
	}

	.custom-search-input {
		width: 300px;
		padding: 8px 15px;
		border: 1px solid #ddd;
		font-size: 0.9rem;
		margin-left: 10px;
	}
	
	.modal-header, .modal-footer{
		border : none;
	}
	
	.modal-footer{
		padding: 10px 0;
	}
	

	.login-modal, .join-modal {
		padding: 20px;
	}

	
	.login-group {
		margin-bottom: 15px;
	}
	
	.join-group{
		margin-top: 15px;
	}



	.login-group input,
	.join-group input {
		width: 100%;
		padding: 10px;
		border: 1px solid #ddd;
		border-radius: 5px;
		font-size: 14px;
	}

	.mainBtn {
		width: 100%;
		padding: 10px;
		margin: 0;
		background-color: #007bff;
		color: white;
		border: none;
		border-radius: 5px;
		font-size: 16px;
		cursor: pointer;
	}

	.mainBtn:hover {
		background-color: #0056b3;
	}

	.error-message {
		color: red;
		font-size: 14px;
/* 		margin-top: 10px; */
	}

	.success-message {
		color: green;
		font-size: 14px;
		margin-top: 10px;
	}

	.link-group {
		text-align: center;
		margin-top: 20px;
	}

	.link-group a {
		color: #007bff;
		text-decoration: none;
		margin: 0 10px;
		font-size: 14px;
	}

	.link-group a:hover {
		text-decoration: underline;
	}
	
	.foundEmail, .foundpw {
		color: #007bff;
        text-decoration: none;
        margin: 0 15px;
        font-size: 14px;
    }
    
    .link-container, .switch-modal {
        text-align: center;
        margin: 15px 0;
   	}
   	
   	hr{
   		margin-top : 0;
   	}
	
</style>
</head>
<body>
	<nav class="navbar navbar-light bg-light custom-navbar px-4" style="justify-content: space-between; flex-wrap: nowrap;">
	  <div class="d-flex align-items-center">
	    <a class="navbar-brand font-weight-bold mr-4" href="main.jsp">책첵</a>
	    <a class="nav-link px-3" href="main.log">홈</a>
	    <a class="nav-link px-3" href="book.log">도서</a>
	  </div>
	
	  <div class="d-flex align-items-center">
	    <form class="form-inline mr-3" action="searchBook.log" method="GET" >
	      <input class="form-control form-control-sm" name="bkeyword" type="search" placeholder="검색" style="width: 200px;">
	    </form>
	    
		<!-- 비회원 -->
	    <c:if test = "${userEmail eq null}">
		    <button type="button" class="btn btn-sm btn-link text-secondary" data-toggle="modal" data-target="#loginModal" onclick="openLoginModal()">로그인</button>
		    <button type="button" class="btn btn-sm btn-link text-secondary" data-toggle="modal" data-target="#joinModal" onclick="openJoinModal()">회원가입</button>
	 	</c:if>
	 	
		<!-- 회원 -->
	 	<c:if test = "${userEmail ne null}">
		 	<button type="button" class="btn btn-sm btn-link text-secondary" onclick="location.href='myPageHome.log'">마이페이지</button>
		    <button type="button" class="btn btn-sm btn-link text-secondary" onclick="location.href='logout.log'" >로그아웃</button>
	 	</c:if>
	  </div>
	</nav>
	
	<!-- 로그인 모달 -->
	<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">CHAECKCHECK</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <h3 class="loginWord" style="text-align:center; margin: 20px 0 0 0;">로그인</h3>
	      <div class="modal-body">
			  <form class="login-modal" id="loginForm" novalidate>
			    <div class="login-group">
			      <input class="email" type="email" id="u_email" name="u_email" placeholder="이메일">
			    </div>
			    <div class="login-group">
			      <input class="password" type="password" id="u_pw" name="u_pw" placeholder="비밀번호">
			    </div>
			    <div class="alert alert-danger" role="alert" style="display: none;"></div>
			    <div id="loginError" class="error-message"></div>
			    <div id="loginSuccess" class="success-message"></div>
			    <div class="modal-footer">
			      <button class="mainBtn" type="button" onclick="loginBtn()">로그인</button>
			    </div>
			  </form>
			  <div class="link-container">
			    <a class="foundpw" href="findPw.log">비밀번호 찾기</a><br>
			  </div>
			  <div class="switch-modal">계정이 없으신가요? <a class="joinMem" onclick="switchJoin()">회원가입</a></div>
		</div>
	    </div>
	  </div>
	</div>
	
	<!-- 회원가입 모달 -->
	<div class="modal fade" id="joinModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">CHAECKCHECK</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="join-form">
		      <h3 class="joinWord" style="text-align:center; margin: 20px 0 0 0;">회원가입</h3>
		      <div class="modal-body">
				  <form class="join-modal" id="registerForm" name="registerForm" action="registerUser.log" onsubmit="return validate()" method="post" novalidate>
				    <div class="join-group" onclick="emailInput()">
				      <input class="email" type="email" id="registerEmail" name="u_email" placeholder="이메일" autocomplete="off">
				    </div>
				    <div class="error-message" id="emailNoneMessage" style="display:none; color:red;"><small>이메일을 입력해주세요.</small></div>
				    <div class="error-message" id="emailErrorMessage" style="display:none; color:red;"><small>이메일을 올바른 형식으로 입력해주세요.</small></div>
				    <div class="error-message" id="emailCheckMessage" style="display:none; color:red;"><small>이미 존재하는 이메일입니다.</small></div>
				    <div class="join-group">
				      <div class="join_input">
				        <input class="nickname" type="text" id="registerNick" name="u_nick" placeholder="닉네임" autocomplete="off" onclick="nickInput()">
				      </div>
				    </div>
				    <div class="error-message" id="nickErrorMessage" style="display: none; color: red;"><small>한글/영어/숫자로 2~10자 사이로 입력해주세요.</small></div>
				    <div class="error-message" id="nickCheckMessage" style="display: none; color: red;"><small>이미 존재하는 닉네임입니다.</small></div>
				    <div class="join-group">
				      <div class="join_input">
				        <input class="password" type="password" id="registerPw" name="u_pw" placeholder="비밀번호" onclick="pwInput()">
				      </div>
				    </div>
				    <div class="error-message" id="pwErrorMessage" style="display: none; color: red;"><small>영어/숫자/특수문자를 각각 1개 이상 포함하여 8~20자 사이로 입력해주세요.</small></div>
				    <div class="join-group">
				      <div class="join_input">
				        <input class="password" type="password" id="registerPw1" name="registerPw1" placeholder="비밀번호 확인" onclick="pw1Input()">
				      </div>
				    </div>
				    <div class="error-message" id="pw1ErrorMessage" style="display: none; color: red;"><small>위의 비밀번호와 일치시켜 주세요.</small></div>
				  </form>
				</div>
			<div class="modal-footer">
			  <button class="mainBtn" type="submit" form="registerForm">회원가입</button>
			</div>
			<div class="switch-modal">이미 계정이 있으신가요? <a class="loginMem" onclick="switchLogin()">로그인</a></div>
	      </div>
	    </div>
	  </div>
	</div>
	<hr>
	<script>
	// 모달 스위칭 할 때 창 여닫기
	function openLoginModal() {
		$('#loginModal').modal('show');
	}

	function closeLoginModal() {
	   	$('#loginModal').modal('hide');
	}

	function openJoinModal() {
	    $('#joinModal').modal('show');
	}

	function closeJoinModal() {
	    $('#joinModal').modal('hide');
	}

	// 로그인 → 회원가입 모달 스위치
	function switchJoin() {
		document.getElementById('loginForm').reset();
		clearMessages();
	 	closeLoginModal();
	    setTimeout(openJoinModal, 300);
	}

	// 회원가입 → 로그인 모달 스위치
	function switchLogin() {
		document.getElementById('registerForm').reset();
		clearMessages();
	    closeJoinModal();
	    setTimeout(openLoginModal, 300);
	}
            	
	// 이전 에러 메시지 숨김 - 모달 전환 또는 재입력 시 화면 초기화
	function clearMessages() {
	  const elements = document.querySelectorAll('.error-message, .success-message');
	  elements.forEach(el => el.style.display = 'none');
	}
	
	// 메시지 표시 - 로그인 실패 등 상황별 안내 표시
	function showMessage(type, message, elementID) {
	  const element = document.getElementById(elementID);
	  element.textContent = message;
	  element.style.display = 'block';
	}
	
	// 로그인 요청
	function loginBtn(){
	if($("#u_email").val() == "" || $("#u_email").val() == null){
		$(".alert-danger").css("display","block").html("이메일을 입력해주세요.");
		myExec = setTimeout(function(){
			$(".alert-danger").css("display","none");
		}, 2000);
	} else if($("#u_pw").val() == "" || $("#u_pw").val() == null){
		$(".alert-danger").css("display","block").html("비밀번호를 입력해주세요.");
		myExec = setTimeout(function(){
			$(".alert-danger").css("display","none");
		}, 2000);
	} else {
		const formData = {
			u_email : $("#u_email").val(),
			u_pw : $("#u_pw").val()
		};
		
		$.ajax({
			url : "loginUser.log",
			type : "POST",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(formData),
			cache : false,
			success : function(data) {
				console.log("data : " + data);
				if(data == 0){
					alert("이메일과 비밀번호를 다시 확인해주세요.");
				} else {
					 console.log("로그인 성공!");
					 alert("환영합니다!");
					 closeLoginModal();
	                 location.href = data;
				}
			},
			error: function() {
				console.error("로그인 요청 실패 : ", error);
				alert("이메일과 비밀번호를 확인해 주세요.");
			}
		});
	}
}
	
	//Enter 키 이벤트
	$("#u_email, #u_pw").on("keyup", function(e) {
	    if (e.key === "Enter" || e.keyCode === 13) {
	        loginBtn(); // 로그인 실행
	    }
	});

	
	</script>
</body>
</html>