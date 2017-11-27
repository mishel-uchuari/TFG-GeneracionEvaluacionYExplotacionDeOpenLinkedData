var visibles = true;
var links;
var nodos;
var circle;
var textRectangles;

function createGraph(data) {
	console.log(data);
    links = getFormatoJson(data);
    nodos = {};
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
    $("svg").remove();
    var w = $("#graph").width(),
        h = 1000;
    var force = d3.layout.force().nodes(d3.values(nodos)).links(links).size(
            [w, h]).linkDistance(function(d) {
            return (getTamanoTexto(d.type, "Bellefair", "10px") + 25);
        }).charge(-500).theta(0.1).gravity(0.05)
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
        "height", h).attr("orient", "auto");
    
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
    
    circle = svg.append("svg:g").selectAll("circle").data(d3.values(recursos))
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
            "r": 15,
            "fill": "#ccc",
            "stroke": "#000000"
        }).on('dblclick', connectedNodes).call(force.drag);

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
            "fill": "#ccc",
            "justify-content": "center",
            "aling-items": "center",
            "stroke": "#000000"
        }).on('dblclick', connectedNodes).call(force.drag);


    textRectangles = svg.append("svg:g").selectAll("g").data(d3.values(literales)).enter()
        .append("svg:g");

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

    var textCircles = svg.append("svg:g").selectAll("g").data(d3.values(recursos)).enter()
        .append("svg:g");

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

    // $("[class^='path_label']").hide();

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
    /**
	 * el codigp a partir de aqui es para lo de destacar los nodos a los que
	 * esta ligado un nodo
	 * 
	 * @returns
	 */

    var toggle = 0;

    // Create an array logging what is connected to what
    var linkedByIndex = {};
    links.forEach(function(d) {
        linkedByIndex[d.source.name + "," + d.source.name] = 1;
        linkedByIndex[d.target.name + "," + d.target.name] = 1;
        linkedByIndex[d.source.name + "," + d.target.name] = 1;
    });

    function connectedNodes(elementIndex) {
        if (toggle == 0) {
            
        	circle.style("opacity", function(o) {
                var opacity = 0.04;
                if (linkedByIndex[elementIndex.name + "," + o.name] == 1 || linkedByIndex[o.name + "," + elementIndex.name] == 1) {
                    opacity = 1;
                }
                return opacity;
            });
            rectangle.style("opacity", function(o) {
                var opacity = 0.04;
                if (linkedByIndex[elementIndex.name + "," + o.name] == 1 || linkedByIndex[o.name + "," + elementIndex.name] == 1) {
                    opacity = 1;
                }
                
                return opacity;
            });
            link.style("opacity", function(o){
            	var opacity = 0.04;
                if (linkedByIndex[elementIndex.name + "," + o.source.name] == 1 || linkedByIndex[o.source.name + "," + elementIndex.name] == 1) {
                    opacity = 1;
                }
                return opacity;
            });
            var cont=0;
            console.log(rectangle);
            console.log("------");
            console.log(textRectangles);
            
            // for (var e in textRectangles[0]){
// var nodoTextoRecurso = d3.select(temp1[0][e]).node().__data__.name;
// }
            
            
            
// textPath.style("opacity", function(o) {
// var opacity = 0.04;
// if (linkedByIndex[elementIndex.name + "," + o.name] == 1 ||
// linkedByIndex[o.name + "," + elementIndex.name] == 1) {
// opacity = 1;
// }
// return opacity;
// });
            toggle = 1;
        } else {
            // Put them back to opacity=1
            circle.style("opacity", 1);
            rectangle.style("opacity", 1);
            link.style("opacity", 1);
            toggle = 0;
        }
    }
}

// function onMouseOver(pNodo) {
// cont=cont+1;
// console.log(cont);
// $("[id=" + eliminarSimbolos(pNodo) + "]").show();
// }

function onMouseOut() {
    if (visibles) {
        $("[class^='path_label']").hide();
        visibles = false;
    } else {
        $("[class^='path_label']").show();
        visibles = true;
    }
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

function ocultarElemento(element) {
    var theNode = d3.select(element);
    theNode.attr("fill", "#ccc");
}

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

function getFormatoJson(data) {
    var data = JSON.parse(JSON.stringify(data));
    var x = data.split(";");
    var generalElements = [];
    var centralElements = {};
    x.forEach(function(links, i) {
        y = links.split(",");
        generalElements.push({
            source: y[0],
            target: y[1],
            type: y[2]
        });
        centralElements = {};
    });
    return generalElements;
}