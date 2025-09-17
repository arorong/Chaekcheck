<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CHAEKCHECK</title>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/validation.js"></script>

<style>
	.container{
		display: flex;
		justify-content: center;
		align-items: center; /* 세로 가운데 정렬 */
	}
	.pwChangeContainer{
		margin: auto;
	}	
	a{
		text-decoration: none;
		color: inherit;
	}
	
	
</style>
</head>
<body>

<div class="container">
	<div class="pwChangeContainer">
		<div class="logo">
			<h2><a href="main.log">CHAEKCHECK</a></h2>
		</div>
		<form id="pwChangeForm" novalidate>
			<div>
				<h3>비밀번호 변경</h3>
				<p>변경하실 새 비밀번호를 입력해주세요.</p>
			</div>
			<input type="hidden" name="u_email" value="${u_email}">
			<div onclick="pwInput()">
				<input class="password" type="password" id="updatePw" name="u_pw" placeholder="영어/숫자/특수문자 1개 이상, 8~20자" >
			</div>
			<div class="error-message" id="pwErrorMessage" style="display: none; color: red;">
				<small>✓ 형식에 맞는 비밀번호를 입력해주세요.</small>
			</div>
			<div onclick="pw1Input()">
			<input class="password" type="password" id="updatPw1" name="updatPw1" placeholder="비밀번호 확인">
			</div>
			<div class="error-message" id="pw1ErrorMessage" style="display: none; color: red;">
				<small>✓ 위의 비밀번호와 일치시켜주세요.</small>
			</div>
			<div>
				<button type="submit">비밀번호 변경</button>
			</div>
		</form>
	</div>
</div>

	<script>
	$(document).ready(function() {
	    // 모든 에러/성공 메시지 숨기기
	    function hideAllMessages() {
	        $('.error-message, .success-message').hide();
	    }
	    
	    // 비밀번호 유효성 검사
	    function validatePassword(password) {
	        return password.length >= 8;
	    }
	    
	    // 비밀번호 확인
	    function validatePasswordConfirm(password, confirmPassword) {
	        return password === confirmPassword;
	    }
	    
	    // form 제출
	    $('#pwChangeForm').on('submit', function(e) {
	        e.preventDefault();
	        hideAllMessages();
	        console.log("");
	        
	        var email = $('input[name="u_email"]').val();
	        var password = $('#updatePw').val();
	        var confirmPassword = $('#updatPw1').val();
	        
	        var isValid = true;
	        
	        // 비밀번호 길이 체크
	        if (!validatePassword(password)) {
	            $('#pwErrorMessage').show();
	            isValid = false;
	        }
	        
	        // 비밀번호 확인 체크
	        if (!validatePasswordConfirm(password, confirmPassword)) {
	            $('#pw1ErrorMessage').show();
	            isValid = false;
	        }
	        
	        if (!isValid) {
	            return;
	        }
	        
	        // AJAX 요청
	        $.ajax({
	            url: 'resetPassword.log',
	            type: 'POST',
	            data: {
	                u_email: email,
	                u_pw: password
	            },
	            success: function(data) {
	                if (data === 'success') {
	                    $('#successMessage').show();
	                    // 3초 후 메인 페이지로 이동
	                    setTimeout(function() {
	                    	alert("비밀번호가 변경되었습니다.\n다시 로그인해주세요.");
	                        window.location.href = 'main.log';
	                    }, 3000);
	                } else {
	                    $('#serverErrorMessage').show();
	                }
	            },
	            error: function() {
	                $('#serverErrorMessage').show();
	            }
	        });
	    });
	    
	    // 입력 필드에 포커스가 갔을 때 메시지 숨기기
// 	    $('#updatePw, #updatPw1').on('focus', function() {
// 	        hideAllMessages();
// 	    });
	});
	</script>

</body>
</html>