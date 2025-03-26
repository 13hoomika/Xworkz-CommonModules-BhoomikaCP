<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <title>SignUp</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: url('https://w0.peakpx.com/wallpaper/1002/401/HD-wallpaper-lamp-dark-electricity-lighting-light.jpg') no-repeat center center fixed;
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

        .navbar-nav .nav-item {
            margin-left: 15px;
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
            transition: background-color 0.3s ease; /* Smooth transition */
        }
        input[type="submit"]:disabled {
            background-color: #cccccc; /* Gray color when disabled */
            cursor: not-allowed; /* Change cursor to indicate disabled state */
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
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav ms-auto">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="index.jsp">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="signIn.jsp">SignIn</a>
                        </li>

                    </ul>
                </div>
            </div>
        </div>
    </nav>

    <!-- Form Container -->
        <div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
            <div class="form-container">
                <form action="signup" method="POST">
                    <h1 class="text-center col-12">Sign Up</h1>

                    <c:if test="${not empty error}">
                        <p style="color: red;">${error}</p>
                    </c:if>

                    <label for="name">First Name</label>
                    <input type="text" name="name" id="name" onchange="checkName()" required><br>
                    <span id="nameError" style="color: red"></span>

                    <label for="email">Email</label>
                    <input type="text" name="email" id="email" onchange="checkEmail()" required><br>
                    <span id="emailError" style="color: red"></span>

                    <label for="phNumber">Phone Number</label>
                    <input type="tel" name="phNumber" id="phNumber" onchange="checkPhNo()" required><br>
                    <span id="phNoError" style="color: red"></span>

                    <label for="doB">Date of Birth</label>
                    <input type="date" name="doB" id="doB" required onchange="calculateAge()"><br>

                    <label for="age">Age</label>
                    <input type="number" name="age" id="age" required readonly><br>

                    <label for="gender">Gender</label>
                    <select name="gender" id="gender" required>
                        <option value="">Select Gender</option>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                        <option value="other">Other</option>
                    </select><br>

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

                    <input type="submit" id="submitBtn" value="Submit" disabled>
                </form>
            </div>
        </div>

   <script>
           // Function to calculate age based on date of birth
           function calculateAge() {
               const dobInput = document.getElementById('doB').value;
               const ageOutput = document.getElementById('age');
               const dob = new Date(dobInput);
               if (!dobInput) {
                   alert('Please enter a valid date of birth.');
                   return;
               }
               const today = new Date();
               let age = today.getFullYear() - dob.getFullYear();
               const monthDifference = today.getMonth() - dob.getMonth();
               // Adjust age if the birthday has not occurred yet this year
               if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < dob.getDate())) {
                   age--;
               }
               ageOutput.value = age + 1;
               validateForm(); // Validate form after calculating age
           }

           // Function to validate name
           function checkName() {
               var checkValue = document.getElementById('name').value;
               console.log(checkValue);
               if (checkValue !== "") {
                   var xhttp = new XMLHttpRequest();
                   xhttp.open("GET", "http://localhost:8080/Xworkz_CommonModule_BhoomikaCP/checkValue/" + checkValue);
                   xhttp.send();
                   xhttp.onload = function () {
                       console.log(this.responseText);
                       document.getElementById("nameError").innerHTML = this.responseText;
                       validateForm(); // Call validateForm after AJAX response
                   };
               } else {
                   document.getElementById("nameError").innerHTML = ""; // Clear error if field is empty
                   validateForm(); // Call validateForm to recheck form state
               }
           }

           // Function to validate email
           function checkEmail() {
               var checkEmailValue = document.getElementById('email').value.trim(); // Remove spaces
               console.log("Checking email:", checkEmailValue); // Debugging

               if (checkEmailValue) {
                   var encodedEmail = encodeURIComponent(checkEmailValue); // Encode special characters
                   var xhttp = new XMLHttpRequest();
                   xhttp.open("GET", "http://localhost:8080/Xworkz_CommonModule_BhoomikaCP/checkEmailValue/" + encodedEmail, true);
                   xhttp.send();
                   xhttp.onload = function () {
                       console.log("Response:", this.responseText);
                       document.getElementById("emailError").innerHTML = this.responseText;
                       validateForm(); // Call validateForm after AJAX response
                   };
               } else {
                   document.getElementById("emailError").innerHTML = ""; // Clear error if field is empty
                   validateForm(); // Call validateForm to recheck form state
               }
           }

           // Function to validate phone number
           function checkPhNo() {
               var checkPhValue = document.getElementById("phNumber").value;
               console.log(checkPhValue);
               if (checkPhValue != "") {
                   var xhttp = new XMLHttpRequest();
                   xhttp.open("GET", "http://localhost:8080/Xworkz_CommonModule_BhoomikaCP/checkPhValue/" + checkPhValue);
                   xhttp.send();
                   xhttp.onload = function () {
                       console.log(this.responseText);
                       document.getElementById("phNoError").innerHTML = this.responseText;
                       validateForm(); // Call validateForm after AJAX response
                   };
               } else {
                   document.getElementById("phNoError").innerHTML = ""; // Clear error if field is empty
                   validateForm(); // Call validateForm to recheck form state
               }
           }

           // Function to validate the entire form
           function validateForm() {
               const name = document.getElementById('name').value;
               const email = document.getElementById('email').value;
               const phNumber = document.getElementById('phNumber').value;
               const doB = document.getElementById('doB').value;
               const age = document.getElementById('age').value;
               const gender = document.getElementById('gender').value;
               const location = document.getElementById('location').value;
               const submitBtn = document.getElementById('submitBtn');

               // Check if all fields are filled and there are no errors
               if (name && email && phNumber && doB && age && gender && location &&
                   document.getElementById('nameError').innerHTML === "" &&
                   document.getElementById('emailError').innerHTML === "" &&
                   document.getElementById('phNoError').innerHTML === "") {
                   submitBtn.disabled = false; // Enable submit button
               } else {
                   submitBtn.disabled = true; // Disable submit button
               }
           }

           // Add event listeners to input fields
           document.getElementById('name').addEventListener('input', validateForm);
           document.getElementById('email').addEventListener('input', validateForm);
           document.getElementById('phNumber').addEventListener('input', validateForm);
           document.getElementById('doB').addEventListener('change', validateForm);
           document.getElementById('gender').addEventListener('change', validateForm);
           document.getElementById('location').addEventListener('change', validateForm);
       </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>