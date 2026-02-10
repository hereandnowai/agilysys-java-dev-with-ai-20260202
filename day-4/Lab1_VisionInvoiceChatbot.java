import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Base64;
import java.util.Scanner;

public class Lab1_VisionInvoiceChatbot {

    private static final String MODEL = "qwen3-vl:32b";
    private static final String INVOICE_RELATIVE_PATH = "day-4/invoice.webp";
    private static final String OLLAMA_CHAT_ENDPOINT = "http://localhost:11434/api/chat";
    private static final Duration TIMEOUT = Duration.ofSeconds(60);
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(TIMEOUT)
            .build();

    public static void main(String[] args) throws IOException, InterruptedException {
        var invoicePath = Path.of(INVOICE_RELATIVE_PATH);
        if (!Files.exists(invoicePath)) {
            System.err.println("Invoice not found at " + invoicePath.toAbsolutePath());
            return;
        }

        var dataUrl = toDataUrl(invoicePath);
        printWelcome();

        try (var scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Your question (type 'exit' to quit): ");
                var question = scanner.nextLine().trim();
                if (question.isEmpty()) {
                    continue;
                }
                if ("exit".equalsIgnoreCase(question) || "quit".equalsIgnoreCase(question)) {
                    System.out.println("Thanks for using the invoice vision chatbot.");
                    break;
                }

                try {
                    var assistantReply = askVisionModel(question, dataUrl);
                    System.out.println("Assistant: " + assistantReply + "\n");
                } catch (IOException | InterruptedException e) {
                    System.err.println("Failed to reach Ollama: " + e.getMessage());
                }
            }
        }
    }

    private static void printWelcome() {
        System.out.println("Vision Invoice Chatbot");
        System.out.println("Model: " + MODEL);
        System.out.println("The bot reads only the provided invoice image and will not hallucinate.");
        System.out.println("Ask questions about the invoice (amounts, vendor, date, line items, due date, etc.).\n");
    }

    private static String askVisionModel(String question, String imageDataUrl) throws IOException, InterruptedException {
        var payload = buildPayload(question, imageDataUrl);
        var request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_CHAT_ENDPOINT))
                .timeout(TIMEOUT)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        var response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Unexpected status " + response.statusCode() + ": " + response.body());
        }

        return extractAssistantText(response.body());
    }

    private static String buildPayload(String question, String imageDataUrl) {
        var userInstruction = "Please analyze the attached invoice image and answer the question: "
                + question + " If the detail is not present, clearly say you cannot find it.";
        var escapedInstruction = escapeJson(userInstruction);
        var escapedImageData = escapeJson(imageDataUrl);

        return """
                {
                  "model": "%s",
                  "messages": [
                    {
                      "role": "system",
                      "content": "You are a finance assistant that reads invoices without hallucinating."
                    },
                    {
                      "role": "user",
                      "content": [
                        {
                          "type": "input_text",
                          "text": "%s"
                        },
                        {
                          "type": "input_image",
                          "image_url": "%s"
                        }
                      ]
                    }
                  ]
                }
                """.formatted(MODEL, escapedInstruction, escapedImageData);
    }

    private static String extractAssistantText(String responseBody) {
        var marker = "\"content\":\"";
        var index = responseBody.indexOf(marker);
        if (index == -1) {
            return responseBody;
        }
        var builder = new StringBuilder();
        for (int i = index + marker.length(); i < responseBody.length(); i++) {
            var ch = responseBody.charAt(i);
            if (ch == '\\') {
                if (i + 1 >= responseBody.length()) {
                    break;
                }
                var next = responseBody.charAt(i + 1);
                switch (next) {
                    case 'n' -> builder.append('\n');
                    case 'r' -> builder.append('\r');
                    case 't' -> builder.append('\t');
                    case '\\', '"', '/' -> builder.append(next);
                    default -> builder.append(next);
                }
                i++;
            } else if (ch == '"') {
                break;
            } else {
                builder.append(ch);
            }
        }
        return builder.toString().strip();
    }

    private static String toDataUrl(Path imagePath) throws IOException {
        var bytes = Files.readAllBytes(imagePath);
        var mediaType = Files.probeContentType(imagePath);
        if (mediaType == null) {
            mediaType = "image/webp";
        }
        var base64 = Base64.getEncoder().encodeToString(bytes);
        return "data:" + mediaType + ";base64," + base64;
    }

    private static String escapeJson(String value) {
        var builder = new StringBuilder(value.length());
        for (var ch : value.toCharArray()) {
            switch (ch) {
                case '\\' -> builder.append("\\\\");
                case '"' -> builder.append("\\\"");
                case '\n' -> builder.append("\\n");
                case '\r' -> builder.append("\\r");
                case '\t' -> builder.append("\\t");
                default -> builder.append(ch);
            }
        }
        return builder.toString();
    }
}