# Tourist Guide Platform

**Tourist Guide Platform** is a comprehensive web application developed as part of the "Web Programming" course. The project focuses on creating an online tourism platform that enables users to explore and discover destinations worldwide. The system consists of two main components: a Content Management System (CMS) for administrators and content editors, and a public platform for reading tourist articles.

## 📋 Project Overview

The **Tourist Guide Platform** provides a complete solution for managing and consuming tourist content. The CMS allows authenticated users to manage destinations, articles, and users with role-based access control, while the public platform offers visitors an intuitive interface to browse articles, discover destinations, and engage with content through comments.

## ✨ Key Features

### Content Management System (CMS):
- **Authentication & Authorization**: Secure login system with role-based access (Content Editor vs Administrator)
- **Destination Management**: Create, edit, and manage tourist destinations with descriptions
- **Article Management**: Full CRUD operations for tourist articles with activity tagging
- **User Management**: Administrator functionality to manage system users and their permissions
- **Activity-based Organization**: Categorize articles by tourist activities (skiing, hiking, swimming, etc.)

### Public Reading Platform:
- **Article Discovery**: Browse latest articles, most popular content, and destination-specific content
- **Interactive Features**: Comment system for reader engagement
- **Smart Navigation**: Filter articles by destinations and tourist activities
- **Analytics**: Track article views and display trending content
- **Responsive Design**: Optimized viewing experience across devices

### Additional Features:
- **Data Validation**: Comprehensive input validation and error handling
- **Pagination**: Efficient content browsing with paginated results
- **Security**: Password hashing and secure session management
- **Relational Database**: Persistent data storage with proper entity relationships

## 🛠️ Technologies Used

- **Frontend**: HTML, CSS, JavaScript
- **Backend**: JAX-RS (Java REST API framework)
- **Database**: MySQL (relational database)
- **Security**: SHA-256 password hashing
- **Authentication**: Cookie-based or JWT session management

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher (for JAX-RS backend)
- MySQL database server
- Web server (Apache Tomcat or similar)
- Modern web browser

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/VukadinovicLuka/TourGuide.git

2. Database Setup:

Create a MySQL database
Set up the database schema with required tables (User, Destination, Article, Activity, Comment)
Create initial administrator user manually in the database


3. Backend Setup:

Navigate to the backend directory:
bashcd backend

Configure database connection settings
Build and deploy the backend:
bashmvn clean install
mvn jetty:run



4. Frontend Setup:

Navigate to the frontend directory and serve the HTML files through a web server
Configure API endpoints to match your backend deployment



💡 Usage
For Administrators:

Access the CMS through the login page
Manage destinations, articles, and users
Monitor content performance and user engagement

For Content Editors:

Create and manage tourist articles
Organize content by destinations and activities
Edit existing content and manage article lifecycle

For Visitors:

Browse articles by destination or activity
Read full articles and engage through comments
Discover trending and popular content

📚 Documentation
The complete project documentation, including detailed technical requirements and entity specifications, is available in the project documentation file provided with the course materials.
📧 Contact
For any questions or feedback, please reach out to luka.zarkovo29@gmail.com.
