package www.dream.com.common.model;

import java.util.Date;

import lombok.Data;

/**
 * 공통 관리 정보
 * @author YongHoon Kim
 */
@Data
public abstract class CommonMngVO {
	private Date registrationDate; //등록 시점 
	private Date updateDate; //수정시점
}
