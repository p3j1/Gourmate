# Gourmate
배달 및 포장 음식 주문 관리 플랫폼

<br/>

## 목차
- [프로젝트 소개](#프로젝트-소개)
- [프로젝트 목적](#프로젝트-목적)
- [팀원 역할 분담](#팀원-역할-분담)
- [서비스 구성 및 실행 방법](#서비스-구성-및-실행-방법)
- [ERD 설계](#erd-설계)
- [기술 스택](#기술-스택)

<br/>


## 프로젝트 소개
Gourmate는 Gourmet와 Mate의 조합으로, 고객이 음식을 쉽게 주문하고 가까운 가게에서 맛있는 음식을 만날 수 있도록 도와주는 동반자 라는 의미를 담은 주문 관리 플랫폼입니다.<br> 
전화 주문 및 수작업 기록 방식에서 벗어나, 앱을 통해 주문을 받고 처리할 수 있도록 하여 업무의 효율성을 극대화하고, 반복적이고 인력이 많이 소요되는 작업을 자동화하는 것을 목표로 합니다.

<br/>

## 프로젝트 목적
- **백엔드 개발 역량 강화**: 기능/비기능 요구사항을 구체화하고 이를 실현
- **협업 경험**: 팀 프로젝트를 통해 팀원들과의 소통 및 협업 역량 강화
- **AI 서비스 연동**: 생성형 인공지능 API를 활용하여 사용자에게 편리성 제공
- **효율적인 주문 관리**: 앱을 통한 실시간 주문 수집과 처리로 기존의 전화 주문 및 수작업 기록 방식을 개선하여, **업무 효율성을 극대화**하고, **자동화된 주문 처리 시스템**을 구축
- **반복 작업 자동화**: 인력이 많이 소요되는 반복적인 업무를 자동화하여 **업무의 오류를 줄이고, 시간을 절약**하며, 운영 효율성을 향상시킬 수 있도록 지원

<br/>




## 팀원 역할 분담
| 이름 | 역할 및 담당 기능|
|------|------|
| 김지수 | 가게, 리뷰, 카테고리 CRUD |
| 연제민 | 유저 CRUD, JWT 인증/인가 | 
| 이서영 | 주문, 주소 CRUD | 
| 이소현 | 메뉴 CRUD, AI API 연동, CD(자동 배포) |


<br/>


## 서비스 구성 및 실행 방법
### 서비스 요구 사항

- Java 17
- Gradle 8.10.2
- PostgreSQL 16.3
- Springboot 3.3.5

### 실행 방법

- **배포 서버 정보**:
    - 퍼블릭 IP 주소: `43.203.124.237`
    - 기본 포트: `8080`
    - 접속 URL: [`http://43.203.124.237:8080`](http://43.203.124.237:8080/)




<br/>


## ERD 설계

<img width="1040" alt="스크린샷 2024-11-18 오후 12 46 01" src="https://github.com/user-attachments/assets/b1fec4c3-4bff-45c0-a67b-a372daa6a4b6">




<br/>
<br/>


## 기술 스택
<div align="center">
    <img src="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white">
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">
    <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens"><br/>
    <img src="https://img.shields.io/badge/Spring Data JPA-gray?style=for-the-badge&logoColor=white"/>
    <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=MySQL&logoColor=white">
      <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=MySQL&logoColor=white"><br/>
</div>

<br/>
<br/>


