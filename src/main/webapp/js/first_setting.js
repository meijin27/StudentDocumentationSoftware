// app.js
document.addEventListener('DOMContentLoaded', function(){
    var submitButton = document.getElementById('submitButton');
    var agreeCheckbox = document.getElementById('agreeCheckbox');
    // Bootstrapのモーダルコンポーネントを初期化
    var myModal = new bootstrap.Modal(document.getElementById('termsModal'), {});

    // 初期状態では送信ボタンを無効化
    submitButton.disabled = true;

    // 同意するチェックボックスの状態が変わるたびに呼び出されるイベントリスナー
    agreeCheckbox.addEventListener('change', function() {
        // チェックボックスがチェックされていれば送信ボタンを有効化
        if(this.checked) {
            submitButton.disabled = false;
        } 
        // チェックボックスがチェックされていなければ送信ボタンを無効化
        else {
            submitButton.disabled = true;
        }
    });

    // 利用規約のリンクがクリックされたときにモーダルを表示する
    document.getElementById('termsLink').addEventListener('click', function(e) {
        e.preventDefault();  // リンクのデフォルトの動作（ページ遷移）を防ぐ
        myModal.show();  // モーダルを表示する
    });
});
