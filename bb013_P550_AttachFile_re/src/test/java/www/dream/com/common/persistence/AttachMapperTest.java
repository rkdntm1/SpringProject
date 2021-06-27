package www.dream.com.common.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.common.attachFile.model.AttachFileVO;
import www.dream.com.common.attachFile.model.MultimediaType;
import www.dream.com.common.attachFile.persistence.AttachFileVOMapper;
 
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml") //여기에 들어있는정보를바탕으로 객체를 만들어라 junit아
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AttachMapperTest {
	@Autowired
	private AttachFileVOMapper attachMapper;
	
	@Test
	public void test200Insert() {
		String postId = "TTTT3";
		List<AttachFileVO> list = new ArrayList<>();
		AttachFileVO avo = new AttachFileVO();
		avo.setUuid(UUID.randomUUID().toString());
		avo.setSavedFolderPath("ddd");
		avo.setPureFileName("hihi");
		avo.setMultimediaType(MultimediaType.image);
		list.add(avo);
		
		avo = new AttachFileVO();
		avo.setUuid(UUID.randomUUID().toString());
		avo.setSavedFolderPath("eee");
		avo.setPureFileName("bibi");
		avo.setMultimediaType(MultimediaType.video);
		list.add(avo);
		
		attachMapper.insert(postId, list);
	}
	
	@Test
	public void test400Delete() {
		String postId = "TTTT1";
		attachMapper.delete(postId);
	}

}
