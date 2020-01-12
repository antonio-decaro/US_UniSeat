$(function () {

    $("[name='toggle_prenotazione']").click(function() {
        loadInfo($(this).val());
    });

    $("form[name='prenota_posto'] #submit_button").click(function () {
        prenotaPosto($(this).parents('form:first'));
    });

    $("form[name='prenota_aula'] #submit_button").click(function () {
        prenotaAula($(this).parents('form:first'));
    });
});

function loadInfo(idAula) {

    $.get("/PrelevaAuleServlet", "aula="+idAula, function (data) {
        var aula = JSON.parse(data);
        $("#id_aula").val(aula.id);
        $("#nome_aula").text("Aula " + aula.nome);
        $("#posti_aula").text(aula.nPosti);
        $("#posti_disponibili").text(aula.nPosti - aula.nPostiOccupati);
        $("#durata-prenotazione").val("0");

        if (aula.servizi.includes("PRESE"))
            $("#servizi_prese").css({opacity: 1.0});
        else
            $("#servizi_prese").css({opacity: 0.2});

        if (aula.servizi.includes("COMPUTER"))
            $("#servizi_computer").css({opacity: 1.0});
        else
            $("#servizi_computer").css({opacity: 0.2});

        $("#modificaAulaLink").prop("href", "/_admin/ModificaAula.jsp?id=" + aula.id);
    });
}

function prenotaPosto(form) {
    var data = form.serialize();
    $.post("/PrenotaPostoServlet", data, function (msg) {
        showMessage(msg);
    }).fail(function (msg) {
        showError(msg.responseText);
    });
}

function prenotaAula(form) {
    var selector = "#oraInizio_prenotazione";
    var oraInizio = $(selector).val();
    $(selector).val(oraInizio + ":00");
    var data = form.serialize();
    $.post("/PrenotaAulaServlet", data, function (msg) {
        showMessage(msg);
    }).fail(function (msg) {
        showError(msg.responseText);
    })
}
