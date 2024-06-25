let result = null;

jQuery(document).ready(function () {
	result = $('#company-find-agents').val();
	if (result != 0) {
		checkedTest(result);
	}
	$('#company-find-agents').on('change', function() {
		result = $(this).val();
		checkedTest(result);
	});
});

function checkedTest(result) {
	$('#setting-agents').empty();
	// console.log($(this).val());
	// result = ($(this).val());
	if (result == null) {
		$('#setting-agents').append($('<option></option>').text('企業を選んでください'));
	} else {
		$.ajax({
			url: "/cola_wari/agenda/company_agents"
			, type: "post"
			, data: { comId: result }
			, success: (agents) => {
				$('#setting-agents').empty();
				if (agents == null) {
					console.log("null");
					$('#setting-agents').append($('<option></option>').text('企業を正しく選んでください'));
				}
				agents.forEach(element => {
					console.log(element)
					// $option = '<option value=' + element.agendaId  + ' text='+element.title+ '>';
					$('#setting-agents').append($('<option></option>').attr('value', element.agentId).text(element.agentName));
				});
			}
			, error: () => {
				console.log("error : find_agent");
			}
		});
	}

}