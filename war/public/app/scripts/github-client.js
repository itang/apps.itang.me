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
          var reposList = _.reject(result.data, function(repos) {
            return _.str.startsWith(repos.name, "_"); //屏蔽私有项目
          });
          _.each(reposList, function(repos, index) {
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
    var template = Handlebars.compile($("#repos-template").html());

    function showReposFor(target) {
      return function(repos, index) {
        target.append($(template(repos)));
      }
    }

    function showErrorFor(target) {
      return function(result) {
        target.html("加载数据出错, 请刷新页面重试.");
      }
    }

    _.each([
      {target: $("#myrepos"), uri:"/github/users/" + user + "/repos"},
      {target: $("#mywatched"), uri:"/github/users/" + user + "/watched"}
    ], function(it) {
      loadReposList(it.uri, {}, showReposFor(it.target), showErrorFor(it.target));
    });
  });
})();//END