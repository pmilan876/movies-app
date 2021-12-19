

var doGet=function(url,successHandler,failureHandler){
    $.ajax({
        url: url,
        type: 'GET',
        success: successHandler,
        headers:{
            "X-USER":localStorage.getItem("X-USER"),
            "X-TOKEN":localStorage.getItem("X-TOKEN"),
            "Authorization":"Bearer "+localStorage.getItem("X-TOKEN")
        }
    })
    .fail(failureHandler);

}

var doPost=function(url, data, successHandler,failureHandler){
    $.post({
        url: url,
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        headers:{
            "X-USER":localStorage.getItem("X-USER"),
            "X-TOKEN":localStorage.getItem("X-TOKEN"),
            "Authorization":"Bearer "+localStorage.getItem("X-TOKEN")

        }

    })
    .done(successHandler)
    .fail(failureHandler);
}

var doDelete=function(url, successHandler, failureHandler){
    $.ajax({
        url: url,
        type: 'DELETE',
        success: successHandler,
        headers:{
            "X-USER":localStorage.getItem("X-USER"),
            "X-TOKEN":localStorage.getItem("X-TOKEN"),
            "Authorization":"Bearer "+localStorage.getItem("X-TOKEN")
        }
    })
    .fail(failureHandler);
}

var doPut=function(url, data, successHandler, failureHandler){
    $.ajax({
        url: url,
        type: 'PUT',
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        success: successHandler,
        headers:{
            "X-USER":localStorage.getItem("X-USER"),
            "X-TOKEN":localStorage.getItem("X-TOKEN"),
            "Authorization":"Bearer "+localStorage.getItem("X-TOKEN")
        },
        error: failureHandler
    });
}

