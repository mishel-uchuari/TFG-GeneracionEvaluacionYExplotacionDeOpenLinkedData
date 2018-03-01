var grafo = false;
var json;
// Inicia la barra de progreso
function initProgressBar() {
	$("table").empty();
	$("svg").remove;
	$("#resultTable").append("<div id='container' class='container'></div>");
	var bar = new ProgressBar.Circle(container, {
		color : '#666',
		strokeWidth : 4,
		trailWidth : 1,
		easing : 'easeInOut',
		duration : 600,
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
	bar.animate(1.0);
}

// Elimina la barra de progreso
function deleteProgressBar() {
	$(".container").remove();
}

// Obtiene el json de la query
function getQueryData(query) {
	queryToCompare = query;
	queryToCompare = queryToCompare.toLowerCase();
	initProgressBar();
	if (queryToCompare.includes("select") || queryToCompare.includes("construct")) {
		$.ajax({
			url : "ServGeneradorResultados",
			data : {
				"query" : query
			},
			type : "post",
			success : function(recivedData) {
				deleteProgressBar();
				if (recivedData.includes("error")
						|| recivedData.includes("Encountered")) {
					swal({
						title : "Error de sintaxis",
						text : recivedData,
						type : "warning",
						showCancelButton : false,
						confirmButtonClass : "btn-primary",
						confirmButtonText : "OK",
						closeOnConfirm : false
					});
				} else if (recivedData.includes("No se han encontrado datos")) {
					if(queryToCompare.includes("recurso")){
						swal({
							title : "Sin datos",
							text : 'No hay datos adicionales sobre ese recurso. Prueba con otro.',
							type : "warning",
							showCancelButton : false,
							confirmButtonClass : "btn-primary",
							confirmButtonText : "OK",
							closeOnConfirm : false
						});
					}
					else{
						swal({
							title : "Sin datos",
							text : recivedData,
							type : "warning",
							showCancelButton : false,
							confirmButtonClass : "btn-primary",
							confirmButtonText : "OK",
							closeOnConfirm : false
						});
					}
				} else {
					if (queryToCompare.includes("select")) {
						activateButton("table");
						if (grafo == true) {// Si estaba activado boton grafo,
							// desactivarlo
							disableButton("graph")
						}
						grafo = false;// No grafo
					} else if (queryToCompare.includes("construct")) {
						activateButton("table")
						activateButton("graph")
						grafo = true;// Se puede ver como grafo
					}
					json = recivedData;
					console.log(json);
					createTable(json);
				}
			}
		});
	} else {
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
	}
}

function activateButton(button){
	$("#"+button+"-btn").removeClass(
	"btn btn-default previous disabled").addClass(
	"btn btn-default previous");
}

function disableButton(button){
	$("#"+button+"-btn").removeClass(
	"btn btn-default previous").addClass(
	"btn btn-default previous disabled");
}
