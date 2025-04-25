package com.atguigu.javaailangchain4j;

import com.atguigu.javaailangchain4j.assistant.MemoryChatAssistant;
import com.atguigu.javaailangchain4j.assistant.SeparateChatAssistant;
import com.atguigu.javaailangchain4j.bean.ChatMessages;
import com.atguigu.javaailangchain4j.entity.Appointment;
import com.atguigu.javaailangchain4j.service.AppointmentService;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
class JavaAiLangchain4jApplicationTests {
	@Autowired
	private OpenAiChatModel openAiChatModel;
	@Autowired
	private OllamaChatModel ollamaChatModel;
	@Autowired
	private QwenChatModel qwenChatModel;
	@Autowired
	private MemoryChatAssistant memoryChatAssistant;
	@Autowired
	private SeparateChatAssistant separateChatAssistant;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private AppointmentService appointmentService;

	@Test
	void contextLoads() {
		//初始化模型
		OpenAiChatModel model = OpenAiChatModel.builder()
		//LangChain4j提供的代理服务器，该代理服务器会将演示密钥替换成真实密钥， 再将请求转发给OpenAI API
				.baseUrl("http://langchain4j.dev/demo/openai/v1") //设置模型api地址（如果apiKey="demo"，则可省略baseUrl的配置）
                .apiKey("demo") //设置模型apiKey
				.modelName("gpt-4o-mini") //设置模型名称 尚硅⾕
				.build();
		//向模型提问
		String answer = model.chat("你好");
		//输出结果
		System.out.println(answer);
	}

	@Test
	void contextLoads2() {
		//向模型提问
		String answer = openAiChatModel.chat("你好");
		//输出结果
		System.out.println(answer);
	}

	@Test
	void contextLoads3() {
		String answer = ollamaChatModel.chat("你好");
		//输出结果
		System.out.println(answer);
	}

	@Test
	public void testDashScopeQwen() {
		//向模型提问
		String answer = qwenChatModel.chat("你好");
		//输出结果
		System.out.println(answer);
	}
	@Test
	public void testDashScopeWanx(){
		WanxImageModel wanxImageModel = WanxImageModel.builder()
				.modelName("wanx2.1-t2i-plus")
				.apiKey(System.getenv("DASH_SCOPE_API_KEY"))
				.build();
		Response<Image> response = wanxImageModel.generate("奇幻森林精灵：在一片弥漫着轻柔薄雾的古老森林深处，阳光透过茂密枝叶洒下金色光斑。一位身材娇小、长着透明薄翼的精灵少女站在一朵硕大的蘑菇上。她有着海藻般的绿色长发，发间点缀着蓝色的小花，皮肤泛着珍珠般的微光。身上穿着由翠绿树叶和白色藤蔓编织而成的 连衣裙，手中捧着一颗散发着柔和光芒的水晶球，周围环绕着五彩斑斓的蝴蝶，脚下是铺满苔藓的地面，蘑菇和蕨类植物丛生，营造出神秘而梦幻的氛围。");
				System.out.println(response.content().url());
	}

	@Test
	public void test(){
		String answer1 = memoryChatAssistant.chat("我是环环");
		System.out.println(answer1);
		String answer2 = memoryChatAssistant.chat("我是谁");
		System.out.println(answer2);
	}
	@Test
	public void testSeparate(){
		String answer1 = separateChatAssistant.chat(1,"我是环环");
		System.out.println(answer1);
		String answer2 = separateChatAssistant.chat(1,"我是谁");
		System.out.println(answer2);
		String answer3 = separateChatAssistant.chat(2,"我是kunkun");
		System.out.println(answer3);
	}

	@Test
	public void testMongo(){
		ChatMessages chatMessages = new ChatMessages();
		chatMessages.setContent("聊天记录列表");
		mongoTemplate.insert(chatMessages);
	}

	@Test
	public void testFindById() {
		ChatMessages chatMessages = mongoTemplate.findById("6808fc050600b245982c7580",
				ChatMessages.class);
		System.out.println(chatMessages);
	}

	@Test
	public void testUpdate() {
		Criteria criteria = Criteria.where("_id").is("6801ead733ba9c4a0d9b6c7b");
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("content", "新的聊天记录列表");
		//修改或新增
		mongoTemplate.upsert(query, update, ChatMessages.class);
	}
	/**
	 * 新增或修改文档
	 */
	@Test
	public void testUpdate2() {
		Criteria criteria = Criteria.where("_id").is("6801ead733ba9c4a0d9b6c7b");
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("content", "新的聊天记录列表");
		//修改或新增
		mongoTemplate.upsert(query, update, ChatMessages.class);
	}
	/**
	 * 删除文档
	 */
	@Test
	public void testDelete() {
		Criteria criteria = Criteria.where("_id").is("6801ead733ba9c4a0d9b6c7b");
		Query query = new Query(criteria);
		mongoTemplate.remove(query, ChatMessages.class);
	}

	@Test
	public void testUserMessage() {
		String answer = memoryChatAssistant.chat("我是环环");
		System.out.println(answer);
	}

	@Test
	public void testSystemMessage() {
		String ans = separateChatAssistant.chat(3,"你好，小蓝施,今天几号");
		System.out.println(ans);
	}

	@Test
	void testGetOne() {
		Appointment appointment = new Appointment();
		appointment.setUsername("张三");
		appointment.setIdCard("123456789012345678");
		appointment.setDepartment("内科");
		appointment.setDate("2025-04-14");
		appointment.setTime("上午");
		Appointment appointmentDB = appointmentService.getOne(appointment);
		System.out.println(appointmentDB);
	}

	@Test
	void testSave() {
//		Appointment appointment = new Appointment();
//		appointment.setUsername("张三");
//		appointment.setIdCard("123456789012345678");
//		appointment.setDepartment("内科");
//		appointment.setDate("2025-04-14");
//		appointment.setTime("上午");
//		appointment.setDoctorName("张医生");
		Appointment appointment = Appointment.builder()
								.username("张三")
								.idCard("123456789012345678")
								.department("内科")
								.date("2025-04-14")
								.time("上午")
								.doctorName("张医生")
								.build();

		appointmentService.save(appointment);
	}

	@Test
	void testRemoveById() {
		appointmentService.removeById(1L);
	}


}
