##### Бэкенд для приложения-копии инстаграма.

Текущий сервер: http://178.128.239.249/  
адрес изображений http://178.128.239.249/картинка.jpg  
ограничение на файл 2MБ

##### Api:

##### Получить список всех сообщений
GET - "/all"

##### Получить список сообщений конкретного юзера  
GET - "/feed/user/{userId}"

##### Получить список сообщений юзера по токену  
GET -  "/feed/user_messages" 
Параматр - token

##### Получить список всех юзеров
GET - "/all_users"  

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
Параметры - text, file, token

В HTML выглядит так (без юзера):
```
<form method="post" action="/upload" enctype="multipart/form-data">
    <input type="text" name="text" placeholder="text">
    <input type="file" name="file">
    <button type="submit">Добавить</button>
</form>
```

Все изображения находятся по адресу /images/название.jpg

##### User
`регистрация пользователя:`\
POST запрос на /register\
json формата:
```
{
  "name": "Пётр Петрович",
  "email": "pochta@gmail.com",
  "password": "12345678",
  "confirmPassword": "12345678"
}
```
статус ответа - ок - успешно (текст ответа: pochta@gmail.com activated)

`получения token'а (для добавления/обновления и тп.)`\
POST запрос на /login\
json формата:
```
{
  "email": "pochta@gmail.com",
  "password": "12345678"
}
```
ответ необходим для дальнейших операций, пример:\
{ "value": "07a98826-7f59-472a-8be0-a8a234fa87d0" }\
`потверждение аккаунта:`\
POST запрос на /confirm\
ОБЯЗАТЕЛНЫЙ ПАРАМЕТР: ?token=ТОКЕН_ИЗ_ПИСЬМА(после регистрации, врменно отключено)\
пример: /confirm?token=bd84e187-ef26-493b-b90d-9983d588b1df\
`запрос смены пароля:`\
POST запрос на /forgot\
ОБЯЗАТЕЛНЫЙ ПАРАМЕТР: ?email=ПОЧТА\
отправлят ключ для смены пароля (временно отключено)\
`смена пароля:`\
POST запрос на /reset\
ОБЯЗАТЕЛНЫЙ ПАРАМЕТРЫ:
token=ТОКЕН_ИЗ_ПИСЬМА(после пред. запроса, врменно отключено)\
и новый пароль в JSON: { "password":"qwerty123", "confirmPassword":"qwerty123" }


##### Profile
`получить профиль текущего пользователя (по токену) :`  
GET - "/user/profile?token={VALUE}"  
`получить профиль пользователя по userId :`  
GET - "/user/profile?userId={ID}"  
`изменить профиль текущего пользователя (по токену) :`  
PUT - "/user/profile?token={VALUE}"  
```
{
  "name" : "{NEW_NAME}"
}
```  
в ответ приходит обновленный профиль  
`получить аватар текущего пользователя (по токену) :`  
GET - "/user/avatar?token={VALUE}"  
`получить аватар пользователя по userId :`  
GET - "/user/avatar?userId={ID}"  
`изменить автар текущего пользователя (по токену) :`  
PUT - "/user/avatar?token={VALUE}"  
Content-Type : multipart/form-data  
параметр - file  
в ответ приходит обновленный аватар


##### Комментарии
`добавить комментарий :`  
POST - "/message/{MESSAGE_ID}/comment?token={VALUE}" 
```
{
  "text" : "{SOME_TEXT}"
}
```  
в ответ приходит обновленный список комментариев


##### Лайки  
POST - "/like/{MESSAGE_ID}?token={VALUE}"  
в ответ приходит актуальный список лайкнувших юзеров для данного сообщения


##### Подписки
`подписаться на пользователя :`  
POST - "/follow/users/{USER_ID}"  
возвращает актуальный список id пользователей на которых подписан  
пример: /follow/users/21?token=1ad04569-3974-42ee-826c-562e977facc4  
`получить список id пользователей на которых подписан :`  
GET - "/follow/users"  
пример: /follow/users?token=1ad04569-3974-42ee-826c-562e977facc4  
`получить ленту подписок с пагинацией текущего пользователя :`  
GET - "/follow/messages"  
дополнительный параметр page, значение по умолчанию 1  
В хедере возвращает:  
totalMessages  
totalPages  
currentPage  
пример: /follow/messages?page=1&token=1ad04569-3974-42ee-826c-562e977facc4
