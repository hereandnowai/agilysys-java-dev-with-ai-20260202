# Lab 1: GitHub MCP Server

## Overview
The GitHub MCP Server enables GitHub Copilot to interact directly with GitHub repositories, allowing you to create repositories, manage issues, pull requests, branches, and more without leaving VS Code.

## Prerequisites
- VS Code with GitHub Copilot extension installed
- Docker installed on your system (for Docker-based setup)
- GitHub Personal Access Token (PAT)

## Installation Methods

### Method 1: Docker-based Installation (Recommended)

1. **Generate GitHub Personal Access Token**
   - Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
   - Click "Generate new token (classic)"
   - Select scopes: `repo`, `read:org`, `user`, `workflow`
   - Copy the generated token

2. **Configure MCP Server in VS Code**
   - **macOS:** `~/Library/Application Support/Code/User/mcp.json`
   - **Windows:** `%APPDATA%\Code\User\mcp.json`
   - Add the following configuration:

```json
{
  "servers": {
    "io.github.github/github-mcp-server": {
      "type": "stdio",
      "command": "docker",
      "args": [
        "run",
        "-i",
        "--rm",
        "-e",
        "GITHUB_PERSONAL_ACCESS_TOKEN=your_token_here",
        "ghcr.io/github/github-mcp-server:0.30.2"
      ],
      "gallery": "https://api.mcp.github.com",
      "version": "0.30.2"
    }
  }
}
```

3. **Replace Token**
   - Replace `your_token_here` with your actual GitHub PAT

4. **Restart VS Code**
   - Completely close and reopen VS Code to load the MCP server

### Method 2: Binary Installation

1. **Download GitHub MCP Server Binary**
```bash
# For macOS/Linux (ARM/Intel)
curl -L -o github-mcp-server https://github.com/github/github-mcp-server/releases/download/v0.30.2/github-mcp-server-macos
chmod +x github-mcp-server

# For Windows (PowerShell)
Invoke-WebRequest -Uri https://github.com/github/github-mcp-server/releases/download/v0.30.2/github-mcp-server-win.exe -OutFile github-mcp-server.exe
```

2. **Configure in mcp.json**
```json
{
  "servers": {
    "github-official": {
      "type": "stdio",
      "command": "env",
      "args": [
        "GITHUB_PERSONAL_ACCESS_TOKEN=your_token_here",
        "/path/to/github-mcp-server",
        "stdio"
      ],
      "gallery": "https://api.github.com",
      "version": "0.30.2"
    }
  }
}
```

## Accessing with GitHub Copilot

### 1. Verify Connection
Ask Copilot:
```
Can you check my GitHub profile?
```

### 2. Create Repository
```
Create a new public repository named "my-test-repo" in my GitHub account
```

### 3. List Repositories
```
Show me my recent GitHub repositories
```

### 4. Manage Issues
```
List all open issues in the repository owner/repo-name
```

### 5. Create Pull Request
```
Create a pull request from branch feature-x to main in repository owner/repo
```

### 6. Search Code
```
Search for Python files containing "machine learning" in GitHub
```

## Available Operations

### Repository Management
- Create, fork, and search repositories
- List branches and commits
- Manage files (create, update, delete)

### Issue Management
- Create, update, and list issues
- Add comments to issues
- Close issues with state reasons

### Pull Request Operations
- Create and update pull requests
- Merge pull requests
- Request Copilot reviews
- Add review comments

### Code Search
- Search repositories by metadata
- Search code across GitHub
- Find specific files and patterns

## Troubleshooting

### Server Not Starting
- Check if Docker is running: `docker ps`
- Verify token has correct permissions
- Check VS Code logs: View → Output → GitHub Copilot

### Authentication Errors
- Regenerate GitHub PAT with correct scopes
- Ensure token is not expired
- Update token in `mcp.json`

### Tool Calls Failing
- Restart VS Code after configuration changes
- Check Docker image is pulled: `docker pull ghcr.io/github/github-mcp-server:0.30.2`
- Verify internet connection

## Best Practices

1. **Token Security**
   - Never commit tokens to version control
   - Use environment variables for tokens
   - Rotate tokens regularly

2. **Efficient Queries**
   - Use specific repository names when possible
   - Leverage pagination for large result sets
   - Use minimal output when full details aren't needed

3. **Rate Limiting**
   - Be aware of GitHub API rate limits
   - Use authenticated requests (automatic with PAT)
   - Batch operations when possible

## Example Workflows

### Workflow 1: Create Repository and Add Files
```
1. Create a new public repository named "my-project"
2. Create a README.md file in the repository with content "# My Project"
3. Create a new branch called "feature/add-docs"
```

### Workflow 2: Issue Management
```
1. List all open issues in my repository
2. Create a new issue titled "Add documentation"
3. Add a comment to issue #5
```

### Workflow 3: Code Search and Analysis
```
1. Search for repositories related to "machine learning" in Python
2. Find all Java files in repository owner/repo
3. List commits in the main branch
```

## Resources
- [GitHub MCP Server Documentation](https://github.com/github/github-mcp-server)
- [MCP Protocol Specification](https://spec.modelcontextprotocol.io/)
- [GitHub API Documentation](https://docs.github.com/en/rest)
