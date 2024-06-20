jQuery(document).ready(function () {
	jQuery("#add-event").submit(function () {
		alert("Submitted");
		var values = {};
		$.each($("#add-event").serializeArray(), function (i, field) {
			values[field.name] = field.value;
		});
		console.log(values);
	});
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
						jQuery(".eventUrl").attr("href", "/schedule/detail/" + event.scheduleId);
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
