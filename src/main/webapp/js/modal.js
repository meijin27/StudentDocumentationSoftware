/* 初期設定画面において利用規約及びプライバシーポリシーを新規ウィンドウで表示するためのjavascript */

window.onload = function() {
    var termsUrl = document.querySelector('#termsModal .modal-body').getAttribute('data-terms-url');
    var privacyUrl = document.querySelector('#privacyModal .modal-body').getAttribute('data-terms-url');
    
    loadModalContent('#termsModal', termsUrl);
    loadModalContent('#privacyModal', privacyUrl);
}

function loadModalContent(modalSelector, url) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var lines = xhr.responseText
                .replace(/&/g, '&amp;')  // Replace & with its HTML entity
                .replace(/</g, '&lt;')   // Replace < with its HTML entity
                .replace(/>/g, '&gt;')   // Replace > with its HTML entity
                .replace(/"/g, '&quot;') // Replace " with its HTML entity
                .replace(/'/g, '&#039;') // Replace ' (apostrophe) with its HTML entity
                .split('\n'); // Split the text into an array of lines
            var fullText = '<p class="text-left">' + lines.join('<br>') + '</p>'; // Join all the lines, replacing newline characters with <br> tags, and wrap in a <p> tag with the "text-left" class
            document.querySelector(modalSelector + ' .modal-body').innerHTML = fullText; // Now it's safe to use innerHTML
        }
    }
    xhr.open('GET', url, true);
    xhr.send();
}
