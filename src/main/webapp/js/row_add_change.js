var customOptions; // customOptionsをグローバル変数として定義

document.addEventListener("DOMContentLoaded", function() {
    var currentURI = document.body.getAttribute('data-current-uri');
    // 学割証発行時のみ作動する
    if (currentURI.endsWith("/mainMenu/generalStudent/student-discount-coupon.jsp")) {
        customOptions = {
            maxSets: 2,
            renameEnabled: true,
            labelSuffix: "枚目",
            buttonSuffix: "枚目の削除"
        };
    // 学費延納願のみ作動する
    } else if (currentURI.endsWith("/mainMenu/generalStudent/petition-for-deferred-payment.jsp")) {
        customOptions = {
            maxSets: 4,
            renameEnabled: true,
            labelSuffix: "個目の納期と金額",
            buttonSuffix: "個目の納期と金額の削除"
        };
    // 在留期間更新許可申請書　１枚目のみ作動する
    } else if (currentURI.endsWith("/mainMenu/internationalStudent/period-update-first.jsp")) {
        customOptions = {
            maxSets: 6,
            renameEnabled: true,
            labelSuffix: "人目の在日親族及び同居人",
            buttonSuffix: "人目の在日親族及び同居人の削除"
        };
    }
});

