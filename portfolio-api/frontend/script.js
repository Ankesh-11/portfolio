
const API_BASE_URL = "http://localhost:8080/api";

// Load Portfolio
fetch(`${API_BASE_URL}/portfolio/me`)
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to load portfolio");
        }
        return response.json();
    })
    .then(data => {
        document.getElementById("fullName").textContent = data.fullName || '';
        document.getElementById("title").textContent = data.title || '';
        document.getElementById("aboutMe").textContent = data.aboutMe || '';
        document.getElementById("experience").textContent = data.totalExperience || '';
        document.getElementById("email").textContent = data.email || '';
        document.getElementById("phone").textContent = data.phone || '';
        document.getElementById("location").textContent = data.location || '';
        document.getElementById("bio").textContent = data.bio || '';

        // Set dynamic resume link
        const resumeLink = document.getElementById("resumeLink");
        if (data.resumeUrl) {
            console.log(data.resumeUrl)
            resumeLink.href = data.resumeUrl;
            resumeLink.textContent = "Download Resume";
        } else {
            resumeLink.href = "#";
            resumeLink.textContent = "Resume Not Available";
        }

        // Set dynamic profile image
        // const profileImg = document.getElementById("profileImage");
        // if (data.profileImageUrl) {
        //     console.log(data.profileImageUrl)
        //     profileImg.src = ;
        //     profileImg.alt = "Profile Image";
        // } else {
        //     profileImg.src = "default-placeholder.png"; // fallback image
        //     profileImg.alt = "No image available";
        // }

        // Social Media Links
        const socialLinks = document.getElementById("socialLinks");
        socialLinks.innerHTML = "";

        const iconMap = {
            linkedIn: "fab fa-linkedin",
            github: "fab fa-github",
            instagram: "fab fa-instagram",
            leetcode: "fa-solid fa-code",
            gfg: "fa-solid fa-laptop-code",
            portfolioDriveLink: "fas fa-briefcase"
        };

        Object.entries(data.socialMediaLinks || {}).forEach(([key, url]) => {
            if (url && iconMap[key]) {
                const a = document.createElement("a");
                a.href = url;
                a.target = "_blank";
                a.title = key.charAt(0).toUpperCase() + key.slice(1);

                const icon = document.createElement("i");
                icon.className = iconMap[key];

                a.appendChild(icon);
                socialLinks.appendChild(a);
            }
        });

        // Skills
        const skillsList = document.getElementById("techSkills");
        skillsList.innerHTML = "";
        (data.skills || []).forEach(skill => {
            const li = document.createElement("li");
            li.textContent = skill;
            skillsList.appendChild(li);
        });

        // Projects
        const projectList = document.getElementById("projectList");
        projectList.innerHTML = "";
        (data.projects || []).forEach(project => {
            const div = document.createElement("div");
            div.classList.add("project");

            const title = document.createElement("h3");
            title.textContent = project.title;
            div.appendChild(title);

            const desc = document.createElement("p");
            desc.textContent = project.description;
            div.appendChild(desc);

            const tech = document.createElement("p");
            tech.innerHTML = `<strong>Tech Stack: </strong> ${project.techStack}`;
            div.appendChild(tech);

            if (project.githubRepoUrl) {
                const githubLink = document.createElement("a");
                githubLink.href = project.githubRepoUrl;
                githubLink.target = "_blank";
                githubLink.textContent = "GitHub Repo";
                githubLink.style.marginRight = "10px";
                div.appendChild(githubLink);
            }

            if (project.projectUrl) {
                const liveLink = document.createElement("a");
                liveLink.href = project.projectUrl;
                liveLink.target = "_blank";
                liveLink.textContent = "Live Project";
                div.appendChild(liveLink);
            }

            projectList.appendChild(div);
        });
    })
    .catch(error => {
        console.error("❌ Error fetching portfolio data:", error);
        document.getElementById("fullName").textContent = "Portfolio not available";
    });

// Load Services
fetch(`${API_BASE_URL}/services`)
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to load services");
        }
        return response.json();
    })
    .then(services => {
        const serviceList = document.getElementById("serviceList");
        serviceList.innerHTML = '';
        services.forEach(service => {
            const div = document.createElement("div");
            div.classList.add("service");

            const title = document.createElement("h4");
            title.textContent = service.title;
            div.appendChild(title);

            const desc = document.createElement("p");
            desc.textContent = service.description;
            div.appendChild(desc);

            serviceList.appendChild(div);
        });
    })
    .catch(error => {
        console.error("❌ Error fetching services:", error);
    });


// Contact form submission handler
document.addEventListener("DOMContentLoaded", () => {
    const contactForm = document.getElementById("contactForm");
    contactForm?.addEventListener("submit", (e) => {
        e.preventDefault();
        const formData = new FormData(contactForm);
        const data = Object.fromEntries(formData);

        fetch('http://localhost:8080/api/contact', {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) alert("Message sent successfully!");
                else alert("Failed to send message.");
                contactForm.reset();
            })
            .catch(() => alert("Error sending message."));
    });
});

const modal = document.getElementById("adminLoginModal");
const openModalBtn = document.getElementById("adminLoginBtn");
const closeModalBtn = document.getElementById("closeModal");

openModalBtn.onclick = function () {
    modal.style.display = "block";
}

closeModalBtn.onclick = function () {
    modal.style.display = "none";
}

window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// === Toast Notification ===
function showToast(message, isSuccess = true) {
    const toast = document.createElement('div');
    toast.className = `toast ${isSuccess ? 'toast-success' : 'toast-error'}`;
    toast.textContent = message;
    document.body.appendChild(toast);
    setTimeout(() => toast.classList.add('show'), 100);
    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

// === Admin Login Form Submission ===
document.getElementById("adminLoginForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const formData = new FormData(this);
    const loginData = {
        username: formData.get("username"),
        password: formData.get("password")
    };

    fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(loginData)
    })
        .then(response => {
            if (!response.ok) throw new Error("Login failed");
            return response.json();
        })
        .then(data => {
            localStorage.setItem("token", data.token);
            showToast("Login Successful!");
            setTimeout(() => {
                window.location.href = "dashboard.html";
            }, 1000);
        })
        .catch(error => {
            console.error("Login Error:", error);
            showToast("Invalid Credentials", false);
        });
});

