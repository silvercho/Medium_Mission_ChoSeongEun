## 요청 종류별 추천하는 HTTP 메서드 
- POST :생성
  - 회원가입
  - 글쓰기
  - 댓글쓰기
  - 로그인
    - 로그인을 하면 세션이 생성
  - 로그아웃
- PUT(PATCH) :수정
  - 글 수정
  - 댓글 수정
- DELETE : 삭제
  - 글 삭제
  - 댓글 삭제
  - 추천취소
- GET : 나머지

## 어플리케이션 형태에 따른 HTTP 메서드 활용 
MPA : 타임리프 , JSP 
    - POST,GET 
SPA : 리액트, 뷰 , 앵귤러 , 스벨트

# 할일

- [x] GET /member/join : 가입 폼
- [x] POST /member/join : 가입 폼 처리
- [x] GET /member/login : 로그인 폼
  [x] POST /member/login : 로그인 폼 처리
- [x] POST /member/logout : 로그아웃

## 글 작업

- [x] GET / : 홈
  - 최신글 30개 노출
- [x] GET /post/list : 전체 글 리스트
  - 공개된 글만 노출
- [x] GET /post/myList : 내 글 리스트
  - 내 글 목록 조회
- [x] GET /post/1 : 1번 글 상세보기
- [x] GET /post/write : 글 작성 폼
- [x] POST /post/write : 글 작성 처리
- [x] GET /post/1/modify : 1번 글 수정 폼
- [x] PUT /post/1/modify : 1번 글 수정 폼 처리
- [x] DELETE /post/1/delete : 1번 글 삭제

## 블로그 작업

- [x] GET /b/user1 : 회원 user1 의 전체 글 리스트
  - 특정 회원의 글 모아보기
- [x] GET /b/user1/3 : 회원 user1 의 글 중에서 3번글 상세보기

## 추가 작업
- [x] 조회수
- [x] 추천수
