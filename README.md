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


# 1. Member 클래스에 `private boolean isPaid` 필드를 추가 
# 2. Post 클래스에 `private boolean isPaid` 필드를 추가 
# 3. NotProd 에서 유료멤버십 회원(샘플데이터) 와 유료글(샘플데이터) 100개 생성

## 카카오 로그인 / 토스 페이먼츠 yml 연결(secret code)
- [x] 카카오 로그인 
- [x] 토스페이먼츠

## 토스트 UI 에디터 적용
- [ ] 마크다운 에디터 
