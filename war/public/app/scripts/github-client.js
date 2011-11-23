
(function(){
     $(function(){
         $.ajax({
         url: "/github",
          type:"POST",
         dataType:"json",
         success: function(result){
                var repos = result["repositories"];
                for(var i=0;i< repos.length;i++){
                //TODO 使用js模板
                    $("#myrepos").append('<li><a href="' + repos[i].url + '" title="' + repos[i].description + '" target="_blank">' + repos[i].name + '</a></li>');
                }
            }
         });
     });

})();