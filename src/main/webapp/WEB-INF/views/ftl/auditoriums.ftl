<html>
    <head>
        <title>Auditoriums</title>
    </head>
    <body>
        <h1>Auditoriums</h1>
        <#list auditoriums as auditorium>
            <div>
                name: ${auditorium.name}
                </br>
                number of seats: ${auditorium.numberOfSeats}
                </br>
                date: ${auditorium.date?date}
                </br>
            </div>
            </br>
        </#list>
    </body>
</html>