//빈 칸 확인
function validateBlank() {
    if ($('#categoryId').val() == "") {
        alert("category 선택하세요.");
        return false;
    }
    else
        return true;
}


// kakao map api
// 지도랑 다각형 그리는 함수
function drawPolygon(locationList) {
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(37.5145, 126.9093), // 지도의 중심좌표
            level: 6 // 지도의 확대 레벨
        };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다
    let polygonPath = [];
    for(let i = 0; i < locationList.length; i++) {
        var polyPosition = new kakao.maps.LatLng(locationList[i].lat, locationList[i].lng);
        polygonPath.push(polyPosition);
    }

// 지도에 표시할 다각형을 생성합니다
    var polygon = new kakao.maps.Polygon({
        path: polygonPath, // 그려질 다각형의 좌표 배열입니다
        strokeWeight: 3, // 선의 두께입니다
        strokeColor: '#39DE2A', // 선의 색깔입니다
        strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: 'longdash', // 선의 스타일입니다
        fillColor: '#A2FF99', // 채우기 색깔입니다
        fillOpacity: 0.7 // 채우기 불투명도 입니다
    });

// 지도에 다각형을 표시합니다
    polygon.setMap(map);
}


//검색하기
function search() {
    if(validateBlank() == true) {
        const categoryId = $('#categoryId').val();

        // ajax 통신
        const header = $("meta[name='_csrf_header']").attr('content');
        const token = $("meta[name='_csrf']").attr('content');

        $.ajax({
            type: 'GET',
            url: '/location/categoryResult/categoryId/' + categoryId,
            // contentType: "application/json; charset=UTF-8",
            // dataType : 'json',
            data: ({
                "categoryId": categoryId,
            }),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                drawPolygon(data);
            },
            error: function (request, status, error) {
                alert("searchList / status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            }
        });
    }
}