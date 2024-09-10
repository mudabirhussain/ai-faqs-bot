package ai.bot.faqs.controllers;

import ai.bot.faqs.records.ChatRequest;
import ai.bot.faqs.services.FaqsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai-bot")
public class FaqsController {

    private final FaqsService faqsService;

    public FaqsController(FaqsService faqsService) {
        this.faqsService = faqsService;
    }

    @PostMapping("/chat")
    public String chatBot(@RequestBody ChatRequest chatRequest) {
        return faqsService.chat(chatRequest);
    }
}
