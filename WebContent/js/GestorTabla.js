//Funcion crea la tabla a partir del json
function createTable(json) {
	var data = getFormatoJson(json);
	$("#table").html("");
	$("svg").remove();
	var columns = addAllColumnHeaders(data);

	for (var i = 0; i < data.length; i++) {
		var row$ = $('<tr/>');
		for (var colIndex = 0; colIndex < columns.length; colIndex++) {
			var cellValue = data[i][columns[colIndex]];

			if (cellValue == null) {
				cellValue = "";
			}

			row$.append($('<td/>').html(cellValue));
		}
		$("#table").append(row$);
	}
}

//Funcion añade las columnas
function addAllColumnHeaders(data) {
	var columnSet = [];
	var headerTr$ = $('<tr/>');

	for (var i = 0; i < data.length; i++) {
		var rowHash = data[i];
		for ( var key in rowHash) {
			if ($.inArray(key, columnSet) == -1) {
				columnSet.push(key);
				headerTr$.append($('<th/>').html(key));
			}
		}
	}
	$("#table").append(headerTr$);

	return columnSet;
}

//Le da formato a los datos que se le pasan por parametro para construir un json adecuado
function getFormatoJson(data) {
	var data = JSON.parse(JSON.stringify(data));
	var x = data.split(";");
	var generalElements = [];
	var centralElements = {};
	x.forEach(function(links, i) {
		y = links.split(",");
		generalElements.push({
			Subject : y[0],
			Predicate : y[2],
			Object : y[1]
		});
		centralElements = {};
	});
	return generalElements;
}
