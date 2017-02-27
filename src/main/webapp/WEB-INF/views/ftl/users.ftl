<html>
    <head>
        <title>Users</title>
    </head>
    <body>
        <h1>Users</h1>
        <#list users as user>
            <div>
                name: ${user.firstName!} ${user.lastName!}
                </br>
                email: ${user.email!}
                </br>
                birthday: ${(user.birthday?date)!}
                </br>
            </div>
            </br>
        </#list>
        <form action="/logout" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </body>
</html>