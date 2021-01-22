(function ($) {
    let path = window.location.pathname
    path += (path[path.length-1] == "/") ? "" : "/"
    path += "load"
    $.ajax({
        url: path,
        type: "GET",
        dataType: "json"
    
    }).done(function(data){
        // eslint-disable-next-line no-undef
        $("body").html(e.responseText)
    }).fail(function(e){
        $("body").html(e.responseText)
        console.log("fail")
        console.log(e.responseText)
    } )

})(jQuery);
