import dev.langchain4j.model.ollama.OllamaChatModel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Lab1_SimpleTextRag {


        public static void main(String[] args) throws Exception {
        // Load knowledge base
        String knowledge = Files.readString(Path.of(
                "/Users/hnai/Desktop/agilysys-java-dev-with-ai-20260202/day-2/knowledge.txt"));
        String[] chunks = knowledge.split("\n\n");

        // Setup LLM (no embedding model needed)
        var model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("gemma3:270m").build();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Text RAG Chatbot (No Embeddings). Type 'exit' to quit.\n");

        while (true) {
            System.out.print("You: ");
            String question = scanner.nextLine();
            if (question.equalsIgnoreCase("exit")) break;

            // Simple keyword retrieval (no embeddings)
            StringBuilder context = new StringBuilder();
            for (String chunk : chunks) {
                for (String word : question.toLowerCase().split("\\s+")) {
                    if (word.length() > 3 && chunk.toLowerCase().contains(word)) {
                        context.append(chunk).append("\n");
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
