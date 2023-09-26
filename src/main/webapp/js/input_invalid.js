$(document).ready(function() {

  // 検証関数の定義
  function validateInputs() {
    $(':input[required]').each(function() {
      
      // もしセレクトボックスの場合
      if ($(this).is('select')) {
        // 選択されたオプションのvalueが空の場合
        if ($(this).val() === '' || $(this).val() === null) {
          $(this).addClass('input-invalid');
        } else {
          $(this).removeClass('input-invalid');
        }
      }
      // もしセレクトボックス以外の場合
      else {
        if ($(this).val() === '') {
          $(this).addClass('input-invalid');
        } else {
          $(this).removeClass('input-invalid');
        }
      }
    });
  }

  // ページ読み込み時に検証関数を呼び出し
  validateInputs();

  // 入力が変更されたときにも検証関数を呼び出し
  // ドキュメントレベルでイベントを捉えて、動的に追加された要素にも適用
  $(document).on('input change', ':input[required]', function() {
    validateInputs();
  });


  $('#addSetBtn').on('click', function() {
    // 新たなフォーム要素を追加するコード
    // 動的に追加した後で検証関数を呼び出す
    validateInputs();
  });



});
