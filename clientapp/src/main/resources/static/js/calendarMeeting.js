document.addEventListener("DOMContentLoaded", function () {
  const prevMonthButton = document.getElementById("prevMonth");
  const nextMonthButton = document.getElementById("nextMonth");
  const currentMonthYear = document.getElementById("currentMonthYear");
  const table = document.querySelector("table");

  const today = new Date();
  let currentMonth = today.getMonth();
  let currentYear = today.getFullYear();

  function updateCalendar() {
    // Hapus semua entri kalender sebelumnya
    while (table.rows.length > 1) {
      table.deleteRow(1);
    }

    // Setel bulan dan tahun yang ditampilkan
    const currentMonthDate = new Date(currentYear, currentMonth, 1);
    currentMonthYear.textContent = currentMonthDate.toLocaleDateString(
      "en-US",
      { month: "long", year: "numeric" }
    );

    // Hitung hari pertama dari bulan saat ini
    const firstDay = currentMonthDate.getDay();

    // Hitung jumlah hari dalam bulan ini
    const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

    let date = 1;

    // Tambahkan baris-baris ke kalender
    for (let i = 0; i < 6; i++) {
      const row = table.insertRow();

      // Tambahkan sel-sel untuk setiap hari dalam satu minggu
      for (let j = 0; j < 7; j++) {
        const cell = row.insertCell();
        if (i === 0 && j < firstDay) {
          // Sel-sel kosong sebelum hari pertama
        } else if (date <= daysInMonth) {
          cell.textContent = date;
          if (
            currentYear === today.getFullYear() &&
            currentMonth === today.getMonth() &&
            date === today.getDate()
          ) {
            cell.classList.add("today"); // Bold tanggal hari ini
          }
          date++;
        }
      }
    }
  }

  // Tambahkan event listener untuk tombol bulan sebelumnya
  prevMonthButton.addEventListener("click", function () {
    if (currentMonth === 0) {
      currentMonth = 11;
      currentYear--;
    } else {
      currentMonth--;
    }
    updateCalendar();
  });

  // Tambahkan event listener untuk tombol bulan selanjutnya
  nextMonthButton.addEventListener("click", function () {
    if (currentMonth === 11) {
      currentMonth = 0;
      currentYear++;
    } else {
      currentMonth++;
    }
    updateCalendar();
  });

  // Tampilkan kalender saat halaman dimuat
  updateCalendar();
});
