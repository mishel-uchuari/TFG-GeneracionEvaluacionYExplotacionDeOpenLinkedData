<!DOCTYPE html>
<html>
  <head>
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