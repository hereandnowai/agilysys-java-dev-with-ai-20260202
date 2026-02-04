import java.nio.file.Path;
import java.util.Scanner;

import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class Lab2_SimplePDF_RAG {
        public static void main(String[] args) {
            // Load and parse PDF
            var parser = new ApachePdfBoxDocumentParser();
            var doc = FileSystemDocumentLoader.loadDocument(
                    Path.of("/Users/hnai/Desktop/agilysys-java-dev-with-ai-20260202/day-2/About_HERE_AND_NOW_AI.pdf"), parser);

            // Split into chunks
            var splitter = DocumentSplitters.recursive(300, 50);
            var chunks = splitter.split(doc);

            // Setup LLM (no embedding model needed)
            var model = OllamaChatModel.builder()
                    .baseUrl("http://localhost:11434")
                    .modelName("gemma3:27b").build();

            Scanner scanner = new Scanner(System.in);
            System.out.println("PDF RAG Chatbot (No Embeddings). Type 'exit' to quit.\n");

            while (true) {
                System.out.print("You: ");
                String question = scanner.nextLine();
                if (question.equalsIgnoreCase("exit")) break;

                // Simple keyword retrieval (no embeddings)
                StringBuilder context = new StringBuilder();
                for (var chunk : chunks) {
                    String text = chunk.text();
                    for (String word : question.toLowerCase().split("\\s+")) {
                        if (word.length() > 1 && text.toLowerCase().contains(word)) {
                            context.append(text).append("\n---\n");
                            break;
                        }
                    }
                }

                String prompt = "Context:\n" + context + "\nQuestion: " + question;
                System.out.println("Bot: " + model.generate(prompt) + "\n");
            }
            scanner.close();
        }
}
