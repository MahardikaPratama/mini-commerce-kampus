<div align="center">
  <img height="150" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original-wordmark.svg"  />
</div>

###

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"  />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original.svg" height="40" alt="spring logo"  />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/postgresql/postgresql-original-wordmark.svg" height="40" alt="postgresql logo"  />
</div>

###

<div align="center">
  <!-- Badges for contributors, commit activity, and last commit -->
  <img src="https://img.shields.io/github/contributors/MahardikaPratama/mini-commerce-kampus?color=red" alt="Contributors" />
  <img src="https://img.shields.io/github/commit-activity/m/MahardikaPratama/mini-commerce-kampus?color=blue" alt="Commit Activity" />
  <img src="https://img.shields.io/github/last-commit/MahardikaPratama/mini-commerce-kampus?color=yellow" alt="Last Commit" />
  <br>
  
  <!-- Badges for repository size and license -->
  <img src="https://img.shields.io/github/repo-size/MahardikaPratama/mini-commerce-kampus?color=green" alt="Repository Size" />
  <br>
  
  <!-- Badges for programming languages -->
  <img src="https://img.shields.io/github/languages/top/MahardikaPratama/mini-commerce-kampus?color=purple" alt="Top Language" />
</div>

###

<h1>About Mini Commerce Kampus</h1>  
<p>Mini Commerce Kampus is a backend implementation of a simple e-commerce system built using a <strong>Microservices Architecture</strong>. The platform consists of two main services: <strong>catalog-service</strong> and <strong>order-service</strong>. Both services run independently, have their own separated databases, and communicate via HTTP to ensure data integrity and isolation.</p>  

<p>This project is built to fulfill specific acceptance criteria regarding microservice communication, database isolation, and transactional logic. Core features include:</p>  
<ul>
  <li><strong>2 Independent Services</strong>: `catalog-service` and `order-service` running simultaneously with separated databases.</li>
  <li><strong>CRUD Operations & Order Creation</strong>: Full functionality for managing products and creating orders.</li>
  <li><strong>Inter-service Communication</strong>: The `order-service` communicates with the `catalog-service` via HTTP (`RestClient`) to validate products and manage stock.</li>
  <li><strong>Stock Management</strong>: Stock decreases dynamically when an order is created and returns to its original amount if the order is cancelled.</li>
  <li><strong>Database Isolation</strong>: Strict architectural rules ensuring no direct query or access from one service to another service's database.</li>
  <li><strong>Validation & Proper Error Handling</strong>: Features a `GlobalExceptionHandler` returning clean JSON errors (400 Bad Request/404 Not Found) without exposing the server stack trace.</li>
  <li><strong>Unit Testing (JUnit 5 & Mockito)</strong>: Implemented unit tests for critical Service Layer business logic to ensure system reliability and correct stock calculations.</li>
  <li><strong>Complete Documentation</strong>: Includes a Postman Collection and PDM/DDL schemas to easily replicate the environment.</li>
</ul> 

<h2>Order Status Transition Flow</h2>  
<p>The state of an order is strictly managed through the following logic:</p>  
<ul>
  <li><strong>PENDING:</strong> The default status when an order is newly created.</li>
  <li><strong>PAID:</strong> Triggered via PATCH <code>/api/orders/{id}/pay</code>. Validation ensures this can only happen if the status is PENDING.</li>
  <li><strong>CANCELLED:</strong> Triggered via PATCH <code>/api/orders/{id}/cancel</code>. Returns stock to the catalog and is only valid if the status is PENDING.</li>
</ul>

<h1 align="center">🌟 Contributor 🌟</h1>
<div align="center">
  <table border="0">
    <tr>
      <td align="center">
        <a href="https://github.com/MahardikaPratama">
          <img src="https://avatars.githubusercontent.com/u/117805307?v=4" width="100" alt="MahardikaPratama" style="border-radius: 50%; border: 2px solid #ffd700;" />
        </a>
        <br>
        <a href="https://github.com/MahardikaPratama" style="color:#4caf50; font-weight: bold; text-decoration: none;">MahardikaPratama</a>
      </td>
    </tr>
  </table>
</div>

<h1 align="center">🚀 Technologies We Use 🚀</h1>
<div align="center">
  <p>Powered by reliable tools and frameworks to deliver the best backend performance!</p>
  <div style="display: flex; flex-wrap: wrap; justify-content: center; gap: 10px;">
    <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
    <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot" />
    <img src="https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
    <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white" alt="Hibernate" />
    <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" />
  </div>
</div>

<h1 style="text-align: center; color: #4CAF50;">Setup & Installation</h1>
<p style="font-size: 18px;">Follow the steps below to set up and run the application locally:</p>

<ol style="font-size: 16px; line-height: 1.6;">
  <li><strong>Clone the repository:</strong>
    <pre><code>git clone https://github.com/MahardikaPratama/mini-commerce-kampus.git</code></pre>
  </li>
  <li><strong>Configure Databases:</strong> Open `application.properties` (or `application.yml`) in both <code>catalog-service</code> and <code>order-service</code> and configure your PostgreSQL / MySQL connection details.</li>
</ol>

<h1 style="text-align: center; color: #2196F3;">Running the Services</h1>
<p style="font-size: 16px;">Since it's a microservice architecture, you need to run both services in separate terminals.</p>

<p style="font-size: 16px;"><strong>Run Catalog Service:</strong></p>
<pre><code>cd catalog-service
./mvnw spring-boot:run</code></pre>

<p style="font-size: 16px;"><strong>Run Order Service:</strong></p>
<pre><code>cd order-service
./mvnw spring-boot:run</code></pre>

<h1 style="text-align: center; color: #FF9800;">Attachments & Testing</h1>
<ul style="font-size: 16px; line-height: 1.6;">
  <li><strong>Postman Collection:</strong> You can find the Postman collection within the repository. Import it into Postman to easily test all the REST APIs (Create Order, Pay, Cancel, etc.).</li>
  <li><strong>PDM/DDL:</strong> The database schema design files are also provided for your reference.</li>
  <li><strong>Unit Tests:</strong> The project includes comprehensive unit tests for the core business logic (Service layer) using <strong>JUnit 5</strong> and <strong>Mockito</strong>.</li>
</ul>

<p style="font-size: 16px;"><strong>Running Unit Tests:</strong></p>
<pre><code># Run tests for Catalog Service
cd catalog-service
./mvnw test

# Run tests for Order Service
cd order-service
./mvnw test</code></pre>

<br>

<div align="center">
  <img src="https://www.polban.ac.id/wp-content/uploads/2021/11/MASTER-LOGO-POLBAN-SMALL-1.png" height="100" alt="Polban Logo" />
</div>
