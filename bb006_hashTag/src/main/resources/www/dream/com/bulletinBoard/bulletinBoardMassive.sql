select count(*)
  from s_post;

select *
  from s_post			
			
insert into s_post(id, board_id, writer_id, title, content)
select get_id(seq_post_id.nextval), board_id, writer_id, title, content
  from s_post
  
select *
  from s_post order by id			

select *
  from s_post order by id || 'rrr';
  
  
  
인덱스가 있는 column을 그대로 사용하면 DB가 인덱스를 활용해 주는데
그 값을 강제적으로 바꾸면 인덱스가 활용하지 못하여 내부적으로
모든 데이터를 읽고 DB안의 메모리에서 정렬을 완료한다.
이 때 엄청난 시간이 걸린다.
--============================================================
-- ROWNUM 테이블에서 가져온 데이터를 이용해서 번호를 메기는 방식으로 위의 결과는 테이블에서 가장 먼저가져올 수 있는 데이터들을 꺼내서 번호를 붙여주고있다.
a
b
c

a 1
b 2
c 3

c 1
b 2
a 3 
--============================================================