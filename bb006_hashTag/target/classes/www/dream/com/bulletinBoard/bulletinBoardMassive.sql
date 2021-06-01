select *
  from s_post
  
insert into s_post(id, board_id, writer_id, title, content)			
select get_id(seq_post_id.nextval), board_id, writer_id, title, content
  from s_post

select *
  from s_post order by id;
  
select *
  from s_post order by id || 'rrr';
--인덱스가 있는 컬럼을 그대로 사용하면 DB가 인덱스를 활용 해 주는데, 그 값을 강제적으로 바꾸면 인덱스를 활용하지 못하여
--내부적으로 모든 데이터를 읽고 DB안의 메모리에서 정렬을 완료한다.
--이 때 엄청난 시간이 걸린다.