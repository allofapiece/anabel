<form action="/login" method="post">
    <div>
        <label>Email:<input type="text" name="email"/></label>
    </div>
    <div>
        <label>Password:<input type="password" name="password"/></label>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <div>
        <input type="submit" value="Sign In"/>
    </div>
</form>
<a href="/signup">Registration</a>