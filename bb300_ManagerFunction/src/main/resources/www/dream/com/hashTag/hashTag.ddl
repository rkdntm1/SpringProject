drop table bm_ht2StringId;
drop table b_hashtag;

truncate table bm_ht2post;

drop SEQUENCE seq_hashtag_id;

CREATE SEQUENCE seq_hashtag_id;

--	id, super_id, hashtag, description
create table b_hashtag(
	id				number(9) primary key,
	super_id		number(9) references s_hashtag(id), 
	hashtag			varchar2(100),
	description		varchar2(1000)
);
--단어 중복 방지 용도
create unique index uidx_hashtag on b_hashtag(hashtag);

--	hashtag_id, post_id, occur_cnt
create table bm_ht2StringId(
	hashtag_id		number(9),
	opponent_type	varchar2(50), --Post,Party
	opponent_id 		varchar2(4000),
	occur_cnt		number(9),
	primary key(hashtag_id, opponent_type, opponent_id)
);


create unique index idx_fromOpponent on bm_ht2StringId(opponent_type, opponent_id, hashtag_id);

--개인화 서비스. Personalization
create table sm_ht2NumberId(
	hashtag_id		number(9),
	opponent_type	varchar2(50), --Product 
	opponent_id 	number(20),
	occur_cnt		number(9),
	--최종 검색 활용 시점
	latest_use_time timestamp		default sysdate not null,
	primary key(user_id, opponent_type, opponent_id)
);

--sequence를 활용하여 원하는 개수 만큼 숫자형 id 만들어 내기    5,6,7
CREATE OR REPLACE FUNCTION genMultiId(cnt number) RETURN varchar2
IS
	seq_val number(9);
	strRet varchar2(4000) := '';
BEGIN
	FOR i in 1..cnt LOOP
		select seq_hashtag_id.nextval into seq_val from dual;
		strRet := strRet || ',' || seq_val;
	END LOOP;

    RETURN ltrim(strRet, ',');
END;

select genMultiId(5) from dual;
