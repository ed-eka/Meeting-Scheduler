$(document).ready(function () {
  $(".js-example-basic-multiple").select2();
});
$(document).ready(function () {});

function beforeCreate() {
  const participants = [];
  const addressVal = null;

  // Loop id melalui semua elemen input peserta
  const participantInputs = document.querySelectorAll(".add-participant");
  participantInputs.forEach((input) => {
    const nameVal = input.querySelector(
      'input[name^="participants["][name$="].name"]'
    ).value;
    const emailVal = input.querySelector(
      'input[name^="participants["][name$="].email"]'
    ).value;
    const phoneVal = input.querySelector(
      'input[name^="participants["][name$="].phone"]'
    ).value;

    participants.push({
      name: nameVal,
      email: emailVal,
      phone: phoneVal,
      phoneNumber: phoneVal,
    });
  });

  // Kirim data peserta dalam permintaan AJAX
  participants.forEach((participant) => {
    $.ajax({
      method: "POST",
      url: "/api/person",
      dataType: "json",
      beforeSend: addCsrfToken(),
      data: JSON.stringify(participant),
      contentType: "application/json",
      success: (result) => {
        console.log(result);
      },
    });
  });
}

function create() {
  let personIdVal = $("#person-name").val();
  let statusVal = 1;

  $.ajax({
    method: "POST",
    url: "/api/meeting/create-form",
    dataType: "json",
    beforeSend: addCsrfToken(),
    data: JSON.stringify({
      invitation: personIdVal,
      status: statusVal,
    }),
    contentType: "application/json",
    success: (result) => {
      console.log(result);
    },
  });
}

function beforeUpdate(id) {
  const participants = [];

  // Loop id melalui semua elemen input peserta
  const participantInputs = document.querySelectorAll(".add-participant");
  participantInputs.forEach((input) => {
    const nameVal = input.querySelector(
      'input[name^="participants["][name$="].name"]'
    ).value;
    const emailVal = input.querySelector(
      'input[name^="participants["][name$="].email"]'
    ).value;
    const phoneVal = input.querySelector(
      'input[name^="participants["][name$="].phone"]'
    ).value;

    participants.push({ name: nameVal, email: emailVal, phone: phoneVal });
  });

  $.ajax({
    method: "POST",
    url: "/api/person/registration",
    dataType: "json",
    beforeSend: addCsrfToken(),
    data: JSON.stringify(participants),
    contentType: "application/json",
    success: (result) => {
      console.log(result);
    },
  });
}

function update(id) {
  let personIdVal = $("#person-name").val(); // Tambahkan deklarasi variabel "nameVal"

  $.ajax({
    method: "PUT",
    url: "/api/meeting/update-form/" + id,
    dataType: "json",
    beforeSend: addCsrfToken(),
    data: JSON.stringify({
      invitation: personIdVal,
    }),
    contentType: "application/json",
    success: (result) => {
      console.log(result.id);
    },
  });
}

function cancelMeeting(id) {
  let statusVal = 4;

  Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to restore this meeting???",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, cancel it!",
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        method: "PUT",
        url: "/api/meeting/update-form/" + id,
        beforeSend: addCsrfToken(),
        data: JSON.stringify({
          status: statusVal,
        }),
        contentType: "application/json",
        success: (result) => {
          console.log(result.id);
        },
      });
    }
  });
}
