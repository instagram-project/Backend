<html>
<body>
<h4>Добавить изображение и текст</h4>
<form method="post" action="/savemessage" enctype="multipart/form-data">
    <input type="text" class="form-control" name="text" placeholder="text">
    <input type="file" name="file">
    <button class="btn btn-dark" type="submit">Добавить</button>
</form>
<br>

<ul class="list-group mt-3">
    <#if messages??>
<#list messages as message>
    <li class="list-group-item">
        <#if message.filename??>
            <p><img width="100px" src="/images/${message.filename}"></p>
        </#if>

        <p>${message.text}</p>
    </li>
</#list>
    </#if>
</ul>

</body>
</html>