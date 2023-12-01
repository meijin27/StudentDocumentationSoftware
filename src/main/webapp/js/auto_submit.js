// ページがロードされた時にフォームを自動送信
window.onload = function() {
    // 'submitted' 属性が存在しない場合のみフォームを送信
	var formElement = document.getElementById('autoSubmitForm');
    var isSubmitted = formElement.getAttribute('data-submitted') === 'true';
    if (!isSubmitted) {
        formElement.submit();
    }
}