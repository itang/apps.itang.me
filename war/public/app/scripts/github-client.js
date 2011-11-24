 /**
  * JS for github client.
  *
  */
(function(){
    var user = "itang";
    function load(url, target){
         $.ajax({
         url: url + "?page=0&per_page=500",
         type: "GET",
         dataType: "json",
         success: function(result){
                var repos = result
                for(var i=0;i< repos.length;i++){
                //TODO 使用js模板
                    target.append('<li><a href="' + repos[i].html_url + '" title="' + repos[i].description + "  " + repos[i].pushed_at + '" target="_blank">' + repos[i].name + '</a></li>');
                }
            }
         });
    }

    $(function(){
        load("/github/users/" + user + "/repos", $("#myrepos"));
        load("/github/users/" + user + "/watched", $("#mywatched"));
    });
})();