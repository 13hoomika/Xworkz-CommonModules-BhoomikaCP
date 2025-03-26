<%@ page language="java" contentType="text/html; charset=US-ASCII"
pageEncoding="US-ASCII"%>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Profile</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: url('https://w0.peakpx.com/wallpaper/243/587/HD-wallpaper-river-boat-night-dark-fog.jpg') no-repeat center center fixed;
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

        .navbar-nav .nav-link {
            color: white;
            padding: 8px 15px;
            border-radius: 5px;
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        .navbar-nav .nav-link:hover {
            background-color: #f46523;
            color: white;
        }
        .form-container {
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
            margin-top: 120px;
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
    <c:set></c:set>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-transparent fixed-top">
              <div class="container-fluid">
                  <a class="navbar-brand" href="#">
                    <img src="https://www.x-workz.in/Logo.png" alt="Logo">
                  </a>
                  <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                              <span class="navbar-toggler-icon"></span>
                          </button>
                          <div class="collapse navbar-collapse" id="navbarNav">
                              <ul class="navbar-nav ms-auto">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="welcome.jsp">DashBoard</a>
                        </li>

                    </ul>
                </div>
            </div>
        </div>
    </nav>
    <div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
        <div class="form-container">
            <c:if test="${not empty profile}">
                <form action="updateProfile" method="POST"  enctype="multipart/form-data">
                <h1 class="text-center col-12">Update Profile</h1>
                <h2 style="color:green">${updateProfileMsg}</h2>
                    <input type="number" name="id" hidden value="${profile.id}">
                    <input type="text" name="email" hidden value="${profile.email}">

                    <label for="name">Name</label>
                    <input type="text" name="name" id="name" value="${profile.name}" required><br>

                    <label for="phNumber">Phone Number</label>
                    <input type="tel" name="phNumber" id="phNumber" value="${profile.phNumber}" required><br>

                    <!--<label for="age">Age</label>
                    <input type="number" name="age" id="age" value="${profile.age}" required><br>

                    <label for="gender">Gender</label>
                    <select name="gender" id="gender" required>
                        <option value="male" ${profile.gender == 'male' ? 'selected' : ''}>Male</option>
                        <option value="female" ${profile.gender == 'female' ? 'selected' : ''}>Female</option>
                        <option value="other" ${profile.gender == 'other' ? 'selected' : ''}>Other</option>
                    </select><br>-->

                    <!-- Hidden fields to keep age and gender unchanged -->
                    <input type="hidden" name="age" value="${profile.age}">
                    <input type="hidden" name="gender" value="${profile.gender}">

                    <label for="location">Location</label>
                    <select name="location" id="location" class="form-select" required>
                        <option value="">Select Location</option>
                        <option value="Bangalore">Bangalore</option>
                        <option value="Mysore">Mysore</option>
                        <option value="Hubli">Hubli</option>
                        <option value="Belgaum">Belgaum</option>
                        <option value="Mangalore">Mangalore</option>
                        <option value="Dharwad">Dharwad</option>
                        <option value="Tumkur">Tumkur</option>
                        <option value="Gulbarga">Gulbarga</option>
                        <option value="Bijapur">Bijapur</option>
                        <option value="Shivamogga">Shivamogga</option>
                        <option value="Udupi">Udupi</option>
                        <option value="Davangere">Davangere</option>
                        <option value="Raichur">Raichur</option>
                    </select>

                    <label for="pwd">New Password</label>
                    <input type="password" name="pwd" id="pwd" required><br>

                    <label for="confirmPwd">Confirm Password</label>
                    <input type="password" name="confirmPwd" id="confirmPwd" required><br>

                    <label for="profileData">Upload Profile Image</label>
                    <input type="file" id="profileData" name="multipartFile" accept="image/*"><br>

                    <input type="submit" value="Update"><br>
                </form>
            </c:if>
            <c:if test="${not empty error}">
                    <p style="color:red;">${error}</p>
                </c:if>
            <!--<c:if test="${empty profile}">
                <h3 style="color: red;">User not found.</h3>
            </c:if>-->
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


</body>
</html>
