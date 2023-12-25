$(document).ready(function () {
  $("#cardMeeting").hide();
  $("#role").hide();

  createCalendarView();
  createScheduleStatusView();
  createScheduleUpcomingViewCard();
  createSchedulePieChartStatus();
  createScheduleAllPerMonth();
});

function createCalendarView() {
  var calendarEl = document.getElementById("calendar-dashboard");
  var events = [];
  var colors = ["#FF8282", "#F4C68A", "#645CBB"];
  var color;
  $.ajax({
    method: "GET",
    url: "api/dashboard/meeting/participant",
    dataType: "json",
    beforeSend: addCsrfToken(),
    contentType: "application/json",
    success: (result) => {
      $.each(result, function (i, item) {
        if (i % 2 == 0) {
          color = colors[2];
        } else if (i == 0) {
          color = colors[1];
        } else {
          color = colors[0];
        }
        events.push({
          id: i,
          title: result[i].meeting.title,
          start: result[i].meeting.dateTimeStart,
          end: result[i].meeting.dateTimeEnd,
          color: color,
          background: color,
        });
      });

      var configCalendar = {
        timeZone: "UTC",
        headerToolbar: {
          left: "prev,next today",
          center: "title",
          right: "dayGridMonth,timeGridWeek",
        },
        editable: false,
        events: events,
        height: 400,
      };
      new FullCalendar.Calendar(calendarEl, configCalendar).render();
    },
  });
}

function createScheduleUpcomingViewCard() {
  $.ajax({
    method: "GET",
    url: "api/dashboard/meeting/date",
    dataType: "json",
    beforeSend: addCsrfToken(),
    contentType: "application/json",
    success: (result) => {
      if (result != "") {
        $(".txt-null").hide();
        $("#cardMeeting").append(`
            <div class="card-body">
              <div class="d-flex justify-content-between">
                <h5 class="card-title">${result.title}</h5>
                <span class="badge" id="badge-status">${result.online === true ? "Online" : "Offline"}</span>
              </div>
              <div class="d-flex justify-content-between" id="txt-date-schedule">
                <span>${result.dateTimeStart}</span>
                <span>${result.dateTimeEnd}</span>
              </div>
              <p class="card-text">
                ${result.description}
              </p>
            </div>
      `);
        $("#cardMeeting").show();
      }
    },
  });
}

function createSchedulePieChartStatus() {
  var labels = ["Online", "Offline", "Hybrid"];
  var dataOnline = [];
  var dataOffline = [];
  var dataHybrid = [];
  var data = [];

  $.ajax({
    method: "GET",
    url: "api/dashboard/meeting/participant",
    dataType: "json",
    beforeSend: addCsrfToken(),
    contentType: "application/json",
    success: (result) => {
      result.forEach((element) => {
        console.log(element);
        if (element.meeting.isOnline === "Online") {
          dataOnline.push(element);
        } else if (element.meeting.isOnline === "Offline") {
          dataOffline.push(element);
        } else {
          dataHybrid.push(element);
        }
      });

      data.push(dataOnline.length);
      data.push(dataOffline.length);
      data.push(dataHybrid.length);

      var barColors = ["#FF006E", "#3A86FF", "#8338EC"];

      var chartConfig = {
        type: "pie",
        data: {
          labels: labels,
          datasets: [
            {
              backgroundColor: barColors,
              data: data,
            },
          ],
        },
        options: {
          title: {
            display: true,
            text: "Pie Chart",
          },
        },
      };

      new Chart(document.getElementById("chStatus"), chartConfig);
    },
  });
}

function createScheduleStatusView() {
  $.ajax({
    method: "GET",
    url: "api/dashboard/meeting/status",
    dataType: "json",
    beforeSend: addCsrfToken(),
    contentType: "application/json",
    success: (result) => {
      $.each(result, function (i, item) {
        if (result[i].id === 1) {
          document.getElementById("c-scheduled").innerText = result[i].count;
        } else if (result[i].id === 2) {
          document.getElementById("c-start").innerText = result[i].count;
        } else if (result[i].id === 3) {
          document.getElementById("c-end").innerText = result[i].count;
        } else if (result[i].id === 4) {
          document.getElementById("c-canceled").innerText = result[i].count;
        }
      });
    },
  });
}

function createScheduleAllPerMonth() {
  var labels = ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec"];
  var data = [];
  var jan = [];
  var feb = [];
  var mar = [];
  var apr = [];
  var may = [];
  var jun = [];
  var jul = [];
  var aug = [];
  var sept = [];
  var oct = [];
  var nov = [];
  var dec = [];

  $.ajax({
    method: "GET",
    url: "api/dashboard/meeting/participant",
    dataType: "json",
    beforeSend: addCsrfToken(),
    contentType: "application/json",
    success: (result) => {
      result.forEach((element) => {
        const date = new Date(element.meeting.dateTimeStart);

        if (date.getMonth() + 1 == 1) {
          jan.push(element);
        } else if (date.getMonth() + 1 == 2) {
          feb.push(element);
        } else if (date.getMonth() + 1 == 3) {
          mar.push(element);
        } else if (date.getMonth() + 1 == 4) {
          apr.push(element);
        } else if (date.getMonth() + 1 == 5) {
          may.push(element);
        } else if (date.getMonth() + 1 == 6) {
          jun.push(element);
        } else if (date.getMonth() + 1 == 7) {
          jul.push(element);
        } else if (date.getMonth() + 1 == 8) {
          aug.push(element);
        } else if (date.getMonth() + 1 == 9) {
          sept.push(element);
        } else if (date.getMonth() + 1 == 10) {
          oct.push(element);
        } else if (date.getMonth() + 1 == 11) {
          nov.push(element);
        } else {
          dec.push(element);
        }
      });

      data.push(jan.length);
      data.push(feb.length);
      data.push(mar.length);
      data.push(apr.length);
      data.push(may.length);
      data.push(jun.length);
      data.push(jul.length);
      data.push(aug.length);
      data.push(sept.length);
      data.push(oct.length);
      data.push(nov.length);
      data.push(dec.length);

      var barColors = ["#4059B2", "#3A86FF"];

      var chartConfig = {
        type: "line",
        data: {
          labels: labels,
          datasets: [
            {
              data: data,
              label: "Total Schedule",
              fill: false,
              borderColor: "rgb(142, 143, 250)",
              tension: 0.1,
            },
          ],
        },
        options: {
          title: {
            display: true,
            text: "Schedule/Month",
          },
        },
      };

      new Chart(document.getElementById("chMeeting"), chartConfig);
    },
  });
}
