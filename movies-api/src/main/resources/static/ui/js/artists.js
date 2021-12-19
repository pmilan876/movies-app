
var fetchArtists=function(){
	doGet(url+'artists', renderArtists, failureHandler )
}

var renderArtists=function(artistsJson){
	var tableHtml = prepareArtistsTable(artistsJson,false);
 	$('#adminListingContent').html(tableHtml);

}

var prepareArtistsTable=function(artistsJson, forSelect){
	var tableHtml = "<table class='table'>"+
	"<tr>"+
		"<th>id</th>"+
		"<th>Name</th>"+
		"<th></th>"+
	"</tr>";


	for(var i in artistsJson){
		var artistJson = artistsJson[i];
		tableHtml+= "<tr>"+
			"<td>"+ artistJson["id"] +"</td>"+
			"<td>"+ artistJson["name"] +"</td>"+
			"<td>";
		if(forSelect){
            tableHtml+= "<button artist-id='"+ artistJson["id"] +"' type='button' style='margin-right:10px;' class='btn btn-info btn-sm artist-select'>Select</button>";

		}
		else{
		    tableHtml+= "<button artist-id='"+ artistJson["id"] +"' type='button' style='margin-right:10px;' class='btn btn-info btn-sm artist-edit'>Edit</button>"+
                        "<button artist-id='"+ artistJson["id"] +"' type='button' class='btn btn-danger btn-sm artist-delete'>Delete</button>";

		}

        tableHtml +=    "</td></tr>";
	}

	tableHtml+= "</table>";
	return tableHtml;
}

var showArtistsModal=function(artist){
    clearArtistForm();
    if(artist){
        $("#artistsFormId").val(artist["id"]);
        $("#artistsFormName").val(artist["name"]);

    }
    $('#artistsModal').modal('show');
}


$(document).on("click","#artistsFormSaveBtn", function(){
    var id=$("#artistsFormId").val();
    var name=$("#artistsFormName").val();

    var artist={
        "name":name

    }
    if(id){
        artist["id"] = id;
        doPut(url+"artists",artist,addArtistSuccessHandler,failureHandler);
    }
    else{
        doPost(url+"artists",artist,addArtistSuccessHandler,failureHandler);
    }
});


var addArtistSuccessHandler=function(artist){
    console.log(artist);
    $('#artistsModal').modal('hide');
        fetchArtists();
}


var deleteArtistSuccessHandler=function(artist){
    fetchArtists();
}

var getArtistSuccessHandler=function(artist){
    showArtistsModal(artist);
}

$( document ).on( "click", ".artist-delete",function() {
  var id= $(this).attr("artist-id");
  console.log(id);
  doDelete(url+"artists/"+id,deleteArtistSuccessHandler,failureHandler);
});


$( document ).on( "click", ".artist-edit",function() {
  var id= $(this).attr("artist-id");
  doGet(url+"artists/"+id, getArtistSuccessHandler,failureHandler);
});

var clearArtistForm=function(){
    $("#artistsFormId").val("");
    $("#artistsFormFirstName").val("");
    $("#artistsFormLastName").val("");
    $("#artistsFormAge").val("");
    $("#artistsFormBio").val("");
}

var showDetails=function(){
    $("#detail").show();
    $("#listing").hide();
}

var showListing=function(){
    $("#detail").hide();
    $("#listing").show();
}

$(document).on("click", "#listingBtn",function(){
    $("#detail").hide();
    $("#listing").show();
});

$(document).on("click","#addDirectorBtn",function(){
    $("#addArtistsFormRole").val("Director");
    doGet(url+"artists",addArtistHandler,failureHandler)
});


$(document).on("click","#addActorBtn",function(){
    $("#addArtistsFormRole").val("Actor");
    doGet(url+"artists",addArtistHandler,failureHandler)
});

var addArtistHandler=function(artistsJson){
    var table = prepareArtistsTable(artistsJson,true);
    $("#addArtistTable").html(table);

    $('#addArtistModal').modal('show');
}

$( document ).on( "click", ".artist-select",function() {
    var id= $(this).attr("artist-id");
    var movieId = $("#moviesFormId").val();
    var role = $("#addArtistsFormRole").val();
    var movieArtist = {
        "movie":{"id":movieId},
        "artist":{"id":id},
        "role":role
    }
    doPost(url+"movieartist",movieArtist,artistSelectSuccessHandler,failureHandler);
});

var artistSelectSuccessHandler=function(response){
    $('#addArtistModal').modal('hide');
    loadMovie($("#moviesFormId").val());
    loadMovieArtist($("#moviesFormId").val());
}

$( document ).on( "click", "#addArtistBtn",function() {

        showArtistsModal();
});
