$(document).ready(function () {
  createRoomChart();
  createViewAllSchedule();
  createSchedulePieChartStatus();
  createViewStatusLineChart();
  createStatusPieChart();
});

function createRoomChart() {
  var labels = [];
  var data = [];

  $.ajax({
    method: "GET",
    url: "statistic/api/room",
    dataType: "json",
    contentType: "application/json",
    success: (result) => {
      result.forEach((element) => {
        labels.push(element.name);
        data.push(element.meeting.length);
      });
      var chartConfig = {
        type: "bar",
        data: {
          labels: labels,
          datasets: [
            {
              data: data,
              label: "Schedule Index",
              backgroundColor: "rgba(178, 164, 255, 0.7)",
              borderColor: "rgba(119, 82, 255)",
              borderWidth: 1,
            },
          ],
        },
        options: {
          plugins: {
            title: {
              display: true,
              text: "Meeting in Room",
            },
          },
          legend: {
            position: "bottom",
          },
        },
      };
      new Chart(document.getElementById("chRoom"), chartConfig);
    },
  });
}

function createViewAllSchedule() {
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
    url: "statistic/api/meeting",
    dataType: "json",
    contentType: "application/json",
    success: (result) => {
      result.forEach((element) => {
        const date = new Date(element.dateTimeStart);

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
            },
          ],
        },
        options: {
          legend: {
            position: "bottom",
          },
        },
      };

      new Chart(document.getElementById("lineMonth"), chartConfig);
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
    url: "statistic/api/meeting",
    dataType: "json",
    contentType: "application/json",
    success: (result) => {
      result.forEach((element) => {
        if (element.isOnline === "Online") {
          dataOnline.push(element);
        } else if (element.isOnline === "Offline") {
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
        type: "doughnut",
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
            text: "Schedule",
          },
          legend: {
            position: "right",
          },
          maintainAspectRatio: false,
        },
      };

      new Chart(document.getElementById("pieOnline"), chartConfig);
    },
  });
}

function createViewStatusLineChart() {
  var labels = ["Scheduled", "Started", "Finished", "Canceled"];
  var data = [];
  var dataScheduled = [];
  var dataStarted = [];
  var dataFinished = [];
  var dataCanceled = [];

  $.ajax({
    method: "GET",
    url: "statistic/api/meeting",
    dataType: "json",
    contentType: "application/json",
    success: (result) => {
      result.forEach((element) => {
        if (element.status.id === 1) {
          dataScheduled.push(element);
        } else if (element.status.id === 2) {
          dataStarted.push(element);
        } else if (element.status.id === 3) {
          dataFinished.push(element);
        } else if (element.status.id === 4) {
          dataCanceled.push(element);
        }
      });

      data.push(dataScheduled.length);
      data.push(dataStarted.length);
      data.push(dataFinished.length);
      data.push(dataCanceled.length);

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
            },
          ],
        },
        options: {
          title: {
            display: true,
            text: "Schedule",
          },
          legend: {
            position: "right",
          },
          maintainAspectRatio: false,
        },
      };

      new Chart(document.getElementById("lineStatus"), chartConfig);
    },
  });
}

function createStatusPieChart() {
  var labels = ["Waiting Confirmation", "Accepted", "Rejected"];
  var data = [];
  var dataWaiting = [];
  var dataAccepted = [];
  var dataRejected = [];

  $.ajax({
    method: "GET",
    url: "statistic/api/invitation",
    dataType: "json",
    contentType: "application/json",
    success: (result) => {
      result.forEach((element) => {
        if (element.status.id === 5) {
          dataWaiting.push(element);
        } else if (element.status.id === 6) {
          dataAccepted.push(element);
        } else if (element.status.id === 7) {
          dataRejected.push(element);
        }
      });

      data.push(dataWaiting.length);
      data.push(dataAccepted.length);
      data.push(dataRejected.length);

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
            text: "Invitation",
          },
          legend: {
            position: "right",
          },
          maintainAspectRatio: false,
        },
      };

      new Chart(document.getElementById("pieStatus"), chartConfig);
    },
  });
}
