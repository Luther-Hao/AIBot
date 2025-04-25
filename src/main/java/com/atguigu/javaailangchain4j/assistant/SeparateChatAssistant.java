package com.atguigu.javaailangchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatMemory = "chatMemory",
        chatMemoryProvider = "chatMemoryProvider",
        chatModel = "qwenChatModel"
)
public interface SeparateChatAssistant {
    /**
     * 区分不同的聊天机器人
     * 分离聊天机器人
     * @param id
     * @param message
     * @return
     */
    @SystemMessage("你是我的好朋友，请用昆明话回答问题，并且添加一些表情符号。 今天是几月几号,{{current_date}}")
    String chat(@MemoryId int id, @UserMessage String message);
}
