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

		var w=500,h=500,
			svg=d3.select("#topics")
				.append("svg")
				.attr("width",w)
				.attr("height",h);
		console.log(topics.length);
		for(i=0; i<topics.length; i++){
			var topic = topics[i];
			var newTopic = document.createElement('p');
			var maxWeight = 0;
			var maxCircle;
			var circles = [];
			for (var pair in topic) {
				if (topic.hasOwnProperty(pair)) {
					var w = topic[pair];
					var topicString = w.value;
					
					newTopic.innerHTML += topicString;
					newTopic.style = "margin-left:10px";
					
					
					xCoord = 450*Math.random();
					yCoord = 20*Math.random()+40*(i+1);
					
					var bubble = svg
						.append("circle")
						.attr("cx", xCoord)
						.attr("cy", yCoord)
						.attr("r", w.weight*4)
						.style("fill", "white");
					if (w.weight > maxWeight) { // code to update to largest bubble
						maxCircle = bubble;
						maxWeight = w.weight;
					}
					circles.push(bubble); // add to list
					
					var text1=svg
						.append("text")
						.text(topicString)
						.style("font-size",8)
						.attr("x",xCoord)
						.attr("y",yCoord)
						.attr("text-anchor", "middle");
				}
			}
			// plan: connect bubbles together
			for (var jj = 0; jj < circles.length; jj++) {
				var line = svg
					.append("line")
					.style("stroke", "white")
					.attr("x1", maxCircle.attr("cx"))
					.attr("y1", maxCircle.attr("cy"))
					.attr("x2", circles[jj].attr("cx"))
					.attr("y2", circles[jj].attr("cy"));
			}

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