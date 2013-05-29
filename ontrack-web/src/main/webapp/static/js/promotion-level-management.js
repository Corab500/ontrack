var PromotionLevelManagement = function () {

    function unlink (validationStampItem) {
        // The validation stamp
        var validationStamp = validationStampItem.attr('data-validationStamp');
        // The previous promotion level
        var promotionLevel = validationStampItem.attr('data-promotionLevel');
        if (promotionLevel && promotionLevel != '') {
            var project = $('#project').val();
            var branch = $('#branch').val();
            // Starts indicating the loading
            var promotionLevelLoadingIndicator = '#loading-indicator-' + promotionLevel;
            // Ajax to perform the unlink
            AJAX.get({
                url: 'ui/manage/project/{0}/branch/{1}/validation_stamp/{2}/unlink'.format(
                    project.html(),
                    branch.html(),
                    validationStamp.html()
                ),
                loading: {
                    mode: 'container',
                    el: promotionLevelLoadingIndicator
                },
                successFn: function (data) {
                    if (data.success) {
                        postDnD(validationStampItem, $('#freeValidationStamps'), false);
                    }
                }
            });
        }
    }

    function initAutoPromote() {
        $('.promotionLevelStamps').each(function (index, zone) {
            var promotionLevel = $(zone).attr('data-promotionLevel');
            if ($(zone).find('.validationStamp').length > 0) {
                $('#autoPromote-' + promotionLevel).show();
            } else {
                $('#autoPromote-' + promotionLevel).hide();
            }
            if ($('#autoPromote-' + promotionLevel).attr('data-autoPromote') == 'true') {
                $('#autoPromoteFlag-' + promotionLevel).show();
            } else {
                $('#autoPromoteFlag-' + promotionLevel).hide();
            }
        });
    }

    function postDnD (validationStampItem, target, promotionLevel) {
        // Prepares for alignment
        validationStampItem.css('left', '').css('top', '');
        // Appends the validation stamp item to the target
        validationStampItem.appendTo(target);
        // Set-ups the promotion level to this stamp
        if (promotionLevel) {
            validationStampItem.attr('data-promotionLevel', promotionLevel);
        } else {
            validationStampItem.removeAttr('data-promotionLevel');
        }
        // Initializes all promotion drop zones
        initDropZones();
        // Sets the autopromotion button states
        initAutoPromote();
    }

    function link (validationStampItem, promotionLevelItem) {
        var promotionLevel = promotionLevelItem.attr('data-promotionLevel');
        var validationStamp = validationStampItem.attr('data-validationStamp');
        var project = $('#project').val();
        var branch = $('#branch').val();
        // Starts indicating the loading
        var promotionLevelLoadingIndicator = '#loading-indicator-' + promotionLevel;
        // Ajax to perform the link
        AJAX.get({
            url: 'ui/manage/project/{0}/branch/{1}/validation_stamp/{2}/link/{3}'.format(
                project.html(),
                branch.html(),
                validationStamp.html(),
                promotionLevel.html()
            ),
            loading: {
                mode: 'container',
                el: promotionLevelLoadingIndicator
            },
            successFn: function (data) {
                if (data.success) {
                    // Clears the DnD style for the validation stamp item
                    postDnD(validationStampItem, promotionLevelItem, promotionLevel);
                }
            }
        });
    }

    function autoPromote (button) {
        // Gets the promotion level
        var project = $('#project').val();
        var branch = $('#branch').val();
        var promotionLevel = $(button).attr('data-promotionLevel');
        // Current state of the autopromotion
        var currentAutoPromote = $(button).attr('data-autoPromote');
        // URL
        var url = 'ui/manage/project/{0}/branch/{1}/promotion_level/{2}/autopromote'.format(
            project.html(),
            branch.html(),
            promotionLevel.html()
        );
        if (currentAutoPromote == 'true') {
            url += '/unset';
        } else {
            url += '/set';
        }
        // Call
        AJAX.put({
            url: url,
            loading: {
                el: button
            },
            successFn: function (flag) {
                if (flag.set) {
                    $(button).attr('data-autoPromote', 'true');
                    $(button).text(loc('promotion_level.management.notauto'));
                } else {
                    $(button).attr('data-autoPromote', 'false');
                    $(button).text(loc('promotion_level.management.auto'));
                }
                initAutoPromote();
            }
        });
    }

    function initDropZones () {
        $('.promotionLevelStamps').each(function (index, promotionLevelItem) {
            var dropZone = $('#dropzone-label-' + $(promotionLevelItem).attr('data-promotionLevel'));
            if ($(promotionLevelItem).find('span.validationStamp').length > 0) {
                dropZone.hide();
            } else {
                dropZone.show();
            }
        });
    }

    function init () {
        // All validation stamps are draggable
        $('span.validationStamp').draggable({
            revert: "invalid",
            cursor: "move"
        });
        // Initializes all promotion drop zones
        initDropZones();
        // Droppable zones for the promotion levels
        $('.promotionLevelStamps').droppable({
            activeClass: "ui-state-hover",
            hoverClass: "ui-state-active",
            drop: function( event, ui ) {
                var promotionLevelItem = $(this);
                var validationStampItem = ui.draggable;
                link(validationStampItem, promotionLevelItem);
                return true;
            }
        });
        // Droppable zone for the free validation stamps
        $('#freeValidationStamps').droppable({
            activeClass: "ui-state-hover",
            hoverClass: "ui-state-active",
            drop: function( event, ui ) {
                var validationStampItem = ui.draggable;
                unlink(validationStampItem);
                return true;
            }
        });
    }

    return {

        init: init,
        up: up,
        down: down,
        autoPromote: autoPromote

    };

} ();

$(document).ready(PromotionLevelManagement.init)