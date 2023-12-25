$(document).ready(function () {
  getAllMeeting();
});

function getAllMeeting() {
  $.ajax({
    method: "GET",
    url: "api/list/participant",
    dataType: "json",
    beforeSend: addCsrfToken(),
    contentType: "application/json",
    success: (result) => {
      console.log(result);
      result.forEach((element) => {
        const meetingList = $("#meeting-list");
        const meetingDetail = $("<section>").addClass("meeting-detail-overview");

        const meetingOverviewDetail = $("<div>").addClass("meeting-overview-detail");
        const meetingTitle = $("<div>").addClass("fw-bold").text(element.title);
        meetingOverviewDetail.append(meetingTitle);

        const meetingTime = $("<div>").addClass("meeting-data");
        const meetingTimeIcon = $("<i>").addClass("fa-solid fa-clock");
        const meetingTimeSpan = $("<span>").text("Meeting Time");
        const meetingTimeText = $("<p>").addClass("pl2");
        const startTimeSpan = $("<span>").text(element.dateTimeStart);
        const endTimeSpan = $("<span>").text(element.dateTimeEnd);
        meetingTimeText.append(startTimeSpan, " - ", endTimeSpan);
        meetingTime.append(meetingTimeIcon, meetingTimeSpan, meetingTimeText);

        const meetingPlace = $("<div>").addClass("meeting-data");
        const meetingPlaceIcon = $("<i>").addClass("fa-solid fa-location-dot");
        const meetingPlaceSpan = $("<span>").text("Meeting Place");
        var meetingPlaceBlock1;
        var meetingPlaceBlock2;
        if (element.venue !== null && element.link !== null) {
          meetingPlaceBlock1 = $("<span>")
            .addClass("block pl2")
            .text("Venue : " + element.venue.name);
          meetingPlaceBlock2 = $("<span>")
            .addClass("block pl2")
            .text("Link : " + element.link);
        } else if (element.venue !== null && element.link === null) {
          meetingPlaceBlock1 = $("<span>")
            .addClass("block pl2")
            .text("Venue : " + element.venue.name);
        } else {
          meetingPlaceBlock2 = $("<span>")
            .addClass("block pl2")
            .text("Link : " + element.link);
        }
        meetingPlace.append(meetingPlaceIcon, meetingPlaceSpan, meetingPlaceBlock1, meetingPlaceBlock2);

        const participants = $("<div>").addClass("meeting-data");
        const participantsIcon = $("<i>").addClass("fa-solid fa-users");
        const participantsSpan = $("<span>").text("Participants");
        const participantsBlock = $("<span>").addClass("block pl2").text(element.invitations.length);
        participants.append(participantsIcon, participantsSpan, participantsBlock);

        meetingOverviewDetail.append(meetingTime, meetingPlace, participants);

        const meetingOverviewStatus = $("<div>").addClass("meeting-overview-status");
        const statusSpan = $("<span>").text("Status");
        const statusBlock = $("<span>").addClass("block").text(element.status.name);
        meetingOverviewStatus.append(statusSpan, statusBlock);

        const actionButtons = $("<div>").addClass("meeting-overview-action");

        const viewButton = $("<a>").attr("href", "/detail/" + element.id);
        const viewMeetingButton = $("<button>").attr("type", "button").text("VIEW MEETING");
        viewButton.append(viewMeetingButton);
        actionButtons.append(viewButton);

        if (element.status.id == 1) {
          const rescheduleButton = $("<a>").attr("href", "/update/" + element.id);
          const rescheduleMeetingButton = $("<button>").attr("type", "button").text("RESCHEDULE MEETING");
          rescheduleButton.append(rescheduleMeetingButton);
          actionButtons.append(rescheduleButton);

          const cancelForm = $("<form>")
            .attr("action", "/meeting/status/" + element.id)
            .attr("method", "post");
          const cancelMeetingButton = $("<button>")
            .attr("type", "submit")
            .attr("style", "color: black")
            .addClass("btn btn-danger")
            .text("CANCEL MEETING");
          cancelForm.append(cancelMeetingButton);
          actionButtons.append(cancelForm);

          cancelForm.on("submit", function () {
            return confirm("Are you sure to Cancel this data?");
          });
        }

        if (element.status.id == 2 && element.link) {
          const joinButton = $("<a>").attr("href", element.link);
          const joinMeetingButton = $("<button>").attr("type", "button").text("JOIN MEETING");
          joinButton.append(joinMeetingButton);
          actionButtons.append(joinButton);
        }

        meetingDetail.append(meetingOverviewDetail, meetingOverviewStatus, actionButtons);

        meetingList.append(meetingDetail);
      });
    },
  });
}
