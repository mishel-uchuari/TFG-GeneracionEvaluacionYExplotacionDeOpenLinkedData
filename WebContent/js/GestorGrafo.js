function crearGrafo(data) {
    var links = getFormatoJson(data);
    
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
    
    var w = $("#graph").width(),
        h = 1000;
    var force = d3.layout.force().nodes(d3.values(nodos)).links(links).size(
            [w, h]).linkDistance(180).charge(-500).theta(0.1).gravity(0.05)
        .on("tick", tick).start();
    aux = {};

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

    var svg = d3.select("#result").append("svg:svg").attr("width", w).attr(
        "height", h).attr(  "align-items", "center");

    svg.append("svg:defs").selectAll("marker").data(
            ["end"]).enter().append("svg:marker")
        .attr("id", String).attr("viewBox", "0 -5 10 10").attr("refX", 15)
        .attr("refY", -1.5).attr("markerWidth", 6).attr("markerHeight", 6)
        .attr("orient", "auto").append("svg:path").attr("d",
            "M0,-5L10,0L0,5");

    var link = svg.append("svg:g").selectAll("g.link").data(force.links())
        .enter().append('g').attr('class', 'link');

    var linkPath = link.append("svg:path").attr("class", function(d) {
        return "link " + d.type;
    }).attr("marker-end", function(d) {
        return "url(#" + d.type + ")";
    }).style("stroke", "#ccc").on("mouseover", function(d, i) {
        onMouseOver(eliminarSimbolos(d.type));
    }).on("mouseout", function(d, i) {
        onMouseOut();
    });

    var textPath = link.append("svg:path").attr("id", function(d) {
        return d.source.index + "_" + d.target.index;
    }).attr("class", "textpath");

    var circle = svg.append("svg:g").selectAll("circle").data(d3.values(recursos))
        .enter().append("svg:circle").attr("class", function(d) {
            if (d.name.includes("http")) {
                return "recurso";
            } else {
                return "literal";
            }
        })
        .attr({
            "id":function(d) {
            	return "b"+eliminarSimbolos(d.name);
            },
            "r": 15,
            "fill": "#ccc",
            "stroke": "#000000"
        });

    var rectangle = svg.append("svg:g").selectAll("rectangle").data(d3.values(literales))
        .enter().append("svg:rect").attr("class", function(d) {
            if (d.name.includes("http")) {
                return "recurso";
            } else {
                return "literal";
            }
        })
        .attr({
        	"id":function(d) {
            	return "b"+eliminarSimbolos(d.name);
            },
            "width": function(d) {
                return getTamanoTexto(d.name, "Bellefair","10px")
            },
            "height": 20,
            "fill": "#ccc",
            "justify-content": "center",
            "aling-items": "center",
            "stroke": "#000000"
        }).call(force.drag);


    var textRectangles = svg.append("svg:g").selectAll("g").data(d3.values(literales)).enter()
        .append("svg:g");

    textRectangles.append("svg:text").attr({
        "font-size": "10",
    }).attr("class", "shadow").text(function(d) {
        return d.name;
    });

    textRectangles.append("svg:text").attr({
        "font-size": "10",
    }).text(function(d) {
        return d.name;
    });


    var textCircles = svg.append("svg:g").selectAll("g").data(d3.values(recursos)).enter()
        .append("svg:g");

    textCircles.append("svg:text").attr("x", 8).attr("y", ".31em").attr({
        "font-size": "10",
        "text-anchor": "middle"
    }).attr("class", "shadow").text(function(d) {
        return d.name;
    });

    textCircles.append("svg:text").attr("x", 8).attr("y", ".31em").attr({
        "font-size": "10",
        "text-anchor": "middle"
    }).text(function(d) {
        return d.name;
    });

    var path_label = svg.append("svg:g").selectAll(".path_label").data(
        force.links()).enter().append("svg:text").attr({
        "class": "path_label",
        "id": function(d, i) {
            return eliminarSimbolos(d.type);
        }
    }).append("svg:textPath").attr("startOffset", "50%").attr("text-anchor",
        "middle").attr("xlink:href", function(d) {
        return "#" + d.source.index + "_" + d.target.index;
    }).style("fill", "#000").style({
        "font-size": "10",
        "font-family": "Arial"
    }).text(function(d) {
        return d.type;
    });

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
    
    $("[class^='path_label']").hide();

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
            var valueX = d.x+5;
            return "translate(" + valueX + "," + d.y + ")";
        });

        textCircles.attr("transform", function(d) {
            return "translate(" + d.x + "," + d.y + ")";
        });
    }
}

function onMouseOver(pNodo) {
    $("[id=" + pNodo + "]").show();
}

function onMouseOut() {
    $("[class^='path_label']").hide();
}

function eliminarSimbolos(pString) {
    pString = pString.replace(":", "");
    pString = pString.replace(/\s/g, "");
    pString = pString.replace("+", "");
    pString = pString.replace("//", "");
    pString = pString.replace("/", "");
    pString = pString.replace(/\./g, "");
    pString = pString.replace("+", "");
    return pString;
}

function destacarElemento() {
    var userInput = eliminarSimbolos(document.getElementById("busqueda").value);
    var theNode = d3.select("#b" + userInput);
    theNode.attr("fill", "#337ab7");
    setTimeout(function(){ ocultarElemento("#b" + userInput); }, 9000);
}

function ocultarElemento(element) {
    var theNode = d3.select(element);
    theNode.attr("fill", "#ccc");
}

 function getTamanoTexto(txt, fontname, fontsize){
	  this.e = document.createElement("span");
	  this.e.style.fontSize = fontsize;
	  this.e.style.fontFamily = fontname;
	  this.e.innerHTML = txt;
	  document.body.appendChild(this.e);
	  var w = this.e.offsetWidth;
	  document.body.removeChild(this.e);
	  return w+10;
	}

function getFormatoJson(data){
	var data=JSON.parse(JSON.stringify(data));
	var x=data.split(";");
	var generalElements=[];
	var centralElements={};
	x.forEach(function(links, i){
	y=links.split(",");
	generalElements.push({source:y[0], target:y[1], type:y[2]});
	centralElements={};
	});
	return generalElements;
	}