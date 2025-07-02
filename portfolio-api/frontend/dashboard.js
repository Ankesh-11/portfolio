
const token = localStorage.getItem("token");
if (!token) window.location.href = "index.html";

let personalInfoId = null;

function logout() {
  localStorage.removeItem("token");
  window.location.href = "index.html";
}

function openEditModal() {
  document.getElementById("editModal").style.display = "block";
}

function closeEditModal() {
  document.getElementById("editModal").style.display = "none";
}

function openNewProjectModal(id = null) {
  document.getElementById("projectModal").style.display = "block";
  document.getElementById("editingProjectId").value = id || "";
  if (!id) {
    document.getElementById("projectModalTitle").textContent = "Add Project";
    ["projTitle", "projDesc", "projStack", "projUrl", "projGithub", "projImage"].forEach(id => document.getElementById(id).value = "");
  }
}

function closeProjectModal() {
  document.getElementById("projectModal").style.display = "none";
}

fetch("http://localhost:8080/api/portfolio/me", {
  headers: { Authorization: `Bearer ${token}` }
})
  .then(res => res.json())
  .then(data => {
    personalInfoId = data.id;
    console.log(data);
    document.getElementById("fullName").textContent = data.fullName;
    document.getElementById("title").textContent = data.title;
    document.getElementById("aboutMe").textContent = data.aboutMe;
    document.getElementById("email").textContent = data.email;
    document.getElementById("phone").textContent = data.phone;
    document.getElementById("location").textContent = data.location;
    document.getElementById("bio").textContent = data.bio;

    document.getElementById("editFullName").value = data.fullName;
    document.getElementById("editTitle").value = data.title;
    document.getElementById("editAboutMe").value = data.aboutMe;
    document.getElementById("editEmail").value = data.email;
    document.getElementById("editPhone").value = data.phone;
    document.getElementById("editLocation").value = data.location;
    document.getElementById("editBio").value = data.bio;

    const skillsUl = document.getElementById("techSkills");
    skillsUl.innerHTML = "";
    data.skills.forEach(skill => {
      const li = document.createElement("li");
      li.textContent = skill;
      skillsUl.appendChild(li);
    });
    document.getElementById("skillsInput").value = data.skills.join(", ");

    const projectList = document.getElementById("projectList");
    projectList.innerHTML = "";
    data.projects.forEach(project => {
      const div = document.createElement("div");
      div.className = "project";
      div.innerHTML = `
            <h3>${project.title}</h3>
            <p>${project.description}</p>
            <p><strong>Tech Stack:</strong> ${project.techStack}</p>
            <a href="${project.projectUrl}" target="_blank">View</a>
            <button onclick="openEditProject(${project.id}, '${project.title}', '${project.description}', '${project.techStack}', '${project.projectUrl}', '${project.githubRepoUrl}', '${project.imageUrl}')">Edit</button>
            <button onclick="deleteProject(${project.id})">Delete</button>
          `;
      projectList.appendChild(div);
    });
  });

function saveSection() {
  const body = {
    fullName: document.getElementById("editFullName").value,
    title: document.getElementById("editTitle").value,
    aboutMe: document.getElementById("editAboutMe").value,
    email: document.getElementById("editEmail").value,
    phone: document.getElementById("editPhone").value,
    location: document.getElementById("editLocation").value,
    bio: document.getElementById("editBio").value
  };

  fetch("http://localhost:8080/api/portfolio/create", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`
    },
    body: JSON.stringify(body)
  })
    .then(() => location.reload());
}

function editSkills() {
  document.getElementById("skillsInput").style.display = "block";
  document.querySelector("#skills button[onclick^='saveSkills']").style.display = "inline-block";
}

function saveSkills() {
  const skills = document.getElementById("skillsInput").value.split(",").map(s => s.trim());
  fetch("http://localhost:8080/api/portfolio/create", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`
    },
    body: JSON.stringify({ skills })
  })
    .then(() => location.reload());
}

function deleteProject(projectId) {
  fetch(`http://localhost:8080/api/projects/${projectId}`, {
    method: "DELETE",
    headers: { Authorization: `Bearer ${token}` }
  })
    .then(() => location.reload());
}

function openEditProject(id, title, desc, stack, url, github, image) {
  document.getElementById("projectModalTitle").textContent = "Edit Project";
  document.getElementById("editingProjectId").value = id;
  document.getElementById("projTitle").value = title;
  document.getElementById("projDesc").value = desc;
  document.getElementById("projStack").value = stack;
  document.getElementById("projUrl").value = url;
  document.getElementById("projGithub").value = github;
  document.getElementById("projImage").value = image;
  document.getElementById("projectModal").style.display = "block";
}

function submitProject() {
  const id = document.getElementById("editingProjectId").value;
  const body = {
    title: document.getElementById("projTitle").value,
    description: document.getElementById("projDesc").value,
    techStack: document.getElementById("projStack").value,
    projectUrl: document.getElementById("projUrl").value,
    githubRepoUrl: document.getElementById("projGithub").value,
    imageUrl: document.getElementById("projImage").value
  };

  const url = id
    ? `http://localhost:8080/api/projects/${id}`
    : `http://localhost:8080/api/projects/${personalInfoId}`;

  fetch(url, {
    method: id ? "PUT" : "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`
    },
    body: JSON.stringify(body)
  })
    .then(() => location.reload());
}

const socialLinks = document.getElementById("socialLinks");
socialLinks.innerHTML = '';

const iconMap = {
  linkedIn: "fab fa-linkedin",
  github: "fab fa-github",
  instagram: "fab fa-instagram",
  leetcode: "fas fa-code",
  gfg: "fas fa-laptop-code",
  portfolioDriveLink: "fas fa-briefcase"
};

const socialData = data.socialMediaLinks || {};

Object.entries(socialData).forEach(([key, url]) => {
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

if (socialLinks.children.length === 0) {
  socialLinks.innerHTML = "<p>No social media links available.</p>";
}

fetch('http://localhost:8080/api/services')
  .then(services => {
    const serviceList = document.getElementById("serviceList");
    serviceList.innerHTML = '';

    if (services.length === 0) {
      serviceList.innerHTML = "<p>No services available.</p>";
      return;
    }

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
