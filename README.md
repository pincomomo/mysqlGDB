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
        Proccessed MySql Query :
         SELECT jt1.egid
         FROM edges jt1 
         INNER JOIN edges jt2 ON jt1.egid = jt2.egid 
         INNER JOIN edges jt3 ON jt1.egid = jt3.egid 
         INNER JOIN edges jt4 ON jt1.egid = jt4.egid 
         INNER JOIN edges jt5 ON jt1.egid = jt5.egid 
         AND jt1.efrom = 1 AND jt1.eto = 2 
         AND jt2.efrom = 2 AND jt2.eto = 3 
         AND jt3.efrom = 4 AND jt3.eto = 5 
         AND jt4.efrom = 6 AND jt4.eto = 7 
         AND jt5.efrom = 7 AND jt5.eto = 8 
```

#### DB 구조

##### graphs
 
| gid         | gname                                  |
| ------------|--------------------------------------- |
| graph의 id  | graph의 이름                           |

* Graph(여러 Edge와 Vertex로 이루어져 있음)에 대한 정보를 모아놓은 테이블

##### vertices

| vgid                          | vid          | value       |
| ------------------------------|--------------|-------------|
| vertex가 존재하는 graph의 id  | vertex의 id  | vertex의 값 |

* Graph를 이루는 노드


##### edges

| egid                       | efrom                      | eto                        | value      | 
| ---------------------------|----------------------------|----------------------------|------------| 
| edge가 존재하는 graph의 id | edge의 출발지 vertex의 id  |  edge의 도착지 vertex의 id |  edge의 값 | 

* Graph를 잇는 엣지


#### API

| Class           | Function             | 기능        |
| ----------------|----------------------|------------ |
| QueryProcess    | `Process(String gql)` | 라이브러리에서 제공하는 특화된 쿼리 언어를 MySql 쿼리로 번역함 |
| QueryProcess    | `processArrow(String arrowStatement, Vector<String[]> _values)`| 특화된 쿼리문에서 -> 에 대한 분할작업을 하여 edge의 from과 to 값을 벡터에 저장한다 |
| QueryProcess    | `andProcess(String query, Vector<String[]> _values)`| 특화된 쿼리문에서 AND가 있을 경우에 대한 처리|
| QueryProcess    | `removerow(Vector<String[]> _values, String _from)` | DELETE 쿼리문에서 WHERE에 특화된 쿼리가 있을 시 WHERE절에 대한 처리|
| QueryProcess    | `selectquery(String query)` | SELECT 쿼리문 생성|
| QueryProcess    | `deletequery(String query)` | DELETE 쿼리문 생성|
|<h6>Graph</h6> | `toQuery()`               | 그래프 자료구조를 쿼리에 append 할 수 있도록 스트링 구조로 반환|

####팀원 역할

| 이름   | 역할                                   |
| ------ | -------------------------------------- |
| 공통   | 알고리즘 설계, 구현 (PC 1대)           |
| 고세원 | query processing                       |
| 박준학 | Git 사용 , Test 쿼리문 작성            |
| 이창주 | JDBC + mysql 연동 및 제작 총괄         |
        



#### 데모 시연
