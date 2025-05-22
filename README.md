Tourist Guide Platform
Tourist Guide Platform is a comprehensive web application developed for the Web Programming course. This project provides an online tourism platform that allows users to explore and discover destinations worldwide. It consists of two main components: a Content Management System (CMS) for administrators and content editors, and a public platform for users to browse and engage with tourist articles.
Project Overview
The Tourist Guide Platform offers a robust solution for managing and consuming tourism-related content. The CMS enables authenticated users to manage destinations, articles, and user accounts with role-based access control. The public platform provides an intuitive interface for visitors to explore articles, discover destinations, and interact with content through comments.
Key Features
Content Management System (CMS)

Authentication & Authorization: Secure login with role-based access (Content Editor vs. Administrator).
Destination Management: Create, edit, and manage tourist destinations with detailed descriptions.
Article Management: Full CRUD (Create, Read, Update, Delete) operations for tourist articles, including activity tagging.
User Management: Administrator tools to manage system users and their permissions.
Activity-Based Organization: Categorize articles by tourist activities (e.g., skiing, hiking, swimming).

Public Reading Platform

Article Discovery: Browse latest articles, popular content, and destination-specific articles.
Interactive Features: Comment system to foster reader engagement.
Smart Navigation: Filter articles by destinations or tourist activities.
Analytics: Track article views and highlight trending content.
Responsive Design: Optimized for seamless viewing across desktops, tablets, and mobile devices.

Additional Features

Data Validation: Comprehensive input validation and error handling for robust functionality.
Pagination: Efficient content browsing with paginated results.
Security: Password hashing with SHA-256 and secure session management.
Relational Database: Persistent data storage with well-defined entity relationships.

Technologies Used

Frontend: HTML, CSS, JavaScript
Backend: JAX-RS (Java REST API framework)
Database: MySQL (relational database)
Security: SHA-256 password hashing
Authentication: Cookie-based or JWT session management

Getting Started
Prerequisites

Java 8 or higher (required for JAX-RS backend)
MySQL database server
Web server (e.g., Apache Tomcat or similar)
Modern web browser (e.g., Chrome, Firefox, Edge)

Installation

Clone the Repository
git clone https://github.com/VukadinovicLuka/TourGuide.git


Database Setup
# Create a MySQL database
CREATE DATABASE tourguide_db;

# Set up the database schema with required tables (User, Destination, Article, Activity, Comment)
# Refer to the schema.sql file in the repository for table definitions
mysql -u <username> -p tourguide_db < schema.sql

# Create an initial administrator user manually in the database
INSERT INTO User (username, password, role) VALUES ('admin', SHA2('admin_password', 256), 'ADMIN');


Backend SetupNavigate to the backend directory:
cd backend

Configure database connection settings in src/main/resources/config.properties:
db.url=jdbc:mysql://localhost:3306/tourguide_db
db.user=<your_mysql_username>
db.password=<your_mysql_password>

Build and deploy the backend:
mvn clean install
mvn jetty:run


Frontend SetupNavigate to the frontend directory:
cd frontend

Serve the HTML files through a web server (e.g., Apache, Nginx, or a simple Node.js server).Configure API endpoints in the frontend to match the backend deployment (e.g., update apiBaseUrl in js/config.js to http://localhost:8080/api).


Usage
For Administrators

Access the CMS via the login page at /admin/login.
Manage destinations, articles, and user accounts.
Monitor content performance and user engagement metrics.

For Content Editors

Log in to the CMS to create and manage tourist articles.
Organize content by associating articles with destinations and activities.
Edit existing content and manage the article lifecycle.

For Visitors

Browse articles by destination or activity on the public platform.
Read full articles and leave comments to engage with content.
Discover trending and popular content through the analytics-driven interface.

Documentation
Complete project documentation, including detailed technical requirements and entity specifications, is available in the docs/ directory of the repository or in the project documentation file provided with the course materials.
Contact
For questions, feedback, or support, please reach out to luka.zarkovo29@gmail.com.
