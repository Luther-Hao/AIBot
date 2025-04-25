package com.atguigu.javaailangchain4j.controller;

import com.atguigu.javaailangchain4j.assistant.XiaoZhiAgent;
import com.atguigu.javaailangchain4j.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xiaozhi")
@Tag(name = "小智")
public class XiaoZhiController {
    @Autowired
    private XiaoZhiAgent xiaozhiAgent;

    @Operation(summary = "对话")
    @PostMapping("/chat")
    public String chat(@RequestBody ChatForm chatForm)  {
        return xiaozhiAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }
}
