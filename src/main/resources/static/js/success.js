/*=========================================================
        WDC PMKSY 3.0
        SUCCESS PAGE
=========================================================*/

$(document).ready(function () {

    initializePage();

    bindEvents();

});


/*=========================================================
        INITIALIZE
=========================================================*/

function initializePage() {

    // First menu expanded
    $(".submenu").hide();

    $(".menu-toggle").first().addClass("active");

    $(".menu-toggle").first().next(".submenu").show();

}


/*=========================================================
        BIND EVENTS
=========================================================*/

function bindEvents() {

    /*------------------------------------
            Collapse Sidebar
    ------------------------------------*/

    $("#sidebarToggle").on("click", function () {

        if ($(window).width() <= 992) {

            $("#sidebar").toggleClass("show");

        } else {

            $("#sidebar").toggleClass("collapsed");

        }

    });


    /*------------------------------------
            Expand / Collapse Menu
    ------------------------------------*/

    $(document).on("click", ".menu-toggle", function () {

        let submenu = $(this).next(".submenu");

        if ($(this).hasClass("active")) {

            $(this).removeClass("active");

            submenu.slideUp(200);

        } else {

            $(".menu-toggle").removeClass("active");

            $(".submenu").slideUp(200);

            $(this).addClass("active");

            submenu.slideDown(200);

        }

    });


    /*------------------------------------
            Active Menu
    ------------------------------------*/

    $(document).on("click", ".submenu a", function () {

        $(".submenu a").removeClass("active");

        $(this).addClass("active");

    });


    /*------------------------------------
            Close sidebar on mobile
    ------------------------------------*/

    $(document).on("click", ".submenu a", function () {

        if ($(window).width() <= 992) {

            $("#sidebar").removeClass("show");

        }

    });


    /*------------------------------------
            Resize
    ------------------------------------*/

    $(window).resize(function () {

        if ($(window).width() > 992) {

            $("#sidebar").removeClass("show");

        }

    });

}


/*=========================================================
        LOAD FORM (AJAX)
=========================================================*/

$(document).on("click", ".submenu a", function (e) {

    let url = $(this).attr("href");

    // if external link skip ajax
    if (url.startsWith("http")) {
        return;
    }

    e.preventDefault();

    showLoader();

    $("#contentArea").load(url, function (response, status) {

        hideLoader();

        if (status === "error") {

            $("#contentArea").html(

                "<div class='text-center mt-5'>" +
                "<i class='fa fa-triangle-exclamation fa-3x text-danger'></i>" +
                "<h4 class='mt-3'>Unable to load page.</h4>" +
                "</div>"

            );

        }

    });

});


/*=========================================================
        LOADER
=========================================================*/

function showLoader() {

    $("#contentArea").html(

        "<div class='text-center mt-5'>" +

        "<div class='spinner-border text-primary'></div>" +

        "<h5 class='mt-3'>Loading...</h5>" +

        "</div>"

    );

}

function hideLoader() {

}


/*=========================================================
        ESC KEY CLOSE SIDEBAR
=========================================================*/

$(document).keyup(function (e) {

    if (e.key === "Escape") {

        $("#sidebar").removeClass("show");

    }

});


/*=========================================================
        CLICK OUTSIDE SIDEBAR
=========================================================*/

$(document).mouseup(function (e) {

    if ($(window).width() > 992)
        return;

    let sidebar = $("#sidebar");

    if (!sidebar.is(e.target) &&
        sidebar.has(e.target).length === 0) {

        sidebar.removeClass("show");

    }

});