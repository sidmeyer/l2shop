<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>L2Shop</title>
    <link href="/css/main.css" rel="stylesheet">
    <!--<script src="/jquery-3.3.1.min.js"></script>-->
    <!--<script>-->
    <!--function deleteProduct(id) {-->
        <!--$.ajax({-->
    <!--type: "DELETE",-->
    <!--url: "http://127.0.0.1:8080/products/" + id,-->
    <!--//data: "name=someValue",-->
    <!--success: function(msg){-->
        <!--alert("Data Deleted: " + msg);-->
    <!--}-->
    <!--});-->
    <!--}-->
    <!--</script>-->
</head>
<body>
<a href="/">Home</a><br>
<a href="/products/create">Create new product</a>
<h1 class="hello-title">Hello, Admin!</h1>

<h2>Available products:</h2>

<table border="1">
    <thead>
    <td>ID</td>
    <td>Name</td>
    <td>Price, USD</td>
    <td>Image</td>
    <td>Edit</td>
    <td>Delete</td>
    </thead>
    <tbody>
    <#list products as product>
        <tr>
            <td>${product.id}</td>
            <td><a href="http://127.0.0.1:8081/products/details?product=${product.id}">${product.name}</a></td>
            <td>${product.price}</td>
            <td><img src="${product.imageUrl}" height="100"/></td>
            <td><a href="http://127.0.0.1:8081/products/edit?product=${product.id}">Edit</a></td>
            <td><a href="http://127.0.0.1:8081/products/delete?product=${product.id}">Delete</a></td>
        </tr>
    </#list>
    </tbody>
</table>

<script src="/js/main.js"></script>
</body>
</html>