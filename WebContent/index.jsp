<!DOCTYPE html>
<html>
<meta charset="UTF-8">
  <head>
   <style>
    .link {
      stroke: #999999;
    }
    .node {
      stroke: black;
      opacity: 0.8;
    }
    circle.node {
      stroke-width: 0.5px;
      fill: #9370DB;
    }
    text.node {
      font-family: "Arial";
      font-size: 11px;
    }
    </style>
    <script src="http://d3js.org/d3.v3.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/d3sparql.js"></script>
    <script>
   
    function render() {
    	var json = {
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
    				        "value" : "http://opendata.euskadi.eus/en"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.eurohelp.es/MikelEgana"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/experience"
    				      },
    				      "o" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/ehu"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.eurohelp.es/MikelEgana"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/education"
    				      },
    				      "o" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/university-of-manchester"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/java"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
    				      },
    				      "o" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/ComputerLanguage"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/java"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/name"
    				      },
    				      "o" : {
    				        "type" : "literal",
    				        "value" : "Java"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/r"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
    				      },
    				      "o" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/ComputerLanguage"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/r"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/name"
    				      },
    				      "o" : {
    				        "type" : "literal",
    				        "value" : "R"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/en"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
    				      },
    				      "o" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/Language"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/en"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/name"
    				      },
    				      "o" : {
    				        "type" : "literal",
    				        "value" : "english"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.eurohelp.es/ArkaitzCarbajo"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
    				      },
    				      "o" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/SeniorProgrammer"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.eurohelp.es/ArkaitzCarbajo"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/name"
    				      },
    				      "o" : {
    				        "type" : "literal",
    				        "value" : "Arkaitz Carbajo"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.eurohelp.es/ArkaitzCarbajo"
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
    				        "value" : "http://opendata.eurohelp.es/ArkaitzCarbajo"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/skill"
    				      },
    				      "o" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/en"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.eurohelp.es/InakiArizmendi"
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
    				        "value" : "http://opendata.eurohelp.es/InakiArizmendi"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/name"
    				      },
    				      "o" : {
    				        "type" : "literal",
    				        "value" : "Iñaki Arizmendi"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.eurohelp.es/LeireBardaji"
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
    				        "value" : "http://opendata.eurohelp.es/LeireBardaji"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/name"
    				      },
    				      "o" : {
    				        "type" : "literal",
    				        "value" : "Leire Bardaji"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.eurohelp.es/LeireBardaji"
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
    				        "value" : "http://opendata.euskadi.eus/es"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
    				      },
    				      "o" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/Language"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/es"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/name"
    				      },
    				      "o" : {
    				        "type" : "literal",
    				        "value" : "Spanish"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/es"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/alternateName"
    				      },
    				      "o" : {
    				        "type" : "literal",
    				        "value" : "es"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/eu"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
    				      },
    				      "o" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/Language"
    				      }
    				    }, {
    				      "s" : {
    				        "type" : "uri",
    				        "value" : "http://opendata.euskadi.eus/eu"
    				      },
    				      "p" : {
    				        "type" : "uri",
    				        "value" : "http://schema.org/name"
    				      },
    				      "o" : {
    				        "type" : "literal",
    				        "value" : "Basque"
    				      }
    				    } ]
    				  }
    				};
    	var config = {
        // for d3sparql.tree()
     /*    "root": "s",
        "parent": "p",
        "child": "o",
        // for d3sparql.dendrogram()
        "width": 500,
        "height": 500,
        "margin": 50,
        "radius": 10,
        "selector": "#result" */
    			"charge": -500,
    	        "distance": 200,
    	        "width": 1000,
    	        "height": 750,
    	        "selector": "#result"
      }
      d3sparql.forcegraph(json, config)
    }
    function exec_offline() {
      d3.json("json/graph.json", render)
    }
    function toggle() {
      d3sparql.toggle()
    }
	//http://ckan.eurohelp.es:5820/LODgenAppTurismo#!/query/
	//http://opendata.eurohelp.es:5820/test#!/query/
    </script>
  </head>
  <body>
    <div id="query" style="margin: 10px">
      <h1>d3dendrogram</h1>
      <!-- <form class="form-inline">
 -->     
 	<form method="post" action="ServQuery">
 <label>SPARQL endpoint:</label>
        <!---->   <div class="input-append">
          <input id="endpoint" class="span5" value="http://opendata.eurohelp.es:5820/test#!/query/" type="text">
          <button class="btn" type="button" onclick="render()">Query</button>
          <button class="btn" type="button" onclick="exec_offline()">Use cache</button>
          <button class="btn" type="button" onclick="toggle()">Hide<i id="button" class="icon-chevron-up"></i></button>
        </div> 
<input type="submit">     
      </form>
      <textarea id="sparql" class="span9" rows=15>
select * where {?s ?o ?p}
      </textarea>
    </div>
    <div id="result"></div>
  </body>
</html>