$(document).ready(function () { 

	$("#countries").change(function(){
		var selectedCountry = $(this).children("option:selected").val();
		$.ajax({
			type: "GET",
			url: "/ComuniDiceWeb/usuario",
			data: { 'id':selectedCountry,
				'action':"preSignUp"},
				contentType:"application/x-www-form-urlencoded; charset=ISO-8859-1",
				dataType:"json",
				success: function (regionsArray) {

					if(!$("#regions").html().isEmpty){
						$("#regions").html("");
					}

					for(var i = 0; i < regionsArray.length; i++){
                        $("#regions").html($("#regions").html()
                            +"<option value="+regionsArray[i].idRegion+">"+regionsArray[i].nombre+"</option>")

					}

				}
		});

	});
});