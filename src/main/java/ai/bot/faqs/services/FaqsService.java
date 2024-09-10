package ai.bot.faqs.services;


import ai.bot.faqs.records.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FaqsService {

    private final ChatClient chatClient;
    private final PgVectorStore vectorStore;

    @Value("classpath:/prompts/faqs-references.st")
    private Resource botPromptTemplate;

    public FaqsService(ChatClient chatClient, PgVectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    public String chat(ChatRequest chatRequest) {
        //Preparing Params
        Map<String, Object> promptParams = new HashMap<>();
        promptParams.put("question", chatRequest.question());
        promptParams.put("documents", String.join("\n", findSimilarDocument(chatRequest.question())));
        var prompt = new PromptTemplate(botPromptTemplate, promptParams).create();

        //Calling the AI Bot
        return chatClient.prompt(prompt)
                .call()
                .content();
    }

    private List<String> findSimilarDocument(String question){
        List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.query(question).withTopK(3));
        return similarDocuments.stream().map(Document::getContent).toList();
    }
}
