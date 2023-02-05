$(document).ready(function() {
    $("#loginButton").click(function() {
        var memberId = $("#memberId").val();
        var password = $("#password").val();

        if (!validationCheck("memberId")) {}
        if (!validationCheck("password")) {}
        else {
            $.ajax({
                type: "POST",
                url: "/ourboard/api/v1/member/login",
                data: { memberId: memberId, password: password },
                success: function(data) {
                    alert(data);
                    alert("Login Successful");
                },
                error: function(xhr, status, error) {
                    alert(error);
                    alert(JSON.stringify(xhr));
                    alert(status);
                    alert("Login Unsuccessful");
                }
            });
        }
    });
});

function validationCheck(obj) {
    var isValid = true;

    if($("#" + obj).val() == "") {
        $("#" + obj).focus();
        alert("Please enter a value")
        isValid = false;
    }

    return isValid;
}

function enterKeyPress(event, obj) {
    if (event.which == 13) {
      $("#" + obj).click();
    }
}