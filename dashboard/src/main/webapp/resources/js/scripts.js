function recomputeDialogSize() {
    for (i = 0; i < parent.document.getElementsByTagName('iframe').length; i++) {
        var iframe = parent.document.getElementsByTagName('iframe')[i];
        iframe.removeAttribute('style');
        iframe.removeAttribute('height');
        iframe.height = iframe.contentWindow.document.form.scrollHeight + "px !important";
    }

    for (i = 0; i < parent.document.getElementsByClassName('ui-dialog').length; i++) {
        var widgetVar = parent.document.getElementsByClassName('ui-dialog')[i].getAttribute('data-widgetvar');
        if (widgetVar != null) {
            window.parent.PF(widgetVar).initPosition();
        }
    }
}