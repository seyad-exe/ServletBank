# Java Servlet Banking API

A robust, enterprise-grade banking API built entirely from scratch using raw Java Servlets. This project demonstrates a deep understanding of backend fundamentals, layered architecture, state management, and database connection pooling without relying on heavy frameworks like Spring Boot.

## 🚀 Features
* **Custom Security:** Password hashing via BCrypt and stateful authentication using Server-Side Sessions.
* **Role-Based Access Control (RBAC):** Servlet Filters dynamically guard endpoints based on 'USER' or 'ADMIN' roles.
* **ACID Transactions:** Manual JDBC transaction management (`commit`/`rollback`) ensuring zero data loss during account transfers.
* **Optimized Database:** Centralized connection pooling via HikariCP.
* **Automated Migrations:** Database schema version control powered by Liquibase.

## 🛠️ Technology Stack
* **Language:** Java 21
* **Server:** Apache Tomcat 10 (Jakarta EE 10)
* **Database:** MySQL 8.0
* **Build Tool:** Maven
* **Core Libraries:** Jakarta Servlet API, HikariCP, Liquibase, JBCrypt

---

## 🏗️ System Architecture

The application strictly follows a 3-Tier Layered Architecture to separate HTTP traffic, business rules, and database execution.

```mermaid
flowchart TD
    Client[Client / Postman] -->|HTTP Request| Tomcat[Apache Tomcat 10]
    
    subgraph Tomcat Server
        Filter[Security Filters] -->|Passes check| Controllers[Controllers / Servlets]
        Controllers -->|Data Transfer| Services[Business Service Layer]
        Services -->|SQL execution| Repositories[Repository / DAO Layer]
    end
    
    Repositories -->|Borrow Connection| Hikari[HikariCP Connection Pool]
    Hikari <-->|JDBC| DB[(MySQL Database)]
```

---

## 🔄 Sequence Diagram: Secure Transaction Flow

Here is how the system safely processes a debit/credit request, ensuring Atomicity.

```mermaid
sequenceDiagram
    actor User
    participant Filter as ApiAuthFilter
    participant Servlet as TransactionServlet
    participant Service as AccountService
    participant Repo as AccountRepository
    participant DB as MySQL

    User->>Filter: POST /api/transaction {amount, type}
    Filter->>Filter: Check HttpSession
    Filter-->>Servlet: Forward Request
    Servlet->>Service: processTransaction(user, account, type, amount)
    Service->>Repo: findByAccountNumber()
    Repo-->>Service: Return Account Model
    
    alt Insufficient Funds
        Service-->>Servlet: Error "Insufficient funds"
        Servlet-->>User: 400 Bad Request
    else Funds Available
        Service->>Repo: performTransaction()
        Repo->>DB: conn.setAutoCommit(false)
        Repo->>DB: UPDATE accounts SET balance...
        Repo->>DB: INSERT INTO transactions...
        Repo->>DB: conn.commit()
        Repo-->>Service: true (Success)
        Service-->>Servlet: "SUCCESS"
        Servlet-->>User: 200 OK
    end
```

---

## ⚙️ Local Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/servlet-banking-api.git
   ```
2. **Configure the Database:**
   * Create a MySQL database named `bank_db`.
   * Update the credentials in `src/main/resources/application.properties`.
3. **Run Migrations:**
   * Liquibase is configured to run automatically on server startup. It will create the necessary `users`, `accounts`, and `transactions` tables.
4. **Deploy:**
   * Run the project on an Apache Tomcat 10+ server using Java 21.

---
*Developed by Seyad Abdur Raheem*