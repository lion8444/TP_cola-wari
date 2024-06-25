let checkValues = new Array;

let titleCheck = false;
let startCheck = false;
let endCheck = false;
let addrCheck = false;
let descCheck = false;

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
	const regex = /^[\u0000-\u007F\u3040-\u309F\u30A0-\u30FF\d\s]{1,30}$/;
	let content = $(this);
	if (!regex.test($(this).val())) {
		titleCheck = false;
		content.next(".check_html")
			.text("英語、日本語、数字、空欄で最大30文字以内に入力してください。")
			.css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "12px"
			});
	} else {
		titleCheck = true;
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
		content.next(".check_html")
			.text("'2000/01/01 01:30 (am/pm)/(午前/午後)'の形式で入力してください。")
			.css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "12px"
			});
	} else {
		startCheck = true;
		content.next(".check_html")
			.text("");
		return;
	} if (startCheck && endCheck) {
		let startDate = new Date($('#schedule-start').val());
		let endDate = new Date($('#schedule-end').val());
		if (endDate < startDate) {
			startCheck = false;
			content.next(".check_html")
				.text("開始日付が終了日付より早いです。")
				.css({
					"color": "#FA3E3E",
					"font-weight": "bold",
					"font-size": "12px"
				});
		} else {
			startCheck = true;
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
		content.next(".check_html")
			.text("'2000/01/01 01:30 (am/pm)/(午前/午後)'の形式で入力してください。")
			.css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "12px"
			});
	} else {
		endCheck = true;
		content.next(".check_html")
			.text("");
	} if (startCheck && endCheck) {
		let startDate = new Date($('#schedule-start').val());
		let endDate = new Date($('#schedule-end').val());
		if (endDate < startDate) {
			endCheck = false;
			content.next(".check_html")
				.text("終了日付が開始日付より早いです。")
				.css({
					"color": "#FA3E3E",
					"font-weight": "bold",
					"font-size": "12px"
				});
		} else {
			endCheck = true;
			content.next(".check_html")
				.text("");
			return;
		}
	}


}
function addrRegexCheck() {
	const regex = /^[\u0000-\u007F\u3040-\u309F\u30A0-\u30FF\d\s]{1,100}$/;
	let content = $(this);
	if (!regex.test($(this).val())) {
		addrCheck = false;
		content.next(".check_html")
			.text("英語、日本語、数字、空欄で最大100文字以内に入力してください。")
			.css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "12px"
			});
	} else {
		addrCheck = true;
		content.next(".check_html")
			.text("");
		return;
	}
}
function descRegexCheck() {
	const regex = /^[\u0000-\u007F\u3040-\u309F\u30A0-\u30FF\d\s]{1,500}$/;
	let content = $(this);
	if (!regex.test($(this).val())) {
		descCheck = false;
		content.next(".check_html")
			.text("英語、日本語、数字、空欄で最大500文字以内に入力してください。")
			.css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "12px"
			});
	} else {
		descCheck = true;
		content.next(".check_html")
			.text("");
		return;
	}
}


function updateSchedule() {
	dateTimeRegexCheck();
	endDateTimeRegexCheck();
	if (!titleCheck) {
		$('#schedule-title').focus();
		return false;
	} else if (!startCheck) {
		$('#schedule-start').focus();
		return false;
	} else if (!endCheck) {
		$('#schedule-end').focus();
		return false;
	} else if (!addrCheck) {
		$('#schedule-addr').focus();
		return false;
	} else if (!descCheck) {
		$('#schedule-desc').focus();
		return false;
	} else if (checkValues == null || checkValues.length == 0) {
		alert("参加者を選択してください");
		return false;
	} else if ($('#agendaSelect').val() == null) {
		alert("案件を選択してください");
		return false;
	} else {
		return true;
	}
}