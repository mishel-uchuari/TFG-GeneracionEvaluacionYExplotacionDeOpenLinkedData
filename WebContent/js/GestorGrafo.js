var PATH_LABEL;
function createGraph(data) {
	console.log(data);
// Se elimina la tabla
// Se crea la estructura del json
 var links = getFormatoJsonGrafo(data);
 if(links.length<45){
$("#table").html("");
 var nodos = {};
 links.forEach(function(link) {
     link.source = nodos[link.source] || (nodos[link.source] = {
         name: link.source,
     });
     link.target = nodos[link.target] || (nodos[link.target] = {
         name: link.target
     });
 });
 literales = {};
 recursos = {};
 // Se define la anchura maxima del grafo
 var w = $("#resultGraph").width(),
 // Se define la altura maxima del grafo
  h = 800;
 // Se configuran varios parametros para la forma de generarse el grafo como
 // el tamaño de los enlaces
 var force = d3.layout.force().nodes(d3.values(nodos)).links(links).size(
   [w, h]).linkDistance(function(d) {
   return (getTamanoTexto(d.type, "Bellefair", "10px") + 25);
  }).charge(-500).theta(0.1).gravity(0.05)
  .on("tick", tick).start();
 aux = {};
// Se separan los nodos entre literales y recursos
 for (var key in nodos) {
  if (nodos[key].name.includes("http")) {
   nodos[key].class = "recurso";
   aux = nodos[key];
   recursos[nodos[key].name] = aux;
  } else {
   nodos[key].class = "literal";
   aux = nodos[key];
   literales[nodos[key].name] = aux;
  }
  aux = {};
 }
// Se define la estructura del svg
 var svg = d3.select("#resultGraph").append("svg:svg").attr("width", w).attr(
  "height", h).attr("orient", "auto");
 
 // Se configuran las caracteristicas de los enlaces
 var link = svg.append("svg:g").selectAll("g.link").data(force.links())
  .enter().append('g').attr('class', 'link');

 var linkPath = link.append("svg:path").attr("class", function(d) {
  return "link " + d.type;
 }).attr("marker-end", function(d) {
  return "url(#" + d.type + ")";
 }).style("stroke", "#ccc");

 var textPath = link.append("svg:path").attr("id", function(d) {
  return d.source.index + "_" + d.target.index;
 }).attr("class", "textpath");
 
 // Se configuran las caracteristicas de los recursos
 var circle = svg.append("svg:g").selectAll("circle").data(d3.values(recursos))
  .enter().append("svg:circle").attr("class", function(d) {
   if (d.name.includes("http")) {
    return "recurso";
   } else {
    return "literal";
   }
  })
  .attr({
   "id": function(d) {
    return "b" + eliminarSimbolos(d.name.toLowerCase());
   },
   "r": 15,// #81BEF7
   "fill": "#81BEF7",
   "stroke": "#2e6da4"
  }).on("dblclick", function(d) {// Al hacer doble click sobre un recurso
	  getResourceData(d.name, true)// Se mostrará un grafo solo con la
		// información que le hace referencia
  }).on("mouseover", function (d){// Al pasar el raton por encima se mostrara
									// del grafo sólo su informacion
	  // y la de sus adyacentes
	  connectedNodes(d, 0)}).on("mouseout", function (d){connectedNodes(d, 1)}).call(force.drag);
 
 // Se configuran las caracteristicas de los recursos
 var rectangle = svg.append("svg:g").selectAll("rectangle").data(d3.values(literales))
  .enter().append("svg:rect").attr("class", function(d) {
   if (d.name.includes("http")) {
    return "recurso";
   } else {
    return "literal";
   }
  })
  .attr({
   "id": function(d) {
    return "b" + eliminarSimbolos(d.name.toLowerCase());
   },
   "width": function(d) {
    return getTamanoTexto(d.name, "Bellefair", "10px")
   },
   "height": 20,
   "fill": "#81BEF7",
   "justify-content": "center",
   "aling-items": "center",
   "stroke": "#2e6da4" // Al pasar el raton por encima se mostrara del grafo
		// sólo su informacion y la de sus adyacentes
  }).on("mouseover", function (d){connectedNodes(d, 0)}).on("mouseout", function (d){connectedNodes(d, 1)}).call(force.drag);

// Se configura el texto que aparecera describiendo literales
 var textRectangles = svg.append("svg:g").selectAll("g").data(d3.values(literales)).enter()
  .append("svg:g").attr("id", function(d) {
   return "text" + eliminarSimbolos(d.name);
  });

 textRectangles.append("svg:text").attr({
  "font-size": "10"
 }).attr("class", "shadow").text(function(d) {
  return d.name;
 });

 textRectangles.append("svg:text").attr({
  "font-size": "10"
 }).text(function(d) {
  return d.name;
 });
 
// Se configura el texto que aparecera describiendo recursos
 var textCircles = svg.append("svg:g").selectAll("g").data(d3.values(recursos)).enter()
  .append("svg:g").attr("id", function(d) {
   return "text" + eliminarSimbolos(d.name);
  });

 textCircles.append("svg:text").attr("x", 8).attr("y", ".31em").attr({
  "font-size": "10",
  "text-anchor": "middle"
 }).attr("class", "shadow").text(
  function(d) {
   return d.name;
  });

 textCircles.append("svg:text").attr("x", 8).attr("y", ".31em").attr({
  "font-size": "10",
  "text-anchor": "middle"
 }).text(function(d) {
  return d.name;
 });

 PATH_LABEL = svg.append("svg:g").selectAll(".path_label").data(
  force.links()).enter().append("svg:text").attr({
  "class": "path_label"
 }).append("svg:textPath").attr("startOffset", "50%").attr({
  "text-anchor": "middle",
  "id": function(d) {
   var aux = eliminarSimbolos(d.source.name) + eliminarSimbolos(d.target.name);
   return "r" + aux;
  }
 }).attr("xlink:href", function(d) {
  return "#" + d.source.index + "_" + d.target.index;
 }).style("fill", "#000").style({
  "font-size": "10",
  "font-family": "Arial"
 }).text(function(d) {
  return d.type;// Al pasar el raton por encima mostrara texto asociado y al
	// quitarlo el texto desaparecera
 }).on("mouseover", function(d) {
  onMouseOver(d);
  console.log("se muestra"+ d)
 }).on("mouseout", onMouseOut);

 function arcPath(leftHand, d) {
  var start = leftHand ? d.source : d.target,
   end = leftHand ? d.target :
   d.source,
   dx = end.x - start.x,
   dy = end.y - start.y,
   dr = Math
   .sqrt(dx * dx + dy * dy),
   sweep = leftHand ? 0 : 1;
  return "M" + start.x + "," + start.y + "A" + dr + "," + dr + " 0 0," +
   sweep + " " + end.x + "," + end.y;
 }
 // Fija que inicialmente que el texto de los enlaces no se muestre
 PATH_LABEL[0].map(function(x) {
  x.setAttribute("opacity", "0")
 });
 
 // Funcion fija las nuevas posiciones de los elementos del grafo al moverse
 function tick() {
  linkPath.attr("d", function(d) {
   return arcPath(false, d);
  });

  textPath.attr("d", function(d) {
   return arcPath(d.source.x < d.target.x, d);
  });

  circle.attr("transform", function(d) {
   return "translate(" + d.x + "," + d.y + ")";
  });

  rectangle.attr("transform", function(d) {
   var valueY = d.y - 11;
   var valueX = d.x;
   return "translate(" + valueX + "," + valueY + ")";
  });

  textRectangles.attr("transform", function(d) {
   var valueX = d.x + 5;
   return "translate(" + valueX + "," + d.y + ")";
  });

  textCircles.attr("transform", function(d) {
   return "translate(" + d.x + "," + d.y + ")";
  });

 }

 // Variable que hace referencia a los enlaces que se crean entre los
 // recursos/literales
 var linkedByIndex = {};
 links.forEach(function(d) {
  linkedByIndex[d.source.name + "," + d.source.name] = 1;
  linkedByIndex[d.target.name + "," + d.target.name] = 1;
  linkedByIndex[d.source.name + "," + d.target.name] = 1;
 });
 
 // Funcion focaliza los nodos conectados
 function connectedNodes(elementIndex, toggle) {
  if (toggle == 0) {
   $("[id^=text").attr("opacity", "0");
   PATH_LABEL[0].map(function(x) {
    x.setAttribute("opacity", "0")
   });
   var textRecursos;
   var textLiterales;
   circle.style("opacity", function(o) {
    var opacity = 0;
    if (linkedByIndex[elementIndex.name + "," + o.name] == 1 || linkedByIndex[o.name + "," + elementIndex.name] == 1) {
     opacity = 1;
     // Hace visibles el texto de los links adecuados
     var aux = eliminarSimbolos(o.name) + eliminarSimbolos(elementIndex.name);
     var aux2 = eliminarSimbolos(elementIndex.name) + eliminarSimbolos(o.name);
     d3.select("#r" + aux).attr("opacity", "1");
     d3.select("#r" + aux2).attr("opacity", "1");
     // Hace visible los labeles de los nodos adecuados
     $("#text" + eliminarSimbolos(o.name)).attr("opacity", "1");
    }
    return opacity;
   });

   rectangle.style("opacity", function(o) {
    var opacity = 0;
    if (linkedByIndex[elementIndex.name + "," + o.name] == 1 || linkedByIndex[o.name + "," + elementIndex.name] == 1) {
     opacity = 1;
     // Hace visibles el texto de los links adecuados
     var aux = eliminarSimbolos(o.name) + eliminarSimbolos(elementIndex.name);
     var aux2 = eliminarSimbolos(elementIndex.name) + eliminarSimbolos(o.name);
     d3.select("#r" + aux).attr("opacity", "1");
     d3.select("#r" + aux2).attr("opacity", "1");
     // Hace visible los labeles de los nodos adecuados
     $("#text" + eliminarSimbolos(o.name)).attr("opacity", "1");
    }

    return opacity;
   });
   link.style("opacity", function(o) {
    var opacity = 0;
    if (linkedByIndex[elementIndex.name + "," + o.source.name] == 1 && elementIndex.index == o.source.index || elementIndex.index == o.target.index) {
     opacity = 1;
    }
    return opacity;
   });
  } else {
   // Regresa todo a su estado inicial
   circle.style("opacity", 1);
   rectangle.style("opacity", 1);
   link.style("opacity", 1);
   PATH_LABEL[0].map(function(x) {
    x.setAttribute("opacity", "0")
   });
   $("[id^=text").attr("opacity", "1");
  }
 }
 }else{
	 swal({
			title : "No se puede representar datos mediante grafo",
			text : 'La cantidad de datos a representar es demasiado grande',
			type : "warning",
			showCancelButton : false,
			confirmButtonClass : "btn-primary",
			confirmButtonText : "OK",
			closeOnConfirm : false
		});
	 grafo=false;
	 disableButton("graph");
 }
}

// Al pasar el raton sobre un enlace se muestra su texto asociado
function onMouseOver(pElement) {
 d3.select("#r" + eliminarSimbolos(pElement.source.name) + eliminarSimbolos(pElement.target.name)).attr("opacity", "1");
}

// Al retirar el raton de un enlace se oculta su texto asociado
function onMouseOut() {
 PATH_LABEL[0].map(function(x) {
  x.setAttribute("opacity", "0")
 });

}

String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.replace(new RegExp(search, 'g'), replacement);
};

// Elimina caracteres especiales
function eliminarSimbolos(pString) {
 pString = pString.replaceAll("%", "");
 pString = pString.replaceAll("/", "");
 pString = pString.replace("@", "");
 pString = pString.replace("#", "");
 pString = pString.replace(/\s/g, "");
 pString = pString.replace("+", "");
 pString = pString.replace("//", "");
 pString = pString.replace("/", "");
 pString = pString.replace(/:/g,'');
 pString = pString.replace(/\./g, "");
 pString = pString.replace("+", "");
 return pString;
}

// Funcion busca elemento en la informacion mostrada en el grafo y lo destaca
// por unos segundos
function destacarElemento() {
 var userInput = eliminarSimbolos(document.getElementById("busqueda").value.toLowerCase());
 var theNode = d3.select("#b" + userInput);
 if (theNode == "") {
  swal(
   'Ops!',
   'No se han obtenido resultados',
   'warning'
  )
 }
 theNode.attr("fill", "#337ab7");
 setTimeout(function() {
  ocultarElemento("#b" + userInput);
 }, 9000);
}

// Funcion oculta el elemento buscado anteriormente
function ocultarElemento(element) {
 var theNode = d3.select(element);
 theNode.attr("fill", "#ccc");
}

// Funcion obtiene el tamaño del texto que se le pase por parametro para saber
// que tamaño asignar al recuadro que lo englobara
function getTamanoTexto(txt, fontname, fontsize) {
 this.e = document.createElement("span");
 this.e.style.fontSize = fontsize;
 this.e.style.fontFamily = fontname;
 this.e.innerHTML = txt;
 document.body.appendChild(this.e);
 var w = this.e.offsetWidth;
 document.body.removeChild(this.e);
 return w + 10;
}

// Funcion termina de conformar el json y darle el formato adecuado
function getFormatoJsonGrafo(data){
	var data=JSON.parse(JSON.stringify(data));
	var x=data.split(";");
	var generalElements=[];
	var centralElements={};
	x.forEach(function(links, i){
	y=links.split(",");
	generalElements.push({source:y[0], type:y[2], target:y[1]});
	centralElements={};
	});
	return generalElements;
	}

function getResourceData(resource){
	var query = "construct{?nomRecurso ?p ?o} where{?nomRecurso ?p ?o " +
			"FILTER(?nomRecurso = <"+resource+">)}"
	getQueryData(query);
			}

