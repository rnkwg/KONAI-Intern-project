//빈 칸 확인
function validateBlank() {
    if ($('#month').val() == "") {
        alert("month 선택하세요.");
        return false;
    }
    else
        return true;
}

//차트에 데이터 넣는 함수
function drawChart(data) {
    let label = [];

    let exerciseData = [];
    let cultureData = [];
    let cookData = [];
    let studyData = [];
    let artData = [];
    let restData = [];

    const ctx = document.getElementById('myChart').getContext('2d');

    const datas = {
        labels: label,
        datasets: [{
            label: '운동',
            data: exerciseData,
            fill: false,
            borderColor: 'rgb(208,13,13)',
            tension: 0.1
        }, {
            label: '문화/예술',
            data: cultureData,
            fill: false,
            borderColor: 'rgb(246,153,28)',
            tension: 0.1
        }, {
            label: '요리',
            data: cookData,
            fill: false,
            borderColor: 'rgb(227,218,39)',
            tension: 0.1
        }, {
            label: '스터디',
            data: studyData,
            fill: false,
            borderColor: 'rgb(46,117,24)',
            tension: 0.1
        }, {
            label: '공예',
            data: artData,
            fill: false,
            borderColor: 'rgb(27,103,204)',
            tension: 0.1
        }, {
            label: '기타',
            data: restData,
            fill: false,
            borderColor: 'rgb(123,24,206)',
            tension: 0.1
        }]
    };

    const myChart = new Chart(ctx, {
        type: 'line',
        data: datas,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Chart.js Line Chart'
                }
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        min: 0,
                        stepSize: 1,
                    }
                }]
            }
        },
    });

    for(let i = 0; i < data.labelList.length; i++) {
        label.push(data.labelList[i]);
        exerciseData.push(data.exerciseList[i]);
        cultureData.push(data.cultureList[i]);
        cookData.push(data.cookList[i]);
        studyData.push(data.studyList[i]);
        artData.push((data.artList[i]));
        restData.push(data.restList[i]);
    }
    myChart.update();
}

//검색하기
function search() {
    if(validateBlank() == true) {
        const month = $('#month').val();

        // ajax 통신
        const header = $("meta[name='_csrf_header']").attr('content');
        const token = $("meta[name='_csrf']").attr('content');

        $.ajax({
            type: 'GET',
            url: '/post/chart/month/' + month,
            // contentType: "application/json; charset=UTF-8",
            // dataType : 'json',
            data: ({
                "month": month,
            }),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                drawChart(data);
            },
            error: function (request, status, error) {
                alert("chart / status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            }
        });
    }
}