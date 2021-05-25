package www.dream.com.bulletinBoard.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import www.dream.com.bulletinBoard.service.PostService;

@Controller
@RequestMapping("/post/*")
public class PostController {
	@Autowired
	private PostService postService;
	
	/** 특정 게시판에 등록되어 있는 게시글을 목록으로 조회하기  void:/post/list.jsp로 변환 */
	@GetMapping(value="list")
	public void listPost(@RequestParam("boardId") int boardId, Model model) {
		model.addAttribute("listPost", postService.getList(boardId));
		model.addAttribute("boardId", boardId);
	}
	
	@GetMapping(value="readPost")
	public void findPostById(@RequestParam("boardId") int boardId, 
			@RequestParam("postId") String id, Model model) {
		model.addAttribute("post", postService.findPostById(id));
		model.addAttribute("boardId", boardId);
	}
	
	@PostMapping(value="removePost")
	public String removePost(@RequestParam("boardId") int boardId, 
			@RequestParam("postId") String id, RedirectAttributes rttr) {
		if (postService.deletePostById(id)) {
			rttr.addFlashAttribute("result", "성공");
		}
		return "redirect:/post/list?boardId=" + boardId;
	}
}
