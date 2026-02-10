# SYSTEM UNDERSTANDING — advanced-java-ai

## 1. Application Overview
- **Application Name**: `advanced-java-ai` (Agilysys Java AI Training)
- **Purpose**: An educational and development platform designed to train engineers on building AI-powered applications using Java.
- **Primary Problem it Solves**: Bridges the gap between traditional Java development and modern AI integration (LLMs, RAG, Multimodal, MCP).
- **Target Users**: Java developers, software architects, and AI engineers.
- **Real-World Use Cases**:
    - Building domain-specific chatbots (RAG).
    - Automating document analysis (PDF/Invoice processing).
    - Extending IDE and system capabilities via Model Context Protocol (MCP).
    - Prototyping Spring AI-based microservices.

---

## 2. High-Level System Architecture
- **Architecture Style**: Poly-monolith / Modular Lab Structure. The project is organized as a single Maven project where each "Lab" file serves as an independent entry point (standalone application).
- **Frontend ↔ Backend Interaction**: 
    - Most labs are Command Line Interfaces (CLI) using `Scanner` for user input.
    - Future-state (Day 5) targets a Spring Boot Web architecture.
- **External Services**:
    - **Ollama**: Local LLM runner acting as the primary inference engine (`http://localhost:11434`).
    - **GitHub API**: Leveraged through MCP for repository management.
    - **Tavily**: Web search engine for RAG enhancements.
- **Architecture Diagram**:
```text
[User (CLI/VS Code)] 
       │
       ▼
[Java Application (Labs)] ◄───► [Local File System (PDFs, TXT)]
       │
       ├─[Spring AI / LangChain4j]
       │       │
       │       ▼
       └─────[Ollama (Local LLM)]
               │
               ▼
       [Models: gemma3, caramelai, qwen3-vl]
```

---

## 3. Frontend
- **Framework / Library**: None currently implemented.
- **UI/UX Behavior**: interaction is text-based via the terminal console.
- **Key Screens**: 
    - `Lab1_Ollama_Chatbot`: Chat console.
    - `Lab1_SimpleTextRag`: Question-answering console with context injection.
    - `Lab1_VisionInvoiceChatbot`: Multimodal interaction console.

---

## 4. Backend
- **Runtime**: Java 17 (Eclipse Temurin/OpenJDK).
- **Frameworks**: 
    - **Spring Boot 3.2.5 / 4.0.2**: Used for dependency injection, auto-configuration, and standalone runners.
    - **Spring AI**: Abstracted LLM interactions.
    - **LangChain4j 0.31.0**: Used for complex RAG pipelines, PDF parsing, and model orchestration.
- **Folder Structure**:
    - `day-1/`: Basic LLM connectivity.
    - `day-2/`: RAG patterns (Text, PDF, Spring AI integration).
    - `day-3/`: Documentation and config for Model Context Protocol (MCP).
    - `day-4/`: Vision/Multimodal labs.
    - `day-5/`: Structured Spring Boot web application boilerplate.
- **Error Handling**: Basic exception wrapping (`try-catch`) with console logging.
- **API Design**: No internal REST APIs; logic is encapsulated in `main` methods or `CommandLineRunner` beans.

---

## 5. Database & Storage
- **Database**: 
    - **PostgreSQL**: Leveraged for MCP-based database tools and mentioned in `pom.xml`.
    - **MySQL**: Supported through MCP servers.
- **File-Based Storage**: 
    - `knowledge.txt`: Raw text knowledge base for simple RAG.
    - `*.pdf`: Document knowledge bases (processed via Apache PDFBox).
    - `invoice.webp`: Sample image for multimodal vision processing.
- **Entity Relationships**: Currently flat; metadata is extracted from files at runtime rather than managed through an ORM (like JPA) in the current labs.

---

## 6. API Documentation (AS-IS)
The application primarily consumes external APIs rather than exposing its own.

### External: Ollama API Chat
- **Endpoint**: `http://localhost:11434/api/chat`
- **Method**: `POST`
- **Payload**:
  ```json
  {
    "model": "model_name",
    "messages": [ { "role": "user", "content": "text" } ]
  }
  ```
- **Response**: JSON stream or object containing assistant replies.

---

## 7. Core Functionalities (CURRENT ONLY)

### 1. Simple LLM Chat (`Lab1_Ollama_Chatbot`)
- Connects to a local Ollama instance and facilitates a basic request-response loop using the `gemma3:270m` model.

### 2. Keyword-Based RAG (`Lab1_SimpleTextRag`)
- Loads a local `knowledge.txt`.
- Splits text into chunks.
- Performs basic keyword matching on user queries to inject context into the LLM prompt.
- Handled without vector embeddings.

### 3. PDF Document RAG (`Lab2_SimplePDF_RAG`)
- Uses `ApachePdfBoxDocumentParser` to extract text from PDF files.
- Integrates with LangChain4j for document ingestion.

### 4. Vision-Based Invoice Analysis (`Lab1_VisionInvoiceChatbot`)
- Specifically designed for the `qwen3-vl:32b` multimodal model.
- Converts `invoice.webp` to a Base64 data URL.
- Sends both image and text instructions to the model to extract financial details (amounts, dates, vendors).

### 5. Model Context Protocol (MCP) Integration
- Configures VS Code to act as an MCP host.
- Connects LLMs to external tools: GitHub, Postgres, MySQL, Playwright (web browsing), and Docker.

---

## 8. Configuration & Environment
- **`application.properties`**: 
    - `spring.ai.ollama.base-url`: Points to the local Ollama instance.
    - `spring.ai.ollama.chat.options.model`: Default model (e.g., `caramelai`).
- **Environment Variables**: Managed via `dotenv-java` for secrets (like GitHub PATs if used in code).

---

## 9. Deployment & Infrastructure
- **Hosting**: Local execution (Desktop/Development laptop).
- **Dependencies**: 
    - Ollama must be running as a background service.
    - Maven for building and resolving classpath.
- **Tunnels**: No external proxies or tunnels are strictly required except for MCP web search.

---

## 10. Security Considerations
- **Authentication**: Minimal. Most labs operate on `localhost` without auth.
- **Secrets Management**: GitHub Personal Access Tokens (PATs) are required for MCP labs and should be stored in `mcp.json` or environment variables (never hardcoded).
- **Data Privacy**: All LLM processing is local (via Ollama), ensuring data remains on the machine.

---

## 11. Logging, Monitoring & Debugging
- **Logging**: SLF4J with `slf4j-simple` backend. Default level is `WARN` for Spring framework.
- **Debugging**: Standard Java debugger in VS Code.

---

## 12. Tech Stack Summary
| Category | Technology |
| :--- | :--- |
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.2.5+ / Spring AI |
| **AI LLM Library**| LangChain4j |
| **Inference Engine**| Ollama (Local) |
| **Models** | gemma3:270m, qwen3-vl:32b, caramelai |
| **Build Tool** | Maven |
| **Database** | PostgreSQL, MySQL (via MCP) |
| **Document Parser**| Apache PDFBox |

---

## 13. Current Status & Maturity Assessment
- **Maturity**: **Beta / Educational**. The codebase is a collection of functional prototypes.
- **Complete**: Connectivity to local LLMs, basic RAG, PDF parsing, Multimodal/Vision interaction.
- **Partial**: Spring Boot Web integration (shell created in Day 5).
- **Stubbed**: `day-1/Lab1_Ollama_Chatbot.java` is currently an empty class template awaiting implementation logic.

---

## 14. Known Constraints & Assumptions
- **Hard-coded Paths**: Several labs use absolute paths (e.g., `Lab1_SimpleTextRag.java` points to `/Users/hnai/...`). These are machine-specific.
- **Model Availability**: Assumes local Ollama has specific models (`gemma3`, `qwen3-vl`) pre-downloaded.
- **Connectivity**: Assumes port `11434` is open and accessible.

---

## 15. How This App Is Meant to Be Extended
- **Modularity**: New labs can be added to the Day-specific folders by creating new classes with `main` methods.
- **Centralization**: The individual lab logic is designed to be eventually migrated into the Day 5 `VisionRagApplication` as standardized Services and REST Controllers.
- **MCP Expansion**: Adding new servers to `mcp.json` allows the agent to interact with more external systems (e.g., Jira, Slack).
