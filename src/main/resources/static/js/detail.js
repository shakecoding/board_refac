// 바닐라 js
function goUpdate() {
    if (!confirm('게시글을 수정하시겠습니까?')) {
        return; // 사용자가 취소를 선택한 경우 아무 것도 하지 않습니다.
    }

    var boardId = document.querySelector('input[name="boardId"]').value;
    window.location.href = '/board/edit/' + boardId;
}

function goDelete() {
    if (confirm('게시글을 삭제하시겠습니까?')) {
        // 사용자가 '확인'을 선택했을 경우, 삭제 절차 진행
        var boardId = document.querySelector('input[name="boardId"]').value;
        // Form을 통해 POST 요청으로 서버에 삭제를 요청하도록 변경
        var form = document.createElement('form');
        form.method = 'post';
        form.action = '/board/delete/' + boardId;
        document.body.appendChild(form);
        form.submit();
    }
    // 사용자가 '취소'를 선택한 경우, 함수를 종료하고 아무것도 하지 않습니다.
}

// 날짜 포맷
function formatDate(dateString) {
    const now = new Date();
    const commentDate = new Date(dateString); // 문자열을 Date 객체로 변환

    const nowYear = now.getFullYear();
    const nowMonth = now.getMonth();
    const nowDate = now.getDate();

    const commentYear = commentDate.getFullYear();
    const commentMonth = commentDate.getMonth();
    const commentDateDate = commentDate.getDate();

    let displayText = "";

    // 년, 월, 일이 모두 같은 경우 "오늘"로 표시
    if (nowYear === commentYear && nowMonth === commentMonth && nowDate === commentDateDate) {
        displayText = "오늘";
    } else {
        // 그 외의 경우, 정해진 포맷으로 표시
        const yy = commentYear.toString().slice(-2); // 마지막 두 자리를 가지고 옴.
        const M = commentMonth + 1; // 월은 0부터 시작하므로 1을 더해줍니다.
        const d = commentDateDate;
        const HH = commentDate.getHours().toString().padStart(2, '0');
        const mm = commentDate.getMinutes().toString().padStart(2, '0'); // 두자리 수 일 때 앞에 0을 붙임.

        displayText = `${yy}년 ${M}월 ${d}일 ${HH}시 ${mm}분`;
    }
    return displayText;
}

// 댓글 관련 jQuery

const loginId = $('input[name="loginId"]').val(); // 로그인 ID 가져오기

// 페이지가 로드될 때 댓글 목록을 불러오는 함수 호출
$(document).ready(function () {
    var boardId = $('input[name="boardId"]').val(); // 게시글 ID 가져오기
    getComments(boardId); // 댓글 목록 불러오기
});


// 댓글 추가
function addComment() {
    var boardId = $('input[name="boardId"]').val(); // 게시글 ID 가져오기
    var commentContent = $('#commentContent').val(); // 댓글 내용 가져오기


    // 댓글 내용이 비어있는 경우 경고
    if (!commentContent) {
        alert('댓글 내용을 입력해주세요.');
        return;
    }

    $.ajax({
        type: "POST",
        url: "/api/comments", // 여기는 실제 댓글 등록을 처리하는 API 경로로 변경해야 합니다.
        contentType: "application/json",
        data: JSON.stringify({
            boardId: boardId,
            commentContent: commentContent,
            providerId: loginId
        }),
        success: function (response) {
            console.log('댓글 등록 성공:', response);
            // 댓글 입력란 초기화
            $('#commentContent').val('');
            // 댓글 목록 새로고침 등의 처리
            getComments(boardId); // 댓글 목록을 새로고침하는 함수를 호출해야 합니다.
        },
        error: function (error) {
            console.error('댓글 등록 실패:', error);
        }
    });
}

// 댓글 목록 조회 함수
function getComments(boardId) {
    $.ajax({
        type: "GET",
        url: "/api/comments/" + boardId,
        success: function (comments) {
            var commentListSection = $('.comment-list')
            // 댓글 목록을 비웁니다.
            commentListSection.empty();

            // 타임리프는 서버 사이드 템플릿 엔진이라서 서버에서 HTML에 랜더링 할 때 사용.
            // Ajax 는 클라이언트 측에서 실행되는 친구이기 때문에 th 는 사용할 수 없다.
            if (comments.length === 0) {
                commentListSection.append(
                    `<div class='alert alert-info' role='alert'>
                             첫번째 댓글을 남겨주세요!
                         </div>`
                );
            }

            // 받아온 댓글 데이터로 댓글 목록을 만듭니다.
            comments.forEach(function (comment) {
                var commentDate = formatDate(comment.commentRegisterDate);
                var editIndicator = "";

                // 댓글 수정일이 등록일과 다를 경우 "수정됨" 표시를 추가합니다.
                if (comment.commentUpdateDate && comment.commentRegisterDate !== comment.commentUpdateDate) {
                    var updateDate = formatDate(comment.commentUpdateDate);
                    commentDate = updateDate;
                    editIndicator = " (수정)";
                }
                var buttons = "";

                if (comment.providerId === loginId) {
                    buttons = `<div class="comment-actions">
                                   <button onclick="editComment(${comment.commentId})" class="btn btn-primary">수정</button>
                                   <button onclick="deleteComment(${comment.commentId})" class="btn btn-danger">삭제</button>
                               </div>`;
                }
                var commentElement = `
                        <div class="comment-card" id="comment-${comment.commentId}">
                            <div class="comment-body">
                                <div class="comment-title">${comment.name}</div> <!-- 댓글 작성자 이름 -->
                                <div class="comment-subtitle">${commentDate}${editIndicator}</div> <!-- 댓글 작성일 -->
                                <p class="comment-text">${comment.commentContent}</p> <!-- 댓글 내용 -->
                                ${buttons}<!--수정 삭제 버튼-->
                            </div>
                        </div>`;

                // 만든 댓글 목록을 페이지에 추가합니다.
                commentListSection.append(commentElement);
            });
        },
        error: function (error) {
            console.error('댓글 목록 불러오기 실패:', error);
        }
    });
}


// 댓글 목록 조회 함수 내부에서 호출될 댓글 수정 폼 생성 함수
function createCommentEditForm(commentId, currentContent) {
    return `
        <div class="mb-3">
            <textarea class="form-control comment-edit-content" rows="3">${currentContent}</textarea>
        </div>
        <button class="btn btn-primary" onclick="updateComment(${commentId})">수정완료</button>
        <button class="btn btn-secondary" onclick="cancelEdit()">취소</button>
    `;
}

// 댓글 수정 이벤트 처리기
function editComment(commentId) {
    // 기존 댓글 내용을 가진 요소를 찾아 수정 폼으로 대체합니다.
    var commentCard = $(`#comment-${commentId}`);
    var currentContent = commentCard.find('.comment-text').text();
    commentCard.find('.comment-body').html(createCommentEditForm(commentId, currentContent));
}

// 댓글 수정 취소 이벤트 처리기
function cancelEdit() {
    // 댓글 목록을 새로고침하여 기존의 댓글을 다시 불러옵니다.
    getComments($('input[name="boardId"]').val());
}

// 댓글 수정 완료 이벤트 처리기
function updateComment(commentId) {
    var updatedContent = $(`#comment-${commentId}`).find('.comment-edit-content').val();

    // 비어 있는 경우 경고
    if (!updatedContent) {
        alert('수정할 내용을 입력해주세요.');
        return;
    }

    $.ajax({
        type: "PUT",
        url: `/api/comments/${commentId}`,
        contentType: "application/json",
        data: JSON.stringify({commentContent: updatedContent}),
        success: function (response) {
            console.log('댓글 수정 성공:', response);
            // 댓글 목록 새로고침
            getComments($('input[name="boardId"]').val());
        },
        error: function (error) {
            console.error('댓글 수정 실패:', error);
        }
    });
}

// 댓글 삭제 이벤트 처리기
function deleteComment(commentId) {
    if (!confirm('댓글을 삭제하시겠습니까?')) {
        return; // 사용자가 취소를 선택한 경우 아무 것도 하지 않습니다.
    }

    $.ajax({
        type: "DELETE",
        url: `/api/comments/${commentId}`,
        success: function (response) {
            console.log('댓글 삭제 성공:', response);
            // 댓글 목록 새로고침
            getComments($('input[name="boardId"]').val());
        },
        error: function (error) {
            console.error('댓글 삭제 실패:', error);
            alert('댓글 삭제에 실패했습니다. 다시 시도해주세요.');
        }
    });
}