
function formValido() {
    var edificio = $("#edAula").val();
    var nome = $("#nomeAula").val();
    var posti = $("#postiAula").val();
    var disp = $("#dispAula").val();
    var prese = $("#preseAula").val();
    var pc = $("#pcAula").val();
    var regxPosti = /^[0-9]+$/;
    var regxNome = /^[A-Z a-z 0-9]+$/;
    var verifica = true;

    if (edificio == " " || edificio.length < 1) {
        $("#errEdAula").text("Edificio non selezionato");
        verifica = false;
    }
    if (posti == "" || posti.length < 20 || posti.length > 300) {
        $("#errPostiAula").text("Numero posti non corretto");
        verifica = false;
    } else if (posti.match(regxPosti) == null) {
        $("#errPostiAula").text("Formato numero posti non valido");
        verifica = false;
    }
    if (nome == " " || nome.length < 1 || nome.length > 16) {
        $("#errNomeAula").text("Nome aula non valido");
        verifica = false;
    } else if (nome.match(regxNome) == null) {
        $("#errNomeAula").text("Nome aula non rispetta il formato");
        verifica = false;
    }

    if (disp );

    return verifica;
}

function inserisciNuovaAula(form) {
    $.post("/inserisciAula", $(form).serialize(), function(msg) {
        showMessage(msg);
    }).fail(function (msg) {
        showError(msg.responseText);
    });
}

function formInsA(form) {
    if (formValido()) {
        inserisciNuovaAula(form);
    }

}