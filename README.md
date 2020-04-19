# todo-api : Java, Springboot, JPA

1. application.properties 파일 확인
    - default port : 8081
    - default database : 해당 프로젝트 내 kakaoix_dhyoon.mv.db(h2)
2. TodoApiApplication 실행


#todo-web : vue, typescript, vuetify
1. npm install
or
yarn 실행
2. api서버 포트번호 변경 시 vue.config.js에서 api포트번호로 변경(default 8081)
3. npm run serve or yarn serve 실행
4. http://localhost:8080 접속

#기능 요구 사항

- 사용자는 문자열로 된 todo 항목을 추가 할 수 있다. : 완료 
- todo는 다른 todo들을 참조할 수 있다. : 완료
- 사용자는 todo 목록을 조회할 수 있다. : 완료
- 작성 일자, 최종 수정 일자, 내용, 참조하고 있는 todo들의 id가 표시되어야 한다. (예시 참고) : 완료
- 페이지 네이션 구현 하면 가산점 : 완료
- 사용자는 todo를 수정할 수 있다. : 완료
- 사용자는 todo를 삭제할 수 있다. : 완료
- 사용자는 todo를 완료 또는 미완료로 상태변경을 할 수 있다. : 완료
- 참조하고 있는 todo들이 모두 완료 상태가 아니라면 todo를 완료할 수 없다. : 완료