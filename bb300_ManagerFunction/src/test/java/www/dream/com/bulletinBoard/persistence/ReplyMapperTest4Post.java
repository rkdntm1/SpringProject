package www.dream.com.bulletinBoard.persistence;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.common.dto.Criteria;
import www.dream.com.party.model.Admin;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReplyMapperTest4Post {
	@Autowired
	private ReplyMapper postMapper;

	//@Test
	public void test000InsertPost() {
		try {
			BoardVO commNotice = new BoardVO(1);
			PostVO post = new PostVO("아름다운 강산", "제3공화국 시절, 지금은 한국 록의 대부로 일컬어지는 신중현에게 당시 정권이었던 박정희를 찬양하는 노래를 만들라고 꾸준히 강요했다", new Admin("admin"));
			postMapper.insert(commNotice, post);
			System.out.println("지금 만든 객체의 아이디는 " + post.getId() + "책 기준 191쪽 참조");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void test010DeleteById() {
		try {
			System.out.println(postMapper.deleteReplyById("00003"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void test040GetList() {
		assertNotNull(postMapper);
		try {
			postMapper.getList(1, new Criteria()).forEach(post->{
				System.out.println(post);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void test020UpdateById() {
		try {
			PostVO post = (PostVO) postMapper.findReplyById("031WK");
			if (post != null) {
				post.setTitle(post.getTitle() + "1");
				postMapper.updatePost(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test030FindById() {
		try {
			ReplyVO post = postMapper.findReplyById("00002");
			System.out.println(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
