let checkValues = new Array;
let inputCheck = false;
jQuery(document).ready(function () {
	$('#schedule-title').on('keyup', textInputCheck);
	$('#schedule-start').on('keyup', textInputCheck);
	$('#schedule-end').on('keyup', textInputCheck);
	$('#schedule-addr').on('keyup', textInputCheck);
	$('#schedule-desc').on('keyup', textInputCheck);
	$('input:checkbox').on('change', checkedTest);
	$('#add-event-submit').on('click', insertScheduleSubmit);
});
// $(document).on('change', 'input:checkbox[name=userId]', checkedTest);

function textInputCheck() {
	if(!$(this).val() == "") {
		inputCheck = true;
	} else {
		inputCheck = false;
	}
}

function insertScheduleSubmit() {
	if (!inputCheck) {
		alert("全入力欄を正しく入力してください。");
		return;
	}
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
		$('#agendaSelect').append($('<option></option>').attr('value', 0).text('案件無し（個人スケジュール）'));
	}else {
		$.ajax({
			url: "/cola_wari/schedule/agendaList"
			, type: "post"
			, data: { userIdList: checkValues }
			, success: (agendas) => {
				$('#agendaSelect').empty();
				if (agendas == null || agendas.length == 0) {
					console.log("null");
					$('#agendaSelect').append($('<option></option>').attr('value', 0).text('案件無し（個人スケジュール）'));
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