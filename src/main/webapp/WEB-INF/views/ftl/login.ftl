<html>
    <head>Wow such custom much login so cool page!</head>
    <body>
        <form action="/login" method="post">
            <#if RequestParameters.error??>
                <p>
                    Invalid username and password.
                </p>
            </#if>
            <#if RequestParameters.logout??>
                <p>
                    You have been logged out.
                </p>
            </#if>
            <p>
                <label for="username">Username</label>
                <input type="text" id="username" name="username"/>
            </p>
            <p>
                <label for="password">Password</label>
                <input type="password" id="password" name="password"/>
            </p>
            <p>
                <label for="remember-me">Remember Me:</label>
                <input type="checkbox" id="remember-me" name="remember-me"/>
            </p>
            <input type="hidden"
                name="${_csrf.parameterName}"
                value="${_csrf.token}"/>
            <button type="submit" class="btn">Log in</button>
        </form>
    </body>
</html
