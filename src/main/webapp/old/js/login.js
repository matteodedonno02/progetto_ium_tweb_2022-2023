$("#loginForm").submit(function (e) {
    e.preventDefault();
    const email = $("input[name='email']").val();
    const password = $("input[name='password']").val();
    console.log(email);
    console.log(password);

    $.ajax({
        url: "UserServlet",
        method: "POST",
        data: {
            "operation": "login",
            "email": email,
            "password": password
        },
        success: function (data) {
            window.location.replace("index.html");
        }
    })
});