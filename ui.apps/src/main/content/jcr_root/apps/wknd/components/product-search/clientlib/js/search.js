(function () {
    "use strict";

    function escapeHtml(value) {
        // Build text via textContent so any HTML in data is neutralised (XSS-safe).
        var div = document.createElement("div");
        div.textContent = value == null ? "" : value;
        return div.innerHTML;
    }

    function renderResults(container, products) {
        if (!products.length) {                      // no-results state
            container.innerHTML = '<p class="cmp-product-search__msg">No products found.</p>';
            return;
        }
        container.innerHTML = products.map(function (p) {
            return '<div class="cmp-product-search__card">' +
                     '<strong>' + escapeHtml(p.name) + '</strong>' +
                     '<span>' + escapeHtml(p.category) + ' &middot; ₹' + escapeHtml(p.price) + '</span>' +
                   '</div>';
        }).join("");
    }

    function decorate(root) {
        var endpoint = root.getAttribute("data-endpoint");
        var input = root.querySelector(".cmp-product-search__input");
        var button = root.querySelector(".cmp-product-search__button");
        var results = root.querySelector(".cmp-product-search__results");

        function search() {
            var category = input.value.trim();       // empty -> search all
            var url = endpoint + (category ? "?category=" + encodeURIComponent(category) : "");

            results.textContent = "Loading…";     // loading state
            fetch(url)
                .then(function (res) {
                    if (!res.ok) { throw new Error("HTTP " + res.status); }
                    return res.json();
                })
                .then(function (products) { renderResults(results, products); })
                .catch(function () {                   // error state
                    results.innerHTML = '<p class="cmp-product-search__msg cmp-product-search__msg--error">' +
                        "Something went wrong. Please try again.</p>";
                });
        }

        button.addEventListener("click", search);
        input.addEventListener("keydown", function (e) {
            if (e.key === "Enter") { search(); }
        });
    }

    document.addEventListener("DOMContentLoaded", function () {
        document.querySelectorAll(".cmp-product-search").forEach(decorate);
    });
})();
