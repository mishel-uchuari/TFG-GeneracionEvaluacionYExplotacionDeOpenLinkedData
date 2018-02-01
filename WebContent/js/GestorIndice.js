var grafo = false;
var json;
// Inicia la barra de progreso
function initProgressBar() {
	$("svg").remove();
	$("#resultados").append("<div id='container'></div>");
	var bar = new ProgressBar.Circle(container, {
		color : '#666',
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
	bar.animate(1.0);
}

// Elimina la barra de progreso
function deleteProgressBar() {
	$("#container").remove();
}

// Obtiene el json de la query
function getQueryData(query) {
	query = query.toLowerCase();

	if (query.includes("select") || query.includes("construct")) {
		$.ajax({
			url : "ServGeneradorResultados",
			data : {
				"query" : query
			},
			type : "post",
			success : function(recivedData) {
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
					}, function() {
					});
				} else if (recivedData.includes("No se han encontrado datos")) {
					swal({
						title : "Sin datos",
						text : recivedData,
						type : "warning",
						showCancelButton : false,
						confirmButtonClass : "btn-primary",
						confirmButtonText : "OK",
						closeOnConfirm : false
					}, function() {
					});
				} else {
					if (query.includes("select")) {
						$("#table-btn").removeClass(
								"btn btn-default previous disabled").addClass(
								"btn btn-default previous");
						if (grafo == true) {// Si estaba activado boton grafo,
							// desactivarlo
							$("#graph-btn").removeClass(
									"btn btn-default previous").addClass(
									"btn btn-default previous disabled");
						}
						grafo = false;// No grafo
					} else if (query.includes("construct")) {
						$("#table-btn").removeClass(
								"btn btn-default previous disabled").addClass(
								"btn btn-default previous");
						$("#graph-btn").removeClass(
								"btn btn-default previous disabled").addClass(
								"btn btn-default previous");
						grafo = true;// Se puede ver como grafo
					}
					json = recivedData;
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
