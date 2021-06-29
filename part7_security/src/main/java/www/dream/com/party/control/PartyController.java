package www.dream.com.party.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import www.dream.com.party.service.PartyService;

@Controller
@RequestMapping("/party/*")
public class PartyController implements AuthenticationSuccessHandler, AccessDeniedHandler {
	@Autowired
	private PartyService partyService;
	
	@GetMapping(value="list")
	public void getList(Model model) {
		model.addAttribute("listParty", partyService.getList());		
	}

	@GetMapping(value="customLogin")
	public void loginInput(String error, String logout, Model model) {
		if (error != null) {
			model.addAttribute("error", "로그인 실패. 재시도 바람");
		}
		if (logout != null) {
			model.addAttribute("logout", "안녕히 가세요.");
		}
	}
	
	/**
	 * 로그인 성공 시 각 사용자의 권한 유형에 따라 개인화된 화면을 연동시켜주기 위한 기능을 이곳에서 개발 합니다
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		List<String> roleNames = new ArrayList<>();
		authentication.getAuthorities().forEach(authority -> {
			roleNames.add(authority.getAuthority());
		});
		
		if (roleNames.contains("admin")) {
			response.sendRedirect("/post/listBySearch?boardId=1");
			return;
		}
		if (roleNames.contains("manager")) {
			response.sendRedirect("/post/listBySearch?boardId=2");
			return;
		}
		response.sendRedirect("/post/listBySearch?boardId=3");
	}
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.sendRedirect("/party/accessError");
	}

	
}
