document.addEventListener("DOMContentLoaded", function () {

    // Select all date fields on the current page
    const dateInputs = document.querySelectorAll('input[type="date"]');

    dateInputs.forEach(function (input) {

        // Keep existing min and max date restrictions
        const minDate = input.getAttribute("min");
        const maxDate = input.getAttribute("max");

        flatpickr(input, {

            // Actual value submitted to Spring Boot
            dateFormat: "Y-m-d",

            // Visible date format
            altInput: true,
            altFormat: "d/m/Y",

            // Preserve existing date restrictions
            minDate: minDate || null,
            maxDate: maxDate || null,

            // User selects date from the calendar
            allowInput: false

        });

    });

});