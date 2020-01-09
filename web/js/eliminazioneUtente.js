function eliminaUtente(email) {
    $.post("/eliminaUtente", email, function (msg) {
        showMessage(msg);
    }).fail(function (msg) {
        showError(msg.responseText);
    });
}


$(function () {

    $("[name='toggle_eliminazioneUtente']").click(function () {
        $("#idEmailUtente").val($(this).val());
        $("#confermaEmail").text("Eliminare l'utente "+$(this).val()+" ?");

    })

    $("form[name='eliminaUtente'] #submit_button").click(function () {
        eliminaUtente($(this).val());
    });

});