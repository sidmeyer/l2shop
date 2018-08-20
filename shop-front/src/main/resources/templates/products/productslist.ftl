<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>L2Shop</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<a href="/">Home</a>
<h1 class="hello-title">Hello, Buyer!</h1>

<h2>Available products:</h2>

<table border="1">
    <thead>
    <td>ID</td>
    <td>Name</td>
    <td>Price, USD</td>
    <td>Image</td>
    </thead>
    <tbody>
    <#list products as product>
        <tr>
            <td>${product.id}</td>
            <td><a href="http://127.0.0.1:8081/products/details?product=${product.id}">${product.name}</a></td>
            <td>${product.price}</td>
            <td><img src="${product.imageUrl}" height="100" /></td>
        </tr>
    </#list>
    </tbody>
</table>

<script src="/js/main.js"></script>
</body>
</html>