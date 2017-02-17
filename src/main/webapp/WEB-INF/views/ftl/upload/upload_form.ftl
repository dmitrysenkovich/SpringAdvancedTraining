<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>managing-movie-theater-app</title>
</head>
<body>
    <center>
        <h1>Movie Application</h1>
        <form method="post" action="/upload/files/usersEvents" enctype="multipart/form-data">
            <table border="0">
                <tr>
                    <td>USERS  (JSON):</td>
                    <td><input type="file" name="fileUsers" size="50" /></td>
                </tr>
                <tr>
                    <td>EVENTS (JSON):</td>
                    <td><input type="file" name="fileEvents" size="50" /></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Upload" /></td>
                </tr>
            </table>
        </form>
    </center>
</body>
</html>