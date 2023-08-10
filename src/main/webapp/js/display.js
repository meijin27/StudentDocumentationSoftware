$(document).ready(function() {
    $('#addSetBtn').click(function() {
        var nextSet = $(".additional-set.hidden").first();
        
        if (nextSet.length) {
            nextSet.removeClass('hidden');
            nextSet.find('select, input, label').attr('required', true);
        }

        if (!$(".additional-set.hidden").length) {
            $('#addSetBtn').hide();
        }
        renumberSets();
    });

    $(document).on('click', '.removeSetBtn', function() {
        var currentSet = $(this).closest('.set');
        currentSet.addClass('hidden');
        currentSet.find('select, input').val('').removeAttr('required');

        // 追加ボタンを再表示
        $('#addSetBtn').show();

        renumberSets();
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
        });
    }
});