package www.dream.com.common.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;
import www.dream.com.framework.langPosAnalyzer.HashTarget;
import www.dream.com.framework.printer.PrintTarget;

/**
 * 공통 관리 정보
 * @author YongHoon Kim
 */
@Data
public abstract class CommonMngVO {
	private Date registrationDate; //등록 시점
	@HashTarget @PrintTarget(order=400, caption="수정일", pattern="yyyy-MM-dd")
	private Date updateDate; //수정시점
	private String formattedUpdateDate;
	
	public String getFormattedUpdateDate() {
		if (updateDate != null) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			return fmt.format(updateDate);	
		}
		return "";
	}
	@Override
	public String toString() {
		return "CommonMngVO[등록일=" + registrationDate + ", 수정일=" + updateDate + "]";
	} 
}
