<form action="/signup" method="post">
    <div>
        <label>Display name:<input type="text" name="username"/></label>
    </div>
    <div>
        <label>Email:<input type="email" name="email"/></label>
    </div>
    <div>
        <label>Password:<input type="password" name="password"/></label>
    </div>
    <div>
        <label>Confirm password:<input type="password" name="confirmedPassword"/></label>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <div>
        <input type="submit" value="Sign Up"/>
    </div>
</form>