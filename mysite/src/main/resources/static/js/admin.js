document.addEventListener("DOMContentLoaded", () => {
    const menuButton = document.querySelector(".admin-mobile-menu");
    const backdrop = document.querySelector(".admin-mobile-backdrop");

    const closeMenu = () => document.body.classList.remove("admin-menu-open");

    menuButton?.addEventListener("click", () => {
        document.body.classList.toggle("admin-menu-open");
    });

    backdrop?.addEventListener("click", closeMenu);
    document.querySelectorAll(".admin-sidebar a").forEach((link) => {
        link.addEventListener("click", closeMenu);
    });

    document.addEventListener("keydown", (event) => {
        if (event.key === "Escape") closeMenu();
    });

    const revealItems = document.querySelectorAll(
        ".admin-heading, .admin-stat-card, .admin-section, .admin-toolbar, " +
        ".admin-table-wrap, .admin-form-card, .admin-publish-card, .admin-help-card"
    );

    revealItems.forEach((item, index) => {
        item.classList.add("admin-reveal");
        item.style.transitionDelay = `${Math.min(index, 5) * 55}ms`;
    });

    requestAnimationFrame(() => {
        revealItems.forEach((item) => item.classList.add("is-visible"));
    });

    const search = document.querySelector("[data-admin-search]");
    const rows = [...document.querySelectorAll("[data-admin-row]")];
    const emptySearch = document.querySelector("[data-admin-search-empty]");

    search?.addEventListener("input", () => {
        const query = search.value.trim().toLocaleLowerCase("ja");
        let visible = 0;

        rows.forEach((row) => {
            const matches = row.textContent.toLocaleLowerCase("ja").includes(query);
            row.hidden = !matches;
            if (matches) visible++;
        });

        if (emptySearch) {
            emptySearch.hidden = visible !== 0;
        }
    });

    const slugInput = document.querySelector("[data-slug-input]");
    const titleInput = document.querySelector("[data-title-input]");
    let slugTouched = Boolean(slugInput?.value);

    slugInput?.addEventListener("input", () => {
        slugTouched = slugInput.value.trim().length > 0;
    });

    titleInput?.addEventListener("input", () => {
        if (!slugInput || slugTouched) return;
        slugInput.placeholder = titleInput.value.trim()
            ? "保存時にタイトルから自動生成されます"
            : "空欄なら自動生成";
    });

    const bodyTextarea = document.querySelector("[data-review-body]");
    const count = document.querySelector("[data-review-count]");

    const updateCount = () => {
        if (bodyTextarea && count) {
            count.textContent = `${bodyTextarea.value.length.toLocaleString()}文字`;
        }
    };

    bodyTextarea?.addEventListener("input", updateCount);
    updateCount();
});
