// nickname 중복 확인
function checkNickname() {
    const nickname = $('#nickname').val();
    const staticNickname = $('#staticNickname').val();

    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');

    //변경 nickname과 현재 nickname이 동일할때
    if(nickname == staticNickname) {
        alert("기존의 nickname 입니다.");
        $('#nickname').val('');
        return false;
    }

    $.ajax({
        url:'/user/nicknameCheck', //Controller에서 요청 받을 주소
        type:'post', //POST 방식으로 전달
        data:{"nickname":nickname},
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(nicknameCheck){ //컨트롤러에서 넘어온 cnt값을 받는다
            if(nicknameCheck == 0){ //cnt가 1이 아니면(=0일 경우) -> 사용 가능한 아이디
                $('.nicknameOk').css("display","inline-block");
                $('.nicknameError').css("display", "none");
            } else { // cnt가 1일 경우 -> 이미 존재하는 아이디
                $('.nicknameError').css("display","inline-block");
                $('.nicknameOk').css("display", "none");
                alert("nickname 다시 입력해주세요.");
                $('#nickname').val('');
            }
        },
        error:function(request, status, error){
            alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
        }
    });
}

//password 제약조건
function validatePassword(password) {
    if(password != "") {
        const reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,32}$/;
        const hangulcheck = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

        if (false === reg.test(password)) {
            alert('password 8자 이상 32자 이하 이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.');
            password.focus();
            return false;
        } else if (hangulcheck.test(password)) {
            alert("password 한글을 사용 할 수 없습니다.");
            password.focus();
            return false;
        } else if (password.search(/\s/) != -1) {
            alert("password 공백 없이 입력해주세요.");
            password.focus();
            return false;
        }
        return true;
    }
    else
        return true;
}

//nickname 제약조건
function validateNickname(nickname) {
    if(nickname != "") {
        if (nickname.search(/\s/) != -1) {
            alert("nickname 공백 없이 입력해주세요.");
            nickname.focus();
            return false;
        }
        return true;
    }
    else
        return true;
}

// 빈칸 없는지 확인
function validateBlank() {
    if ($('#password').val() == "" && $('#nickname').val() == "" && $('#locationUserId').val() == "") {
        alert("입력된 항목이 없습니다.");
        return false;
    }
    return true;
}

//회원 정보 수정 진행
function check() {

    if(validateBlank() == true) {

        validatePassword(myPageForm.password.value);
        validateNickname(myPageForm.nickname.value);

        // ajax 통신
        var header = $("meta[name='_csrf_header']").attr('content');
        var token = $("meta[name='_csrf']").attr('content');

        $.ajax({
            async: "true",
            type: "POST",
            url: "/user/myPage",
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            contentType: "application/json; charset=UTF-8",
            dataType: 'JSON',
            // dataType: "text",
            data: JSON.stringify({
                id: $('input[name="id"]').val(),
                password: $('input[name="password"]').val(),
                nickname: $('input[name="nickname"]').val(),
                locationUserId: $('#locationUserId').val(),
            }),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                alert("회원정보를 수정했습니다.");
                window.location.replace("/user/myPage");
            },
            error: function (request, status, error) {
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            }
        });
    }
}