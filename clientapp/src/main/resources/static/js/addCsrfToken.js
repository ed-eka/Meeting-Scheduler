function addCsrfToken() {
    let value = $("meta[name='_csrf']").attr("content");
    let key = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(key, value);
    });
}