# java-filmorate
ER-диаграмма:
![ER-диаграмма](Filmorate.jpg)
Основные запросы. 
1) Показать список всех фильмов.

SELECT *

FROM FILM;
2) Получение конкретного юзера по ID

SELECT NAME AS name

FROM USERS

WHERE USER_ID = <тут номер ID>

3) Получить топ-10 фильмов.

SELECT f.FILM_NAME AS name,

COUNT(l.USER_ID) AS likes

FROM FILM AS f

LEFT JOIN FILM_USER_LIKES AS l ON f.FILM_ID=l.FILM_ID

GROUP BY name

ORDER BY likes DESC

LIMIT_10;

