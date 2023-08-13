$(document).ready(function() {

   $('#addSetBtn').click(function() {
        var nextSet = $(".additional-set.hidden").first();

        if (nextSet.length) {
            nextSet.removeClass('hidden');
      	    // 元々requiredが設定されている入力フィールドにrequired属性を追加
            nextSet.find('select[data-required="true"], input[data-required="true"]').attr('required', true);
        }

        if (!$(".additional-set.hidden").length) {
            $('#addSetBtn').hide();
        }
        renumberSets();

        // 追加ボタンの表示・非表示のロジックを実行
    	updateAddButtonVisibility();

    });

	$(document).on('click', '.removeSetBtn', function() {
	    var currentSet = $(this).closest('.set');
	    currentSet.addClass('hidden'); // この行を変更: 非表示にするためにhiddenクラスを追加
	
	    // 入力フィールドのrequired属性を削除
	    currentSet.find('select[data-required="true"], input[data-required="true"]').removeAttr('required');
	
	    renumberSets();
	
	    // 追加ボタンの表示・非表示のロジックを実行
	    updateAddButtonVisibility();
	});

	function renumberSets() {
	    $(".set:not(.hidden)").each(function(index, el) {
	        var newIndex = index + 1;
	        $(el).find('select, input, label').each(function() {
	            var name = $(this).attr('name');
	            if (name) {
	                $(this).attr('name', name.replace(/\d+/, newIndex));
	            }
	
	            var id = $(this).attr('id');
	            if (id) {
	                $(this).attr('id', id.replace(/\d+/, newIndex));
	            }
	
	            var forAttr = $(this).attr('for');
	            if (forAttr) {
	                $(this).attr('for', forAttr.replace(/\d+/, newIndex));
	            }
	        });
	        $(el).find('h3').text(newIndex + "行目");
	        $(el).find('.removeSetBtn').text(newIndex + "行目の削除");
	    });
	}

    // 追加ボタンの表示・非表示のロジック
	function updateAddButtonVisibility() {
	    var currentSetCount = $(".set:not(.hidden)").length;
	    var maxSets = 10;
	
	    if (currentSetCount < maxSets) {
	        $("#addSetBtn").show();
	    } else {
	        $("#addSetBtn").hide();
	    }
	}

    
});