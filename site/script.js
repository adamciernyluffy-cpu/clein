// Update this to your real GitHub repository before publishing.
const REPOSITORY_URL = "https://github.com/";
const RELEASES_URL = REPOSITORY_URL.endsWith("/") ? REPOSITORY_URL : REPOSITORY_URL + "/";
const releaseUrl = REPOSITORY_URL === "https://github.com/" ? REPOSITORY_URL : REPOSITORY_URL + "/releases/latest";
document.querySelectorAll("#githubLink,#downloadButton,#bottomDownload").forEach(link => {
  link.href = releaseUrl;
});
const menuButton = document.querySelector(".menu-toggle");
const navLinks = document.querySelector(".nav-links");
menuButton?.addEventListener("click", () => navLinks.classList.toggle("open"));
navLinks?.querySelectorAll("a").forEach(link => link.addEventListener("click", () => navLinks.classList.remove("open")));
const observer = new IntersectionObserver(entries => {
  entries.forEach(entry => {
    if (entry.isIntersecting) {
      entry.target.classList.add("visible");
      observer.unobserve(entry.target);
    }
  });
}, { threshold: 0.12 });
document.querySelectorAll(".reveal").forEach(el => observer.observe(el));
