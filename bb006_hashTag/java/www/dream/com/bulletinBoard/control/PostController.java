package www.dream.com.bulletinBoard.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.service.BoardService;
import www.dream.com.bulletinBoard.service.PostService;
import www.dream.com.common.DTO.Criteria;
import www.dream.com.party.model.Party;
import www.dream.com.party.model.User;

@Controller
@RequestMapping("/post/*")
public class PostController {
	@Autowired
	private PostService postService;

	@Autowired
	private BoardService boardService;

	/** 특정 게시판에 등록되어 있는 게시글을 목록을 조회하기 ++ void : /post/list.jsp로 반환 ++ */

	@GetMapping(value = "list")
	public void list(@RequestParam("boardId") int boardId, 
			@ModelAttribute("pagenation") Criteria fromUser, Model model) {
		model.addAttribute("listPost", postService.getList(boardId, fromUser));
		model.addAttribute("boardId", boardId);
		model.addAttribute("boardName", boardService.getBoard(boardId).getName());
		fromUser.setTotal(postService.getTotalCount(boardId));
	}

	@GetMapping(value = {"readPost", "modifyPost"})
	public void findPostById(@RequestParam("boardId")  int boardId, 
			@RequestParam("postId") String id, @ModelAttribute("pagenation") Criteria fromUfser, Model model) {
		model.addAttribute("post", postService.findPostById(id));
		model.addAttribute("boardId", boardId);
	}

	@GetMapping(value = "registerPost")
	public void registerPost(@RequestParam("boardId") int boardId, Model model) {
		model.addAttribute("boardId", boardId);
	}

	@PostMapping(value = "registerPost")
	public String registerPost(@RequestParam("boardId") int boardId, PostVO newPost, RedirectAttributes rttr) {
		BoardVO board = new BoardVO(boardId);
		Party writer = new User("hong");
		newPost.setWriter(writer);
		postService.insert(board, newPost);

		rttr.addFlashAttribute("result", newPost.getId());

		return "redirect:/post/list?boardId=" + boardId;
	}


	@PostMapping(value = "modifyPost")
	public String modifyPost(@RequestParam("boardId") int boardId, Criteria fromUser,
			PostVO modifiedPost, RedirectAttributes rttr) {
		if (postService.updatePost(modifiedPost)) {
			rttr.addFlashAttribute("result", "수정처리가 성공");

		}
		rttr.addAttribute("boardId", boardId);
		rttr.addAttribute("pageNumber", fromUser.getPageNumber());
		rttr.addAttribute("amount", fromUser.getAmount());
		return "redirect:/post/list";
	}

	@PostMapping(value = "removePost")
	// 특정 url을 지정해줄거야
	public String removePost(@RequestParam("boardId") int boardId, 
			@RequestParam("postId") String id, Criteria fromUser, 
			RedirectAttributes rttr) {
		if (postService.deletePostById(id)) {
			rttr.addFlashAttribute("result", "삭제처리가 성공");
			rttr.addAttribute("boardId", boardId);
			rttr.addAttribute("pageNumber", fromUser.getPageNumber());
			rttr.addAttribute("amount", fromUser.getAmount());

		}
		return "redirect:/post/list";
	}

}
