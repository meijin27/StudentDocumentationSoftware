document.addEventListener('DOMContentLoaded', () => {
    const selectElements = document.querySelectorAll('select');

    selectElements.forEach(selectElement => {
        function updateColor() {
            if (selectElement.selectedIndex === 0) {
                selectElement.style.color = '#bbb';
            } else {
                selectElement.style.color = 'black';
            }
        }

        selectElement.addEventListener('change', updateColor);

        // 初期の色設定
        updateColor();
    });
});
