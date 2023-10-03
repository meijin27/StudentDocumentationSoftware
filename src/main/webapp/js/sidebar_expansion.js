var sidebarHeadings = document.querySelectorAll('.sidebar-heading');

sidebarHeadings.forEach(function(heading, index) {

    var link = heading.querySelector('a');
    var span = heading.querySelector('span:not([data-feather])'); // ヘッダーの<span>要素を取得
    var targetId = link.getAttribute('data-bs-target').substring(1);  // '#' を取り除く
    var targetCollapse = new bootstrap.Collapse(document.getElementById(targetId), {toggle: false});
    
    // Check if the collapse contains an active link. If so, show it by default.
    var hasActiveLink = !!document.getElementById(targetId).querySelector('.nav-link.active');
    if (hasActiveLink) {
        targetCollapse.show();
        link.innerHTML = '<span data-feather="minus-circle"></span>';
        feather.replace();
    }


    // この部分で<span>要素にもクリックイベントを追加
    span.addEventListener('click', function() {
        targetCollapse.toggle();
    });
    
    link.addEventListener('click', function(event) {
        event.preventDefault();
        targetCollapse.toggle();
    });

    document.getElementById(targetId).addEventListener('show.bs.collapse', function () {
        link.innerHTML = '<span data-feather="minus-circle"></span>';
        feather.replace();
    });

    document.getElementById(targetId).addEventListener('hide.bs.collapse', function () {
        link.innerHTML = '<span data-feather="plus-circle"></span>';
        feather.replace();
    });
});