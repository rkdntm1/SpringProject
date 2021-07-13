drop SEQUENCE seq_reply_id;
drop table s_reply;
drop table s_board;

--id, name, description
create table b_board(
	id				number(9) primary key,
	name			varchar2(100),
	description		varchar2(1000),
	reg_dt			timestamp		default sysdate not null,	--등록시점
	upt_dt			timestamp		default sysdate not null
);
insert into b_board(id, name, description)
	values(1, '유저메인페이지', '유저들에게 제공하는 메인페이지');
insert into b_board(id, name, description)
	values(2, '라이더메인페이지', '라이더들에게 제공하는 메인페이지');
insert into b_board(id, name, description)
values(3, '관리자메인페이지', '관리자들에게 제공하는 메인페이지');

CREATE SEQUENCE seq_reply_id;
--id, writer_id, content, reg_dt, upt_dt, descrim, board_id, title, read_cnt, like_cnt, dislike_cnt
create table b_reply(
	id				varchar2(4000)	primary key,
	writer_id		varchar2(10)	REFERENCES s_party(user_id),
	content			varchar2(4000),
	reg_dt			timestamp		default sysdate not null,	--등록시점
	upt_dt			timestamp		default sysdate not null,
	descrim			varchar2(10)	not null,	--reply, post
	--descrim이 post일 경우 사용되는 정보들
	board_id		number(9)		REFERENCES s_board(id),
	title			varchar2(100),
	read_cnt		number(9)		default 0,
	like_cnt		number(9)		default 0,
	dislike_cnt		number(9)		default 0
);
create index index_reply_board_id on b_reply(board_id, id);
drop index index_reply_board_id;
