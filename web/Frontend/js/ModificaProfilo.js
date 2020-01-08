function validation() {

    var nome = $("#inputNome").val();
    var cognome = $("#inputCognome").val();
    var pass = $("#inputPassword").val();
    var confPass = $("#inputConfPassword").val();
    var verifica = true;
    const regexN = /^[a-z A-Z]+$/;
    const regexC = /^[a-z A-Z]+$/;
    const regexP = /^((?=.*[\d])(?=.*[a-z])(?=.*[A-Z])).+$/;


    if (nome == "" || nome.length < 1 || nome.length > 20 || nome ==" ") {
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
    if (pass === "" || pass.length > 32 || pass.length < 8 || pass === " ") {
        $("#errP").text("Il campo Password non rispetta la lunghezza");
        verifica = false;
    } else if (pass.match(regexP) == null) {
        $("#errP").text("Il campo Password non rispetta il formato");
        verifica = false;
    } else if (pass != confPass) {
        $("#errP").text("Le Password non corrispondono");
        verifica = false;
    }

    return verifica;
}

function modificaDati(form) {
    $.post("modificaProfilo",$(form).serialize(),function(msg){

        if(msg == 200){
            $(window.location).attr('href','VisualizzaProfiloJSP.jsp');
        }

    });
}

function controllaFormModifica(form) {
    if (validation()) {
        modificaDati(form);
    }
}

function setEditabili() {
    $("#inputNome").prop("readonly", false);
    $("#inputCognome").prop("readonly", false);
    $("#inputPassword").prop("readonly", false);
    $("#inputConfPassword").prop("readonly", false);
    $("#buttonModifica").hide();
    $("#divPassHide").show();
    $("#divConfPassHide").show();
    const form = $('form[name="formModifica"]');
    $("#buttonConfermaModifica").show();
}

function blankLabel(id1){
    $('#'+id1).text(' ');

}
