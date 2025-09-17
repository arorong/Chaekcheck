//이메일, 닉네임, 비밀번호 유효성 검사 및 중복 확인 JS


const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const nickPattern = /^[a-zA-Z0-9가-힣]{2,10}$/;
const pwPattern = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/;
	
function validate() {
	var istrue = true;
	
	var email = document.getElementById('registerEmail');
	var nick = document.getElementById('registerNick');
	var pw = document.getElementById('registerPw');
	var pw1 = document.getElementById('registerPw1');
	
	var emailNoneMessage = document.getElementById('emailNoneMessage');
	var nickErrorMessage = document.getElementById('nickErrorMessage');
	var pwErrorMessage = document.getElementById('pwErrorMessage');
	var pw1ErrorMessage = document.getElementById('pw1ErrorMessage');
	
	// 모든 에러 메시지 초기화
	emailErrorMessage.style.display = 'none';
	nickErrorMessage.style.display = 'none';
	pwErrorMessage.style.display = 'none';
	pw1ErrorMessage.style.display = 'none';
	
	// 이메일 검증
	if(email.value == '' || email.value == null || !emailPattern.test(email.value)) {
		email.focus();
		emailErrorMessage.style.display = 'block';
		istrue = false;
	}
	
	// 닉네임 검증
	if(!nickPattern.test(nick.value)) {
		nick.focus();
		nickErrorMessage.style.display = 'block';
		istrue = false;
	}
	
	// 비밀번호 검증
	if(pw.value == '' || pw.value == null || !pwPattern.test(pw.value)) {
		pw.focus();
		pwErrorMessage.style.display = 'block';
		istrue = false;
	}
	
	// 비밀번호 재확인
	if(pw1.value != pw.value) {
		pw1.focus();
		pw1ErrorMessage.style.display = 'block';
		istrue = false;
	}
	
	if(nickCheck == 0) {
		istrue = false;
	}
	if(emailCheck == 0) {
		istrue = false;
	}
	if(!istrue){
		alert('입력한 정보를 다시 확인해주세요.');
	}
	console.log(istrue);
	return istrue;
}

var nickCheck = 0;
var emailCheck = 0;

// 닉네임 유효성 검사 함수
function nickInput() {
    var nick = document.getElementById('registerNick');
    var nickErrorMessage = document.getElementById('nickErrorMessage');
    var nickCheckMessage = document.getElementById('nickCheckMessage');
    
    nick.focus(); // Focus on the input field
    
    nick.addEventListener('input', function() {
        var nickvalue = nick.value.trim();
        
        if(nickvalue == "" || !nickPattern.test(nickvalue)) {
            nickErrorMessage.style.display = 'block'; // Display error message
            nickCheckMessage.style.display = 'none'; // Hide duplicate message
            nickCheck = 0; // 사용 불가
        } else {
            nickErrorMessage.style.display = 'none'; // Hide error message
            checkNickDuplicate(nickvalue); // 닉네임 중복 확인 함수로
        }
    });
}

// 닉네임 중복 확인 함수
function checkNickDuplicate(nickvalue) {
    var nickCheckMessage = document.getElementById('nickCheckMessage');
    let nickval = {
        "u_nick": nickvalue
    };
    
    console.log("닉네임 중복 확인 : ", nickvalue);
    
    $.ajax({
        url: "checkNick.log",
        type: "post",
        data: nickval,
        dataType: "json",
        cache: false,
        async: false,
        success: function(data) {
            console.log('닉네임 중복체크 결과:', data);
            if (data == 0) {
                console.log('사용 가능한 닉네임');
                nickCheckMessage.style.display = 'none';
                nickCheck = 1; // 중복 없음, 사용 가능
            } else {
                console.log('중복된 닉네임 - 사용 불가');
                nickCheckMessage.style.display = 'block';
                nickCheck = 0; // 중복 있음, 사용 불가
            }
        },
        error: function(err) {
            console.log('닉네임 중복체크 error:', err);
            nickCheck = 0; // 에러 발생시 중복체크 실패로 처리
        }
    });
}

// 이메일 유효성 검사 함수  
function emailInput() {
    var email = document.getElementById('registerEmail');
    var emailErrorMessage = document.getElementById('emailErrorMessage');
    var emailNoneMessage = document.getElementById('emailNoneMessage');
    var emailCheckMessage = document.getElementById('emailCheckMessage');
    
    email.focus(); // Focus on the input field
    
    email.addEventListener('input', function() {
        var emailvalue = email.value.trim();
        
        if (emailvalue == "") {
            emailNoneMessage.style.display = 'block';
            emailErrorMessage.style.display = 'none';
            emailCheckMessage.style.display = 'none';
            emailCheck = 0;
        } else if (!emailPattern.test(emailvalue)) {
            emailErrorMessage.style.display = 'block';
            emailNoneMessage.style.display = 'none';
            emailCheckMessage.style.display = 'none';
            emailCheck = 0;
        } else {
            emailErrorMessage.style.display = 'none';
            emailNoneMessage.style.display = 'none';

            checkEmailDuplicate(emailvalue); // 이메일 중복 확인 함수로
        }
    });
}

// 이메일 중복 확인 함수
function checkEmailDuplicate(emailvalue) {
    var emailCheckMessage = document.getElementById('emailCheckMessage');
    let emailval = {
        "u_email": emailvalue
    };
    
    console.log("이메일 중복 확인 : ", emailvalue);
    
    $.ajax({
        url: "checkEmail.log",
        type: "post",
        data: emailval,
        dataType: "json",
        cache: false,
        async: false,
        success: function(data) {
            console.log('이메일 중복체크 결과:', data);
            if (data == 0) {
                console.log('사용 가능한 이메일');
                emailCheckMessage.style.display = 'none';
                emailCheck = 1; // 중복 없음, 사용 가능
            } else {
                console.log('중복된 이메일 - 사용 불가');
                emailCheckMessage.style.display = 'block';
                emailCheck = 0; // 중복 있음, 사용 불가
            }
        },
        error: function(err) {
            console.log('이메일 중복체크 error:', err);
            emailCheck = 0; // 에러 발생시 중복체크 실패로 처리
        }
    });
}

// 비밀번호 유효성 검사 함수
function pwInput() {
    var pw = document.getElementById('registerPw');
    var pwErrorMessage = document.getElementById('pwErrorMessage');

    pw.focus(); // Focus on the input field

    pw.addEventListener('input', function() {
        var pwvalue = pw.value.trim();
        if(pwvalue == "" || !pwPattern.test(pwvalue)) {
            pwErrorMessage.style.display = 'block'; // Display error message				
        } else {
            pwErrorMessage.style.display = 'none'; // Hide error message
        }
    });
}

// 비밀번호 확인 유효성 검사 함수
function pw1Input() {
    var pw = document.getElementById('registerPw');
    var pw1 = document.getElementById('registerPw1');
    var pw1ErrorMessage = document.getElementById('pw1ErrorMessage');
    
    pw1.focus(); // Focus on the input field

    pw1.addEventListener('input', function() {
        var pw1value = pw1.value.trim();
        var pwvalue = pw.value.trim();
        if (pw1value == '' || pw1value != pwvalue) {
            pw1ErrorMessage.style.display = 'block'; // Display error message
        } else {
            pw1ErrorMessage.style.display = 'none'; // Hide error message
        }
    });
}
