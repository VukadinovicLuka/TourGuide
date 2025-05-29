# Tourist Guide Platform

**Tourist Guide Platform** is a comprehensive web application developed for the Web Programming course. This project provides an online tourism platform that allows users to explore and discover destinations worldwide. It consists of two main components: a Content Management System (CMS) for administrators and content editors, and a public platform for users to browse and engage with tourist articles.

## Project Overview

The **Tourist Guide Platform** offers a robust solution for managing and consuming tourism-related content. The CMS enables authenticated users to manage destinations, articles, and user accounts with role-based access control. The public platform provides an intuitive interface for visitors to explore articles, discover destinations, and interact with content through comments.

### Key Features
**Content Management System (CMS)**

- Authentication & Authorization: Secure login with role-based access (Content Editor vs. Administrator).
- Destination Management: Create, edit, and manage tourist destinations with detailed descriptions.
- Article Management: Full CRUD (Create, Read, Update, Delete) operations for tourist articles, including activity tagging.
- User Management: Administrator tools to manage system users and their permissions.
- Activity-Based Organization: Categorize articles by tourist activities (e.g., skiing, hiking, swimming).

**Public Reading Platform**

- Article Discovery: Browse latest articles, popular content, and destination-specific articles.
- Interactive Features: Comment system to foster reader engagement.
- Smart Navigation: Filter articles by destinations or tourist activities.
- Analytics: Track article views and highlight trending content.
- Responsive Design: Optimized for seamless viewing across desktops, tablets, and mobile devices.

**Additional Features**

- Data Validation: Comprehensive input validation and error handling for robust functionality.
- Pagination: Efficient content browsing with paginated results.
- Security: Password hashing with SHA-256 and secure session management.
- Relational Database: Persistent data storage with well-defined entity relationships.

### Technologies Used

- **Frontend**: HTML, CSS, JavaScript
- **Backend**: JAX-RS (Java REST API framework)
- **Database**: MySQL (relational database)
- **Security**: SHA-256 password hashing
- **Authentication**: Cookie-based or JWT session management

## Getting Started

### Prerequisites

- Java 8 or higher (required for JAX-RS backend)
- MySQL database server
- Web server (e.g., Apache Tomcat or similar)
- Modern web browser (e.g., Chrome, Firefox, Edge)

### Installation

1. Clone the Repository
```bash
git clone https://github.com/VukadinovicLuka/TourGuide.git
cd TourGuide
```

2. Database Setup
   - Access MySQL command line:
  ```bash
     mysql -u root -p
  ```
   - Create the database:
  ```sql
     CREATE DATABASE vodic_schema;
     exit;
  ```
     
3. Configure Database Connection
   - Update src/main/resources/META-INF/persistence.xml
   - Replace YOUR_MYSQL_ROOT_PASSWORD with your actual MySQL password:
  ```xml
     <property name="javax.persistence.jdbc.password" value="YOUR_MYSQL_ROOT_PASSWORD" />
  ```

4. Build and Run the Application
  ```bash
  mvn clean compile
  mvn jetty:run
   ```

6. Access the Application:
   - Open the browser and navigate to:
  http://localhost:8080

## Usage

### For Administrators

Access the CMS via the login page at /admin/login.
Manage destinations, articles, and user accounts.
Monitor content performance and user engagement metrics.

### For Content Editors

Log in to the CMS to create and manage tourist articles.
Organize content by associating articles with destinations and activities.
Edit existing content and manage the article lifecycle.

### For Visitors

Browse articles by destination or activity on the public platform.
Read full articles and leave comments to engage with content.
Discover trending and popular content through the analytics-driven interface.

## Documentation
Complete project documentation, including detailed technical requirements and entity specifications, is available in the docs/ directory of the repository.

## Contact
For questions, feedback, or support, please reach out to luka.zarkovo29@gmail.com.
