$(function () {
    // VARIABLES =============================================================
    var TOKEN_KEY = "jwtToken"

    // FUNCTIONS =============================================================
    function getJwtToken() {
        return localStorage.getItem(TOKEN_KEY);
    }

    function setJwtToken(token) {
        localStorage.setItem(TOKEN_KEY, token);
    }

    function removeJwtToken() {
        localStorage.removeItem(TOKEN_KEY);
    }

    $("#loginForm").submit(function (event) {
        event.preventDefault();
        var $form = $(this);
        var formData = {
            username: $form.find('input[id="username"]').val(),
            password: $form.find('input[id="password"]').val()
        };

        doLogin(formData);
    });

    function doLogin(loginData) {
        $.ajax({
            url: "/api/authenticate",
            type: "POST",
            data: JSON.stringify(loginData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                alert(data.id_token);
                setJwtToken(data.id_token);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR);
                if (jqXHR.status === 401) {

                } else {
                    throw new Error("an unexpected error occured: " + errorThrown);
                }
            }
        });
    }

    function createAuthorizationTokenHeader() {
        var token = getJwtToken();
        if (token) {
            return {"Authorization": "Bearer " + token};
        } else {
            return {};
        }
    }
});