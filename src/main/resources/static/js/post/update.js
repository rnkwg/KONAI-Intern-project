//Toast UI Editor
const Editor = toastui.Editor;

const editor = new Editor({
    el: document.querySelector('#editor'),
    height: '500px',
    initialEditType: 'wysiwyg',
    previewStyle: 'vertical',
    hideModeSwitch : true,
    language : 'ko-KR',
    // initialValue: $('#content').val()
});

editor.setMarkdown($('#content').val())


// kakao map api

//위도경도 변수
let lat = $('#lat').val();
let lng = $('#lng').val();

//주소 변수
let address = $('#address').val();

//행정주소 변수
let dongAddress = $('#dongAddress').val();

let markerFlag = false;

var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
var options = { //지도를 생성할 때 필요한 기본 옵션
    center: new kakao.maps.LatLng(lat, lng), //지도의 중심좌표.
    level: 2, //지도의 레벨(확대, 축소 정도)
};

var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

// 마커가 표시될 위치입니다
var markerPosition = new kakao.maps.LatLng($('#lat').val(), $('#lng').val());

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
    position: markerPosition
});

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

// 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
// searchAddrFromCoords(map.getCenter());

// 지도에 클릭 이벤트를 등록합니다
// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
    // 클릭한 위도, 경도 정보를 가져옵니다
    latlng = mouseEvent.latLng;
    lat = latlng.getLat();
    lng = latlng.getLng();
    searchAddrFromCoords(latlng, checkYdp);
});

// 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'idle', function() {
    searchAddrFromCoords(map.getCenter(), displayCenterInfo);
});

function searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청합니다
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
}

function searchDetailAddrFromCoords(coords, callback) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

// 지도 좌측상단에 마커에 대한 주소정보를 표출하는 함수입니다
function displayCenterInfo(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        var infoDiv = document.getElementById('centerAddr');

        for(var i = 0; i < result.length; i++) {
            // 행정동의 region_type 값은 'H' 이므로
            if (result[i].region_type === 'H') {
                infoDiv.innerHTML = result[i].address_name;
                dongAddress = result[i].address_name;
                break;
            }
        }
    }
}


//마커의 위치가 영등포구인지 확인
function checkYdp(result, status) {
    // 좌표로 행정동 주소 정보를 요청합니다
    var notYdp = false;

    if (status === kakao.maps.services.Status.OK) {

        for(var i = 0; i < result.length; i++) {

            if (result[i].region_2depth_name === '영등포구') {
                markerFlag = true;

                searchAddrFromCoords(latlng, displayCenterInfo);

                searchDetailAddrFromCoords(latlng, function (result, status) {
                    if (status === kakao.maps.services.Status.OK) {
                        // var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';

                        address = result[0].address.address_name;

                        var content = '<div class="bAddr">' +
                            '<span class="title">주소정보</span><div>지번주소 : ' +
                            address +
                            '</div></div>';

                        // 마커를 클릭한 위치에 표시합니다
                        marker.setPosition(latlng);
                        marker.setMap(map);

                        // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
                        infowindow.setContent(content);
                        infowindow.open(map, marker);
                    }
                });
            }

            else {
                notYdp = true;
                break;
            }
        }

        if(notYdp == true)
            alert("영등포구를 선택해주세요");
    }
}


// 빈칸 없는지 확인
function validateBlank(content) {
    if ($('#title').val() == "") {
        alert("title 입력하세요.");
        return false;
    }
    if (content == "") {
        alert("content 입력하세요.");
        return false;
    }
    return true;
}


//첨부파일이 있으면 true 없으면 false
let fileFlag = false;

//첨부파일 추가
function addFile() {
    if(fileFlag == false) {
        var str = "<div><input type=\"file\" class=\"custom-file-input\" id=\"file\" name=\"file\">"
        str += "<a class=\"btn btn-primary\" onClick=\"deleteFile($(this))\">파일삭제</a></div>"
        $("#file-list").append(str);
        fileFlag = true;
    }
    else {
        alert("첨부파일은 하나만 가능합니다.");
    }
}

//기존의 첨부파일 삭제 후 추가
function addOriginFile() {
    if(fileFlag == false) {
        var str = "<div><input type=\"file\" class=\"custom-file-input\" id=\"file\" name=\"file\">"
        str += "<a class=\"btn btn-primary\" onClick=\"deleteFile($(this))\">파일삭제</a></div>"
        $("#file-list-origin").append(str);
        fileFlag = true;
    }
    else {
        alert("첨부파일은 하나만 가능합니다.");
    }
}

//첨부파일 있는지 확인
function checkFile() {
    if ($('#file')[0].files[0] == null) {
        alert("첨부파일이 없습니다.");
        return false;
    }
    return true;
}

//첨부파일 삭제
function deleteFile(obj) {
    if(checkFile() == true) {
        const result = confirm("첨부파일을 삭제하겠습니까?");
        if(result == true) {
            obj.parent().remove();
            fileFlag = false;
        }
    }
}

//기존의 첨부파일 삭제
function deleteOriginFile(obj) {
    const result = confirm("첨부파일을 삭제하겠습니까?");
    if(result == true) {
        obj.parent().remove();
    }
    fileFlag = false;
    var str = "<a class=\"btn btn-primary\" onclick=\"addOriginFile()\">파일추가</a><br>";
    $("#file-list-origin").prepend(str);
}


//빈칸이 있는지 확인 후 글 수정
function check() {
    const content = editor.getMarkdown();

    if(validateBlank() == true) {
        // ajax 통신
        const header = $("meta[name='_csrf_header']").attr('content');
        const token = $("meta[name='_csrf']").attr('content');
        const postId =  $('#postId').val();

        const formData = new FormData();

        formData.append("postId", postId);
        formData.append("title", $('#title').val());
        formData.append("content", content);

        formData.append("locationId", $('#locationId').val());
        formData.append("lat", lat);
        formData.append("lng", lng);
        formData.append("address", address);
        formData.append("dongAddress", dongAddress);

        if($('#file')[0] != null) {
            formData.append("file", $('#file')[0].files[0]);
        }

        $.ajax({
            // async: "true",
            type: "POST",
            url: "/post/update",
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            processData: false,
            contentType: false,
            // contentType: "application/json; charset=UTF-8",
            // dataType: 'JSON',
            // data: JSON.stringify({
            //     categoryId: $('#categoryId').val(),
            //     postId: postId,
            //     title: $('#title').val(),
            //     content: content,
            //     locationId: $('#locationId').val(),
            // }),
            data: formData,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                alert("글을 수정했습니다.");
                window.location.replace("/post/view/postId/" + postId);
            },
            error: function (request, status, error) {
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            }
        });
    }
}