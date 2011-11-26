/**
 * JS for github client.
 *
 * require("jquery", "underscore", "handlebars")
 */
(function() {//START
  var user = "itang";

  function loadReposList(url, options, showRepos, showError) {
    options.page = options.page || 1;
    options.per_page = options.per_page || 10;

    $.ajax({
      url: url + "/" + options.page + "/" + options.per_page,
      type: "GET",
      dataType: "json",
      success: function(result) {
        if (result.success) {
          var reposList = result.data;
          _.each(reposList, function(repos, index) {
            //TODO 使用js模板
            showRepos(repos, index);
          });
        } else {
          if (showError) {
            showError(result);
          }
        }
      } //end success
    }); //end $.ajax
  }

  $(function() {
    var source = $("#repos-template").html();
    var template = Handlebars.compile(source);
    var myrepos = $("#myrepos");

    function showErrorFor(target) {
      target.html("");//clear
      target.append("加载数据出错, 请刷新页面重试.")
    }

    loadReposList("/github/users/" + user + "/repos", {}, function(repos, index) {
      myrepos.append($(template(repos)));
    }, function(result) {
      myrepos.append("加载数据出错, 请刷新页面重试.")
    });
    var mywatched = $("#mywatched");
    loadReposList("/github/users/" + user + "/watched", {}, function(repos, index) {
      mywatched.append($(template(repos)));
    }, function(result) {
      showErrorFor(mywatched);
    });
  });
})();//END