# 🍽️ Monolithic 기반  서비스 구축 Project

단일 애플리케이션 아키텍처 기반으로 주문 관리 시스템을 구현한 MVP 프로젝트입니다.  
전화 주문 → 앱 기반 실시간 주문 환경으로 변화함에 따라 **효율적인 주문 관리**와 **안정적인 서비스 운영**을 목표로 구축되었습니다.  

---

## 🎯 Goal

- **효율적인 주문 시스템 구축**  
  과거 수기 관리에서 발생하던 **비효율과 오류 문제를 개선**하고,  
  **앱 기반 실시간 주문 시스템**으로 현대적인 서비스 환경을 구현  

- **안정적인 단일 서비스 아키텍처 운영**  
  단일 코드베이스에서 **일관된 로직 관리**와 **빠른 개발 속도** 확보  

---

## 🛠️ Tech Stack

- **프레임워크**: Spring Boot  
- **빌드 도구**: Gradle  
- **라이브러리**: JPA, Spring Security 등  
- **버전 관리**: Git, GitHub  
- **데이터베이스**: PostgreSQL  
- **배포 및 인프라**: AWS EC2, GitHub Actions  

---

## 📊 Performance Test

### 1. 사양 확인

- **Thread (사용자)**: 3,000명  
- **Loop Count**: 30  
  - 각 사용자가 "사용자 조회", "매장 검색" 시나리오를 30번 실행  
- **Ramp-up Time**: 30초  
  - 30초 동안 사용자가 점진적으로 증가 (1초에 100명씩)  
- **총 요청 수**: 180,000  
  - 사용자 조회 90,000, 매장 검색 90,000  

---

### 2. RPS 및 네트워크 대역폭 계산

- **평균 응답 시간**: 6,726 ms (**6.7초**)  
  - 목표: 1~2초 이내 응답 시간  
- **동시 접속자 수**: 3,000명  
- **RPS (초당 요청 수)**: 446 req/sec  
  - 총 요청 수 ÷ 총 소요 시간 = 초당 446개 처리  
- **평균 응답 크기**: 362.5 Byte (≈ 0.000345 MB)  
- **네트워크 대역폭**: 0.153 MB/s  
  - 초당 주고받는 데이터의 양  

---

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
