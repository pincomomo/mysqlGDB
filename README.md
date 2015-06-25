# MySql 그래프 데이터 저장 라이브러리
---
###개발내용
    * 기존 데이터 베이스를 확장하지 않고 그대로 사용하면서 그래프를 저장할 수 있는 라이브러리 
    * 직관도 높은 기호를 사용하는 쿼리 정의
    * 정의 된 쿼리의 INSERT , FROM , SELECT , WHERE 등 특정 구문 이후에 오는 쿼리의 일부분을 MySql에 맞게 처리함 
    * 노드간 관계 표현 쿼리 기호 '->' 사용, 

---

#### Example Query: SELECT egid FROM edges WHERE 1->2->3 AND 4->5 AND 6->7->8
```vim
        Proccessed MySql Query :<br> 
         SELECT jt1.egid<br> 
         FROM edges jt1 <br>
         INNER JOIN edges jt2 ON jt1.egid = jt2.egid <br>
         INNER JOIN edges jt3 ON jt1.egid = jt3.egid <br>
         INNER JOIN edges jt4 ON jt1.egid = jt4.egid <br>
         INNER JOIN edges jt5 ON jt1.egid = jt5.egid <br>
         AND jt1.efrom = 1 AND jt1.eto = 2 <br>
         AND jt2.efrom = 2 AND jt2.eto = 3 <br>
         AND jt3.efrom = 4 AND jt3.eto = 5 <br>
         AND jt4.efrom = 6 AND jt4.eto = 7 <br>
         AND jt5.efrom = 7 AND jt5.eto = 8 <br>
```

####팀원 역할

| 이름   | 역할                                   |
| ------ | -------------------------------------- |
| 공통   | 알고리즘 설계, 구현 (PC 1대)           |
| 고세원 | query processing                       |
| 박준학 | Git 사용 , Test 쿼리문 작성            |
| 이창주 | JDBC + mysql 연동 및 제작 총괄         |
        
        
##### 데모 시연
