тестовое задание Буртовой Ян, skype yanchik366.

Eсли используется nginx то настроить распределение запросов по ip.

Eсли jee тогда вместо ConcurrentHashMap redis.

# Задание 1 

### Цель: проверить навыки написания алгоритмов и навык владения Java SDK и библиотеками Spring, Spring Boot

##### Описание:
* написать spring-boot приложение, которое будет содержать 1 контроллер с одним методом, который возвращает HTTP 200 и пустую строку.

* Написать функционал, который будет ограничивать количество запросов с одного IP адреса на этот метод в размере 50 штук в минуту. Если количество запросов больше, то должен возвращаться 502 код ошибки, до тех пор, пока количество обращений за последнюю минуту не станет ниже 50.

* Сделать так, чтобы это ограничение можно было применять быстро к новым методам и не только к контроллерам, а также к методам классов сервисного слоя.

* Реализация должна учитывать многопоточную высоконагруженную среду исполнения и потреблять как можно меньше ресурсов.

* Проект должен собираться при помощи maven командой mvn clean package и запускаться командой java -jar test-1.jar. Порт приложения должен быть 8080.

* Использовать Java 8 & maven 3.

* Не использовать сторонних библиотек для троттлинга.
