# Lab 2: MySQL MCP Server

## Overview
The MySQL MCP Server enables GitHub Copilot to interact with MySQL databases, allowing you to query databases, create tables, insert data, and perform database operations through natural language.

## Prerequisites
- VS Code with GitHub Copilot extension installed
- Node.js (v18 or higher) and npm installed
- A MySQL database server (Local or Remote)

## Installation

### Step 1: Install MySQL MCP Server via npm

```bash
# macOS/Linux
npm install -g @modelcontextprotocol/server-mysql

# Windows (PowerShell)
npm install -g @modelcontextprotocol/server-mysql
```

### Step 2: Configure MCP Server in VS Code

1. **Open mcp.json configuration**
   - **macOS:** `~/Library/Application Support/Code/User/mcp.json`
   - **Windows:** `%APPDATA%\Code\User\mcp.json`

2. **Add MySQL MCP Server configuration**

```json
{
  "servers": {
    "mysql": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-mysql",
        "--host", "localhost",
        "--user", "root",
        "--password", "your_password",
        "--database", "your_database"
      ]
    }
  }
}
```

### Step 3: Create Sample Database (Optional)

```sql
-- Connect to your MySQL server and run the following:
CREATE DATABASE IF NOT EXISTS shop_db;
USE shop_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (name, email) VALUES 
    ('Alice Johnson', 'alice@example.com'),
    ('Bob Smith', 'bob@example.com'),
    ('Carol Davis', 'carol@example.com');

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO orders (user_id, product, amount) VALUES
    (1, 'Laptop', 1299.99),
    (2, 'Mouse', 29.99),
    (1, 'Keyboard', 89.99);
```

### Step 4: Update Configuration with Credentials

Ensure your `mcp.json` has the correct `host`, `user`, `password`, and `database` name you created in Step 3.

### Step 5: Restart VS Code

## Accessing with GitHub Copilot

### 1. Query Data
```
Show me all users from the database
```

### 2. Filter and Search
```
Find all orders with amount greater than $50
```

### 3. Join Tables
```
Get all orders with user names and emails
```

### 4. Create Tables
```
Create a new table called products with columns: id, name, price, and stock
```

### 5. Insert Data
```
Add a new user: John Doe with email john@example.com
```

### 6. Update Records
```
Update the email of user with id 1 to newalice@example.com
```

### 7. Aggregate Queries
```
Calculate total sales amount by user
```

### 8. Database Schema
```
Show me the schema of the users table
```

## Advanced Configuration

### For Multiple Databases

```json
{
  "servers": {
    "mysql-prod": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-mysql",
        "--host", "prod-db.example.com",
        "--user", "writer",
        "--password", "prod_pass",
        "--database", "main_db"
      ]
    },
    "mysql-test": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-mysql",
        "--host", "localhost",
        "--user", "root",
        "--password", "test_pass",
        "--database", "test_db"
      ]
    }
  }
}
```

## Available Operations

### Data Querying
- SELECT queries with WHERE clauses
- JOIN operations (INNER, LEFT, RIGHT, FULL)
- Aggregate functions (COUNT, SUM, AVG, MAX, MIN)
- GROUP BY and HAVING clauses
- ORDER BY and LIMIT

### Data Modification
- INSERT new records
- UPDATE existing records
- DELETE records
- UPSERT operations

### Schema Operations
- CREATE TABLE
- ALTER TABLE
- DROP TABLE
- CREATE INDEX
- View table schemas

### Advanced Features
- Transactions
- Subqueries
- Common Table Expressions (CTEs)
- Window functions

## Example Workflows

### Workflow 1: Data Analysis
```
1. Show me the total number of users
2. Calculate the average order amount
3. Find the top 5 customers by total spending
4. Show monthly sales trends
```

### Workflow 2: Data Management
```
1. Create a new table for product categories
2. Insert sample categories: Electronics, Books, Clothing
3. Add a category_id column to the products table
4. Update products to assign them to categories
```

### Workflow 3: Reporting
```
1. Generate a report of all orders from last month
2. Show users who haven't placed any orders
3. Calculate revenue by product category
4. Find duplicate email addresses in the users table
```

## Troubleshooting

### Server Not Starting
- Verify Node.js is installed: `node --version`
- Check npm package is installed: `npm list -g @modelcontextprotocol/server-sqlite`
- Ensure database file path is correct and accessible

### Permission Errors
- Check file permissions on database file
- Ensure VS Code has access to the database directory
- On macOS, grant Full Disk Access to VS Code if needed

### Query Failures
- Verify database schema matches your queries
- Check for SQL syntax errors
## Troubleshooting

### Server Not Starting
- Verify Node.js is installed: `node --version`
- Check npm package is installed: `npm list -g @modelcontextprotocol/server-mysql`
- Ensure MySQL server is running and accessible (default port 3306)

### Permission Errors
- Ensure the MySQL user has sufficient privileges: `GRANT ALL ON shop_db.* TO 'user'@'localhost';`
- Check if firewall is blocking the connection

### Query Failures
- Verify database schema matches your queries
- Check for SQL syntax errors specific to MySQL
- Ensure foreign key constraints are satisfied

### Connection Issues
- For remote databases, verify network connectivity
- Check credentials (host, user, password) in `mcp.json`
- Ensure MySQL allows remote connections if necessary

## Best Practices

4. **Backup Strategy**
   - Regularly backup database files
   - Test restore procedures
   - Keep backups in secure locations

## Performance Tips

- Create indexes on foreign keys and frequently queried columns
- Use EXPLAIN QUERY PLAN to understand query execution
- Batch INSERT operations for better performance
- Use prepared statements to prevent SQL injection

## Resources
## Resources
- [MCP MySQL Server](https://github.com/modelcontextprotocol/servers/tree/main/src/mysql)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [SQL Tutorial](https://www.w3schools.com/sql/)
- [MySQL Workbench](https://www.mysql.com/products/workbench/) - GUI tool for MySQL
