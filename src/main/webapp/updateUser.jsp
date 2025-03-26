<%@ page language="java" contentType="text/html; charset=US-ASCII"
pageEncoding="US-ASCII"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update form</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: url('https://images.pexels.com/photos/2531709/pexels-photo-2531709.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2') no-repeat center center fixed;
            background-size: cover;
            min-height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }
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
        .form-container {
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
            margin-top: 120px; /* Add margin to create space below the navbar */
            margin-bottom: 50px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input, select, textarea, button {
            width: 100%;
            margin-bottom: 15px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background-color: #f46523;
            color: #fff;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">
                <img src="https://www.x-workz.in/Logo.png" alt="Logo">
            </a>
            <!-- Offcanvas Toggle Button -->
            <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <!-- Offcanvas Menu -->
            <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
                <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="offcanvasNavbarLabel">Menu</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <div class="offcanvas-body">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="index.jsp">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="signUp.jsp">SignUp</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="signIn.jsp">SignIn</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="allUsers">All Users</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
    <div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
        <div class="form-container">
            <form action="updateUser" method="POST">
            <input type="number" name="id" hidden value=${fetch.getId()}><br>
            <h1 class="text-center col-12">Update User</h1>
                <label for="name">First Name</label>
                <input type="text" name="name" id="name" value=${fetch.getName()}><br>

                <label for="email">Email</label>
                <input type="text" name="email" id="email" value=${fetch.getEmail()}><br>

                <label for="phNumber">Phone Number</label>
                <input type="tel" name="phNumber" id="phNumber" value=${fetch.getPhNumber()}><br>

                <label for="age">Age</label>
                <input type="number" name="age" id="age" value=${fetch.getAge()}><br>

                <label for="gender">Gender</label>
                <select name="gender" id="gender" value=${fetch.getGender()}>
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                    <option value="other">Other</option>
                </select><br>

                <label for="location">Location</label>
                <select name="location" id="location" value=${fetch.getLocation()}>
                    <option value="city">City</option>
                    <option value="rural">Rural</option>
                    <option value="suburban">Suburban</option>
                </select><br>

                <label for="pwd">Password</label>
                <input type="password" name="pwd" id="pwd" value=${fetch.getPwd()}><br>

                <label for="confirmPwd">Confirm Password</label>
                <input type="password" name="confirmPwd" id="confirmPwd" value=${fetch.getConfirmPwd()}><br>

                <input type="submit" value="Update"><br>
                <h2 style="color:green">${updateMsg}</h2>

                <a href="allUsers">Click here to view all users</a>
            </form>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


</body>
</html>
