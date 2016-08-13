//Global Topics array that will be populated when the submit button is pressed.
var topics;

//Submit Handler

$("#submit").click(function(){

	var fileVAL = document.getElementById('file').files[0].name;
	var numTopVAL = $("#numTops").val();
	var accuracyVAL = $("#accuracy").val();

	var data = {file:fileVAL, num:numTopVAL, acu:accuracyVAL};

	$.post("/topics", data, function(response) {
		topics = JSON.parse(response);
		console.log(topics);
	});

	document.getElementById("loading").style.visibility = "visible";
	var topicDiv = document.getElementById("topics");
	topicDiv.innerHTML = "";

	$(document).one("ajaxStop", function() {
		document.getElementById("loading").style.visibility = "hidden";

		console.log(topics.length);
		for(i=0; i<topics.length; i++){
			var topic = topics[i];
			var topicString = topic.w1.value + ", " + topic.w2.value + ", " + topic.w3.value + ", " + topic.w4.value + ", " + topic.w5.value;

			var newTopic = document.createElement('p');
			newTopic.innerHTML = topicString;
			newTopic.classList.add("topic");
			topicDiv.appendChild(newTopic);
		}
	});
});

// File Upload JS

var form = document.getElementById('file-form');
var fileSelect = document.getElementById('file-select');
var uploadButton = document.getElementById('upload-button');

$('form').submit(function() {

  // Get the selected files from the input.
	var file = document.getElementById('file-select').files[0];

	// Create a new FormData object.
	var formData = new FormData();

  formData.append('file', file, file.name);

  $.post("/file", formData, function(response) {

	});

});