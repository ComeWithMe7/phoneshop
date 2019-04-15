<<html>
<head>
    <title>404</title>
</head>
<body>
<h1>${pageContext.response.sendError(404, pageContext.exception.message)}</h1>
</body>
</html>