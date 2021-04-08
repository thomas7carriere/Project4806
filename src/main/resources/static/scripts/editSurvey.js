var newQuestionCount = 0;

/**
 *
 * @type JSON object
 */
var editDTO = {
    "surveyID" : 0,
    "surveyName" : "",
    "newQuestions" : [],
    "edited" : {}
};


function addNewQuestion(e){
    e.preventDefault();
    let selectedQuestion = $("#questionDD").val();
    let numberOfChoices = $("#numMC").val();
    if(selectedQuestion === "multipleChoice") {
        if (numberOfChoices > 5 || numberOfChoices < 2) {
            alert("Invalid number of choices. Please select between 2 and 5");
            return false;
        }
    }
    newQuestionCount++;
    let $newQInput;
    let $minInput;
    let $maxInput;
    let selectedQ = $("#questionDD").val();
    let $newQForm = $("<form>", {id:"newQForm" + newQuestionCount, name: selectedQ, class: "newQForm"});
    let $newQType = $("<input>", {name: "questionType", value: selectedQ, type: "hidden"});
    $(".questions").append($newQForm);
    switch(selectedQ){
        case "openEnded":
            console.log("open");
            $newQInput = $("<input>", {type: "text", id:"newQ" + newQuestionCount, name:"question", placeholder: "New Question " + newQuestionCount});
            $newQForm.append($newQType, $newQInput).append("<br /><br />");
            break;

        case "range":
            console.log("range");
            $newQInput = $("<input>", {type: "text", id:"newQ" + newQuestionCount, name:"question", placeholder: "New Question " + newQuestionCount});
            $minInput = $("<input>", {type: "number", id:"min" + newQuestionCount, name:"min", placeholder: "New Question " + newQuestionCount + " min", min: 0});
            $maxInput = $("<input>", {type: "number", id:"max" + newQuestionCount, name:"max", placeholder: "New Question " + newQuestionCount + " max", min: 0});
            $newQForm.append($newQType, $newQInput, "<br />", $minInput, "<br />", $maxInput).append("<br /><br />");
            break;

        case "multipleChoice":
            console.log("multipleChoice");
            let numOfChoices = $("#numMC").val();
            $newQInput = $("<input>", {type: "text", id:"newQ" + newQuestionCount, name:"question", placeholder: "New Question " + newQuestionCount});
            $newQForm.append($newQType, $newQInput, "<br />");
            for(let i = 0; i< numOfChoices; i++){
                $newQForm.append($("<input>", {type: "text", id:"choice" + i , name:"choices", class:"choices", placeholder: "Choice " + i}), "<br />");
            }
            $newQForm.append("<br /><br />");
            break;
    }
    return false;
}

function saveSurvey(e){
    if(!validateSurvey()){
        alert("Not Valid")
        return false;
    }
    e.preventDefault();
    let surveyID = $('#surveyN').attr('name');
    let surveyName = $('#surveyN').val();
    editDTO['surveyID'] =surveyID;
    editDTO['surveyName'] = surveyName;
    if(newQuestionCount >0){
        for(let i = 1; i <= newQuestionCount; i++){
            let newQForm = '#newQForm' + i;
            let formArray = objectifyForm($(newQForm).serializeArray());
            let choiceArray = [];
            if ($(newQForm).attr('name') === "multipleChoice") {
                $(newQForm + ' .choices').each(function () {
                    choiceArray.push(this.value);
                });
                formArray["choices"] = choiceArray;
            }
            editDTO["newQuestions"][i - 1] = formArray;
        }
    }

    $(".editableQs").each(function() {
        var edit = objectifyForm($(this).find(':input').serializeArray());
        editDTO["edited"] = edit;
        console.log(JSON.stringify(editDTO));
    });

    $.ajax({
        url: "/mysurveys/edit",
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(editDTO),
        dataType: 'json',
        success:(data) => {
            alert("Survey Edited Successfully");
            window.location.href = "/mysurveys";
        },
        fail: (data) =>{
            alert("Failed to Edit Survey");
        }
    });
    return false;
}

function showMC(){
    let value = $("#questionDD").val();
    if(value === "multipleChoice"){
        $("#MC").show();
    }
    else{
        $("#MC").hide();
    }
}

function objectifyForm(formArray) {
    let returnArray = {};
    for (let i = 0; i < formArray.length; i++){
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}
function validateSurvey(){
    //flag to keep track of whether the .each() function was broken out of, otherwise true will always be returned
    let breakOut = false;
    $(".newQForm input").each(function () {
        if($(this).val() ===""){
            breakOut = true;
            return false;
        }
    });
    $(".editableQs input").each(function (){
        if($(this).val() ===""){
            breakOut = true;
            return false;
        }
    });
    if(breakOut || $("#surveyN").val() ===""){
        alert("Please fill in Survey Name and all Question Fields");
        return false;
    }
    return true;
}
$(document).ready(function() {
    showMC();
});