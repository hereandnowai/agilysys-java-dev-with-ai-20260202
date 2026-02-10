# HERE AND NOW AI — advanced-java-ai
> **"AI is Good"**

Welcome to the **advanced-java-ai** training repository. This project is a comprehensive Java-based training environment for building AI applications using local LLMs (via Ollama). It bridges the gap between traditional Java development and modern AI integration (LLMs, RAG, Multimodal, MCP).

---

## 🏢 About HERE AND NOW AI
- **Organization**: HERE AND NOW AI
- **Website**: [hereandnowai.com](https://hereandnowai.com)
- **Slogan**: "AI is Good"

---

## 🚀 Application Overview
- **Purpose**: Educational platform for training engineers on Java AI integration.
- **Key Themes**:
    - **Day 1**: Basic LLM connectivity and simple chatbots.
    - **Day 2**: RAG patterns (Text, PDF, Spring AI integration).
    - **Day 3**: Model Context Protocol (MCP) configuration.
    - **Day 4**: Vision/Multimodal AI (e.g., invoice processing).
    - **Day 5**: Structured Spring Boot Web architecture boilerplate.

## 🛠 Tech Stack
| Category | Technology |
| :--- | :--- |
| **Language** | Java 17 (OpenJDK/Temurin) |
| **Framework** | Spring Boot 3.2.5+ / Spring AI |
| **AI LLM Library**| LangChain4j 0.31.0 |
| **Inference Engine**| Ollama (Local) |
| **Models** | gemma3:270m, qwen3-vl:32b, caramelai |
| **Build Tool** | Maven |

## 📁 Project Structure
- `day-1/`: Basic connectivity and "Hello World" LLM labs.
- `day-2/`: Retrieval Augmented Generation (RAG) with local text and PDF files.
- `day-3/`: MCP labs for connecting LLMs to GitHub, SQL, and more.
- `day-4/`: Multimodal labs for image and document processing.
- `day-5/`: Production-ready Spring Boot demo.

## ⚙️ Setup & Requirements
1. **Ollama**: Ensure Ollama is running at `http://localhost:11434`.
2. **Models**: Download required models:
   ```bash
   ollama pull gemma3:270m
   ollama pull caramelai
   ollama pull qwen3-vl:32b
   ```
3. **Build**: Run `mvn clean compile` to build the labs.

---

## 📝 License
This project is licensed under the **MIT License**.

---
*For more information, visit [hereandnowai.com](https://hereandnowai.com).*
