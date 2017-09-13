 function exec(json) {
      var config = {
        "selector": "#result"
      }
      d3sparql.htmltable(json, config)
    }