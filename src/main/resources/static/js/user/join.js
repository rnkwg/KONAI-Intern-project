// id 중복 확인
let idFlag = false;
function checkId() {
    const id = $('#id').val(); //id값이 "id"인 입력란의 값을 저장

    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');

    $.ajax({
        url:'/user/idCheck', //Controller에서 요청 받을 주소
        type:'post', //POST 방식으로 전달
        data:{"id":id},
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(idCheck){ //컨트롤러 에서 넘어온 cnt값을 받는다
            if(idCheck == 0){ //cnt가 1이 아니면(=0일 경우) -> 사용 가능한 아이디
                $('.idOk').css("display","inline-block");
                $('.idError').css("display", "none");
                idFlag = true;
            } else { // cnt가 1일 경우 -> 이미 존재하는 아이디
                idFlag = false;
                $('.idError').css("display","inline-block");
                $('.idOk').css("display", "none");
                $('#id').val('');
                alert("id 다시 입력해주세요.");
            }
        },
        error:function(request, status, error){
            // alert("에러입니다");
            alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
        }
    });
}

// nickname 중복 확인
function checkNickname() {
    const nickname = $('#nickname').val(); //id값이 "id"인 입력란의 값을 저장
    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');
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
            // alert("에러입니다");
            alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
        }
    });
}

//빈칸 없는지 확인
function validateBlank() {
    if (joinForm.id.value == "") {
        alert("id 입력하세요.");
        return false;
    }
    if (joinForm.password.value == "") {
        alert("password 입력하세요.");
        return false;
    }
    if (joinForm.nickname.value == "") {
        alert("nickname 입력하세요.");
        return false;
    }
    if ($('#locationUserId').val() == "") {
        alert("location 선택하세요.");
        return false;
    }
    return true;
}

//id 제약조건
function validateId(id) {
    const hangulcheck = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
    if(hangulcheck.test(id)){
        alert("id 한글을 사용 할 수 없습니다.");
        id.focus();
        return false;
    }
    else if(id.search(/\s/) != -1){
        alert("id 공백 없이 입력해주세요.");
        id.focus();
        return false;
    }
    return true;
}

//password 제약조건
function validatePassword(password) {
    const reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,32}$/;
    const hangulcheck = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

    if(false === reg.test(password)) {
        alert('password 8자 이상 32자 이하 이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.');
        password.focus();
        return false;
    }
    else if(hangulcheck.test(password)){
        alert("password 한글을 사용 할 수 없습니다.");
        password.focus();
        return false;
    }
    else if(password.search(/\s/) != -1){
        alert("password 공백 없이 입력해주세요.");
        password.focus();
        return false;
    }
    return true;
}

//nickname 제약조건
function validateNickname(nickname) {
    if(nickname.search(/\s/) != -1){
        alert("nickname 공백 없이 입력해주세요.");
        nickname.focus();
        return false;
    }
    return true;
}

// 빈칸이 있는지 확인 후 회원가입 진행
function check() {
    checkId();
    if (validateBlank() == true && idFlag == true) {
        validateId(joinForm.id.value);
        validatePassword(joinForm.password.value);
        validateNickname(joinForm.nickname.value);

        // ajax 통신
        var header = $("meta[name='_csrf_header']").attr('content');
        var token = $("meta[name='_csrf']").attr('content');

        $.ajax({
            async: "true",
            type: "POST",
            url: "/user/join",
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
                alert("회원가입에 성공했습니다.\n다시 로그인 해주세요.");
                window.location.replace("/");
            },
            error: function (request, status, error) {
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            }
        });
    }
}