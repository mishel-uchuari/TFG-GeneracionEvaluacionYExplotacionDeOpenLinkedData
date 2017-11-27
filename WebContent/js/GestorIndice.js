var json;

function initProgressBar() {
	$("svg").remove();
	$("#resultados").append("<div id='container'></div>");
	var bar = new ProgressBar.Circle(container, {
		color : '#666',
		// This has to be the same size as the maximum width to
		// prevent clipping
		strokeWidth : 4,
		trailWidth : 1,
		easing : 'easeInOut',
		duration : 1700,
		text : {
			autoStyleContainer : false
		},
		from : {
			color : '#D8D8D8',
			width : 1
		},
		to : {
			color : '##337ab7',
			width : 4
		},
		step : function(state, circle) {
			circle.path.setAttribute('stroke', state.color);
			circle.path.setAttribute('stroke-width', state.width);
			var value = Math.round(circle.value() * 100);
		}
	});
	bar.animate(1.0); // Number from 0.0 to 1.0
}

function deleteProgressBar() {
	$("#container").remove();
}

function getQueryData(query) {
	console.log(query);
	$.ajax({
		url : "ServGeneradorResultados",
		data : {
			"query" : query
		},
		type : "post",
		success : function(receivedData) {
			json = receivedData;
			if (!receivedData) {
				console.log("vaciooooo")
				swal({
					title : "Query no permitida",
					text : "Las querys deben limitarse a SELECT o CONSTRUCT",
					type : "warning",
					showCancelButton : false,
					confirmButtonClass : "btn-primary",
					confirmButtonText : "OK",
					closeOnConfirm : false
				}, function() {
				});
			} else if (receivedData.includes("error")
					|| receivedData.includes("Encountered")) {
				swal({
					title : "Error de sintaxis",
					text : receivedData,
					type : "warning",
					showCancelButton : false,
					confirmButtonClass : "btn-primary",
					confirmButtonText : "OK",
					closeOnConfirm : false
				}, function() {
				});
			} else {
				createTable(json);
			}
		}
	});
}
