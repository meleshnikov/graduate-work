# Дипломный проект
## Платформа по перепродаже вещей

***

<p align="center">
<img src="https://www.pioneerdrama.com/Images/Title_Art/TRIO.png" width="200" alt="Shelter Logo">
</p>

***

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

***

### Проект написан командой "Trio" курса "SkyPro Java-developer" в составе:
* Андрей Мелешников ([GitHub](https://github.com/meleshnikov))
* Олеся Петрова ([GitHub](https://github.com/olxandro))
* Верещагин Михаил ([GitHub]())
***

***

**Задачей команды было написать бекенд-часть сайта на Java для готовой фронтенд части и реализовать следующий функционал:**
* Авторизация и аутентификация пользователей.
* Распределение ролей между пользователями: пользователь и администратор.
* CRUD для объявлений на сайте: администратор может удалять или редактировать все объявления, а пользователи — только свои.
* Под каждым объявлением пользователи могут оставлять отзывы.
* В заголовке сайта можно осуществлять поиск объявлений по названию.
* Показывать и сохранять картинки объявлений.
### Стек технологий
***
**В проекте используются**:

* Backend:
    - Java 17
    - Maven
    - Spring Boot
    - Spring Web
    - Spring Data
    - Spring JPA
    - Spring Security
    - GIT
    - REST
    - Swagger
    - Lombok
    - Stream API
* SQL:
    - PostgreSQL
    - Liquibase
* Frontend:
    - Docker образ

***

### Запуск
***

**Для запуска нужно:**
- Клонировать проект в среду разработки
- Прописать properties в файле **[application.properties](src/main/resources/application.properties)**
- Запустить **[Docker](https://www.docker.com)**
- Запустить **[Docker образ](https://drive.google.com/file/d/1ZoGOJaHidywKNYlvNuz6kb0KoGPbeC_b/view)**
- Запустить метод **main** в файле **[HomeworkApplication.java](src/main/java/ru/skypro/homework/HomeworkApplication.java)**

После выполнения всех действий сайт будет доступен по ссылке http://localhost:3000 и Swagger по [ссылке](http://localhost:8080/swagger-ui/index.html#).