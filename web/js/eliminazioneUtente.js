function eliminaUtente(form, row) {
    var data = form.serialize();
    $.post("/eliminaUtente", data, function (msg) {
        showMessage(msg);
    }).fail(function (msg) {
        showError(msg.responseText);
    });

}


$(function () {

    var row;

    $("[name='toggle_eliminazioneUtente']").click(function () {
        row = $(this).parents('tr:first');
        $("#idEmailUtente").val($(this).val());
        $("#confermaEmail").text("Eliminare l'utente " + $(this).val()+" ?");

    });

    $("form[name='eliminaUtente'] #submit_button").click(function () {
        eliminaUtente($(this).parents('form:first'));
        row.remove();
    });

});