var sidebarHeadings = document.querySelectorAll('.sidebar-heading');

sidebarHeadings.forEach(function(heading, index) {

    var link = heading.querySelector('a');
    var targetId = link.getAttribute('data-bs-target').substring(1);  // '#' を取り除く
    var targetCollapse = new bootstrap.Collapse(document.getElementById(targetId), {toggle: false});
    
    // Check if the collapse contains an active link. If so, show it by default.
    var hasActiveLink = !!document.getElementById(targetId).querySelector('.nav-link.active');
    if (hasActiveLink) {
        targetCollapse.show();
        link.innerHTML = '<span data-feather="minus-circle"></span>';
        feather.replace();
    }

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