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
import www.dream.com.common.dto.Criteria;
import www.dream.com.party.model.Admin;
 
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml") //여기에 들어있는정보를바탕으로 객체를 만들어라 junit아
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostMapperTest {
	@Autowired
	private PostMapper postMapper;
	
	@Test
	public void test000InsertPost() {
		try {
			BoardVO commNotice = new BoardVO(1);
			PostVO post = new PostVO("테스트2", "게시글 테스트", new Admin("admin"));
			postMapper.insert(commNotice, post);
			//insert()문에서는 추가된 데이터의 PK값을 알 수 없다. -> 그래서 ->insertSelectKey()를 사용한다. p191참조
			System.out.println("지금 만든 객체의 ID는 " + post.getId()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test010DeleteById() {
		try {
			System.out.println(postMapper.deletePostById("00003"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test040GetList() {
		assertNotNull(postMapper);
		try {
			postMapper.getList(1, new Criteria()).forEach(post->{System.out.println(post);});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test020UpdateById() {
		try {
			PostVO post = postMapper.findPostById("04OlJ");
			if (post != null) {
				post.setTitle(post.getTitle() + "업데이트 v1");
				postMapper.updatePost(post);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test030FindById() {
		try {
			System.out.println(postMapper.findPostById("00003"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
