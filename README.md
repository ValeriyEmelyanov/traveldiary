# Travel diary

Training project.

In the traveler's diary, the user keeps records of their exciting trips and travels:
 - travel planning: when, where, what expenses are planned, notes,
 - a reflection of the fact: the actual date and cost, impressions and notes on memory,
 - you can cancel a scheduled train (without deleting it).
You can also read / learn from the travel experience of other users.

В дневнике путешественника пользователь ведет записи о своих увлекательных поездках и путешествиях:
 - планирование путешествия: когда, куда, какие планируются расходы, заметочки,
 - отражение факта: фактические даты и расходы, впечатления и заметки на память,
 - можно отменить запланированную поезду (не удаляя).
Так же можно почитать / перенять опыт путешествий других пользователей.

#### Tools and Technologies Used

* Java 11
* REST API
* Spring Boot
* Spring Data JPA
* Spring Security + JWT
* Spring AOP
* PostgreSQL 12
* Flyway
* Lombok
* SpringDoc & OpenAPI 3.0 & Swagger
* Maven
* JUnit 5
* Mockito
* Hamcrest

#### Roles and authorities

ADMIN - manages the list of users. It does not have access to reference and user information.
SENIOR - manages reference information.
USER - keeps records in his diary, can view the diaries of other users.

A user can have multiple roles. A user with any role can change their password.

ADMIN - управляет списком пользователей. Доступа к справочной и пользовательской информации не имеет.
SENIOR - управляет справочной информацией.
USER - ведет записи в своем дневнике, может просматривать дневники других пользователей.

Пользователь может иметь несколько ролей. Пользователь с любой ролью может изменить свой пароль.
