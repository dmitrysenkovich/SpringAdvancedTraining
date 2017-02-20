<html>
    <head>
        <title>Users</title>
    </head>
    <body>
        <h1>Users</h1>
        <#list users as user>
            <div>
                name: ${user.firstName} ${user.lastName}
                </br>
                email: ${user.email}
                </br>
                birthday: ${user.birthday?date}
                </br>
            </div>
            </br>
        </#list>
    </body>
</html>