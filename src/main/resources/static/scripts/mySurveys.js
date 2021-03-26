function editButton(){
    var surveyId = $("#surveyDropDown").val();
    let isOpen = $("#surveyDropDown option:selected").attr('name');
    if(surveyId != null && isOpen !== "Closed"){
        var value = "/mysurveys/edit/" + surveyId;
        $(location).attr('href', value);
    }
    else{
        alert("No survey to edit or survey is closed");
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

function deleteSurvey(){
    var surveyId = $("#surveyDropDown").val()
    if(surveyId != null){
        $.ajax({
            url: '/survey/delete/' + surveyId,
            type: 'DELETE',
            contentType: 'application/json',
            dataType: 'json',
            success: function() {
                alert("Survey deleted successfully");
                var value = "/mysurveys";
                $(location).attr('href', value);
            },
            statusCode: {
                404: function() {
                    alert("Unable to delete survey");
                }
            }
        });
    }
    else{
        alert("No survey to delete")
    }
}
