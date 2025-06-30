
fetch('http://localhost:8080/api/personal-info')
    .then(response => response.json())
    .then(data => {
        document.getElementById("fullName").textContent = data.fullName;
        document.getElementById("title").textContent = data.title;
        document.getElementById("aboutMe").textContent = data.aboutMe;
        document.getElementById("email").textContent = data.email;
        document.getElementById("phone").textContent = data.phone;
        document.getElementById("location").textContent = data.location;
        document.getElementById("bio").textContent = data.bio;

        document.getElementById("resumeLink").href = data.resumeUrl;

        const socialLinks = document.getElementById("socialLinks");
        socialLinks.innerHTML = '';

        // Mapping keys to Font Awesome icon classes
        const iconMap = {
            linkedIn: "fab fa-linkedin",
            github: "fab fa-github",
            instagram: "fab fa-instagram",
            leetcode: "fa-solid fa-code", // LeetCode doesn't have an official FA icon
            gfg: "fa-solid fa-laptop-code", // GFG also doesn't have an official FA icon
            portfolioDriveLink: "fas fa-briefcase" // or any suitable icon
        };

        Object.entries(data.socialMediaLinks).forEach(([key, url]) => {
            if (url && iconMap[key]) {
                const a = document.createElement("a");
                a.href = url;
                a.target = "_blank";
                a.title = key.charAt(0).toUpperCase() + key.slice(1); // Optional tooltip

                const icon = document.createElement("i");
                icon.className = iconMap[key];

                a.appendChild(icon);
                socialLinks.appendChild(a);
            }
        });


        const skillsList = document.getElementById("techSkills");
        skillsList.innerHTML = '';
        data.skills?.forEach(skill => {
            const li = document.createElement("li");
            li.textContent = skill;
            skillsList.appendChild(li);
        });

        const projectList = document.getElementById("projectList");
        projectList.innerHTML = '';
        data.projects?.forEach(project => {
            const div = document.createElement("div");
            div.classList.add("project");

            const title = document.createElement("h3");
            title.textContent = project.title;
            div.appendChild(title);

            const desc = document.createElement("p");
            desc.textContent = project.description;
            div.appendChild(desc);

            const tech = document.createElement("p");
            tech.innerHTML = `<strong>Tech Stack:</strong> ${project.techStack}`;
            div.appendChild(tech);

            if (project.projectUrl) {
                const link = document.createElement("a");
                link.href = project.projectUrl;
                link.target = "_blank";
                link.textContent = "View Project";
                div.appendChild(link);
            }

            projectList.appendChild(div);
        });
    })
    .catch(error => {
        console.error('Error fetching portfolio data:', error);
    });

// Load Services from API
fetch('http://localhost:8080/api/services')
    .then(response => response.json())
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
        console.error('Error fetching services:', error);
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

