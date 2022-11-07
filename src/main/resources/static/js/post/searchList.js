//빈 칸 확인
function validateBlank() {
    if ($('#keyword').val() == "") {
        alert("검색어 입력하세요.");
        return false;
    }
    else
        return true;
}


//검색하기
function check() {
    if(validateBlank() == true) {
        const searchType = $('#searchType').val();
        const keyword = $('#keyword').val();

        // ajax 통신
        const header = $("meta[name='_csrf_header']").attr('content');
        const token = $("meta[name='_csrf']").attr('content');

        $.ajax({
            type: 'GET',
            url: '/post/searchResult/searchType/' + searchType + "/keyword/" + keyword,
            data: ({
                "searchType": searchType,
                "keyword": keyword
            }),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                gridData(data);
            },
            error: function (request, status, error) {
                alert("searchList / status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            }
        });
    }
}


/**
 * grid
 * @type {tui.Grid}
 */
const grid = new tui.Grid({
    el: document.getElementById('grid'),
    pageOptions: {
        perPage: 10,
        useClient: true,
    },
    scrollX: false,
    scrollY: false,
    rowHeaders: ['rowNum'],
    columns: [
        {
            header: '게시물 번호',
            name: 'postId'
        },
        {
            header: '제목',
            name: 'title'
        },
        {
            header: '작성자',
            name: 'nickname'
        },
        {
            header: '작성날짜',
            name: 'createDateFormat'
        },
        {
            header: '수정날짜',
            name: 'updateDateFormat'
        }
    ],
});

//grid에 postList넣기
function gridData(postList) {
    let data = [];
    for(let i = 0; i < postList.length; i++) {
        const post = {};
        post.postId = postList[i].postId;
        post.title = postList[i].title;
        post.nickname = postList[i].user.nickname;
        post.createDateFormat = postList[i].createDateFormat;
        post.updateDateFormat = postList[i].updateDateFormat;

        data.push(post);
    }
    grid.resetData(data);
}

grid.hideColumn('postId');

/**
 * grid 클릭하면 게시물 보기
 */
grid.on('click', (ev) => {
    const row = ev.instance.getRow(ev.rowKey);
    location.href = "/post/view/postId/" + row.postId;
});