var topics;

$("#submit").click(function(){

	console.log("Submitting...");

	var fileVAL = "SSGuide.txt";
	var numTopVAL = 10;
	var accuracyVAL = 1;

	var data = {file:fileVAL, num:numTopVAL, acu:accuracyVAL};

	$.post("/topics", data, function(response) {
		topics = JSON.parse(response);
	});

	document.getElementById("loading").style.visibility = "visible";
	var topicDiv = document.getElementById("topics");
	topicDiv.innerHTML = "";

	$(document).ajaxStop(function() {
		document.getElementById("loading").style.visibility = "hidden";

		for(i=0; i<topics.length; i++){
			var topic = topics[i];
			console.log(topic);
			var topicString = topic.w1 + "|" + topic.w2 + "|" + topic.w3 + "|" + topic.w4 + "|" + topic.w5;

			var newTopic = document.createElement('p');
			newTopic.innerHTML = topicString;
			newTopic.style = "margin-left:10px";
			topicDiv.appendChild(newTopic);
		}
	});
});