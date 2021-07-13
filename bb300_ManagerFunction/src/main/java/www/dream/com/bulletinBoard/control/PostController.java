package www.dream.com.bulletinBoard.control;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import www.dream.com.framework.springSecurityAdapter.CustomUser;
import www.dream.com.party.model.Party;

@Controller
@RequestMapping("/post/*")
public class PostController {
	//LRCUD
	@Autowired
	private BoardService boardService;

	@Autowired
	private PostService postService;
	
	/** 특정 게시판에 등록되어 있는 게시글을 목록으로 조회하기 void : /post/list.jsp로 반환 */
	@GetMapping(value="listBySearch")
	public void listPostBySearch(@RequestParam("boardId") int boardId,
			@AuthenticationPrincipal Principal principal,
			@ModelAttribute("pagenation") Criteria userCriteria, Model model) {
		Party curUser = null;
		if(principal != null) {
			UsernamePasswordAuthenticationToken upat=(UsernamePasswordAuthenticationToken) principal;
			CustomUser cu = (CustomUser) upat.getPrincipal();  
			curUser = cu.getCurUser();
		}
		
		model.addAttribute("listPost", postService.getListByHashTag(curUser, boardId, userCriteria));
		model.addAttribute("boardId", boardId);
		model.addAttribute("boardName", boardService.getBoard(boardId).getName());
		userCriteria.setTotal(postService.getSearchTotalCount(boardId, userCriteria));
	}
	
	@Secured("manager")
	@GetMapping(value="listForManager")
	public void listForManager(@RequestParam("boardId") int boardId,
			@AuthenticationPrincipal Principal principal,
			@ModelAttribute("pagenation") Criteria userCriteria, Model model) {
		Party curUser = null;
		if(principal != null) {
			UsernamePasswordAuthenticationToken upat=(UsernamePasswordAuthenticationToken) principal;
			CustomUser cu = (CustomUser) upat.getPrincipal();  
			curUser = cu.getCurUser();
		}
		
		model.addAttribute("listPost", postService.getListByHashTag(curUser, boardId, userCriteria));
		model.addAttribute("boardId", boardId);
		model.addAttribute("boardName", boardService.getBoard(boardId).getName());
		userCriteria.setTotal(postService.getSearchTotalCount(boardId, userCriteria));
	}

	@GetMapping(value={"readPost", "modifyPost"})
	public void findPostById(@RequestParam("boardId") int boardId,
			@RequestParam("postId") String id, @ModelAttribute("pagenation") Criteria fromUser, Model model) {
		model.addAttribute("post", postService.findPostById(id));
		model.addAttribute("boardId", boardId);
	}

	@GetMapping(value="registerPost")
	@PreAuthorize("isAuthenticated()") //현재 사용자가 로그인 처리 했습니까?를 물어봄
	public void registerPost(@RequestParam("boardId") int boardId, Model model) {
		model.addAttribute("boardId", boardId);
	}

	@PostMapping(value="registerPost")
	@PreAuthorize("isAuthenticated()") //현재 사용자가 로그인 처리 했습니까?를 물어봄
	public String registerPost(
			@AuthenticationPrincipal Principal principal,
			@RequestParam("boardId") int boardId, 
			PostVO newPost, RedirectAttributes rttr) {
		newPost.parseAttachInfo();
		BoardVO board = new BoardVO(boardId);
		
		UsernamePasswordAuthenticationToken upat=(UsernamePasswordAuthenticationToken) principal;
		CustomUser cu = (CustomUser) upat.getPrincipal();  
		Party writer = cu.getCurUser();
		
		newPost.setWriter(writer);
		postService.insert(board, newPost);
		
		rttr.addFlashAttribute("result", newPost.getId());
		
		//listBySearch로 목록 조회하면 어떤 단점이 있을까? 검색한 단어와 상관성이 없는 신규 게시글을 볼 수 없다.
		return "redirect:/post/listBySearch?boardId=" + boardId;
	}

	@PostMapping(value="modifyPost")
	@PreAuthorize("principal.username == #writerId")
	public String modifyPost(@RequestParam("boardId") int boardId, PostVO modifiedPost, 
			Criteria fromUser, RedirectAttributes rttr, @RequestParam("writerId") String writerId) {
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
	
	@PostMapping(value="removePost")
	@PreAuthorize("principal.username == #writerId")
	public String removePost(@RequestParam("boardId") int boardId, Criteria fromUser,
			@RequestParam("postId") String id, RedirectAttributes rttr, @RequestParam("writerId") String writerId) {
		if (postService.deletePostById(id)) {
			rttr.addFlashAttribute("result", "삭제처리가 성공");
		}
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
		builder.queryParam("boardId", boardId);
		fromUser.appendQueryParam(builder);
		//게시글의 전체 내용이 바뀌기 보다는 조금의 내용이 바뀌는 것이 수정 행위의 일반적인 경향
		//다만 너무나 대폭적인 수정의 경우 재 검색 하여야 보일 것
		return "redirect:/post/listBySearch" + builder.toUriString();
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="batchDeletePost")
	public String batchDeletePost(@RequestParam("boardId") int boardId, Criteria fromUser,
			@RequestParam("postId") ArrayList<String> postIds, RedirectAttributes rttr) {
		System.out.println(postIds);
		
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
		builder.queryParam("boardId", boardId);
		fromUser.appendQueryParam(builder);
		//게시글의 전체 내용이 바뀌기 보다는 조금의 내용이 바뀌는 것이 수정 행위의 일반적인 경향
		//다만 너무나 대폭적인 수정의 경우 재 검색 하여야 보일 것
		return "redirect:/post/listForManager" + builder.toUriString();
	}
}
