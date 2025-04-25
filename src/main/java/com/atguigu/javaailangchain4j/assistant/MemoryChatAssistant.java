package com.atguigu.javaailangchain4j.assistant;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemory = "chatMemory"
)
public interface MemoryChatAssistant {
    @UserMessage("你是我的好朋友，请用昆明话回答问题，并且添加一些表情符号。 {{it}}")
    String chat(String message);
}
