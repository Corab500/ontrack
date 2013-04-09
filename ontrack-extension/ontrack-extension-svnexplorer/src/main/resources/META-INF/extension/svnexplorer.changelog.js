var ChangeLog = function () {

    var revisions = null;

    function loadRevisions () {
        if (revisions == null) {
            // FIXME Loads the revisions
            // TODO How identify the change log summary, in order not to load it again?
            // TODO Do we put it in session? What about having several logs on different pages?
            // TODO In session, with a UID and an expiration time?
            // TODO In a common cache with an expiration time?
            AJAX.get({
                url: 'ui/extension/svnexplorer/changelog/revisions',
                loading: {
                    el: '#revisions',
                    mode: 'appendText'
                },
                successFn: $.noop,
                errorFn: changelogErrorFn()
            });
        }
    }

    function changelogErrorFn () {
        return AJAX.simpleAjaxErrorFn(AJAX.elementErrorMessageFn('#changelog-error'));
    }

    function init () {
        $('#revisions-tab').on('show', loadRevisions);
    }

    return {
        init: init
    };

} ();

$(document).ready(ChangeLog.init);