
function formValido() {
    var nome = $("#nomeAula").val();
    var posti = $("#postiAula").val();
    var disp = $("#dispAula").val();
    var prese = $("#preseAula").val();
    var pc = $("#pcAula").val();
    var regxPosti = /^[0-9]+$/;
    var regxNome = /^[A-Z a-z 0-9]+$/;
    var verifica = true;

    if (posti === "" || posti.length < 20 || posti.length > 300) {
        $("#errPostiAula").text("Numero posti non corretto");
        verifica = false;
    } else if (posti.match(regxPosti) == null) {
        $("#errPostiAula").text("Formato numero posti non valido");
        verifica = false;
    }
    if (nome === " " || nome.length < 1 || nome.length > 16) {
        $("#errNomeAula").text("Nome aula non valido");
        verifica = false;
    } else if (nome.match(regxNome) == null) {
        $("#errNomeAula").text("Nome aula non rispetta il formato");
        verifica = false;
    }
    if (disp === "" || disp ===" ") {
        $("#errDispAula").text("Orari di disponibilità errati");
        verifica = false;
    }

    return verifica;
}

function inserisciNuovaAula(form) {
    $.post("/inserisciAula", $(form).serialize(), function(msg) {
        showMessage(msg);
    }).fail(function (msg) {
        showError(msg.responseText);
    });
}

function controllaInsAula(form) {
    if (formValido()) {
        inserisciNuovaAula(form);
    }
}

function blankLabel(id1){
    $('#'+id1).text(' ');

}