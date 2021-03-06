package www.dream.com.hashTag.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HashtagVO {
	private int id;
	private String hashtag;
	private String description;
	
	private int occurCnt; //λΉλμ

	public HashtagVO(int id, String hashtag) {
		this.id = id;
		this.hashtag = hashtag;
	}
}
