<html>
    <head>
        <title>Purchased tickets for event</title>
    </head>
    <body>
        <h1>Purchased tickets for event</h1>
        <#list tickets as ticket>
            <div>
                user: ${ticket.user.firstName} ${ticket.user.lastName}
                </br>
                event: ${ticket.event.name}
                </br>
                auditorium: ${ticket.auditorium.name}
                </br>
                seat: ${ticket.seat}
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