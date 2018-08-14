##### Бэкенд для приложения-копии инстаграма.

Текущий сервер: http://138.197.150.20

##### Api:

##### Получить список всех сообщений
GET - "/all"

##### Получить список всех сообщений с пагинацией (пока что 10 сообщений/стр.)
GET - "/"  
Параматр - page (номер страницы)

Возвращает список на данной странице (по дефолту 1-ая страница). Отсортировано по дате: сначала новые

В хедере возвращает:  
totalMessages  
totalPages  
currentPage

##### Получить конкретное сообщение
GET - "/message"  
Параметр - messageId

##### Отредактировать текст сообщения
POST - "/edit"  
Параметры - messageId, text

Возвращает отредактированное сообщение

##### Удаление сообщения
POST - "/delete"  
Параметр - messageId

##### Загрузка сообщения на сервер
POST - "/upload"  
Параметры - text, file

В HTML выглядит так:
```
<form method="post" action="/upload" enctype="multipart/form-data">
    <input type="text" name="text" placeholder="text">
    <input type="file" name="file">
    <button type="submit">Добавить</button>
</form>
```

Все изображения находятся по адресу /images/название.jpg