/**
 * project
 */

(function($){
  $(function(){
    $("table#projects").tablesorter(/*{ sortList: [[1,0]] }*/);

    $(".ding").click(function(){
      var self = $(this);
      var projectName = self.attr("data");
      $.ajax({
        url: "/projects/ding/" + projectName,
        type: "POST",
        dataType: "json",
        success: function(result){
          if(result.success){
            self.parent().prev().html(result.data.attention);
            // alert(JSON.stringify(result));
          }else{
            alert(result.message);
          }
        }
      });
    });
    
    $("#btnSync").click(function(){
      $.ajax({
        url: "/projects/sync",
        type: "POST",
        dataType: "json",
        success: function(result){
          if(result.success){
            alert("同步成功");
          }
        }
      });
    });
  });
})(jQuery);
