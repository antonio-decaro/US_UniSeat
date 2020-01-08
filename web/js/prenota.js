var AULE;

$(function () {

    $("[name='toggle_prenotazione']").click(function() {
        loadInfo($(this).val());
    });

    $("#submit_button").click(function () {
        sendInfo($(this).parents('form:first'));
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
    });
}

function sendInfo(form) {
    var data = form.serialize();
    // $.post("/PrenotaPostoServlet", data, function () {
    //     alert("Prenotazione effettuata con successo.");
    // }).fail(function (msg) {
    //     alert(msg.toString())
    // });
}
