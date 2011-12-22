/**
 * loader roy scripts.
 */
(function ($) {
  var royCompile = function (roycode) {
    var jscode;
    try {
      jscode = roy.compile(roycode).output;
    } catch (e) {
      jscode = e;
    }

    return jscode;
  };

  $("royscript").each(function () {
    var it = this;
    var scriptPath = $(it).attr("href");
    $.ajax({
      url:scriptPath,
      dataType:"text",
      success:function (roycode) {
        console.log("roy source:\n" + roycode);
        var roycode = royCompile(roycode);
        console.log("after royCompile:\n" + roycode);
        var script = '<script type="text/javascript">' + roycode + '</script>';
        $('body').append($(script));
      }
    });
  })
})(jQuery);