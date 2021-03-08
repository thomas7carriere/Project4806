/**
 * Maximum number of choices allowed for a MultipleChoice question
 * @type {number}
 */
const MAX_MULTIPLECHOICE = 5;

/**
 * Types of question that can be created
 * @type {{OPEN_ENDED: string, RANGE: string, MULTIPLE_CHOICE: string}}
 */
const questionType = {
    OPEN_ENDED: "openEnded",
    RANGE: "range",
    MULTIPLE_CHOICE: "multipleChoice"
};

/**
 * Track the number of questions created so far
 * @type {number}
 */
var questionCount = 0;

/**
 * SurveyDTO representation used to store questions
 * @type JSON object
 */
var survey = {
    "name" : "",
    "questions" : [] };

/**
 * Called when question Selection Form is submitted. Creates a new question form with appropriate inputs.
 * @param e submit event from the question select form
 * @returns {boolean} false to indicate the form should not be submitted through default means
 */
function addQuestion(e){
    if(!validateQuestion()){
        alert("Invalid number of choices")
        return false;
    }
    e.preventDefault();
    questionCount++;
    let $questionInput;
    let $minInput;
    let $maxInput;
    let selectedQuestion = $("#qDropDown").val();
    let $questionForm = $("<form>", {id:"questionForm" + questionCount, name: selectedQuestion, class: "questionForm"});
    let $questionType = $("<input>", {name: "questionType", value: selectedQuestion, type: "hidden"});
    $("body").append($questionForm);
    switch(selectedQuestion){ //check which question has been selected and generate the corresponding HTML elements
        case questionType.OPEN_ENDED :
            console.log("open");
            $questionInput = $("<input>", {type: "text", id:"question" + questionCount, name:"question", placeholder: "Question " + questionCount});
            $questionForm.append($questionType, $questionInput).append("<br /><br />");
            break;

        case questionType.RANGE :
            console.log("range");
            $questionInput = $("<input>", {type: "text", id:"question" + questionCount, name:"question", placeholder: "Question " + questionCount});
            $minInput = $("<input>", {type: "number", id:"min" + questionCount, name:"min", placeholder: "Question" +questionCount + "min", min: 0});
            $maxInput = $("<input>", {type: "number", id:"max" + questionCount, name:"max", placeholder: "Question" +questionCount + "max", min: 0});
            $questionForm.append($questionType, $questionInput,"<br />", $minInput,"<br />", $maxInput).append("<br /><br />");
            break;

        case questionType.MULTIPLE_CHOICE :
            console.log("multipleChoice");
            let numberOfChoices = $("#numberOfMultipleChoices").val();
            $questionInput = $("<input>", {type: "text", id:"question" + questionCount, name:"question", placeholder: "Question " + questionCount});
            $questionForm.append($questionType, $questionInput, "<br />");
            for(let i = 0; i < numberOfChoices; i++) {
                $questionForm.append($("<input>", {type: "text", id:"choice" + i , name:"choices", class:"choices", placeholder: "Choice " + i}), "<br />");
            }
            $questionForm.append("<br />");
            break;
    }
    return false;
}

/**
 * Called when the Survey is ready to be submitted. Formats the input from the questionForms into a JSON
 * that can be received by the Controller and parsed into an object via Spring's @RequestBody annotation.
 */
function sendSurvey(){
    if(!validateSurvey()){
        return false;
    }
    for(let i = 1; i <= questionCount; i++) {
        let questionForm = '#questionForm' + i;
        let formArray = objectifyForm($(questionForm).serializeArray());
        let choiceArray = [];
        //if questionForm is for a multipleChoice, extra step is needed to create choices array
        if ($(questionForm).attr('name') == questionType.MULTIPLE_CHOICE) {
            $(questionForm + ' .choices').each(function () {
                choiceArray.push(this.value);
            });
            formArray["choices"] = choiceArray;
        }
        survey["questions"][i - 1] = formArray;
        survey["name"] = $("#surveyName").val();
    }
    console.log(JSON.stringify(survey));
    $.ajax({
        url: "/survey/create",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(survey),
        dataType: 'json',
        success: (data) => {
            alert("Survey created successfully");
        },
        fail: (data) =>{
            alert("Failed to create Survey");
        }
    });
    //we can use the return data like this if we want
    //.then(data => functionName(data.moreData));
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

/**
 * Check to ensure question selection form is valid. Currently only needs to validate the number of choices selected.
 * @returns {boolean} validity of the question selection form
 */
function validateQuestion(){
    let selectedQuestion = $("#qDropDown").val();
    let numberOfChoices = $("#numberOfMultipleChoices").val();
    if(selectedQuestion === questionType.MULTIPLE_CHOICE) {
        if (numberOfChoices > MAX_MULTIPLECHOICE || numberOfChoices < 2) {
            alert("Invalid number of choices. Please select between 2 and " + MAX_MULTIPLECHOICE);
            return false;
        }
    }
    return true;
}

/**
 * Check to ensure all question forms have been filled out properly
 * @returns {boolean} validity of each of the question forms
 */
function validateSurvey(){
    if(questionCount <= 0) {
        alert("Need at least 1 question");
        return false;
    }
    //flag to keep track of whether the .each() function was broken out of, otherwise true will always be returned
    let breakOut = false;
    $(".questionForm input").each(function () {
        if($(this).val() ===""){
            breakOut = true;
            return false;
        }
    });
    if(breakOut || $("#surveyName").val() ==""){
        alert("Please fill in Survey Name and all Question Fields");
        return false;
    }
    return true;
}

/**
 * Called when the selected dropdown option from #numberOfMultipleChoices is changed
 * Hides the option to input the number of answers if MultipleChoice isn't selected
 */
function toggleMC(){
    let value = $("#qDropDown").val();
    if(value === questionType.MULTIPLE_CHOICE){
        $("#divMC").show();
    }
    else{
        $("#divMC").hide();
    }
}

/**
 * Called once DOM(document) is loaded.
 * toggleMC() call to show/hide the number of Choices input field.
 */
$(document).ready(function() {
    toggleMC();
});