var json;
	function getQueryData() {
		var form = $("#form");
		$
				.ajax({
					url : "ServGeneradorResultados",
					data : form.serialize(),
					type : "post",
					success : function(receivedData) {
						json = receivedData;
						if (!receivedData) {
						console.log("vaciooooo")
							swal(
									{
										title : "Query no permitida",
										text : "Las querys deben limitarse a SELECT o CONSTRUCT",
										type : "warning",
										showCancelButton : false,
										confirmButtonClass : "btn-primary",
										confirmButtonText : "OK",
										closeOnConfirm : false
									},
									function() {
									});
						}
						else if(receivedData.includes("error") || receivedData.includes("Encountered") ){
						swal(
									{
										title : "Error de sintaxis",
										text : receivedData,
										type : "warning",
										showCancelButton : false,
										confirmButtonClass : "btn-primary",
										confirmButtonText : "OK",
										closeOnConfirm : false
									},
									function() {
									});
						}
						else{
						createTable(json);}
					}
				});
	}
	
	
	function getFile(element){
		var query=$('textarea').val()
		$.ajax({
            type: "POST",
            url: "ServDescargas",                
            dataType: "json",
            data: {"formato" : element, "query" : query},
            success:function(data){
            	console.log("?funciona?")
            }

        })       
    };