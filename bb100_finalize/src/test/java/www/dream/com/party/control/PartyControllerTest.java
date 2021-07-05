package www.dream.com.party.control;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration //브라우저(Browser) 역할을 대행
@ContextConfiguration({"file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml", // 여러개할때 { } 사용
	"file:src\\main\\webapp\\WEB-INF\\spring\\appServlet\\servlet-context.xml"}) //여기에 들어있는정보를 바탕으로 깡통에 객체를 담아라 junit아
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PartyControllerTest {
	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before // 준비 단계
	public void setup() {
		//Mock 가상의. 가짜. 대행자
		mockMvc =  MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	/**
	 * mockMvc를 통하여 테스트함으로써 back-end 개발자의 할일은 끝남.
	 * front-end 개발자는 EL이나 JSTL을 활용하여 listParty에 들어있는 정보를 HTML로 나타내 준다. 
	 */
	@Test
	public void testPartyList() {
		try {
			//get 방법으로 web Server에게 /party/list 이 url로 요청을 보낸다.
			ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get("/party/list"));
			MvcResult mvcResult = ra.andReturn();
			ModelAndView  modelAndView = mvcResult.getModelAndView();
			//PartyController의 getList(Model model)에서 model.addAttribute("listParty",~)에 담은 정보를 꺼낸다.
			Map model = modelAndView.getModel(); //controller에서 넣어줬던 model
			((List) (model.get("listParty"))).forEach(p->{System.out.println(p);}); //"listParty"에 담긴 정보 반복돌리기
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
