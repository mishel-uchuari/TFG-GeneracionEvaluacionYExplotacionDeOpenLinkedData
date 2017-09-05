<!DOCTYPE html>
<html>
<meta charset="UTF-8">
  <head>
 <style>
    .node {
      border: solid 1px white;
      font: 10px sans-serif;
      line-height: 12px;
      overflow: hidden;
      position: absolute;
      text-indent: 2px;
    }
    </style>
<!--    <style>
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
    </style> -->
    <script src="http://d3js.org/d3.v3.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/d3sparql.js"></script>
    <script src="<%=request.getContextPath()%>/js/script.js"></script>
    <script src="https://unpkg.com/d3-sankey@0"></script>
    <script>
    function tabla() {
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
    				    } ]
    				  }
    				};
  
      var config = {
       
      }
      d3sparql.htmltable(json, config)
    }
    function forcegraph() {
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
    } ]
  }
};
    	var config = {
    	"key1": "s",
        "key2": "o",
        "key3": "p",
        "label1": "s",
        "label2": "o",
        "label2": "p",
    	"charge": -500,
    	"distance": 200,
    	"width": 1000,
    	"height": 750,
    	"selector": "#result"
      }
      d3sparql.forcegraph(json, config)
    }
    
    function treemap() {
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
    } ]
  }
};
     var config = {
        // for d3sparql.tree()
       "root":"s", 
       "parent": "p",
   	   "child": "o",
       "value":  "value",
       
        // for d3sparql.dendrogram()
        "width": 1500,
        "height": 2000,
        "margin": 200,
        "radius": 15,
        "selector": "#result"
      }
      d3sparql.dendrogram(json,config)
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
        <!---->   <div class="input-append">
          <button class="btn" type="button" onclick="forcegraph()">forcegraph</button>
          <button class="btn" type="button" onclick="treemap()">treemap</button>
          <button class="btn" type="button" onclick="perri()">tabla</button>
          
        </div> 
      </form>
    </div>
    <div id="result"></div>
  </body>
</html>