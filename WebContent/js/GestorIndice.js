function ejecutarServlet() {
	var form = $("#form");
	$
			.ajax({
				url : "ServGeneradorResultados",
				data : form.serialize(),
				type : "post",
				success : function(data) {
					console.log(typeof data);
					var obj=JSON.parse(data);
					console.log(typeof obj);
					render(obj);
				}
			});
}


  function render(json) {
    var config = {
      "selector": "#result"
    }
    d3sparql.htmltable(json, config)
  }