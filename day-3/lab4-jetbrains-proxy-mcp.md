# Lab 4: JetBrains MCP Proxy Server

## Overview
The JetBrains MCP Proxy Server enables GitHub Copilot in VS Code to interact with JetBrains IDEs (IntelliJ IDEA, PyCharm, WebStorm, etc.), allowing you to leverage JetBrains' powerful code analysis, refactoring, and navigation features.

## Prerequisites
- VS Code with GitHub Copilot extension installed
- JetBrains IDE installed (IntelliJ IDEA, PyCharm, WebStorm, etc.)
- Node.js (v18 or higher) for running the proxy server
- Both VS Code and JetBrains IDE on the same machine

## Installation

### Step 1: Install JetBrains IDE Plugin

1. **Open your JetBrains IDE** (e.g., IntelliJ IDEA)

2. **Install MCP Plugin**
   - Go to: `Settings/Preferences` → `Plugins`
   - Search for "Model Context Protocol" or "MCP Server"
   - Click `Install` and restart IDE

3. **Configure MCP Server in JetBrains**
   - Go to: `Settings/Preferences` → `Tools` → `MCP Server`
   - Enable "Start MCP Server"
   - Note the port number (default: 3000)

### Step 2: Install JetBrains MCP Proxy

```bash
# Clone the proxy repository
git clone https://github.com/modelcontextprotocol/jetbrains-proxy
cd jetbrains-proxy

# Install dependencies
npm install

# Build the project
npm run build
```

Or install globally:
```bash
npm install -g @modelcontextprotocol/jetbrains-proxy
```

### Step 3: Configure MCP Server in VS Code

1. **Open mcp.json configuration**
   - **macOS:** `~/Library/Application Support/Code/User/mcp.json`
   - **Windows:** `%APPDATA%\Code\User\mcp.json`

2. **Add JetBrains Proxy configuration**

```json
{
  "servers": {
    "jetbrains-proxy": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/jetbrains-proxy",
        "--port", "3000",
        "--host", "localhost"
      ]
    }
  }
}
```

### Step 4: Start JetBrains IDE with MCP Server

1. **Launch JetBrains IDE** (e.g., IntelliJ IDEA)
2. **Open your project**
3. **Verify MCP Server is running**
   - Check status bar for MCP indicator
   - Or go to: `Tools` → `MCP Server Status`

### Step 5: Restart VS Code

## Configuration Options

### Basic Configuration

```json
{
  "servers": {
    "jetbrains-proxy": {
      "type": "stdio",
      "command": "node",
      "args": [
        "/path/to/jetbrains-proxy/dist/index.js",
        "--port", "3000"
      ]
    }
  }
}
```

### Advanced Configuration with Multiple IDEs

```json
{
  "servers": {
    "intellij-proxy": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/jetbrains-proxy",
        "--port", "3000",
        "--ide", "intellij"
      ]
    },
    "pycharm-proxy": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/jetbrains-proxy",
        "--port", "3001",
        "--ide", "pycharm"
      ]
    }
  }
}
```

### Configuration with Authentication

```json
{
  "servers": {
    "jetbrains-proxy": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/jetbrains-proxy",
        "--port", "3000",
        "--token", "your-secure-token"
      ],
      "env": {
        "JETBRAINS_MCP_TOKEN": "your-secure-token"
      }
    }
  }
}
```

## Accessing with GitHub Copilot

### 1. Code Navigation

```
Show me all references to the class UserService in my JetBrains project
```

```
Find all usages of the method calculateTotal
```

### 2. Code Analysis

```
Analyze the complexity of the AuthController class
```

```
Show me all TODO comments in the current project
```

### 3. Refactoring

```
Suggest refactoring opportunities for the PaymentProcessor class
```

```
Extract this code block into a separate method
```

### 4. Code Generation

```
Generate a builder pattern for the User class
```

```
Create unit tests for the OrderService class
```

### 5. Inspections

```
Run code inspections on the entire project
```

```
Find all unused imports in Java files
```

### 6. Project Structure

```
Show me the project structure and module dependencies
```

```
List all Spring beans in the project
```

### 7. Build and Run

```
Trigger a Maven build in IntelliJ
```

```
Run the main application from IntelliJ
```

## Available Operations

### Code Intelligence
- Go to definition
- Find usages
- Show implementations
- Show hierarchy
- Quick documentation
- Parameter info

### Refactoring
- Rename
- Extract method/variable
- Inline
- Change signature
- Move class
- Safe delete

### Code Generation
- Generate constructors
- Generate getters/setters
- Generate equals/hashCode
- Generate toString
- Generate test methods
- Implement interface methods

### Code Analysis
- Run inspections
- Find duplicates
- Analyze dependencies
- Calculate metrics
- Detect code smells
- Security analysis

### Project Management
- Module navigation
- Dependency analysis
- Build configuration
- Run configurations
- Version control operations

## Example Workflows

### Workflow 1: Code Review Assistance

```
1. Run code inspections on all modified files
2. Find all TODO and FIXME comments
3. Check for unused imports and variables
4. Analyze cyclomatic complexity of modified methods
5. Generate a summary report
```

### Workflow 2: Refactoring Support

```
1. Find all usages of the deprecated method processPayment
2. Suggest alternative implementations
3. Show the impact analysis of renaming the method
4. Generate migration guide for API consumers
```

### Workflow 3: Test Generation

```
1. Analyze the UserRepository class
2. Generate JUnit test cases for all public methods
3. Create mock objects for dependencies
4. Add assertions based on method contracts
```

### Workflow 4: Documentation Enhancement

```
1. Find all public methods without JavaDoc
2. Generate JavaDoc comments based on method signatures
3. Add @param and @return tags
4. Include usage examples
```

## JetBrains IDE-Specific Features

### IntelliJ IDEA (Java/Kotlin)
- Spring Framework support
- Maven/Gradle integration
- Database tools
- Debugger integration
- Profiler access

### PyCharm (Python)
- Virtual environment management
- Django/Flask support
- Scientific tools
- Database integration
- Jupyter notebook support

### WebStorm (JavaScript/TypeScript)
- npm/yarn integration
- React/Vue/Angular support
- Node.js debugging
- ESLint integration
- Webpack configuration

### Rider (.NET/C#)
- NuGet package management
- Unity support
- .NET debugger
- Code coverage
- Performance profiling

## Troubleshooting

### Proxy Connection Failed

```bash
# Check if JetBrains IDE is running
ps aux | grep java | grep jetbrains

# Verify MCP server port is open
lsof -i :3000

# Check proxy logs
tail -f ~/.jetbrains-proxy/logs/proxy.log
```

### IDE Plugin Not Working

1. **Verify Plugin Installation**
   - `Settings` → `Plugins` → Search "MCP"
   - Ensure plugin is enabled

2. **Check Plugin Version**
   - Update to latest version
   - Restart IDE after update

3. **Review IDE Logs**
   - `Help` → `Show Log in Finder/Explorer`
   - Look for MCP-related errors

### Authentication Errors

```json
// Update token in both configurations
{
  "servers": {
    "jetbrains-proxy": {
      "env": {
        "JETBRAINS_MCP_TOKEN": "new-token-here"
      }
    }
  }
}
```

### Performance Issues

1. **Increase Memory Allocation**
   - `Help` → `Edit Custom VM Options`
   - Add: `-Xmx4096m`

2. **Disable Unnecessary Plugins**
   - `Settings` → `Plugins` → Disable unused plugins

3. **Exclude Build Directories**
   - `Settings` → `Project Structure` → Exclude target/build folders

## Best Practices

1. **Resource Management**
   - Close unused JetBrains IDE instances
   - Configure appropriate memory limits
   - Use indexed search for large projects

2. **Security**
   - Use authentication tokens
   - Restrict network access to localhost
   - Keep plugins updated

3. **Performance Optimization**
   - Index project before heavy operations
   - Use power save mode when not actively coding
   - Configure file watchers judiciously

4. **Workflow Integration**
   - Use JetBrains for complex refactoring
   - Leverage VS Code for quick edits
   - Sync settings between IDEs

## Integration Patterns

### Pattern 1: Cross-IDE Development
- Edit in VS Code with Copilot assistance
- Refactor in JetBrains IDE
- Test in JetBrains
- Commit from either IDE

### Pattern 2: Code Review
- Review code in VS Code
- Use JetBrains for deep analysis
- Generate reports from JetBrains
- Document findings in VS Code

### Pattern 3: Learning & Exploration
- Explore codebase in JetBrains
- Ask Copilot for explanations
- Navigate dependencies in JetBrains
- Document insights in VS Code

## Supported JetBrains IDEs

- IntelliJ IDEA (Community & Ultimate)
- PyCharm (Community & Professional)
- WebStorm
- PhpStorm
- Rider
- CLion
- GoLand
- RubyMine
- DataGrip
- Android Studio

## Resources
- [JetBrains MCP Plugin](https://plugins.jetbrains.com/plugin/mcp)
- [JetBrains Platform SDK](https://plugins.jetbrains.com/docs/intellij/welcome.html)
- [MCP Proxy GitHub](https://github.com/modelcontextprotocol/jetbrains-proxy)
- [IntelliJ IDEA Documentation](https://www.jetbrains.com/help/idea/)
- [JetBrains Plugin Development](https://plugins.jetbrains.com/docs/intellij/getting-started.html)

## Notes

> **Note:** The JetBrains MCP Proxy is an emerging integration. Feature availability may vary based on:
> - JetBrains IDE version
> - MCP plugin version
> - Programming language
> - Project type

Check the official repositories for the latest capabilities and updates.
