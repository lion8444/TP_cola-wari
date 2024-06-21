let checkValues = new Array;

jQuery(document).ready(function () {

	$('#selectBoxClick').on("click", showCheckboxes);
	$('input:checkbox').on('change', checkedTest);
	$('#add-event-submit').on('click', insertScheduleSubmit);
});
// $(document).on('change', 'input:checkbox[name=userId]', checkedTest);

function insertScheduleSubmit() {
	if (checkValues == null) {
		alert("参加者を選択してください");
		return;
	}
	$.ajax({
		url: "/cola_wari/schedule/resist/insert"
		, type: "post"
		, data: $('#add-event').serialize()
		, dataType: 'json'
		, success: (data) => {
			if (data == 1) {
				console.log("成功");
				location.reload();
			}
			else {
				console.log("失敗")
				location.replace("/cola_wari/");
			}
		}
		, error: () => {
			alert("error")
		}
	});
}


function checkedTest() {
	if ($(this).is(':checked')) {
		console.log($(this).val());
		checkValues.push($(this).val());
	} else {
		checkValues = checkValues.filter((element) => element !== $(this).val());
	}
	// $('input[name=userId]:checked').each(function() {

	// });
	console.log(checkValues);
	if (checkValues != null) {
		$.ajax({
			url: "/cola_wari/schedule/agendaList"
			, type: "post"
			, data: { userIdList: checkValues }
			, success: (agendas) => {
				if (agendas == null) {
					console.log("null");
					$('#agendaSelect').append($('<option></option>').attr('value', null).text('参加者たちに該当する案件が存在しない'));
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

var expanded = false;

function showCheckboxes() {
	var checkboxes = document.getElementById("checkboxes");
	if (!expanded) {
		checkboxes.style.display = "block";
		expanded = true;
	} else {
		checkboxes.style.display = "none";
		expanded = false;
	}
}

(function () {
	"use strict";
	// ------------------------------------------------------- //
	// Calendar
	// ------------------------------------------------------ //
	$.ajax({
		url: "/cola_wari/schedule/calendar"
		, type: "post"
		, data: { data: "abc" }
		, success: (schedules) => {
			console.log(schedules);
			jQuery(function () {
				// page is ready

				jQuery("#calendar").fullCalendar({
					themeSystem: "bootstrap4",
					// emphasizes business hours
					businessHours: false,
					defaultView: "month",
					// event dragging & resizing
					editable: false,
					// header
					header: {
						left: "title",
						center: "month,agendaWeek,agendaDay",
						right: "today prev,next",
					},

					events: schedules,

					dayClick: function () {
						jQuery("#modal-view-event-add").modal();
					},
					eventClick: function (event, jsEvent, view) {
						jQuery(".event-icon").html("<i class='fa fa-" + event.icon + "'></i>");
						jQuery(".event-title").html(event.title);
						jQuery(".event-body").html(event.description);
						jQuery(".eventUrl").attr("href", "/cola_wari/schedule/detail/" + event.scheduleId);
						jQuery("#modal-view-event").modal();
					},
				});
			});

		}
		, error: () => {
			alert("error")
		}
	});

})(jQuery);