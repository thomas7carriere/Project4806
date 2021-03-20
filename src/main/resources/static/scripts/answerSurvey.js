/**
 * answerDTO representation used to store answers
 * @type JSON object
 */
var answerDTO = {
    "surveyID" : 0,
    "answers" : {}
};

/**
 * Called when view Selection Form is submitted. Creates a new answerDTO  form with appropriate inputs.
 * Ajax is used to transfer the completed answerDTO to the Post mapping of the selected survey
 */

function answerSurvey(e){
    e.preventDefault();

    let surveyID = $('form').attr('id')
    answerDTO['surveyID'] = surveyID

    $("form").each(function(){
        var input = objectifyForm($(this).find(':input').serializeArray());
        answerDTO["answers"] = input;
        console.log(JSON.stringify(answerDTO));
    });

    $.ajax({
        url: "/survey/answer",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(answerDTO),
        dataType: 'json',
        success: (data) => {
            alert("Answers successfully submitted");
            window.location.href = "/survey/view";
        },
        fail: (data) =>{
            alert("Failed");
        }
    });
    return false;
}

/**
 * Takes form data after being serializeArray() call. Creates the desired name, value pairs.
 * @param formArray
 * @returns returnArray
 */
function objectifyForm(formArray) {
    let returnArray = {};
    for (let i = 0; i < formArray.length; i++){
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}
