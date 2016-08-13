//Global Topics array that will be populated when the submit button is pressed.
var topics;

//Submit Handler

$("#submit").click(function(){

	console.log("Submitting...");

	var fileVAL = "TrumpRNC.txt";
	var numTopVAL = 10;
	var accuracyVAL = 1;

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
			var topicString = topic.w1 + ", " + topic.w2 + ", " + topic.w3 + ", " + topic.w4 + ", " + topic.w5;

			var newTopic = document.createElement('p');
			newTopic.innerHTML = topicString;
			newTopic.style = "margin-left:10px";
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

	if (!file.type.match('image.*')) {
    continue;
  }

  // Add the file to the request.
  if (!file.type.match('.txt')) {
    continue;
  }

  formData.append('file', file, file.name);

  $.post("/file", formData, function(response) {
	});
});