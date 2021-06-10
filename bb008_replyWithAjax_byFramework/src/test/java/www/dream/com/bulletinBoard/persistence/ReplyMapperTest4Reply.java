package www.dream.com.bulletinBoard.persistence;

import static org.junit.Assert.assertNotNull;

import java.util.stream.IntStream;

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
import www.dream.com.party.model.User;
 
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml") //여기에 들어있는정보를바탕으로 객체를 만들어라 junit아
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReplyMapperTest4Reply {
	@Autowired
	private ReplyMapper replyMapper;
	@Autowired
	private ReplyMapper postMapper;
	
	@Test
	public void test000InsertReply() {
		try {
			//자게 최신글 찾아서 original(원글)로 삼자
			ReplyVO original = postMapper.getList(3, new Criteria()).get(0);
			//댓글 아무렇게나 만들어서 insert
			User lee = new User("lee");
			IntStream.rangeClosed(0, 1).forEach(i -> {
				ReplyVO reply = new ReplyVO("content" + i, lee);
				replyMapper.insertReply(original.getId(), reply);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test002InsertReplyOfReply() {
		try {
			//자게 최신글 찾아서 original(원글)로 삼자
			ReplyVO original = postMapper.findReplyById("0000500006");
			//댓글 아무렇게나 만들어서 insert
			User lee = new User("lee");
			IntStream.rangeClosed(0, 1).forEach(i -> {
				ReplyVO reply = new ReplyVO("content" + i, lee);
				replyMapper.insertReply("0000500006", reply);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test030FindById() {
		try {
			ReplyVO post = postMapper.findReplyById("0000500006");
			System.out.println(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test040GetList() {
		assertNotNull(replyMapper);
		try {
			replyMapper.getReplyListWithPaging("00005", "00005".length() + ReplyVO.ID_LENGTH, new Criteria()).forEach(reply->{
				System.out.println(reply);
			});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
