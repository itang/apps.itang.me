/**
 * bookmarker.
 *
 * require("jquery", "underscore")
 */
(function () {//START
  function showPage(url) {
    $("#frameWindow").attr("src", url);
  }

  $(function () {
    //refresh click事件 -> iframe reload
    $('#refresh').click(function () {
      var iframe = $("#frameWindow");
      iframe.attr("src", iframe.attr("src"));
    });

    //点击tab链接，在iframe显示网页
    $('.sitelink').click(function (event) {
      var it = this;
      //增加点击率
      (function updateHit() {
        var name = $(it).attr("data-it");
        $.ajax({
          url:"/apps/bookmarkers/" + name + "/inc_hits",
          type:"POST",
          dataType:"json",
          success:function (result) {
            if (result.success) {
              $("#" + name + "hits").html(result.data.currHits);
            } else {
              alert("update hit error:" + result.message);
            }
          }
        });
      })();

      if (it.href.indexOf("github") > -1) { //github 禁止iframe it
        return true;
      }

      //刷新
      showPage(it.href);
      //address bar
      $("#address-bar").attr('value', it.href);

      event.preventDefault();
      event.stopPropagation();
    });

    $('#go').click(function () {
      showPage($("#address-bar").attr('value'));
    });

    $('#fgo').click(function () {
      var url = "/apps/proxy?url=" + $("#address-bar").attr('value');
      showPage(url);
    });

    //自动打开第一个链接
    $('.sitelink:first').trigger('click');
  });
})();//END