/**
 * Created by codedrinker on 05/09/2017.
 */
var api = "https://api.github.com/repos/commenthub/commenthub/issues";
//https://developer.github.com/v3/issues/#list-issues-for-a-repository
// https://api.github.com/repos/commenthub/commenthub/issues?labels=http://www.majiang.life,http://www.majiang.life/blog/simple-in-memory-cache-in-java/
//https://github.com/commenthub/commenthub/issues/1
function init() {
    $("#commenthub_thread").html("<iframe style='width: 100%;height: 100%;border: none;' src='https://commenthub.herokuapp.com/comments?commenthub_website=" + commenthub_website + "&commenthub_identifier=" + commenthub_identifier + "'></iframe>");
}

if (typeof jQuery == 'undefined') {
    console.log("has no jquery");
    var headTag = document.getElementsByTagName("head")[0];
    var jqTag = document.createElement('script');
    jqTag.type = 'text/javascript';
    jqTag.src = 'https://code.jquery.com/jquery-2.2.4.min.js';
    jqTag.onload = init;
    headTag.appendChild(jqTag);
} else {
    console.log("has jquery")
    init();
}