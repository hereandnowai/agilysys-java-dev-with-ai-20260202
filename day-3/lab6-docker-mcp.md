# Lab 6: Docker MCP Server

## Overview
The Docker MCP Server enables GitHub Copilot to interact with Docker containers, images, networks, and volumes through natural language commands, simplifying container management and orchestration.

## Prerequisites
- VS Code with GitHub Copilot extension installed
- Docker Desktop or Docker Engine installed and running
- Node.js (v18 or higher) for running the MCP server
- Basic understanding of Docker concepts

## Installation

### Step 1: Install Docker

**macOS:**
```bash
# Using Homebrew
brew install --cask docker
# Or download Docker Desktop from docker.com
```

**Windows:**
1. Download Docker Desktop from [docker.com](https://www.docker.com/products/docker-desktop/)
2. Follow the installer steps and restart.
3. Ensure WSL 2 or Hyper-V is enabled.

**Linux (Ubuntu/Debian):**
```bash
# Install Docker Engine
sudo apt update
sudo apt install docker.io docker-compose

# Start Docker service
sudo systemctl start docker
sudo systemctl enable docker

# Add user to docker group (avoid sudo)
sudo usermod -aG docker $USER
newgrp docker
```

**Verify Installation:**
```bash
docker --version
docker ps
```

### Step 2: Install Docker MCP Server

```bash
# Install globally
npm install -g @modelcontextprotocol/server-docker

### Step 3: Configure MCP Server in VS Code

1. **Open mcp.json configuration**
   - **macOS:** `~/Library/Application Support/Code/User/mcp.json`
   - **Windows:** `%APPDATA%\Code\User\mcp.json`

2. **Add Docker MCP Server configuration**
1. **Open mcp.json configuration**
   - Location: `~/Library/Application Support/Code/User/mcp.json` (macOS)

2. **Add Docker MCP Server configuration**

```json
{
  "servers": {
    "docker": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-docker"
      ]
    }
  }
}
```

### Step 4: Configure Docker Socket Access

**macOS/Linux:**
```json
{
  "servers": {
    "docker": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-docker"
      ],
      "env": {
        "DOCKER_HOST": "unix:///var/run/docker.sock"
      }
    }
  }
}
```

**Windows:**
```json
{
  "servers": {
    "docker": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-docker"
      ],
      "env": {
        "DOCKER_HOST": "npipe:////./pipe/docker_engine"
      }
    }
  }
}
```

### Step 5: Restart VS Code

## Accessing with GitHub Copilot

### 1. Container Management

```
Show me all running Docker containers
```

```
Start a container named my-app
```

```
Stop all containers
```

```
Remove the container named old-app
```

### 2. Image Operations

```
List all Docker images on my system
```

```
Pull the latest nginx image
```

```
Build a Docker image from the Dockerfile in the current directory
```

```
Remove unused images to free up space
```

### 3. Container Logs

```
Show me the logs from the container named web-server
```

```
Follow the logs of the database container in real-time
```

### 4. Container Inspection

```
Inspect the container named api-server and show its configuration
```

```
Show resource usage for all running containers
```

### 5. Network Management

```
List all Docker networks
```

```
Create a new network named app-network
```

```
Connect the container web-app to the app-network
```

### 6. Volume Management

```
List all Docker volumes
```

```
Create a volume named db-data
```

```
Remove unused volumes
```

### 7. Docker Compose

```
Start all services defined in docker-compose.yml
```

```
Stop and remove all containers from docker-compose
```

## Advanced Configuration

### Remote Docker Host

```json
{
  "servers": {
    "docker-remote": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-docker"
      ],
      "env": {
        "DOCKER_HOST": "tcp://remote-host:2376",
        "DOCKER_TLS_VERIFY": "1",
        "DOCKER_CERT_PATH": "/path/to/certs"
      }
    }
  }
}
```

### Multiple Docker Hosts

```json
{
  "servers": {
    "docker-local": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-docker"
      ],
      "env": {
        "DOCKER_HOST": "unix:///var/run/docker.sock"
      }
    },
    "docker-production": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-docker"
      ],
      "env": {
        "DOCKER_HOST": "tcp://prod-server:2376"
      }
    }
  }
}
```

### With Docker Context

```json
{
  "servers": {
    "docker": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-docker",
        "--context", "my-context"
      ]
    }
  }
}
```

## Available Operations

### Container Operations
- List containers (all, running, stopped)
- Start/stop/restart containers
- Create containers
- Remove containers
- Pause/unpause containers
- Execute commands in containers
- Attach to containers
- Get container logs
- Inspect containers
- Container stats

### Image Operations
- List images
- Pull images
- Build images
- Tag images
- Remove images
- Prune unused images
- Image history
- Inspect images
- Export/import images

### Network Operations
- List networks
- Create networks
- Remove networks
- Connect/disconnect containers
- Inspect networks
- Prune unused networks

### Volume Operations
- List volumes
- Create volumes
- Remove volumes
- Inspect volumes
- Prune unused volumes

### System Operations
- Docker info
- Disk usage
- System events
- System prune
- Version information

## Example Workflows

### Workflow 1: Deploy Web Application

```
1. Pull the nginx:latest image
2. Create a Docker volume named web-content
3. Run nginx container with volume mounted at /usr/share/nginx/html
4. Expose port 80
5. Verify the container is running
```

### Workflow 2: Database Setup

```
1. Create a Docker network named db-network
2. Create a volume named postgres-data
3. Run PostgreSQL container with:
   - Volume mounted for data persistence
   - Connected to db-network
   - Environment variables for credentials
4. Run pgAdmin container connected to same network
5. Show the connection details
```

### Workflow 3: Development Environment

```
1. Build image from Dockerfile with tag my-app:dev
2. Create network called dev-network
3. Start Redis container on dev-network
4. Start my-app container on dev-network
5. Check logs from both containers
```

### Workflow 4: Cleanup and Maintenance

```
1. Stop all running containers
2. Remove stopped containers
3. Remove unused images
4. Remove unused volumes
5. Remove unused networks
6. Show disk space freed
```

### Workflow 5: Container Health Check

```
1. List all running containers with their status
2. Show CPU and memory usage for each container
3. Check logs for any errors
4. Restart unhealthy containers
5. Generate health report
```

## Docker Compose Integration

### Sample docker-compose.yml

```yaml
version: '3.8'

services:
  web:
    image: nginx:latest
    ports:
      - "80:80"
    volumes:
      - ./html:/usr/share/nginx/html
    networks:
      - app-network
    depends_on:
      - api

  api:
    build: ./api
    ports:
      - "3000:3000"
    environment:
      - DATABASE_URL=postgresql://db:5432/myapp
    networks:
      - app-network
    depends_on:
      - db

  db:
    image: postgres:15
    environment:
      - POSTGRES_DB=myapp
      - POSTGRES_USER=admin
      - POSTGRES_PASSWORD=secret
    volumes:
      - db-data:/var/lib/postgresql/data
    networks:
      - app-network

networks:
  app-network:
    driver: bridge

volumes:
  db-data:
```

### Copilot Commands for Docker Compose

```
Start all services from docker-compose.yml
```

```
Show logs from the web service
```

```
Scale the api service to 3 instances
```

```
Rebuild and restart the api service
```

## Best Practices

### 1. Image Management

```
Use specific image tags instead of 'latest' for production
```

```
Regularly update base images for security patches
```

### 2. Container Naming

```
Use descriptive names for containers: app-name-environment-service
```

### 3. Resource Limits

```
Set memory and CPU limits for containers to prevent resource exhaustion
```

### 4. Health Checks

```
Implement health checks in Dockerfiles and docker-compose
```

### 5. Logging

```
Configure log drivers and rotation to manage log file sizes
```

### 6. Security

```
Run containers as non-root users when possible
```

```
Scan images for vulnerabilities before deployment
```

## Troubleshooting

### Container Won't Start

```bash
# Check logs
docker logs container-name

# Inspect container
docker inspect container-name

# Check for port conflicts
docker ps -a
```

### Image Build Failures

```bash
# Build with no cache
docker build --no-cache -t myapp .

# Check Dockerfile syntax
docker build --check .
```

### Network Issues

```bash
# List networks
docker network ls

# Inspect network
docker network inspect network-name

# Check DNS resolution
docker exec container-name ping other-container
```

### Permission Errors

```bash
# Linux: Add user to docker group
sudo usermod -aG docker $USER

# Restart Docker daemon
sudo systemctl restart docker
```

### Disk Space Issues

Ask Copilot:
```
Clean up all unused Docker resources to free disk space
```

## Useful Docker Commands

### Container Commands
```bash
docker run -d --name myapp -p 8080:80 nginx
docker exec -it myapp /bin/bash
docker logs -f myapp
docker stop myapp
docker rm myapp
```

### Image Commands
```bash
docker images
docker pull ubuntu:22.04
docker build -t myapp:1.0 .
docker tag myapp:1.0 myapp:latest
docker rmi myapp:1.0
```

### System Commands
```bash
docker system df          # Show disk usage
docker system prune -a    # Remove all unused resources
docker info               # System-wide information
docker version            # Docker version
```

### Network Commands
```bash
docker network create mynetwork
docker network connect mynetwork container1
docker network disconnect mynetwork container1
docker network rm mynetwork
```

### Volume Commands
```bash
docker volume create myvolume
docker volume ls
docker volume inspect myvolume
docker volume rm myvolume
```

## Container Security

### 1. Image Scanning

Ask Copilot:
```
Scan the nginx:latest image for vulnerabilities
```

### 2. Security Best Practices

```dockerfile
# Use specific base image versions
FROM node:18-alpine

# Don't run as root
USER node

# Use COPY instead of ADD
COPY package*.json ./

# Minimize layers
RUN apt-get update && apt-get install -y \
    package1 \
    package2 \
    && rm -rf /var/lib/apt/lists/*
```

### 3. Secrets Management

```bash
# Use Docker secrets (Swarm mode)
echo "mypassword" | docker secret create db_password -

# Use environment files
docker run --env-file .env myapp
```

## Performance Optimization

### 1. Multi-stage Builds

```dockerfile
# Build stage
FROM node:18 AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Production stage
FROM node:18-alpine
WORKDIR /app
COPY --from=builder /app/dist ./dist
CMD ["node", "dist/index.js"]
```

### 2. Layer Caching

```dockerfile
# Install dependencies first (cached)
COPY package*.json ./
RUN npm install

# Copy source code (changes frequently)
COPY . .
```

### 3. Resource Limits

```bash
docker run -m 512m --cpus="1.5" myapp
```

## Monitoring and Logging

### Container Stats

Ask Copilot:
```
Show CPU and memory usage for all containers
```

### Log Management

```bash
# View logs with timestamps
docker logs -t myapp

# Limit log output
docker logs --tail 100 myapp

# Follow logs
docker logs -f myapp
```

### Health Checks

```dockerfile
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost/ || exit 1
```

## Resources
- [Docker Documentation](https://docs.docker.com/)
- [Docker Hub](https://hub.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Docker MCP Server](https://github.com/modelcontextprotocol/servers)
- [Docker Best Practices](https://docs.docker.com/develop/dev-best-practices/)
- [Container Security](https://docs.docker.com/engine/security/)
- [Docker Networking](https://docs.docker.com/network/)

## Cheat Sheet

```bash
# Essential Commands
docker ps                          # List running containers
docker ps -a                       # List all containers
docker images                      # List images
docker pull image:tag              # Pull image
docker run -d -p 8080:80 image    # Run container
docker stop container              # Stop container
docker rm container                # Remove container
docker rmi image                   # Remove image
docker logs container              # View logs
docker exec -it container bash     # Enter container
docker-compose up -d               # Start compose services
docker-compose down                # Stop compose services
docker system prune -a             # Clean everything
```
