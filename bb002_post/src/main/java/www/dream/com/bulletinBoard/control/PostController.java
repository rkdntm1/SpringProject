package www.dream.com.bulletinBoard.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import www.dream.com.bulletinBoard.model.PostVO;
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
	}
	
	@GetMapping(value="readPost")
	public void findPostById(@RequestParam("postId") String id, Model model) {
		model.addAttribute("post", postService.findPostById(id));
	}
}
