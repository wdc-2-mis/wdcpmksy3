(function () {

    "use strict";


    let activeRequests = 0;

    let loadingStartTime = 0;

    const minimumDisplayTime = 500;

    window.showWaterLoader = function (message) {

        const overlay =
            document.getElementById(
                "waterLoadingOverlay"
            );

        const messageElement =
            document.getElementById(
                "waterLoadingMessage"
            );


        if (!overlay) {

            console.warn(
                "Water loading overlay not found."
            );

            return;
        }


        if (messageElement) {

            messageElement.textContent =
                message ||
                "Your request is being processed.";

        }


        /*
         * Start timer only when first request starts.
         */
        if (activeRequests === 0) {

            loadingStartTime =
                Date.now();

        }


        activeRequests++;


        overlay.classList.add("show");

        document.body.classList.add(
            "water-loading-active"
        );

    };

    window.hideWaterLoader = function () {

        const overlay =
            document.getElementById(
                "waterLoadingOverlay"
            );


        if (!overlay) {
            return;
        }


        /*
         * Don't allow the counter to become negative.
         */
        if (activeRequests > 0) {

            activeRequests--;

        }


        /*
         * If another request is still running,
         * keep loader visible.
         */
        if (activeRequests > 0) {

            return;

        }


        const elapsed =
            Date.now() -
            loadingStartTime;


        const remaining =
            Math.max(
                0,
                minimumDisplayTime - elapsed
            );


        setTimeout(function () {

            /*
             * Another request may have started
             * while waiting.
             */
            if (activeRequests > 0) {

                return;

            }


            overlay.classList.remove(
                "show"
            );

            document.body.classList.remove(
                "water-loading-active"
            );

        }, remaining);

    };


    window.setWaterLoaderMessage =
        function (message) {

            const element =
                document.getElementById(
                    "waterLoadingMessage"
                );


            if (element) {

                element.textContent =
                    message;

            }

        };


    const originalFetch =
        window.fetch;


    window.fetch = function () {

        const options =
            arguments[1] || {};


        /*
         * Some requests can explicitly skip
         * the loader.
         */
        const skipLoader =
            options.skipWaterLoader === true;


        if (!skipLoader) {

            showWaterLoader(
                "Your request is being processed..."
            );

        }


        return originalFetch
            .apply(this, arguments)

            .then(function (response) {

                if (!skipLoader) {

                    hideWaterLoader();

                }

                return response;

            })

            .catch(function (error) {

                if (!skipLoader) {

                    hideWaterLoader();

                }

                throw error;

            });

    };


	document.addEventListener(
	    "click",
	    function (event) {

	        const link =
	            event.target.closest("a");


	        if (!link) {
	            return;
	        }


	        /*
	         * Ignore modified clicks.
	         */
	        if (
	            event.ctrlKey ||
	            event.shiftKey ||
	            event.altKey ||
	            event.metaKey ||
	            event.button !== 0
	        ) {

	            return;

	        }


	        const href =
	            link.getAttribute("href");


	        if (!href ||
	            href === "#" ||
	            href.startsWith("#") ||
	            href.startsWith("javascript:") ||
	            link.hasAttribute("download")) {

	            return;

	        }


	        /*
	         * Don't show loader for external websites.
	         */
	        if (
	            link.origin &&
	            link.origin !== window.location.origin
	        ) {

	            return;

	        }


	        showWaterLoader(
	            "Loading..."
	        );

	    }
	);
})();

/* =========================================================
   GLOBAL CONFIRMATION SYSTEM
   ========================================================= */

(function () {

    let submitCallback = null;
    let deleteCallback = null;
    let completeCallback = null;


    /* =====================================================
       SUBMIT ALERT
       ===================================================== */

    window.submitAlert = function (message, callback) {

        const modalElement =
            document.getElementById("globalSubmitModal");

        const messageElement =
            document.getElementById("globalSubmitMessage");

        if (!modalElement) {
            console.error("Global Submit Modal not found.");
            return;
        }

        messageElement.textContent =
            message || "Are you sure you want to submit this form?";

        submitCallback = callback || null;

        const modal =
            bootstrap.Modal.getOrCreateInstance(modalElement);

        modal.show();
    };


    /* =====================================================
       DELETE ALERT
       ===================================================== */

    window.deleteAlert = function (message, callback) {

        const modalElement =
            document.getElementById("globalDeleteModal");

        const messageElement =
            document.getElementById("globalDeleteMessage");

        if (!modalElement) {
            console.error("Global Delete Modal not found.");
            return;
        }

        messageElement.textContent =
            message || "Are you sure you want to delete this record?";

        deleteCallback = callback || null;

        const modal =
            bootstrap.Modal.getOrCreateInstance(modalElement);

        modal.show();
    };


  

    window.completeAlert = function (message, callback) {

        const modalElement =
            document.getElementById("globalCompleteModal");

        const messageElement =
            document.getElementById("globalCompleteMessage");

        if (!modalElement) {
            console.error("Global Complete Modal not found.");
            return;
        }

        messageElement.textContent =
            message || "Are you sure you want to complete this form?";

        completeCallback = callback || null;

        const modal =
            bootstrap.Modal.getOrCreateInstance(modalElement);

        modal.show();
    };


    /* =====================================================
       SUBMIT CONFIRM BUTTON
       ===================================================== */

    document.addEventListener("click", function (event) {

        if (event.target.closest("#globalSubmitConfirmBtn")) {

            const modalElement =
                document.getElementById("globalSubmitModal");

            const modal =
                bootstrap.Modal.getOrCreateInstance(modalElement);

            modal.hide();

            if (typeof submitCallback === "function") {

                const callback = submitCallback;

                submitCallback = null;

                callback();
            }
        }

    });


    /* =====================================================
       DELETE CONFIRM BUTTON
       ===================================================== */

    document.addEventListener("click", function (event) {

        if (event.target.closest("#globalDeleteConfirmBtn")) {

            const modalElement =
                document.getElementById("globalDeleteModal");

            const modal =
                bootstrap.Modal.getOrCreateInstance(modalElement);

            modal.hide();

            if (typeof deleteCallback === "function") {

                const callback = deleteCallback;

                deleteCallback = null;

                callback();
            }
        }
 
    });
 



    document.addEventListener("click", function (event) {

        if (event.target.closest("#globalCompleteConfirmBtn")) {

            const modalElement =
                document.getElementById("globalCompleteModal");

            const modal =
                bootstrap.Modal.getOrCreateInstance(modalElement);

            modal.hide();

            if (typeof completeCallback === "function") {

                const callback = completeCallback;

                completeCallback = null;

                callback();
            }
        }

    });


})();