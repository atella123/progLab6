# Лабораторная работа #6

## Вариант 3228 ✔️

Разделить программу из лабораторной работы №5 на клиентский и серверный модули. Серверный модуль должен осуществлять выполнение команд по управлению коллекцией. Клиентский модуль должен в интерактивном режиме считывать команды, передавать их для выполнения на сервер и выводить результаты выполнения.

__Необходимо выполнить следующие требования:__

+ Операции обработки объектов коллекции должны быть реализованы с помощью __Stream API__ с использованием лямбда-выражений.
+ Объекты между клиентом и сервером должны передаваться в сериализованном виде.
+ Объекты в коллекции, передаваемой клиенту, должны быть отсортированы по названию
+ Клиент должен корректно обрабатывать временную недоступность сервера.
+ Обмен данными между клиентом и сервером должен осуществляться по протоколу __UDP__
+ Для обмена данными на сервере необходимо использовать __сетевой канал__
+ Для обмена данными на клиенте необходимо использовать __датаграммы__
+ Сетевые каналы должны использоваться в неблокирующем режиме.

__Обязанности серверного приложения:__

+ Работа с файлом, хранящим коллекцию.
+ Управление коллекцией объектов.
+ Назначение автоматически генерируемых полей объектов в коллекции.
+ Ожидание подключений и запросов от клиента.
+ Обработка полученных запросов (команд).
+ Сохранение коллекции в файл при завершении работы приложения.
+ Сохранение коллекции в файл при исполнении специальной команды, доступной только серверу (клиент такую команду отправить не может).
+ Серверное приложение должно состоять из следующих модулей (реализованных в виде одного или нескольких классов):
+ Модуль приёма подключений.
+ Модуль чтения запроса.
+ Модуль обработки полученных команд.
+ Модуль отправки ответов клиенту.
+ Сервер должен работать в однопоточном режиме.

__Обязанности клиентского приложения:__

+ Чтение команд из консоли.
+ Валидация вводимых данных.
+ Сериализация введённой команды и её аргументов.
+ Отправка полученной команды и её аргументов на сервер.
+ Обработка ответа от сервера (вывод результата исполнения команды в консоль).
+ Команду `save` из клиентского приложения необходимо убрать.
+ Команда `exit` завершает работу клиентского приложения.

__Важно!__ Команды и их аргументы должны представлять из себя объекты классов. Недопустим обмен "простыми" строками. Так, для команды `add` или её аналога необходимо сформировать объект, содержащий тип команды и объект, который должен храниться в вашей коллекции.

__Дополнительное задание:__

+ Реализовать логирование различных этапов работы сервера (начало работы, получение нового подключения, получение нового запроса, отправка ответа и т.п.) с помощью __Log4J2__
