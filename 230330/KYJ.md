## 1. thymeleaf를 사용하는 이유

템플릿 엔진 : 템플릿을 원하는 대로 모양을 바꿀 수 있게 해줌

```html
// resources/templates/hello-template.html

<html xmlns:th="http://www.thymeleaf.org">
<body>
	<p th:text="'hello ' + ${name}">hello! empty</p>
</body>
</html>
```

해당 파일의 절대 경로(파일 우클릭-copy-absolute path)로 서버 구동 없이 웹페이지 접근 가능 → thymeleaf 기능

그 때 화면에는 `hello! empty` 출력 → 서버 없이 html을 볼 때 사용

템플릿 엔진으로 동작하게 되면 `"'hello ' + ${name}"` 로 출력



## 2. MVC 패턴을 쓰는 이유(장점)

과거 View에서 모든 로직을 처리할 때에는 코드의 길이가 너무 길어지고, 관리가 매우 어려웠음

요청과 응답을 처리하는 부분을 나눠서 처리하면 유지 보수가 쉽고, 비용이 적게 들 수 있음



## 3. cmd에서 서버 실행하는 방법

1. 빌드
   
    1️⃣ 해당 디렉토리에서 ‘git bash’ 실행
    
    2️⃣ `./gradlew`
    
    3️⃣ `gradlew build`
    
2. 실행
   
    1️⃣ `cd build/libs`
    
    2️⃣ `java -jar hello-spring-0.0.1-SNAPSHOT.jar`
    
3. 오류 발생 시
   
    1️⃣ 최상단 디렉토리로 이동 `cd ..`
    
    2️⃣ `./gradew clean build`
    
    2 다시 진행