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
- [ ] POST /member/logout : 로그아웃 