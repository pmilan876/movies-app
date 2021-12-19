var fetchMovies=function(){
	doGet(url+'movies', renderMovies, failureHandler )
}

var renderMovies=function(moviesJson){
	var tableHtml = "<table class='table'>"+
	"<tr>"+
		"<th></th>"+
		"<th>Title</th>"+
		"<th>Genre</th>"+
		"<th>Release Year</th>"+
        "<th>Run Time</th>"+
		"<th>Movie Rating</th>"+
		"<th>Imdb Rating</th>"+
		"<th></th>"
	"</tr>";


	for(var i in moviesJson){
		var movieJson = moviesJson[i];
		tableHtml+= "<tr>"+
			"<td style=\"text-align:center;\"><img src=\""+ movieJson["imageUrl"] +"\" style=\"width:50px\"></td>"+
			"<td>"+ movieJson["title"] +"</td>"+
			"<td>"+ movieJson["genre"] +"</td>"+
			"<td>"+ movieJson["releaseYear"] +"</td>"+
            "<td>"+ movieJson["runTime"] +"</td>"+
            "<td>"+ movieJson["movieRating"] +"</td>"+
			"<td>"+ movieJson["imdbRating"] +"</td>"+
			"<td>"+
			"<button movie-id='"+ movieJson["id"] +"' type='button' style='margin-right:10px;' class='btn btn-info btn-sm movie-edit'>Edit</button>"+
			"<button movie-id='"+ movieJson["id"] +"' type='button' class='btn btn-danger btn-sm movie-delete'>Delete</button>"+
			"</td>"
		"</tr>"
	}

	tableHtml+= "</table>";
 	$('#adminListingContent').html(tableHtml);

}
var populateMovieForm=function(movie){
    clearMovieForm();
    clearArtistsForm();
    if(movie){
        $("#moviesFormId").val(movie["id"]);
        $("#moviesFormTitle").val(movie["title"]);
        $("#moviesFormGenre").val(movie["genre"]);
        $("#moviesFormReleaseYear").val(movie["releaseYear"]);
        $("#moviesFormRunTime").val(movie["runTime"]);
        $("#moviesFormMovieRating").val(movie["movieRating"]);
        $("#moviesFormDescription").val(movie["description"]);
        $("#moviesFormImageUrl").val(movie["imageUrl"]);
        $("#moviesFormImdbRating").val(movie["imdbRating"]);
    }
}

var clearArtistsForm=function(){
    $("#movieDirectors").html("");
    $("#movieActors").html("");
}

var clearMovieForm=function(){
    $("#moviesFormId").val("");
    $("#moviesFormTitle").val("");
    $("#moviesFormGenre").val("");
    $("#moviesFormReleaseYear").val("");
    $("#moviesFormRunTime").val("");
    $("#moviesFormMovieRating").val("");
    $("#moviesFormDescription").val("");
    $("#moviesFormImageUrl").val("");
    $("#moviesFormImdbRating").val("");
}

$( document ).on( "click", "#moviesFormSaveBtn",function() {
    var id=$("#moviesFormId").val();
    var title=$("#moviesFormTitle").val();
    var genre=$("#moviesFormGenre").val();
    var releaseYear=$("#moviesFormReleaseYear").val();
    var runTime=$("#moviesFormRunTime").val();
    var movieRating=$("#moviesFormMovieRating").val();
    var description=$("#moviesFormDescription").val();
    var imageUrl=$("#moviesFormImageUrl").val();
    var imdbRating=$("#moviesFormImdbRating").val();
    var movie={
      "title":title,
      "genre":genre,
      "releaseYear":releaseYear,
      "runTime":runTime,
      "movieRating":movieRating,
      "description":description,
      "imageUrl":imageUrl,
      "imdbRating":imdbRating
    }
    if(id){
    movie["id"] = id;
    doPut(url+"movies",movie,addMovieSuccessHandler,failureHandler);
    }
    else{
    doPost(url+"movies",movie,addMovieSuccessHandler,failureHandler);

    }

});
$(document).on("click" ,"#addMovieBtn", function(){
    populateMovieForm();
    showHideHelper("#adminDetailContainer","#adminListingContainer");
});

var addMovieSuccessHandler=function(movie){
    loadMovie(movie["id"]);
}

var deleteMovieSuccessHandler=function(movie){
        fetchMovies();
}

var getMovieSuccessHandler=function(movie){
    populateMovieForm(movie);
}
var getMovieArtistSuccessHandler=function(movieArtists){
    $("#movieDirectors").html("");
    $("#movieActors").html("");
    var directorsList='';
    var actorsList='';
    for(var i in movieArtists){
        var ma = movieArtists[i];
        var name= ma["artist"]["name"];
//        +" "+ma["artist"]["lastName"];
        if(ma["role"]=="Director"){
            directorsList+='<div class="row" style="background-color:#e6e6e6;"><div class="col-md-9">'+name+'</div><div ma-id="'+ma["id"]+'" class="col-md-2 remove-artist" style="cursor:pointer; color:red;text-align:center;font-weight:bold;">x</div></div>';
        }
        else{
            actorsList+='<div class="row" style="background-color:#e6e6e6;"><div class="col-md-9">'+name+'</div><div ma-id="'+ma["id"]+'" class="col-md-2 remove-artist" style="cursor:pointer; color:red;text-align:center;font-weight:bold;">x</div></div>';

        }
    }
    $("#movieDirectors").html(directorsList);
    $("#movieActors").html(actorsList);

}
$( document ).on( "click", ".movie-delete",function() {
  var id= $(this).attr("movie-id");
  console.log(id);
  doDelete(url+"movies/"+id,deleteMovieSuccessHandler,failureHandler);
});


$( document ).on( "click", ".movie-edit",function() {
    showHideHelper("#adminDetailContainer","#adminListingContainer");
    var id= $(this).attr("movie-id");
    loadMovie(id);
    loadMovieArtist(id);
});

var loadMovie=function(movieId){
    doGet(url+"movies/"+movieId,getMovieSuccessHandler,failureHandler);
}
var loadMovieArtist=function(movieId){
    doGet(url+"movieartist/movie/"+movieId,getMovieArtistSuccessHandler,failureHandler);
}
$( document ).on( "click", "#backToMovies",function() {
    showHideHelper("#adminListingContainer","#adminDetailContainer");
});

$(document).on("click", "#detailBtn",function(){
    $("#detail").show();
    $("#listing").hide();
});


$( document ).on( "click", ".remove-artist",function() {
  var id= $(this).attr("ma-id");
  doDelete(url+"movieartist/"+id,deleteMovieArtistSuccessHandler,failureHandler);
});

var deleteMovieArtistSuccessHandler=function(response){
    var movieId = $("#moviesFormId").val();
    loadMovieArtist(movieId);
}

$( document ).on( "click", "#backToAdminMovies",function() {
    showHideHelper("#adminListingContainer","#adminDetailContainer");
    fetchMovies();
});

$( document ).on( "click", ".movie-detail",function() {
    var movieId= $(this).attr("movie-id");
    doGet(url+"moviedetail/"+movieId,getMovieDetailSuccessHandler,failureHandler);
});

var getMovieDetailSuccessHandler=function(movieDetail){
    showHideHelper("#movieDetailContainer","#moviesContainer");
    $("#movieImage").attr("src",movieDetail["movie"]["imageUrl"]);
    $("#movieTitle").html(movieDetail["movie"]["title"]);
    $("#imdbRating").html(movieDetail["movie"]["imdbRating"]);
    $("#releaseYear").html(movieDetail["movie"]["releaseYear"]);
    $("#runTime").html(movieDetail["movie"]["runTime"]);
    $("#genre").html(movieDetail["movie"]["genre"]);
    $("#movieRating").html(movieDetail["movie"]["movieRating"]);
     $("#description").html(movieDetail["movie"]["description"]);
    var actors = "";
    for (var i in movieDetail["actors"]){
        var actor = movieDetail["actors"][i];
        actors+=actor["name"]+", ";
    }
    $("#actors").html(actors);
    var directors = "";
    for (var i in movieDetail["directors"]){
        var director = movieDetail["directors"][i];
        directors+=director["name"]+", ";
    }
    $("#directors").html(directors);
}




