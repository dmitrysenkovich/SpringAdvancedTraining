<html>
    <head>
        <title>Error page</title>
    </head>
    <body>
        <h1>Error page</h1>
        <h2>${message!}</h2>
    </body>
    <#if (_csrf.token)??>
        <form action="/logout" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </#if>
</html>