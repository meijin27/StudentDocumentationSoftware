/* 動的に入力フォームを増やしたり減らしたりするためのjavascript */

var defaultOptions = {
    maxSets: 10,
    renameEnabled: true,
    labelSuffix: "番目の期間",
    buttonSuffix: "番目の期間の削除"
};

function renumberSets(options) {
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

        // 非表示のセットのラベルを更新しないようにする
        if(options.renameEnabled) {
            $(el).find('h3').text(newIndex + options.labelSuffix);
            $(el).find('.removeSetBtn').text(newIndex + options.buttonSuffix);
        }
    });
}

// 追加ボタンの表示・非表示のロジック
function updateAddButtonVisibility(options) {
    var currentSetCount = $(".set:not(.hidden)").length;

    if (currentSetCount < options.maxSets) {
        $("#addSetBtn").show();
    } else {
        $("#addSetBtn").hide();
    }
}

window.initializeFormSets = function(customOptions) {
    var options = $.extend({}, defaultOptions, customOptions);
	updateAddButtonVisibility(options); 
    $('#addSetBtn').click(function() {
	var nextSet = $(".additional-set.hidden").first();

    if (nextSet.length) {

        nextSet.removeClass('hidden');
  	    // 元々requiredが設定されている入力フィールドにrequired属性を追加
        nextSet.find('select[data-required="true"], input[data-required="true"]').attr('required', true);
        // 新規セットを最後のセットの後ろに挿入
        nextSet.insertAfter($(".set:not(.hidden)").last());
    }

    if (!$(".additional-set.hidden").length) {
        $('#addSetBtn').hide();
    }
        renumberSets(options);

    // 追加ボタンの表示・非表示のロジックを実行
        updateAddButtonVisibility(options);
	});
	
	$(document).on('click', '.removeSetBtn', function() {
	    var currentSet = $(this).closest('.set');
	    currentSet.addClass('hidden'); // 非表示にするためにhiddenクラスを追加

		// 入力内容をクリアする（ラジオボタンは除外しないと常にラジオボタンの値を削除するようになる）
		currentSet.find('input:not(:radio), select').val('');

	    // 入力フィールドのrequired属性を削除
	    currentSet.find('select[data-required="true"], input[data-required="true"]').removeAttr('required');
	
	    // 非表示にしてDOMのaddSetBtnの前に移動する
	    currentSet.insertBefore('#addSetBtn').addClass('hidden');
	    	    
	    // 再採番
	    renumberSets(options);
	
	    // 追加ボタンの表示・非表示のロジックを実行
        updateAddButtonVisibility(options);
	});
}


$(document).ready(function() {
	if (typeof customOptions !== 'undefined' && customOptions !== null) {
	    window.initializeFormSets(customOptions);
	} else {
	    window.initializeFormSets();
	}

});