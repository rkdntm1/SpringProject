package www.dream.com.bulletinBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.persistence.PostMapper;

@Service
public class PostService {
	@Autowired
	private PostMapper postMapper;

	// LRCUD
	/*
	 * mapper 함수의 인자 개수가 여러개 일 경우 필수적으로 @Param을 넣야합니다. 이를 실수하지 않기 위하여 한개여도 그냥 명시적으로
	 * 넣어주자
	 */
	public List<PostVO> getList(int boardId) {
		return postMapper.getList(boardId);
	}

	/** id 값으로 Post 객체 조회 */
	public PostVO findPostById(String id) {
		return postMapper.findPostById(id);
	}

	public int insert(BoardVO board, PostVO post) {
		return postMapper.insert(board, post);
	}

	/** 게시글 수정 처리 */
	public int updatePost(PostVO post) {
		return postMapper.updatePost(post);
	}

	/** id 값으로 Post 객체 삭제 */
	public boolean deletePostById(String id) {
		return postMapper.deletePostById(id) == 1;
	}
}
