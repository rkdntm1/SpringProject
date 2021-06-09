package www.dream.com.restStudy;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import www.dream.com.bulletinBoard.model.PostVO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration //브라우저(Browser) 역할을 대행
@ContextConfiguration({"file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml", // 여러개할때 { } 사용
	"file:src\\main\\webapp\\WEB-INF\\spring\\appServlet\\servlet-context.xml"}) //여기에 들어있는정보를 바탕으로 깡통에 객체를 담아라 junit아
public class SampleControllerTest {
	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before // 준비 단계
	public void setup() {
		//Mock 가상의. 가짜. 대행자
		mockMvc =  MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	@Test
	public void test() {
		PostVO post = new PostVO();
		post.setTitle("식곤증이 찾아온다");
		post.setContent("매우 졸린상태..");
		String jsonOfPost = new Gson().toJson(post);
		
		try {
			//get 방법으로 web Server에게 /party/list 이 url로 요청을 보낸다.
			ResultActions ra = mockMvc.perform(
					MockMvcRequestBuilders.post("/sample/registerPost/3")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonOfPost));
			ra.andExpect(status().is(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
