document.addEventListener('DOMContentLoaded', function () {
    let selects = document.querySelectorAll('.auto-select');

    selects.forEach(function(select) {
        let selectedValue = select.dataset.selectedValue; // ここでdata-selected-valueの値を読み取れます。
        if (selectedValue && selectedValue !== '') {
            select.value = selectedValue; // セレクトボックスに値をセット
        }
    });
});
