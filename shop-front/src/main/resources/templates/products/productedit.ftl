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

<h2>Edit product ${product.name}</h2>
<img src="${product.imageUrl}" height="100">

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
<table border="1">
    <thead>
    <td>ID</td>
    <td>Name</td>
    <td>Delete</td>
    </thead>
    <tbody>
    <#list categories as category>
    <tr>
        <td>${category.id}</td>
        <td>${category.name}</td>
        <td><a href="/products/deletecategory?product=${product.id}&category=${category.id}">Delete</a></td>
    </tr>
    </#list>
    </tbody>
</table>
<br>
<h2>Other categories:</h2>
<table border="1">
    <thead>
    <td>ID</td>
    <td>Name</td>
    <td>Add</td>
    </thead>
    <tbody>
    <#list otherCategories as otherCategory>
    <tr>
        <td>${otherCategory.id}</td>
        <td>${otherCategory.name}</td>
        <td><a href="/products/addcategory?product=${product.id}&category=${otherCategory.id}">Add</a></td>
    </tr>
    </#list>
    </tbody>
</table>


<script src="/js/main.js"></script>
</body>
</html>