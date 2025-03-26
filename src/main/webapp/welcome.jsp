<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* Global Styles */
        body {
            font-family: 'Poppins', sans-serif;
            background: url('https://images.pexels.com/photos/3706/black-and-white-cartoon-donald-duck-spotlight.jpg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2') no-repeat center center fixed;
            background-size: cover;
            height: 100vh;
            margin: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        /* Navbar */
        .navbar {
            width: 100%;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1000;
        }

        .navbar-brand img {
            height: 50px;
        }

        .navbar-nav .nav-item {
            margin-left: 15px;
        }

        .navbar-nav .nav-link {
            color: white;
            padding: 8px 15px;
            border-radius: 5px;
            transition: 0.3s ease-in-out;
        }

        .navbar-nav .nav-link:hover {
            background-color: #f46523;
            color: white;
        }

        /* Profile Section */
        .profile-container {
            background: rgba(255, 255, 255, 0.95);
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
            text-align: center;
            max-width: 450px;
            width: 90%;
            margin-top: 100px;
        }

        .profile-img {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            //border: 3px solid #f46523;
            object-fit: cover;
            margin-bottom: 15px;
        }

        .profile-container h2 {
            color: #333;
            font-weight: 600;
        }

        .profile-container p {
            color: #666;
            margin-bottom: 20px;
        }

        .btn-custom {
            width: 100%;
            font-size: 16px;
            font-weight: 500;
            margin-top: 10px;
        }
    </style>
</head>
<body>

    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <!-- Brand Logo -->
            <a class="navbar-brand" href="#">
                <img src="https://www.x-workz.in/Logo.png" alt="Logo">
            </a>

            <!-- Navbar Toggler -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- Navbar Links -->
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link" href="index.jsp">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="getProfile">Update Profile</a></li>
                    <li class="nav-item"><a class="nav-link" href="deleteProfile.jsp">Delete Profile</a></li>
                    <li class="nav-item"><a class="nav-link btn btn-danger text-white px-3" href="logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Profile Section -->
    <div class="profile-container">
<!--  <img src="${pageContext.request.contextPath}/view?fileName=${loggedInUser.profileData}"
                          alt="Profile Image" class="profile-img">
-->
        <img src="${empty loggedInUser.profileData ? 'https://cdn-icons-png.flaticon.com/512/149/149071.png' : 'view?fileName='.concat(loggedInUser.profileData)}"
             alt="Profile Image" class="profile-img" width="150" height="150"/>

        <h2>Welcome, ${loggedInUser.name}!</h2>
        <p>You have successfully logged in.</p>

        <a href="resetPassword.jsp" class="btn btn-primary btn-custom">Reset Password</a>
        <a id="ViewProfileBtn" href="view?fileName=${loggedInUser.profileData}" class="btn btn-custom">View Profile Image</a>


    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            let profileImage = "${loggedInUser.profileData}";
            let ViewProfileBtn = document.getElementById("ViewProfileBtn");

            if (!profileImage) {
                ViewProfileBtn.classList.add("btn-secondary");
                ViewProfileBtn.classList.remove("btn-primary");
                ViewProfileBtn.setAttribute("disabled", "true");
                ViewProfileBtn.href = "#"; // Prevents clicking if no image
            } else {
                ViewProfileBtn.classList.add("btn-primary");
            }
        });
    </script>


</body>
</html>
