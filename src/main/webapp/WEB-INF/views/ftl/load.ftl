<html>
    <head>
        <title>Load data</title>
    </head>
    <body>
        <h1>Load data</h1>
        <form action="/api/load?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
            Users to upload: <input type="file" name="users" accept=".json">
            </br>
            Events to upload: <input type="file" name="events">
            </br>
            <button type="submit">Upload data</button>
            </br>
        </form>
        <form action="/logout" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </body>
</html>