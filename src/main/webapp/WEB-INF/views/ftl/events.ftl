<html>
    <head>
        <title>Events</title>
    </head>
    <body>
        <h1>Events</h1>
        <#list events as event>
            <div>
                name: ${event.name}
                </br>
                base price: ${event.basePrice}
                </br>
                rating: ${event.rating}
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