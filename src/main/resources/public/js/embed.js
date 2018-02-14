/**
 * Created by codedrinker on 05/09/2017.
 */
function init() {
    var endpoint = "https://commenthub.herokuapp.com";
    var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
    var eventer = window[eventMethod];
    var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";
    eventer(messageEvent, function (e) {
        if (e.origin != endpoint) {
            return;
        }
        var key = e.message ? "message" : "data";
        var data = e[key];
        document.getElementById("commenthub_iframe").height = data;
    }, false);
    $("#commenthub_thread").html("<iframe id='commenthub_iframe' allowtransparency='true'  horizontalscrolling='no' verticalscrolling='no' frameborder='0' scrolling='no' tabindex='0' width='100%'  style='width: 1px !important; min-width:100% !important; border: none !important; overflow: hidden !important;' src='" + endpoint + "/comments?commenthub_website=" + commenthub_website + "&commenthub_identifier=" + commenthub_identifier + "&commenthub_id=" + commenthub_id + "&commenthub_url=" + commenthub_url + "&commenthub_title=" + commenthub_title + "'></iframe>");
}

function check_config() {
    if (typeof commenthub_id === 'undefined') {
        throw "commenthub_id is missing.";
    }
    if (typeof commenthub_identifier === 'undefined') {
        throw "commenthub_identifier is missing.";
    }
    if ( commenthub_identifier.length > 50){
        throw "commenthub_identifier max length is 50.";
    }
    if (typeof commenthub_url === 'undefined') {
        throw "commenthub_url is missing.";
    }
    if (typeof commenthub_website === 'undefined') {
        throw "commenthub_website is missing.";
    }
    if (typeof commenthub_title === 'undefined') {
        commenthub_title = window.document.title;
    }
}
check_config();
if (typeof jQuery == 'undefined') {
    var headTag = document.getElementsByTagName("head")[0];
    var jqTag = document.createElement('script');
    jqTag.type = 'text/javascript';
    jqTag.src = 'https://code.jquery.com/jquery-2.2.4.min.js';
    jqTag.onload = init;
    headTag.appendChild(jqTag);
} else {
    init();
}