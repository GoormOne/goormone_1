# monolithic_Architecture
사용자에게 음식점을 탐색하고, 메뉴를 주문하고, 배달 상태를 확인할 수 있는 기능을 제공하는 배달 주문 앱

### 📁 프로젝트 폴더 구조
```
📦 food-delivery-app
├── 📁 src
│   └── 📁 main
│       ├── 📁 java/com/example/goormone
│       │   ├── 📁 admin              # 관리자 기능 (가게 등록 승인, 주문 관리 등)
│       │   │   ├── AdminController.java
│       │   │   ├── AdminService.java
│       │   │   └── Admin.java
│       │   ├── 📁 auth               # 로그인, 회원가입, 토큰 처리
│       │   │   ├── AuthController.java
│       │   │   ├── AuthService.java
│       │   │   ├── JwtProvider.java
│       │   │   └── LoginRequest.java
│       │   ├── 📁 cart               # 장바구니 관련 로직
│       │   │   ├── CartController.java
│       │   │   ├── CartService.java
│       │   │   └── CartItem.java
│       │   ├── 📁 order              # 주문 생성, 상태 변경
│       │   │   ├── OrderController.java
│       │   │   ├── OrderService.java
│       │   │   └── Order.java
│       │   ├── 📁 store              # 매장 및 메뉴 관리
│       │   │   ├── StoreController.java
│       │   │   ├── StoreService.java
│       │   │   ├── Menu.java
│       │   │   └── Store.java
│       │   ├── 📁 user               # 사용자 정보 및 역할 관리
│       │   │   ├── UserController.java
│       │   │   ├── UserService.java
│       │   │   └── User.java
│       │   ├── 📁 common             # 공통 유틸, 예외 처리, 응답 형식 등
│       │   │   ├── BaseResponse.java
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   └── ErrorCode.java
│       │   └── FoodDeliveryApplication.java
│       └── 📁 resources
│           ├── application.yml
│           ├── static/
│           └── templates/
├── 📁 test                             # 테스트 코드
├── .gitignore
├── README.md
└── build.gradle or pom.xml

```
