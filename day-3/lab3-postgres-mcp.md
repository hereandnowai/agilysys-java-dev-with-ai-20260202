# Lab 3: PostgreSQL MCP Server

## Overview
The PostgreSQL MCP Server enables GitHub Copilot to interact with PostgreSQL databases, providing advanced database operations, complex queries, and enterprise-grade database management capabilities.

## Prerequisites
- VS Code with GitHub Copilot extension installed
- Node.js (v18 or higher) and npm installed
- PostgreSQL database server installed and running
- Database credentials (host, port, username, password, database name)

## Installation

### Step 1: Install PostgreSQL (if not already installed)

**macOS (using Homebrew):**
```bash
brew install postgresql@15
brew services start postgresql@15
```

**Windows:**
1. Download installer from [postgresql.org](https://www.postgresql.org/download/windows/)
2. Or use winget: `winget install PostgreSQL.PostgreSQL.15`
3. Ensure "PostgreSQL Server" service is running in `services.msc`

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
```

**Verify Installation:**
```bash
psql --version
```

### Step 2: Create Sample Database

```bash
# Connect to PostgreSQL
# On macOS: psql -d postgres (Homebrew uses your username as the default role)
# On Windows/Linux: psql -U postgres
psql -U postgres

# Create database
CREATE DATABASE testdb;

# Connect to the database
\c testdb

# Create sample tables
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    department VARCHAR(50),
    salary DECIMAL(10,2),
    hire_date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE departments (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    manager_id INTEGER,
    budget DECIMAL(12,2)
);

CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department_id INTEGER REFERENCES departments(id),
    start_date DATE,
    end_date DATE,
    budget DECIMAL(12,2)
);

-- Insert sample data
INSERT INTO departments (name, budget) VALUES
    ('Engineering', 500000),
    ('Sales', 300000),
    ('Marketing', 200000);

INSERT INTO employees (first_name, last_name, email, department, salary) VALUES
    ('John', 'Doe', 'john.doe@company.com', 'Engineering', 95000),
    ('Jane', 'Smith', 'jane.smith@company.com', 'Sales', 75000),
    ('Mike', 'Johnson', 'mike.j@company.com', 'Engineering', 88000),
    ('Sarah', 'Williams', 'sarah.w@company.com', 'Marketing', 70000);

INSERT INTO projects (name, department_id, start_date, budget) VALUES
    ('Product Launch', 1, '2026-01-01', 150000),
    ('Q1 Campaign', 3, '2026-01-15', 80000);

-- Exit psql
\q
```

### Step 3: Install PostgreSQL MCP Server

The PostgreSQL MCP server can be run directly using `npx` without a separate installation step, or you can install it globally using npm:

```bash
# Option 1: Install globally (standard)
npm install -g @modelcontextprotocol/server-postgres

# Option 2: Run directly via npx (used in configuration)
npx -y @modelcontextprotocol/server-postgres
```

### Step 4: Configure MCP Server in VS Code

1. **Open mcp.json configuration**
   - **macOS:** `~/Library/Application Support/Code/User/mcp.json`
   - **Windows:** `%APPDATA%\Code\User\mcp.json`

2. **Add PostgreSQL MCP Server configuration**

1. **Open mcp.json configuration**
   - Location: `~/Library/Application Support/Code/User/mcp.json` (macOS)

2. **Add PostgreSQL MCP Server configuration**

```json
{
  "servers": {
    "postgres": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-postgres",
        "postgresql://postgres:your_password@localhost:5432/testdb"
      ]
    }
  }
}
```

### Alternative: Environment Variables Configuration

```json
{
  "servers": {
    "postgres": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-postgres"
      ],
      "env": {
        "PGHOST": "localhost",
        "PGPORT": "5432",
        "PGUSER": "postgres",
        "PGPASSWORD": "your_password",
        "PGDATABASE": "testdb"
      }
    }
  }
}
```

### Step 5: Restart VS Code

## Accessing with GitHub Copilot

### 1. Query Data
```
Show me all employees in the Engineering department
```

### 2. Complex Joins
```
Get all employees with their department information and total project budgets
```

### 3. Aggregations
```
Calculate average salary by department
```

### 4. Window Functions
```
Rank employees by salary within each department
```

### 5. Create Views
```
Create a view that shows employee full names with their departments
```

### 6. Transactions
```
Start a transaction, update employee salaries by 5%, and show me the changes
```

### 7. JSON Operations
```
Create a table to store user preferences as JSON and insert sample data
```

### 8. Full-Text Search
```
Add full-text search capability to the employees table for names
```

## Advanced Features

### 1. Common Table Expressions (CTEs)

Ask Copilot:
```
Use a CTE to find departments with average salaries above $80,000
```

### 2. Recursive Queries

```sql
-- Create organizational hierarchy
CREATE TABLE org_hierarchy (
    employee_id INTEGER PRIMARY KEY,
    manager_id INTEGER REFERENCES org_hierarchy(employee_id),
    name VARCHAR(100)
);
```

Ask Copilot:
```
Show me the complete organizational hierarchy from CEO to all employees
```

### 3. Array Operations

```
Create a table with array columns for employee skills and query for specific skills
```

### 4. JSONB Queries

```
Store employee metadata as JSONB and query for employees with specific attributes
```

### 5. Partitioning

```
Create a partitioned table for sales data by month
```

## Advanced Configuration

### Connection Pooling

```json
{
  "servers": {
    "postgres": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-postgres",
        "postgresql://postgres:password@localhost:5432/testdb?max=10&idle_timeout=30"
      ]
    }
  }
}
```

### SSL Connection

```json
{
  "servers": {
    "postgres-ssl": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-postgres",
        "postgresql://user:password@hostname:5432/dbname?sslmode=require"
      ]
    }
  }
}
```

### Multiple Database Connections

```json
{
  "servers": {
    "postgres-production": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-postgres",
        "postgresql://user:password@prod-host:5432/proddb"
      ]
    },
    "postgres-development": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-postgres",
        "postgresql://user:password@localhost:5432/devdb"
      ]
    }
  }
}
```

## Example Workflows

### Workflow 1: Employee Analytics
```
1. Show me the top 10 highest paid employees
2. Calculate the salary distribution by department
3. Find employees hired in the last 6 months
4. Show departments with headcount greater than 5
```

### Workflow 2: Data Migration
```
1. Create a backup table for employees
2. Copy all data to the backup table
3. Update employee salaries with a 10% increase
4. Verify the changes
```

### Workflow 3: Performance Optimization
```
1. Show me the execution plan for the employee salary query
2. Create indexes on frequently queried columns
3. Analyze table statistics
4. Suggest optimizations for slow queries
```

### Workflow 4: Database Administration
```
1. Show me database size and table sizes
2. List all indexes in the database
3. Find unused indexes
4. Check for missing foreign key indexes
```

## Available Operations

### Data Querying
- Complex SELECT queries with multiple JOINs
- Subqueries and CTEs
- Window functions
- Full-text search
- JSON/JSONB queries
- Array operations
- Geographic queries (PostGIS)

### Data Modification
- INSERT with RETURNING
- UPSERT (INSERT ON CONFLICT)
- Bulk UPDATE operations
- DELETE with conditions
- TRUNCATE operations

### Schema Management
- CREATE/ALTER/DROP tables
- CREATE/DROP indexes
- CREATE views and materialized views
- Manage constraints
- Table partitioning

### Advanced Features
- Transactions (BEGIN, COMMIT, ROLLBACK)
- Savepoints
- Table inheritance
- Custom types and domains
- Triggers and functions
- Extensions (pg_trgm, uuid-ossp, etc.)

## Troubleshooting

### Connection Refused
```bash
# Check if PostgreSQL is running
brew services list  # macOS
sudo systemctl status postgresql  # Linux

# Check PostgreSQL logs
tail -f /usr/local/var/log/postgresql@15.log  # macOS
sudo tail -f /var/log/postgresql/postgresql-15-main.log  # Linux
```

### Authentication Failed
```bash
# Edit pg_hba.conf to allow local connections
# Location: /usr/local/var/postgresql@15/pg_hba.conf (macOS)
# Add or modify: local all all trust

# Restart PostgreSQL
brew services restart postgresql@15
```

### Permission Errors
```sql
-- Grant necessary permissions
GRANT ALL PRIVILEGES ON DATABASE testdb TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
```

### Performance Issues
```sql
-- Analyze tables
ANALYZE;

-- Vacuum tables
VACUUM ANALYZE;

-- Check query performance
EXPLAIN ANALYZE SELECT * FROM employees;
```

## Best Practices

1. **Security**
   - Use strong passwords
   - Implement row-level security (RLS)
   - Encrypt connections with SSL
   - Never store credentials in code

2. **Performance**
   - Create indexes on foreign keys and frequently queried columns
   - Use EXPLAIN ANALYZE to understand query plans
   - Regularly VACUUM and ANALYZE tables
   - Implement connection pooling

3. **Data Integrity**
   - Use foreign key constraints
   - Implement check constraints
   - Use transactions for multi-step operations
   - Validate data before insertion

4. **Backup & Recovery**
   - Regular pg_dump backups
   - Point-in-time recovery setup
   - Test restore procedures
   - Monitor disk space

## Useful PostgreSQL Commands

```sql
-- Database information
\l                          -- List databases
\dt                         -- List tables
\d table_name              -- Describe table
\di                         -- List indexes
\dv                         -- List views

-- Query performance
EXPLAIN query;              -- Show query plan
EXPLAIN ANALYZE query;      -- Execute and show actual performance

-- Database statistics
SELECT pg_size_pretty(pg_database_size('testdb'));
SELECT schemaname, tablename, pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename))
FROM pg_tables ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC;
```

## Resources
- [PostgreSQL Official Documentation](https://www.postgresql.org/docs/)
- [MCP PostgreSQL Server](https://github.com/modelcontextprotocol/servers)
- [PostgreSQL Tutorial](https://www.postgresqltutorial.com/)
- [PostgreSQL Performance Tips](https://wiki.postgresql.org/wiki/Performance_Optimization)
- [pgAdmin](https://www.pgadmin.org/) - GUI tool for PostgreSQL
