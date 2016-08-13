var topics;

$("#submit").click(function(){

	console.log("Submitting...");

	var fileVAL = "ClintonDNC.txt";
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
			var topicString = topic.w1.value + ", " + topic.w2.value + ", " + topic.w3.value + ", " + topic.w4.value + ", " + topic.w5.value;

			var newTopic = document.createElement('p');
			newTopic.innerHTML = topicString;
			newTopic.style = "margin-left:10px";
			topicDiv.appendChild(newTopic);
		}
	});

});