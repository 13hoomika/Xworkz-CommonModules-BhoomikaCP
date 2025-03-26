<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SignIn</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <style>
        /* Body styling with background image */
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

        /* Navbar styling */
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

        /* Form container styling */
        .form-container {
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            margin-top: 120px;
            margin-bottom: 50px;
        }

        /* Label styling */
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        /* Input and button styling */
        input, button {
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

        /* Forgot password link styling */
        .forgot-password {
            text-align: center;
            margin-top: 10px;
        }

        /* CAPTCHA container styling */
        .captcha-container {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 15px;
        }
        .captcha {
            font-size: 20px;
            font-weight: bold;
            text-align: center;
            background-color: #f0f0f0;
            padding: 10px;
            border-radius: 5px;
            flex: 1;
            margin-right: 10px;
        }

        /* Refresh button styling */
        .captcha-refresh button {
            background: none;
            border: none;
            cursor: pointer;
            color: #f46523;
            font-size: 2rem; /* Increased font size for thicker icon */
            transition: color 0.3s ease; /* Smooth color transition on hover */
        }
        .captcha-refresh button:hover {
            color: #d35400; /* Darker shade on hover */
        }

        /* Error message styling */
        .error {
            color: red;
            font-size: 14px;
            margin-bottom: 10px;
            display: none;
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
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="index.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="signUp.jsp">SignUp</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Form Container -->
    <div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
        <div class="form-container">
            <form id="signInForm" action="signIn" method="POST">
                <h1 class="text-center">Sign In</h1>

                <!-- Display error message if any -->
                <h3 style="color:red">${error}</h3>

                <!-- Email input -->
                <label for="email">Email</label>
                <input type="text" name="email" id="email" required>
                <div id="emailError" class="error">Please enter a valid email address.</div>

                <!-- Password input -->
                <label for="pwd">Password</label>
                <input type="password" name="pwd" id="pwd" required>

                <!-- CAPTCHA Section -->
                <div class="captcha-container">
                    <div class="captcha">
                        <canvas id="captchaCanvas" width="150" height="50"></canvas>
                    </div>
                    <!-- Refresh button -->
                    <div class="captcha-refresh">
                        <button type="button" id="refreshCaptcha" title="Refresh CAPTCHA">
                            <i class="bi bi-arrow-repeat"></i> <!-- Bootstrap refresh icon -->
                        </button>
                    </div>
                </div>

                <!-- CAPTCHA input -->
                <label for="captchaInput">Enter CAPTCHA</label>
                <input type="text" name="captchaInput" id="captchaInput" required>
                <div id="captchaError" class="error">Invalid CAPTCHA. Please try again.</div>

                <!-- Submit button -->
                <input type="submit" id="submitBtn" value="Sign In" disabled>

                <!-- Forgot Password Link -->
                <div class="forgot-password">
                    <p class="text-center">Don't have an account? <a href="signUp.jsp">Sign Up</a></p>
                    <p class="text-center"><a href="resetPassword.jsp">Forgot Password?</a></p>
                </div>
            </form>
            <!-- Display success/error message -->
            <h3 style="color:red">${Message}</h3>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Function to generate a random CAPTCHA
        function generateCaptcha() {
            const characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            let captcha = "";
            for (let i = 0; i < 6; i++) {
                captcha += characters.charAt(Math.floor(Math.random() * characters.length));
            }
            return captcha;
        }

        // Function to draw CAPTCHA on canvas
        function drawCaptcha(captcha) {
            const canvas = document.getElementById("captchaCanvas");
            const ctx = canvas.getContext("2d");

            // Clear canvas
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            // Add background color
            ctx.fillStyle = "#f0f0f0";
            ctx.fillRect(0, 0, canvas.width, canvas.height);

            // Add text
            ctx.font = "30px Arial";
            ctx.fillStyle = "#000";
            ctx.textAlign = "center";
            ctx.textBaseline = "middle";
            ctx.fillText(captcha, canvas.width / 2, canvas.height / 2);

            // Add some noise (optional)
            for (let i = 0; i < 50; i++) {
                ctx.fillStyle = `rgba(${Math.random() * 255}, ${Math.random() * 255}, ${Math.random() * 255}, 0.5)`;
                ctx.fillRect(Math.random() * canvas.width, Math.random() * canvas.height, 2, 2);
            }
        }

        // Function to refresh CAPTCHA
        function refreshCaptcha() {
            const newCaptcha = generateCaptcha();
            drawCaptcha(newCaptcha);
            sessionStorage.setItem("captcha", newCaptcha); // Update CAPTCHA in sessionStorage
        }

        // Display CAPTCHA on page load
        document.addEventListener("DOMContentLoaded", function () {
            refreshCaptcha(); // Generate and display initial CAPTCHA
        });

        // Add event listener for the refresh button
        document.getElementById("refreshCaptcha").addEventListener("click", function () {
            refreshCaptcha();
        });

        // Function to validate email using regex
        function validateEmail(email) {
            const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Basic email regex
            return regex.test(email);
        }

        // CAPTCHA validation function
        function validateCaptcha() {
            const userCaptcha = document.getElementById("captchaInput").value;
            const storedCaptcha = sessionStorage.getItem("captcha");
            const captchaError = document.getElementById("captchaError");

            if (userCaptcha !== storedCaptcha) {
                captchaError.style.display = "block"; // Show error message
                return false; // Prevent form submission
            } else {
                captchaError.style.display = "none"; // Hide error message
                return true; // Allow form submission
            }
        }

        // Function to check if all conditions are met
        function validateForm() {
            const email = document.getElementById("email").value;
            const password = document.getElementById("pwd").value;
            const captchaInput = document.getElementById("captchaInput").value;
            const submitBtn = document.getElementById("submitBtn");
            const emailError = document.getElementById("emailError");

            // Validate email
            if (!validateEmail(email)) {
                emailError.style.display = "block"; // Show email error message
                submitBtn.disabled = true; // Disable submit button
                return;
            } else {
                emailError.style.display = "none"; // Hide email error message
            }

            // Check if all fields are filled and CAPTCHA is valid
            if (email && password && captchaInput && validateCaptcha()) {
                submitBtn.disabled = false; // Enable submit button
            } else {
                submitBtn.disabled = true; // Disable submit button
            }
        }

        // Add event listeners to input fields
        document.getElementById("email").addEventListener("input", validateForm);
        document.getElementById("pwd").addEventListener("input", validateForm);
        document.getElementById("captchaInput").addEventListener("input", validateForm);

        // Handle form submission
        document.getElementById("signInForm").addEventListener("submit", function (event) {
            if (!validateCaptcha()) {
                event.preventDefault(); // Prevent form submission if CAPTCHA is invalid
            }
            // If CAPTCHA is valid, the form will submit normally
        });
    </script>
</body>
</html>