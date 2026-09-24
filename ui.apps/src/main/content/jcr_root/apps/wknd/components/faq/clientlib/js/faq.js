(function () {
    "use strict";

    function decorate(root) {
        var buttons = root.querySelectorAll(".cmp-faq__question");

        function closeAll() {
            buttons.forEach(function (b) {
                b.setAttribute("aria-expanded", "false");
                document.getElementById(b.getAttribute("aria-controls")).hidden = true;
            });
        }

        buttons.forEach(function (btn) {
            btn.addEventListener("click", function () {
                var wasOpen = btn.getAttribute("aria-expanded") === "true";
                closeAll();                              // one-open-at-a-time
                if (!wasOpen) {                          // toggle: open only if it was closed
                    btn.setAttribute("aria-expanded", "true");
                    document.getElementById(btn.getAttribute("aria-controls")).hidden = false;
                }
            });
        });
    }

    document.addEventListener("DOMContentLoaded", function () {
        document.querySelectorAll(".cmp-faq").forEach(decorate);
    });
})();
