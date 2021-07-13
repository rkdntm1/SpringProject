package www.dream.com.common.attachFile.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.dream.com.common.attachFile.model.AttachFileVO;

public interface AttachFileVOMapper {
	//단건 처리를 여러번 수행하기 보다는 한번에 여러건을 처리 하여 DB와의 통신 횟수를 줄여서 성능을 높임
	public int insert(@Param("postId") String postId, @Param("listAttachFile") List<AttachFileVO> listAttachFile);
	
	//게시글 삭제 또는 첨부 정보를 수정 시 기존 내용을 전체적으로 삭제하고 남은 정보를 insert를 통하여 저장 
	public int delete(@Param("postId") String postId);
	
}
