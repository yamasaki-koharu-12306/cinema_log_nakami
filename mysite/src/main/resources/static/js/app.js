document.addEventListener("DOMContentLoaded", () => {
    const reducedMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;

    // Navigation
    const menuButton = document.querySelector(".menu-button");
    const navigation = document.querySelector(".global-navigation");
    const backdrop = document.querySelector(".navigation-backdrop");

    const setMenu = (open) => {
        document.body.classList.toggle("menu-open", open);
        navigation?.classList.toggle("is-open", open);
        backdrop?.classList.toggle("is-open", open);
        menuButton?.setAttribute("aria-expanded", String(open));
        menuButton?.setAttribute("aria-label", open ? "メニューを閉じる" : "メニューを開く");
        navigation?.setAttribute("aria-hidden", String(!open));
    };

    menuButton?.addEventListener("click", () => {
        setMenu(!navigation?.classList.contains("is-open"));
    });

    backdrop?.addEventListener("click", () => setMenu(false));
    navigation?.querySelectorAll("a").forEach((link) => {
        link.addEventListener("click", () => setMenu(false));
    });

    document.addEventListener("keydown", (event) => {
        if (event.key === "Escape") setMenu(false);
    });

    // Compact filter on mobile
    const filterButton = document.querySelector(".filter-toggle");
    const filter = document.querySelector(".movie-search");

    filterButton?.addEventListener("click", () => {
        const open = !filter?.classList.contains("is-open");
        filter?.classList.toggle("is-open", open);
        filterButton.setAttribute("aria-expanded", String(open));
    });

    // Scroll reveal
    const revealTargets = [
        ...document.querySelectorAll(
            ".page-title > *, .movie-search, .filter-toggle, .results-bar, " +
            ".detail-hero > *:not(.detail-ambient), .article-body > *, " +
            ".about-simple-profile__content > *, .about-simple-cta > *, .section-head"
        )
    ];

    revealTargets.forEach((element, index) => {
        element.classList.add("reveal-item");
        element.style.transitionDelay = `${Math.min(index % 4, 3) * 55}ms`;
    });

    const cards = [...document.querySelectorAll(".movie-card")];

    if (!reducedMotion && "IntersectionObserver" in window) {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach((entry) => {
                if (!entry.isIntersecting) return;
                entry.target.classList.add("is-visible");
                observer.unobserve(entry.target);
            });
        }, {
            threshold: 0.09,
            rootMargin: "0px 0px -4% 0px"
        });

        revealTargets.forEach((element) => observer.observe(element));
        cards.forEach((card) => observer.observe(card));
    } else {
        revealTargets.forEach((element) => element.classList.add("is-visible"));
        cards.forEach((card) => card.classList.add("is-visible"));
    }

    // Card perspective and poster spotlight: mouse/pen only
    if (!reducedMotion && window.matchMedia("(hover: hover) and (pointer: fine)").matches) {
        cards.forEach((card) => {
            const poster = card.querySelector(".poster");

            card.addEventListener("pointermove", (event) => {
                const rect = card.getBoundingClientRect();
                const x = (event.clientX - rect.left) / rect.width;
                const y = (event.clientY - rect.top) / rect.height;

                const rotateY = (x - 0.5) * 3.2;
                const rotateX = (0.5 - y) * 2.6;

                card.style.setProperty("--tilt-x", `${rotateX.toFixed(2)}deg`);
                card.style.setProperty("--tilt-y", `${rotateY.toFixed(2)}deg`);
                poster?.style.setProperty("--poster-x", `${(x * 100).toFixed(1)}%`);
                poster?.style.setProperty("--poster-y", `${(y * 100).toFixed(1)}%`);
            });

            card.addEventListener("pointerleave", () => {
                card.style.setProperty("--tilt-x", "0deg");
                card.style.setProperty("--tilt-y", "0deg");
                poster?.style.setProperty("--poster-x", "50%");
                poster?.style.setProperty("--poster-y", "50%");
            });
        });
    }

    // Subtle scroll inertia on cards, kept small for readability
    let lastY = window.scrollY;
    let velocity = 0;
    let raf = 0;

    const animateScrollMotion = () => {
        velocity *= 0.82;

        cards.forEach((card, index) => {
            const direction = index % 2 === 0 ? 1 : -1;
            const shift = Math.max(-4, Math.min(4, velocity * 0.055 * direction));
            card.style.setProperty("--card-shift", `${shift.toFixed(2)}px`);
        });

        if (Math.abs(velocity) > 0.08) {
            raf = requestAnimationFrame(animateScrollMotion);
        } else {
            cards.forEach((card) => card.style.setProperty("--card-shift", "0px"));
            raf = 0;
        }
    };

    if (!reducedMotion) {
        window.addEventListener("scroll", () => {
            const currentY = window.scrollY;
            velocity = Math.max(-38, Math.min(38, currentY - lastY));
            lastY = currentY;
            if (!raf) raf = requestAnimationFrame(animateScrollMotion);
        }, { passive: true });
    }

    // Archive reading progress
    const progress = document.querySelector(".archive-progress span");
    if (progress) {
        const updateProgress = () => {
            const max = document.documentElement.scrollHeight - window.innerHeight;
            const ratio = max > 0 ? window.scrollY / max : 0;
            progress.style.transform = `scaleX(${Math.max(0, Math.min(1, ratio))})`;
        };
        updateProgress();
        window.addEventListener("scroll", updateProgress, { passive: true });
        window.addEventListener("resize", updateProgress);
    }

    // Landing spotlight follows pointer
    const intro = document.querySelector(".cinema-intro");
    if (intro && !reducedMotion && window.matchMedia("(pointer: fine)").matches) {
        intro.addEventListener("pointermove", (event) => {
            const x = (event.clientX / window.innerWidth) * 100;
            const y = (event.clientY / window.innerHeight) * 100;
            intro.style.setProperty("--pointer-x", `${x.toFixed(1)}%`);
            intro.style.setProperty("--pointer-y", `${y.toFixed(1)}%`);
        });
    }

    // Enter from landing with keyboard
    const landingLink = document.querySelector(".cinema-title");
    document.addEventListener("keydown", (event) => {
        if (landingLink && (event.key === "Enter" || event.key === " ")) {
            if (document.activeElement === document.body || document.activeElement === landingLink) {
                event.preventDefault();
                landingLink.click();
            }
        }
    });

    // Cinematic page transition for same-origin page links
    if (!reducedMotion) {
        document.querySelectorAll('a[href]').forEach((link) => {
            link.addEventListener("click", (event) => {
                const href = link.getAttribute("href");
                if (
                    !href ||
                    href.startsWith("#") ||
                    link.target === "_blank" ||
                    link.hasAttribute("download") ||
                    event.metaKey ||
                    event.ctrlKey ||
                    event.shiftKey ||
                    event.altKey
                ) {
                    return;
                }

                const target = new URL(link.href, window.location.href);
                if (target.origin !== window.location.origin) return;

                event.preventDefault();
                setMenu(false);
                document.body.classList.add("is-leaving");
                window.setTimeout(() => {
                    window.location.href = target.href;
                }, 640);
            });
        });
    }

    // Restore page when using browser back-forward cache
    window.addEventListener("pageshow", () => {
        document.body.classList.remove("is-leaving");
        setMenu(false);
    });
});
