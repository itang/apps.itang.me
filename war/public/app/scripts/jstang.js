/**
 * jstang.js
 */
(function(global) {
  function namespace(names) {
    if (!names) return global;

    var names_array = names.split(".");
    var target = global;
    for (var i = 0; i < names_array.length; i++) {
      var name = names_array[i];
      target[name] = target[name] || {};
      target = target[name];
    }
    return target;
  }

  var jstang = namespace("jstang");
  jstang.ns = namespace;
  return jstang;
})(window);


