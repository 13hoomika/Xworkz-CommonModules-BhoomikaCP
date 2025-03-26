<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All User Profiles</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        /* General Styles */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            min-height: 100vh;
        }

        h1 {
            color: #000000;
            margin: 10px 0;
            font-size: 2rem;
        }

        a {
            text-decoration: none;
            color: #007bff;
            font-size: 1rem;
            margin: 10px 0;
        }

        a:hover {
            text-decoration: underline;
        }

        /* Table Container */
        .table-container {
            width: 100%;
            max-width: 1200px;
            overflow-x: auto;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 10px;
            margin: 10px 0;
        }

        /* Table Styles */
        table {
            width: 100%;
            border-collapse: collapse;
            table-layout: auto; /* Allow columns to adjust based on content */
            min-width: 800px;
            border: 1px solid #ddd;
        }

        th, td {
            padding: 10px 12px;
            text-align: left;
            border: 1px solid #ddd;
            white-space: normal; /* Allow text to wrap */
            word-break: break-word; /* Break long words if necessary */
        }

        th {
            background-color: #f46523;
            color: white;
            font-weight: bold;
            text-transform: uppercase;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        /* Action Icons */
        .action-icons {
            display: flex;
            /* gap: 10px; */
            justify-content: center; /* Centers horizontally */
            align-items: center; /* Centers vertically */
        }

        .action-icons a {
            color: red;
            font-size: 1.2rem;
            transition: color 0.3s ease;
        }

       /* .action-icons a:hover {
            color: #fb8c00;
        }*/

        .action-icons a:hover {
            animation: shake 0.3s ease-in-out;
        }

        @keyframes shake {
            0% { transform: translateX(0); }
            25% { transform: translateX(-3px); }
            50% { transform: translateX(3px); }
            75% { transform: translateX(-3px); }
            100% { transform: translateX(0); }
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            h1 {
                font-size: 1.8rem;
            }

            .table-container {
                padding: 5px;
            }

            th, td {
                padding: 8px 10px;
            }

            table {
                min-width: 600px;
            }
        }
    </style>
</head>
<body>
    <h1>All Users</h1>
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Sl.No</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone Number</th>
                    <th>Age</th>
                    <th>Gender</th>
                    <th>DOB</th>
                    <th>Location</th>
                    <th>Action</th>
                    <th>Locked</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listOfUsers}" var="user" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>${user.phNumber}</td>
                        <td>${user.age}</td>
                        <td>${user.gender}</td>
                        <td>${user.doB}</td>
                        <td>${user.location}</td>
                        <td>
                            <div class="action-icons">
                                <!--<a href="fetchUser?id=${user.id}"><i class="fas fa-edit"></i></a>-->
                                <a href="delete/${user.id}"><i class="fas fa-trash-alt" ></i></a>
                            </div>
                        </td>
                        <td>
                            <div class="action-icons">
                                <c:choose>
                                    <c:when test="${user.accountLocked}">
                                        <i class="fas fa-lock" style="color: red;" title="Account Locked"></i>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="fas fa-lock-open" style="color: green;" title="Account Unlocked"></i>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <a href="./">Go to Home</a>
</body>
</html>