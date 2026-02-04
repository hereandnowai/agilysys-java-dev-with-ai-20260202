
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lab2_SpringAI_Ollama {

    public static void main(String[] args) {
        SpringApplication.run(Lab2_SpringAI_Ollama.class, args);
    }

    @Bean
    public CommandLineRunner runner(OllamaChatModel chatModel) {
        return args -> {
            String message = "What is your name?";
            System.out.println("Asking Ollama: " + message);

            ChatResponse response = chatModel.call(new Prompt(message));

            System.out.println("Response: " + response.getResult().getOutput().getContent());
        };
    }
}
