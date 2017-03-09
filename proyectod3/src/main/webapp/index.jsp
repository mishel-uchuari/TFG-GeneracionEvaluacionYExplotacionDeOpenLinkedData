<!DOCTYPE html>
<html>
  <head>
    <script src="http://d3js.org/d3.v3.min.js"></script>
    <script src="d3sparql.js"></script>
    <script>
    function exec() {
      var endpoint = d3.select("#endpoint").property("value")
      var sparql = d3.select("#sparql").property("value")
      d3sparql.query(endpoint, sparql, render)
    }
    function render(json) {
      var config = {
        // for d3sparql.tree()
        "root": "root_name",
        "parent": "parent_name",
        "child": "child_name",
        // for d3sparql.dendrogram()
        "width": 900,
        "height": 4500,
        "margin": 350,
        "radius": 5,
        "selector": "#result"
      }
      d3sparql.dendrogram(json, config)
    }
    function exec_offline() {
      d3.json("cache.json", render)
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
	<form method="post" action="ServIdent">
        <label>SPARQL endpoint:</label>
        <div class="input-append">
          <input id="endpoint" class="span5" value="http://opendata.eurohelp.es:5820/test#!/query/" type="text">
          <button class="btn" type="button" onclick="exec()">Query</button>
          <button class="btn" type="button" onclick="exec_offline()">Use cache</button>
          <button class="btn" type="button" onclick="toggle()">Hide<i id="button" class="icon-chevron-up"></i></button>
          	<input type="submit" value="Identificarse">
          
        </div>
      </form>
      <textarea id="sparql" class="span9" rows=15>
select * where {?s ?o ?p}
      </textarea>
    </div>
    <div id="result"></div>
  </body>
</html>