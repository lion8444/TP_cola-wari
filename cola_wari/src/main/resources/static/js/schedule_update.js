let checkValues = new Array;

jQuery(document).ready(function () {
	$('input:checkbox').on('change', checkedTest);
});

function checkedTest() {
	$('#agendaSelect').empty();
	if ($(this).is(':checked')) {
		console.log($(this).val());
		checkValues.push($(this).val());
	} else {
		checkValues = checkValues.filter((element) => element !== $(this).val());
	}
	// $('input[name=userId]:checked').each(function() {

	// });
	console.log(checkValues);
	if (checkValues == null || checkValues.length == 0) {
		$('#agendaSelect').append($('<option></option>').attr('value', 0).text('個人スケジュール'));
	}else {
		$.ajax({
			url: "/cola_wari/schedule/agendaList"
			, type: "post"
			, data: { userIdList: checkValues }
			, success: (agendas) => {
				$('#agendaSelect').empty();
				if (agendas == null) {
					console.log("null");
					$('#agendaSelect').append($('<option></option>').attr('value', 0).text('個人スケジュール'));
				}
				agendas.forEach(element => {
					console.log(element)
					// $option = '<option value=' + element.agendaId  + ' text='+element.title+ '>';
					$('#agendaSelect').append($('<option></option>').attr('value', element.agendaId).text(element.title));
				});
			}
			, error: () => {
				alert("error")
			}
		});
	}

}