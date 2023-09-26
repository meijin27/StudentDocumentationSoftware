$(document).ready(function() {
    $('#password').on('input focus', function() {
        var password = $(this).val();
        
        $('.password-helper').addClass('show'); // パスワード入力フォームがフォーカスされたらポップアップを表示
        $('.req_min8').toggleClass('fulfilled', password.length >= 8);
        $('.req_max32').toggleClass('fulfilled', password.length <= 32);
        $('.req_uppercase').toggleClass('fulfilled', /[A-Z]/.test(password));
        $('.req_lowercase').toggleClass('fulfilled', /[a-z]/.test(password));
        $('.req_number').toggleClass('fulfilled', /[0-9]/.test(password));
            
    }).blur(function() {
        $('.password-helper').removeClass('show'); // パスワード入力フォームからフォーカスが外れたらポップアップを非表示
    });
});
