function formValido() {
    var edificio = $("#edAula").val();
    var nome = $("#nomeAula").val();
    var posti = $("#postiAula").val();
    var disp = $("#dispAula").val();
    var prese = $("#preseAula").val();
    var pc = $("#pcAula").val();
    var regxPosti = /^[0-9]+$/;

    if (edificio == "") {

    }
    if (posti == "" || posti.length < 20 || posti.length > 300) {

    } else if (posti.match(regxPosti) == null) {

    }



    return false;
}

function inserisciNuovaAula(form) {

}

function formInsA(form) {
    if (formValido()) {
        inserisciNuovaAula(form);
    }

}