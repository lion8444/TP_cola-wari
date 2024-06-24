let checkValues = new Array;

jQuery(document).ready(function () {
	$('#add-event-submit').on('click', insertScheduleSubmit);
});

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
					defaultView: "agendaWeek",
					// event dragging & resizing
					editable: false,
					// header
					header: {
						left: "",
						center: "",
						right: "",
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