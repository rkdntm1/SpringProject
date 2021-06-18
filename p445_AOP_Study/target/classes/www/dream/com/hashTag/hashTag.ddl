drop table sm_ht2post;
drop table s_hashtag;

truncate table sm_ht2post;

drop SEQUENCE seq_hashtag_id;


CREATE SEQUENCE seq_hashtag_id;

--단어집
--id, super_id, hashtag, description
create table s_hashtag( 
	id			number(9)	primary key,
	--추상 개념으로 통합 검색에서 활용하기 위하여
	super_id	number(9)	references s_hashtag(id),
	hashtag		varchar2(100),
	description	varchar2(1000)
);
-- 단어 중복 방지 용도
create unique index uidx_hashtag on s_hashtag(hashtag); 

--게시글에서 나타나는 단어들
--hashtag_id, post_id, occur_cnt
create table sm_ht2post(
	hashtag_id		number(9),
	post_id			varchar2(4000),
	occur_cnt		number(9),
	primary key(hashtag_id, post_id)
);

--개인화 서비스(Personalization) : 사용자가 관심있는 정보 
create table sm_ht2party(
	hashtag_id		number(9),
	user_id			varchar2(10),
	occur_cnt		number(9), --활용횟수
	--최종 검색 활용 시점(최신 정보를 기준으로 관심도 설정)
	latest_use_time	timestamp		default sysdate not null 
	primary key(user_id, hashtag_id) -- 순서중요 -> (카드에서) 무늬 숫자 ? 숫자 무늬 ? => 개인화서비스니깐 user_id가 앞에 선다.  
);

--sequence를 활용하여 원하는 개수 만큼 숫자형 id 만들어내기
CREATE OR REPLACE FUNCTION genMultiId(cnt number) RETURN varchar2
IS
	seq_val number(9);
	strRet varchar2(4000) := '';
BEGIN
	FOR i in 1..cnt LOOP
		select seq_hashtag_id.nextval into seq_val from dual;
		strRet := strRet || ',' || seq_val; -- :=는 할당연산자
	END LOOP;
	
	RETURN ltrim(strRet, ',');
END;

select genMultiId(5) from dual