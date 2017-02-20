<html>
    <head>
        <title>Load data</title>
    </head>
    <body>
        <h1>Load data</h1>
        <form action="/api/load" method="post" enctype="multipart/form-data">
            Users to upload: <input type="file" name="users" accept=".json">
            </br>
            Events to upload: <input type="file" name="events">
            </br>
            <button type="submit">Upload data</button>
            </br>
        </form>
    </body>
</html>