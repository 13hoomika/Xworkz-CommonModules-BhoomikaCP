<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>HOME PAGE</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background: url('https://cdn.pixabay.com/photo/2024/12/09/16/22/boat-9255590_1280.jpg') no-repeat center center fixed;
            background-size: cover;
        }
        .navbar-brand img {
            height: 50px;
        }
        /* Navbar Link Spacing */
        .navbar-nav .nav-item {
            margin-left: 15px; /* Add space between nav items */
        }

        .navbar-nav .nav-link {
            color: white; /* Default link color */
            padding: 8px 15px; /* Add padding for better hover effect */
            border-radius: 5px; /* Rounded corners for the hover effect */
            transition: background-color 0.3s ease, color 0.3s ease; /* Smooth transition */
        }

        .navbar-nav .nav-link:hover {
            background-color: #f46523; /* Highlight color on hover */
            color: white; /* Text color on hover */
        }
</style>
</head>
<body>
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
                              <a class="nav-link active" aria-current="page" href="#">Home</a>
                          </li>
                          <li class="nav-item">
                              <a class="nav-link" href="./signUp.jsp">SignUp</a>
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
