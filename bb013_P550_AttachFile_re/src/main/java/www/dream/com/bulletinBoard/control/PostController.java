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
import org.springframework.web.util.UriComponentsBuilder;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.service.BoardService;
import www.dream.com.bulletinBoard.service.PostService;
import www.dream.com.common.dto.Criteria;
import www.dream.com.party.model.Party;
import www.dream.com.party.model.User;

@Controller
@RequestMapping("/post/*")
public class PostController {
	
	//LRCUD
	@Autowired
	private BoardService boardService;

	@Autowired
	private PostService postService;

	/** 특정 게시판에 등록되어 있는 게시글을 목록으로 조회하기  void:/post/list.jsp로 변환 */
	
	/** 검색했을때 게시글목록 조회하기 */
	@GetMapping(value="listBySearch")
	public void listPostBySearch(@RequestParam("boardId") int boardId,
			@ModelAttribute("pagenation") Criteria fromUser, Model model) {
		model.addAttribute("listPost", postService.getListByHashTag(boardId, fromUser));
		model.addAttribute("boardId", boardId);
		model.addAttribute("boardName", boardService.getBoard(boardId).getName());
		//java beans 규약에따라서 기본생성자와 setter가 발동되기때문에 11번 쪽으로 넘어가지지 않기때문에 기존 정보를
		//가지고 새로운 객체를 만들어 줘서 계산 처리를 한다.
		fromUser.setTotal(postService.getSearchTotalCount(boardId, fromUser));
	}

	@GetMapping(value={"readPost", "modifyPost"})
	public void findPostById(@RequestParam("boardId") int boardId, 
			@RequestParam("postId") String id, @ModelAttribute("pagenation") Criteria fromUser, Model model) {
		model.addAttribute("post", postService.findPostById(id));
		model.addAttribute("boardId", boardId);
	}
	
	/** 게시글 등록페이지 들어가기 */
	@GetMapping(value="registerPost")
	public void registerPost(@RequestParam("boardId") int boardId, Model model) {
		model.addAttribute("boardId", boardId);
	}

	/** 게시글 등록페이지에서 입력한 내용 insert하기 */
	@PostMapping(value="registerPost")
	public String registerPost(@RequestParam("boardId") int boardId,
			PostVO newpost, RedirectAttributes rttr) {
		newpost.parseAttachInfo();
		BoardVO board = new BoardVO(boardId);
		Party writer = new User("hong");
		newpost.setWriter(writer);
		postService.insert(board, newpost);

		rttr.addFlashAttribute("result", newpost.getId());
		
		//listBySearch로 목록 조회하면 어떤 단점? 검색한 단어와 상관성이 없는 신규 게시글을 볼 수 없다.
		return "redirect:/post/listBySearch?boardId=" + boardId;
	}

	@PostMapping(value="modifyPost")
	public String modifyPost(@RequestParam("boardId") int boardId, PostVO modifiedPost, Criteria fromUser, RedirectAttributes rttr) {
		modifiedPost.parseAttachInfo();
		if (postService.updatePost(modifiedPost)) {
			rttr.addFlashAttribute("result", "수정처리가 성공");
		}
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
		builder.queryParam("boardId", boardId);
		fromUser.appendQueryParam(builder);
		//게시글의 전체 내용이 바뀌기 보다는 조금의 내용이 바뀌는 것이 수정 행위의 일반적인 경향
		//다만 너무나 대폭적인 수정의 경우 재 검색 하여야 보일 것
		return "redirect:/post/listBySearch" + builder.toUriString();
	}

	/** 게시글 삭제 하기 */
	@PostMapping(value="removePost")
	public String removePost(@RequestParam("boardId") int boardId, 
			@RequestParam("postId") String id, Criteria fromUser, RedirectAttributes rttr) {
		if (postService.deletePostById(id)) {
			rttr.addFlashAttribute("result", "삭제처리가 성공");
		}
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
		builder.queryParam("boardId", boardId);
		fromUser.appendQueryParam(builder);
		return "redirect:/post/listBySearch" + builder.toUriString();
	}
}