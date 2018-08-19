<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<a href="/">Home</a><br>
<a href="/products/management">Back to products management</a>
<h1 class="hello-title">Hello, Buyer!!!</h1>

<h2>Edit product ${product.name}</h2>

<@spring.bind "product"/>

<form action="/products/edit" method="post">
    Id:<br>
    <@spring.formInput "product.id"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Name:<br>
    <@spring.formInput "product.name"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Price:<br>
    <@spring.formInput "product.price"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Image URL:<br>
    <@spring.formInput "product.imageUrl"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    <input type="submit" value="Submit">
</form>

<br>
<h2>Product categories:</h2>
<br>
<table border="1">
    <thead>
    <td>ID</td>
    <td>Name</td>
    </thead>
    <tbody>
    <#list categories as category>
        <tr>
            <td>${category.id}</td>
            <td>${category.name}</td>
        </tr>
    </#list>
    </tbody>
</table>

<script src="/js/main.js"></script>
</body>
</html>