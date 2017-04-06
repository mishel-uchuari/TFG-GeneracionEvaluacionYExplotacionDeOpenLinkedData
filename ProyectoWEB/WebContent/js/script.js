function perri(json, config){
	var w = 1000;
    var h = 600;
    var linkDistance=200;
    var colors = d3.scale.category10();
    config = {
    	      "key1":   "s",       // SPARQL variable name for node1 (optional; default is the 1st variable)
    	      "key2":   "o",       // SPARQL variable name for node2 (optional; default is the 2nd varibale)
    	      "label1": "s",  // SPARQL variable name for the label of node1 (optional; default is the 3rd variable)
    	      "label2": "o",  // SPARQL variable name for the label of node2 (optional; default is the 4th variable)
    	      "value1": "p",
    	    "value2":"p"
    	    }
    var json ={
    		  "head" : {
    			    "vars" : [ "s", "p", "o" ]
    			  },
    			  "results" : {
    			    "bindings" : [ {
    			      "s" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/AlexLara"
    			      },
    			      "p" : {
    			        "type" : "uri",
    			        "value" : "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
    			      },
    			      "o" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/ProjectManager"
    			      }
    			    }, {
    			      "s" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/AlexLara"
    			      },
    			      "p" : {
    			        "type" : "uri",
    			        "value" : "http://schema.org/name"
    			      },
    			      "o" : {
    			        "type" : "literal",
    			        "value" : "Alex Lara"
    			      }
    			    }, {
    			      "s" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/AlexLara"
    			      },
    			      "p" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/skill"
    			      },
    			      "o" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/ProjectManagement"
    			      }
    			    }, {
    			      "s" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/AlexLara"
    			      },
    			      "p" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/certification"
    			      },
    			      "o" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/PMP"
    			      }
    			    }, {
    			      "s" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.eurohelp.es/MikelEgana"
    			      },
    			      "p" : {
    			        "type" : "uri",
    			        "value" : "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
    			      },
    			      "o" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/Analyst"
    			      }
    			    }, {
    			      "s" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.eurohelp.es/MikelEgana"
    			      },
    			      "p" : {
    			        "type" : "uri",
    			        "value" : "http://schema.org/name"
    			      },
    			      "o" : {
    			        "type" : "literal",
    			        "value" : "Mikel Egaña"
    			      }
    			    }, {
    			      "s" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.eurohelp.es/MikelEgana"
    			      },
    			      "p" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/skill"
    			      },
    			      "o" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/java"
    			      }
    			    }, {
    			      "s" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.eurohelp.es/MikelEgana"
    			      },
    			      "p" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/skill"
    			      },
    			      "o" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/r"
    			      }
    			    }, {
    			      "s" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.eurohelp.es/MikelEgana"
    			      },
    			      "p" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/skill"
    			      },
    			      "o" : {
    			        "type" : "uri",
    			        "value" : "http://opendata.euskadi.eus/eus"
    			      }
    			    }]
    			  }
    			};
    var graph = (json.head && json.results) ? d3sparql.graph(json, config) : json

 
    var svg = d3.select("body").append("svg").attr({"width":w,"height":h});

    var force = d3.layout.force()
        .nodes(graph.nodes)
        .links(graph.links)
        .size([w,h])
        .linkDistance([linkDistance])
        .charge([-500])
        .theta(0.1)
        .gravity(0.05)
        .start();

 
    var links = svg.selectAll("line")
      .data(graph.links)
      .enter()
      .append("line")
      .attr("id",function(d,i) {return 'edge'+i})
      .attr('marker-end','url(#arrowhead)')
      .style("stroke","#ccc")
      .style("pointer-events", "none");
    
    var nodes = svg.selectAll("circle")
      .data(graph.nodes)
      .enter()
      .append("circle")
      .attr({"r":15})
      .style("fill",function(d,i){return colors(i);})
      .call(force.drag)


    var nodelabels = svg.selectAll(".nodelabel") 
       .data(graph.nodes)
       .enter()
       .append("text")
       .attr({"x":function(d){return d.x;},
              "y":function(d){return d.y;},
              "class":"nodelabel",
              'font-size':15,
              "stroke":"black"})
       .text(function(d){return d.key;});

    var edgepaths = svg.selectAll(".edgepath")
        .data(graph.links)
        .enter()
        .append('path')
        .attr({'d': function(d) {return 'M '+d.source.x+' '+d.source.y+' L '+ d.target.x +' '+d.target.y},
               'class':'edgepath',
               'fill-opacity':0,
               'stroke-opacity':0,
               'fill':'blue',
               'stroke':'red',
               'id':function(d,i) {return 'edgepath'+i}})
        .style("pointer-events", "none");

    var edgelabels = svg.selectAll(".linkLabel")
        .data(graph.links)
        .enter()
        .append('text')
        .style("pointer-events", "none")
        .attr({'class':'.linkLabel',
               'id':function(d,i){return '.linkLabel'+i},
               'dx':20,
               'dy':0,
               'font-size':7,
               'fill':'black'});
    //VALOR DE LOS NODOS EN D.
    edgelabels.append('textPath')
        .attr('xlink:href',function(d,i) {return '#edgepath'+i})
        .style("pointer-events", "none")
        .text(function(d,i){return d.source.value });
    
    


    svg.append('defs').append('marker')
        .attr({'id':'arrowhead',
               'viewBox':'-0 -5 10 10',
               'refX':25,
               'refY':0,
               //'markerUnits':'strokeWidth',
               'orient':'auto',
               'markerWidth':10,
               'markerHeight':10,
               'xoverflow':'visible'})
        .append('svg:path')
            .attr('d', 'M 0,-5 L 10 ,0 L 0,5')
            .attr('fill', '#ccc')
            .attr('stroke','#ccc');
     

    force.on("tick", function(){

        links.attr({"x1": function(d){return d.source.x;},
                    "y1": function(d){return d.source.y;},
                    "x2": function(d){return d.target.x;},
                    "y2": function(d){return d.target.y;}
        });

        nodes.attr({"cx":function(d){return d.x;},
                    "cy":function(d){return d.y;}
        });

        nodelabels.attr("x", function(d) { return d.x; }) 
                  .attr("y", function(d) { return d.y; });

        edgepaths.attr('d', function(d) { var path='M '+d.source.x+' '+d.source.y+' L '+ d.target.x +' '+d.target.y;
                                           //console.log(d)
                                           return path});       

        edgelabels.attr('transform',function(d,i){
            if (d.target.x<d.source.x){
                bbox = this.getBBox();
                rx = bbox.x+bbox.width/2;
                ry = bbox.y+bbox.height/2;
                return 'rotate(180 '+rx+' '+ry+')';
                }
            else {
                return 'rotate(0)';
                }
        });
    });

}