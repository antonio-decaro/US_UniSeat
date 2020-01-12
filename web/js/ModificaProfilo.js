function validation() {

    var nome = $("#inputNome").val();
    var cognome = $("#inputCognome").val();
    var pass = $("#inputPassword").val();
    var confPass = $("#inputConfPassword").val();
    var verifica = true;
    var regexN = /^[a-z A-Z]+$/;
    var regexC = /^[a-z A-Z]+$/;
    var regexP = /^((?=.*[\d])(?=.*[a-z])(?=.*[A-Z])).+$/;


    if (nome === "" || nome.length < 1 || nome.length > 20 || nome === " ") {
        $("#errN").text("Il campo Nome non rispetta la lunghezza");
        verifica = false;
    } else if (nome.match(regexN) == null) {
        $("#errN").text("Il campo Nome non rispetta il formato");
        verifica = false;
    }
    if (cognome === "" || cognome.length < 1 || cognome.length > 20 || cognome === " ") {
        $("#errC").text("Il campo Cognome non rispetta la lunghezza");
        verifica = false;
    } else if (cognome.match(regexC) == null) {
        $("#errC").text("Il campo Cognome non rispetta il formato");
        verifica = false;
    }

    if (pass !== "") {

        if (pass.length > 32 || pass.length < 8 || pass === " ") {
        $("#errP").text("Il campo Password non rispetta la lunghezza");
        verifica = false;
    } else if (pass.match(regexP) == null) {

            $("#errP").text("Il campo Password non rispetta il formato");
            verifica = false;
        }

    } else if (pass !== "" && pass !== confPass) {
        $("#errP").text("Le Password non corrispondono");
        verifica = false;
    }

    return verifica;
}

function modificaDati(form) {
    $.post("/modificaProfilo", $(form).serialize(), function(msg) {
        $(window.location).attr('href','/_studente/VisualizzaProfilo.jsp');
    });
}

function controllaFormModifica(form) {
    if (validation()) {
        modificaDati(form);
    }
}

function mostraPassword() {
    $("#divPassHide").show();
    $("#divConfPassHide").show();
    $("#inputPassword").prop("readonly", false);
    $("#inputConfPassword").prop("readonly", false);
    $("#buttonModificaPassword").prop("disabled",true);

}

function setEditabili() {
    $("#inputNome").prop("readonly", false);
    $("#inputCognome").prop("readonly", false);
    $("#buttonModifica").hide();
    $("#buttonConfermaModifica").show();
    $("#buttonModificaPassword").show();
}

function blankLabel(id1){
    $('#'+id1).text(' ');

}

