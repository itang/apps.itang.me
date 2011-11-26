 /**
  * JS for github client.
  *
  * require("jquery", "underscore")
  */
(function(){//START
var user = "itang";
function load(url, target){
  $.ajax({
    url: url + "?page=0&per_page=500",
    type: "GET",
    dataType: "json",
    success: function(result){
      if(result.success){
        var reposList = result.data;
        _.each(reposList, function(repos){
          target.append('<li><a href="' + repos.html_url + '" title="' + repos.description + "\n" +
            repos.pushed_at + '" target="_blank">' + repos.name + '</a></li>');
        });
      }else {
        target.append(result.message);
      }
    } //end success
  }); //end $.ajax
}

$(function(){
  load("/github/users/" + user + "/repos", $("#myrepos"));
  load("/github/users/" + user + "/watched", $("#mywatched"));
});
})();//END