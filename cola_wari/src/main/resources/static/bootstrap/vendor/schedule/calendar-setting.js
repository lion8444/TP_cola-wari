let checkValues = new Array;

let titleCheck = false;
let startCheck = false;
let endCheck = false;
let addrCheck = false;
let descCheck = false;

let titleCheck_update = true;
let startCheck_update = true;
let endCheck_update = true;
let addrCheck_update = true;
let descCheck_update = true;

jQuery(document).ready(function () {
	checkValues = $('input[type="checkbox"]:checked').map(function () {
		return $(this).val();
	}).get();
	checkedTest(checkValues);

	$('#schedule-title').on('keyup', titleRegexCheck);
	// $('#schedule-start').on('input', dateTimeRegexCheck);
	// $('#schedule-end').on('input', endDateTimeRegexCheck);
	$('#schedule-addr').on('keyup', addrRegexCheck);
	$('#schedule-desc').on('keyup', descRegexCheck);

	$('input:checkbox').on('change', function () {
		if ($(this).is(':checked')) {
			console.log($(this).val());
			checkValues.push($(this).val());
		} else {
			checkValues = checkValues.filter((element) => element !== $(this).val());
		}
		checkedTest(checkValues);
	});

	$('#add-event-submit').on('click', insertScheduleSubmit);
});
// $(document).on('change', 'input:checkbox[name=userId]', checkedTest);

function titleRegexCheck() {
	const regex = /^[\p{Script=Latin}\p{Script=Hiragana}\p{Script=Katakana}\p{Script=Han}\d\s]{1,30}$/u;
	let content = $(this);
	if (!regex.test($(this).val())) {
		titleCheck = false;
		titleCheck_update = false;
		content.next(".check_html")
			.text("英語、日本語、数字、空欄で最大30文字以内に入力してください。")
			.css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "12px"
			});
	} else {
		titleCheck = true;
		titleCheck_update = true;
		content.next(".check_html")
			.text("");
		return;
	}
}
function dateTimeRegexCheck() {
	const dateTimeRegex = /^(?:(\d{4})\/(\d{2})\/(\d{2}) (\d{2}):(\d{2}) (午前|午後|am|pm))$/;
	let content = $('#schedule-start');
	if (!dateTimeRegex.test($('#schedule-start').val())) {
		startCheck = false;
		startCheck_update = false;
		content.next(".check_html")
			.text("'2000/01/01 01:30 (am/pm)/(午前/午後)'の形式で入力してください。")
			.css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "12px"
			});
	} else {
		startCheck = true;
		startCheck_update = true;
		content.next(".check_html")
			.text("");
		return;
	} if (startCheck && endCheck) {
		let startDate = new Date($('#schedule-start').val());
		let endDate = new Date($('#schedule-end').val());
		if (endDate < startDate) {
			startCheck = false;
			startCheck_update = false;
			content.next(".check_html")
				.text("開始日付が終了日付より早いです。")
				.css({
					"color": "#FA3E3E",
					"font-weight": "bold",
					"font-size": "12px"
				});
		} else {
			startCheck = true;
			startCheck_update = true;
			content.next(".check_html")
				.text("");
			return;
		}
	}
}

function endDateTimeRegexCheck() {
	const dateTimeRegex = /^(?:(\d{4})\/(\d{2})\/(\d{2}) (\d{2}):(\d{2}) (午前|午後|am|pm))$/;
	let content = $('#schedule-end');
	if (!dateTimeRegex.test($('#schedule-end').val())) {
		endCheck = false;
		endCheck_update = false;
		content.next(".check_html")
			.text("'2000/01/01 01:30 (am/pm)/(午前/午後)'の形式で入力してください。")
			.css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "12px"
			});
	} else {
		endCheck = true;
		endCheck_update = true;
		content.next(".check_html")
			.text("");
	} if (startCheck && endCheck) {
		let startDate = new Date($('#schedule-start').val());
		let endDate = new Date($('#schedule-end').val());
		if (endDate < startDate) {
			endCheck = false;
			endCheck_update = false;
			content.next(".check_html")
				.text("終了日付が開始日付より早いです。")
				.css({
					"color": "#FA3E3E",
					"font-weight": "bold",
					"font-size": "12px"
				});
		} else {
			endCheck = true;
			endCheck_update = true;
			content.next(".check_html")
				.text("");
			return;
		}
	}


}
function addrRegexCheck() {
	const regex = /^[\p{Script=Latin}\p{Script=Hiragana}\p{Script=Katakana}\p{Script=Han}\d\s]{1,100}$/u;
	let content = $(this);
	if (!regex.test($(this).val())) {
		addrCheck = false;
		addrCheck_update = false;
		content.next(".check_html")
			.text("英語、日本語、数字、空欄で最大100文字以内に入力してください。")
			.css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "12px"
			});
	} else {
		addrCheck = true;
		addrCheck_update = true;
		content.next(".check_html")
			.text("");
		return;
	}
}
function descRegexCheck() {
	const regex = /^[\p{Script=Latin}\p{Script=Hiragana}\p{Script=Katakana}\p{Script=Han}\d\s]{1,500}$/u;
	let content = $(this);
	if (!regex.test($(this).val())) {
		descCheck = false;
		descCheck_update = false;
		content.next(".check_html")
			.text("英語、日本語、数字、空欄で最大500文字以内に入力してください。")
			.css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "12px"
			});
	} else {
		descCheck = true;
		descCheck_update = true;
		content.next(".check_html")
			.text("");
		return;
	}
}


function insertScheduleSubmit() {
	dateTimeRegexCheck();
	endDateTimeRegexCheck();
	if (!titleCheck) {
		$('#schedule-title').focus();
		return;
	} else if (!startCheck) {
		$('#schedule-start').focus();
		return;
	} else if (!endCheck) {
		$('#schedule-end').focus();
		return;
	} else if (!addrCheck) {
		$('#schedule-addr').focus();
		return;
	} else if (!descCheck) {
		$('#schedule-desc').focus();
		return;
	}
	if (checkValues == null || checkValues.length == 0) {
		alert("参加者を選択してください");
		return;
	}
	if ($('#agendaSelect').val() == null) {
		alert("案件を選択してください");
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


function checkedTest(checkValues) {
	$('#agendaSelect').empty();

	console.log(checkValues);
	if (checkValues == null || checkValues.length == 0) {
		$('#agendaSelect').append($('<option></option>').attr('value', 0).text('案件無し（個人スケジュール）'));
	} else {
		$.ajax({
			url: "/cola_wari/schedule/agendaList"
			, type: "post"
			, data: { userIdList: checkValues }
			, success: (agendas) => {
				$('#agendaSelect').empty();
				if (agendas == null || agendas.length == 0) {
					console.log("null");
					$('#agendaSelect').append($('<option></option>').attr('value', 0).text('案件無し（個人スケジュール）'));
				} else {
					$('#agendaSelect').empty();
					$('#agendaSelect').append($('<option></option>').attr('value', 0).text('個人スケジュール'));
					agendas.forEach(element => {
						console.log(element)
						// $option = '<option value=' + element.agendaId  + ' text='+element.title+ '>';
						$('#agendaSelect').append($('<option></option>').attr('value', element.agendaId).text(element.title));
					});
				}

			}
			, error: () => {
				alert("error")
			}
		});
	}

}
function updateSchedule() {
	dateTimeRegexCheck();
	endDateTimeRegexCheck();
	if (!titleCheck_update) {
		$('#schedule-title').focus();
		return false;
	} else if (!startCheck_update) {
		$('#schedule-start').focus();
		return false;
	} else if (!endCheck_update) {
		$('#schedule-end').focus();
		return false;
	} else if (!addrCheck_update) {
		$('#schedule-addr').focus();
		return false;
	} else if (!descCheck_update) {
		$('#schedule-desc').focus();
		return false;
	} else if (checkValues == null) {
		alert("参加者を選択してください");
		return false;
	} else if ($('#agendaSelect').val() == null) {
		alert("案件を選択してください");
		return false;
	} else {
		return true;
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