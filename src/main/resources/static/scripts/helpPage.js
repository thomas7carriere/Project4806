/**
 * Scroll to the specific function chosen in the drop down menu
 * @param value is the question passed in through helpPage.html
 */
function myAnchor(value){
    var top = document.getElementById(value).offsetTop;
    window.scrollTo(0, top);
}