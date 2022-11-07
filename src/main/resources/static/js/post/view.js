//Toast UI Editor
const viewer = toastui.Editor.factory({
    el: document.querySelector('#viewer'),
    viewer: true,
    height: '500px',
    initialEditType: 'wysiwyg',
    previewStyle: 'vertical',
    hideModeSwitch : true,
    language : 'ko-KR',
    initialValue: $('#content').val()
});


// kakao map api
var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng($('#lat').val(), $('#lng').val()), // 지도의 중심좌표
        level: 2 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 마커가 표시될 위치입니다
var markerPosition  = new kakao.maps.LatLng($('#lat').val(), $('#lng').val());

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
    position: markerPosition
});

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);


//글 삭제
function deletePost() {
    const result = confirm("글을 삭제하겠습니까?");

    if(result == true) {
        // ajax 통신
        const header = $("meta[name='_csrf_header']").attr('content');
        const token = $("meta[name='_csrf']").attr('content');

        const postId = $('#postId').val();
        const categoryId = $('#categoryId').val();

        $.ajax({
            type: 'GET',
            url: '/post/delete/postId/' + postId,
            // data:{"postId":postId},

            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function () {
                alert("글을 삭제했습니다.");
                window.location.replace("/post/list/categoryId/" + categoryId);
            },
            error: function (request, status, error) {
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            }
        });
    }
}


//댓글 빈칸 없는지 확인
function validateBlank() {
    if ($('#comment').val() == "") {
        alert("댓글 내용 입력하세요.");
        return false;
    }
    return true;
}

// 빈칸이 있는지 확인 후 댓글 등록
function writeComment() {
    if(validateBlank() == true) {
        // ajax 통신
        const header = $("meta[name='_csrf_header']").attr('content');
        const token = $("meta[name='_csrf']").attr('content');

        const postId = $('#postId').val();

        $.ajax({
            async: "true",
            type: "POST",
            url: "/post/comment/write",
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            contentType: "application/json; charset=UTF-8",
            dataType: 'JSON',
            data: JSON.stringify({
                postId: postId,
                content: $('#comment').val(),
            }),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                alert("댓글을 작성했습니다.");
                window.location.replace("/post/view/postId/" + postId);
            },
            error: function (request, status, error) {
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            },
        });
    }
}

let updateCommentId;
//댓글 수정 폼하기
function updateFormComment(commentId) {
    updateCommentId = commentId;

    // readonly 삭제
    $("#commentForm" + commentId).removeAttr("readonly");

    //버튼 text, onClick 수정
    document.getElementById('updateButton' + commentId).innerText = '수정하기';
    $("updateButton" + commentId).removeAttr("onClick");
    document.getElementById('updateButton' + commentId).setAttribute("onClick", "updateComment(); ");
}

//댓글 수정 반영하기
function updateComment() {
    if ($('#commentForm' + updateCommentId).val() == "") {
        alert("댓글 내용 입력하세요.");
        return false;
    } else {
        const header = $("meta[name='_csrf_header']").attr('content');
        const token = $("meta[name='_csrf']").attr('content');

        const postId = $('#postId').val();

        $.ajax({
            async: "true",
            type: "POST",
            url: '/post/comment/update/commentId/' + updateCommentId,
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            contentType: "application/json; charset=UTF-8",
            dataType: 'JSON',
            // dataType: "text",
            data: JSON.stringify({
                postId: postId,
                content: $('#commentForm' + updateCommentId).val(),
            }),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                alert("댓글을 수정했습니다.");
                window.location.replace("/post/view/postId/" + postId);
            },
            error: function (request, status, error) {
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            },
        });
    }
}

//댓글 삭제
function deleteComment(commentId) {
    const result = confirm("댓글을 삭제하겠습니까?");

    if(result == true) {
        // ajax 통신
        const header = $("meta[name='_csrf_header']").attr('content');
        const token = $("meta[name='_csrf']").attr('content');

        const postId = $('#postId').val();

        $.ajax({
            type: 'GET',
            url: '/post/comment/delete/commentId/' + commentId,

            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function () {
                alert("댓글을 삭제했습니다.");
                window.location.replace("/post/view/postId/" + postId);
            },
            error: function (request, status, error) {
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            }
        });
    }
}