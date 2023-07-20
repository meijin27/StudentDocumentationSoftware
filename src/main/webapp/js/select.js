window.onload = function() {
    // サーバーサイドから値を取得
    fetch('../api/getInitialSelectionValues')
    .then(response => response.json())
    .then(data => {
        // 選択肢の初期値を設定する関数
        setInitialSelection('studentType', data.studentType);
        setInitialSelection('birthYear', data.birthYear);
        setInitialSelection('birthMonth', data.birthMonth);
        setInitialSelection('birthDay', data.birthDay);
    })
    .catch(error => console.error('Error:', error));
};

// 選択肢の初期値を設定する関数
function setInitialSelection(name, value) {
    var selectElement = document.querySelector(`select[name="${name}"]`);
    if (selectElement) {
        for (var i = 0; i < selectElement.options.length; i++) {
            if (selectElement.options[i].value === value) {
                selectElement.options[i].selected = true;
                break;
            }
        }
    }
}
