Бэкенд для приложения-копии инстаграма.

Текущий сервер: http://138.197.150.20

Api:

Получить список всех сообщений
GET - /all

Получить список всех сообщений с пагинацией (пока что 10 сообщений/стр.)
GET - /page/{pageId}

Получить конкретное сообщение
GET - /message/{messageId}

Загрузка сообщения на сервер
POST - /upload
Параметры - text, file

В HTML выглядит так:
<form method="post" action="/upload" enctype="multipart/form-data">
    <input type="text" name="text" placeholder="text">
    <input type="file" name="file">
    <button type="submit">Добавить</button>
</form>