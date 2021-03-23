function editButton(){
    var surveyId = $("#surveyDropDown").val();
    if(surveyId != null){
        var value = "/mysurveys/edit/" + surveyId;
        $(location).attr('href', value);
    }
    else{
        alert("No survey to edit");
    }
}
function closeSurvey(){
    var surveyId = $("#surveyDropDown").val()
    if(surveyId != null){
        var value = "/mysurveys/close/" + surveyId;
        $(location).attr('href', value);
    }
    else{
        alert("No survey to close");
    }
}
function viewResults(){
    var surveyId = $("#surveyDropDown").val()
    if(surveyId != null){
        var value = "/mysurveys/results/" + surveyId;
        $(location).attr('href', value);
    }
    else{
        alert("No survey to get results");
    }
}