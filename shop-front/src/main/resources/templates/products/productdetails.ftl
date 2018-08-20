<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>L2Shop</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<a href="/">Home</a><br>
<a href="/products/list">Go to products list</a>
<h1 class="hello-title">Hello, Admin!</h1>

<h2>Product details:</h2>

<table border="1">
    <thead>
    <td>ID</td>
    <td>Name</td>
    <td>Price, USD</td>
    <td>Image</td>
    </thead>
    <tbody>
    <tr>
        <td>${product.id}</td>
        <td>${product.name}</td>
        <td>${product.price}</td>
        <td><img src="${product.imageUrl}" height="100"/></td>
    </tr>
    </tbody>
</table>
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