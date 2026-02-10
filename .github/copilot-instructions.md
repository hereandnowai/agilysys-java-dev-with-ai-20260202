# Copilot Instructions for advanced-java-ai

This project is a Java-based training environment for building AI applications using local LLMs (via Ollama). It bridges the gap between traditional Java development and modern AI integration (LLMs, RAG, Multimodal, MCP).

## Project Structure & Architecture
- **Multi-lab Organization/Poly-monolith**: The project is organized by days (`day-1/`, `day-2/`, etc.) instead of a single standard Maven hierarchy. Each "Lab" file is an independent entry point.
- **Daily Themes**:
  - `day-1`: Basic LLM connectivity and simple chatbots.
  - `day-2`: RAG patterns (Text, PDF, Spring AI integration).
  - `day-3`: Model Context Protocol (MCP) configuration and usage.
  - `day-4`: Vision/Multimodal AI (e.g., invoice processing).
  - `day-5`: Structured Spring Boot Web architecture boilerplate.
- **Source Root**: The project root `.` is configured as the `sourceDirectory` in `pom.xml`.
- **Standalone Labs**: Each file named `Lab*_*.java` is a standalone application with a `main` method.
- **Entry Points**: 
  - Standalone Java classes with `main` methods (using `HttpClient` or `Scanner`).
  - `@SpringBootApplication` using `CommandLineRunner` for execution.

## Critical Developer Workflows
- **Running Labs**: Run the `main` method in the specific lab file.
- **Local LLM**: Ensure Ollama is running at `http://localhost:11434`.
- **Configuration**: 
  - `day-2/application.properties` contains Spring AI settings like `spring.ai.ollama.base-url`.
  - Day 5 features a dedicated `src/main/resources/application.properties`.
- **Build**: Use `mvn clean compile` to build all labs across the project.

## Coding Patterns & Conventions
- **AI Libraries**: 
  - Prefer **Spring AI** (`org.springframework.ai`) for Spring-based labs.
  - Use **LangChain4j** (`dev.langchain4j`) for pure Java or advanced RAG/Document-parsing labs.
- **RAG Patterns**:
  - Simple keyword-based retrieval is used in early labs (e.g., `Lab1_SimpleTextRag.java`).
  - Advanced RAG uses `dev.langchain4j.easy.rag` and `ApachePdfBoxDocumentParser`.
- **Vision/Multimodal**: Use `HttpClient` for raw JSON payloads when using vision models (e.g., `Lab1_VisionInvoiceChatbot.java`) to handle specific image input types.
- **File Paths**: Use `Path.of("day-X/filename")` for resource access, as labs are run from the project root.

## Essential Knowledge
- **Ollama Models**: Common models include `gemma3:270m`, `caramelai`, and `qwen3-vl:32b`.
- **MCP (Model Context Protocol)**: Connects LLMs to external tools like GitHub, PostgreSQL, MySQL, Playwright, and Docker.
- **External Services**: Leverages **Tavily** for web search and **GitHub API** for repository management.
- **Data Privacy**: All LLM processing is local via Ollama, ensuring data remains on the machine.

## Tech Stack Summary
- **Runtime**: Java 17 (OpenJDK/Temurin).
- **Frameworks**: Spring Boot 3.2.5 (Labs) / 4.0.2 (Day 5 Demo).
- **Inference**: Ollama (Local).
- **Build Tool**: Maven.

## Examples
- **Spring AI Runner**:
```java
@Bean
public CommandLineRunner runner(OllamaChatModel chatModel) {
    return args -> {
        ChatResponse response = chatModel.call(new Prompt("message"));
        System.out.println(response.getResult().getOutput().getContent());
    };
}
```
- **LangChain4j Setup**:
```java
var model = OllamaChatModel.builder()
    .baseUrl("http://localhost:11434")
    .modelName("model-name").build();
```

## Known Constraints
- **Hardcoded Paths**: Refactor absolute paths (e.g., `/Users/hnai/...`) to relative paths from the root where found.
- **Port Usage**: Assumes `11434` for Ollama is open and accessible.