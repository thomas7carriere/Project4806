

/**
 * answerDTO representation used to store answers
 * @type JSON object
 */
var answerDTO = {
    "surveyID" : 0,
    "questionID" : [],
    "answers" : [],
};

/**
 * Called when view Selection Form is submitted. Creates a new answerDTO  form with appropriate inputs.
 * Ajax is used to transfer the completed answerDTO to the Post mapping of the selected survey
 */

function answerSurvey(){

    let surveyID = $('form').attr('id')
    answerDTO['surveyID'] = surveyID

    $("form").each(function(){
        var input = $(this).find(':input').serialize();
        var inplutSplit = input.split("&")
        console.log(inplutSplit)

        //traverses the array and splits the question and the answer to store in the answerDTO
        for(let i = 0; i <inplutSplit.length ; i++ ){
            tempIdAnswer = inplutSplit[i].split("=")
            answerDTO['questionID'][i] = tempIdAnswer[0]
            answerDTO['answers'][i] = tempIdAnswer[1]
        }
    });

    console.log(JSON.stringify(answerDTO))

    $.ajax({
        url: "/survey/view/" + surveyID,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(answerDTO),
        dataType: 'json',
        success: (data) => {
            alert("successfully");
        },
        fail: (data) =>{
            alert("Failed");
        }
    });
    return false;
}

