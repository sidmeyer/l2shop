<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>L2Shop</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<a href="/">Home</a><br>
<a href="/products/management">Back to products management</a>
<h1 class="hello-title">Hello, Admin!</h1>

<h2>Create new product</h2>

<@spring.bind "product"/>

<form action="/products/create" method="post">
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

<script src="/js/main.js"></script>
</body>
</html>