<html>
<body>
<h4>Добавить изображение и текст</h4>
<form method="post" action="/savemessage" enctype="multipart/form-data">
    <input type="text" class="form-control" name="text" placeholder="text">
    <input type="file" name="file">
    <button class="btn btn-dark" type="submit">Добавить</button>
</form>
<br>

<ul class="list-group mt-3" style="width: 400px; float: left">
    <#if messages??>
<#list messages as message>
    <li class="list-group-item">
        <#if message.filename??>
            <p><img width="100px" src="/${message.filename}"></p>
        </#if>

        <p>${message.text}</p>
    </li>
</#list>
    </#if>
</ul>

<ul class="list-group mt-3" style="width: 400px; float: left">
    <p>Список юзеров:</p>
    <#if users??>
<#list users as user>
    <li class="list-group-item">
        <#if user.id??>
            <p>id: ${user.id}</p>
        </#if>

         <#if user.email??>
            <p>email: ${user.email}</p>
         </#if>

         <#if user.name??>
            <p>name: ${user.name}</p>
         </#if>

         <#if user.role??>
            <p>role: ${user.role}</p>
         </#if>

        <#if user.state??>
            <p>state: ${user.state}</p>
        </#if>
    </li>
</#list>
    </#if>
</ul>

</body>
</html>