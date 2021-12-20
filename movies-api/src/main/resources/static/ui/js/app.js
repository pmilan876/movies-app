
var url="http://movieapp.ddns.net:8080/api/"


var appInit=function(){
    fetchUserInfo();


}

var fetchUserInfo=function(){
   doGet(url+"userinfo",fetchUserSuccessHandler,failureHandler)
}

var fetchUserSuccessHandler=function(userDetail){
    console.log(userDetail);
    localStorage.setItem("X-USER",userDetail["userName"]);
    localStorage.setItem("X-TOKEN",userDetail["token"]);
    localStorage.setItem("X-ROLE",userDetail["role"]);

    var role=localStorage.getItem("X-ROLE");
    if(role=="Admin"){
        showHideHelper("#adminContainer","#userContainer");
        loadListing("Movies");
    }
    else{
        showHideHelper("#userContainer","#adminContainer");
        fetchUserMovies();
    }
}

var showHideHelper=function(showDiv,hideDiv){
    $(hideDiv).hide();
    $(showDiv).show();
}

var loadListing=function(selectedTab){
    if(selectedTab=="Artists"){
        showHideHelper("#addArtistBtn","#addMovieBtn");
        $("#moviesTab").removeClass("active");
        $("#artistsTab").addClass("active");
        $("#addBtn").html("Add Artist");
        fetchArtists();

    }
    else{
        showHideHelper("#addMovieBtn","#addArtistBtn");
        $("#moviesTab").addClass("active");
        $("#artistsTab").removeClass("active");
        $("#addBtn").html("Add Movie");
        fetchMovies();
    }
}

$( document ).on( "click", ".admin-listing-tab",function() {
    var selectedTab= $(this).html();
    loadListing(selectedTab);
});


var fetchUserMovies=function(){
	doGet(url+'movies', renderUserMovies, failureHandler )
}

var renderUserMovies=function(moviesJson){
	var html =
    '<div class="row">'+
        '<div class="col-md-12">';
	for(var i in moviesJson){
		var movieJson = moviesJson[i];
        html+= '<div class="card" style="width: 12rem; float:left;margin-left: 25px;margin-right: 15px;margin-bottom: 20px;height:352px;max-height:352px;text-align:center;">'+
          '<img class="mx-auto d-block" src="'+movieJson["imageUrl"]+'" style="width:169px;height:250px">'+
          '<div class="card-footer" style="position:absolute ; bottom:0 ; width:100% ; height:100px;" >'+
            '<h6 class="card-title">'+ movieJson["title"] +'</h6>'+
            "<button movie-id='"+ movieJson["id"] +"' type='button' style='bottom: 5px; position: absolute;  width: 150px; margin-left: -75px;' class='btn btn-outline-dark btn-sm movie-detail'>Detail</button>"+
          '</div>'+
        '</div>';

	}
        html+="</div>"+
    "</div>"

 	$('#moviesContainer').html(html);
}


$(document).on("click","#backToMovies", function(){
    showHideHelper("#moviesContainer","#movieDetailContainer")
});

var failureHandler=function(xhr, ajaxOptions, thrownError) {
    if(xhr.status=="403"){
        showHideHelper("#loginContainer","#mainContainer");
    }
    else{
        alert(xhr.status);
        alert(thrownError);
    }
}