$(function () {
    initPage();
    $("#setPrese").click(function () {
        setPrese()
    });
    $("#setComputer").click(function () {
        setComputers()
    });
});

function formValido() {

    var nome = $("#nomeAula").val();
    var posti = $("#postiAula").val();
    var disp = $("#dispAula").val();
    var regxPosti = /^[0-9]+$/;
    var regxNome = /^[A-Z a-z 0-9]+$/;
    var verifica = true;

    if (nome === " " || nome.length < 1 || nome.length > 16) {
        $("#errNomeAula").text("Nome aula non valido");
        verifica = false;
    } else if (nome.match(regxNome) == null) {
        $("#errNomeAula").text("Nome aula non rispetta il formato");
        verifica = false;
    }

    if (posti === "" || posti < 20 || posti > 300) {
        $("#errPostiAula").text("Numero posti non corretto");
        verifica = false;
    } else if (posti.match(regxPosti) == null) {
        $("#errPostiAula").text("Formato numero posti non valido");
        verifica = false;
    }

    if (disp === "" || disp === " ") {
        $("#errDispAula").text("Orari di disponibilità errati");
        verifica = false;
    }

    return verifica;
}

function inserisciNuovaAula(form) {

    $.post("/inserisciAula", $("#insNewAula").serialize(), function (msg) {
        showMessage(msg);
    }).fail(function (msg) {
        showError(msg.responseText);
    });
}

function modificaAula(form) {

    $.post("/modificaAula", $("#formModAula").serialize(), function (msg) {
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

function controllaModAula(form) {
    if (formValido()) {
        modificaAula(form);
    }
}

function blankLabel(id1) {
    $('#' + id1).text(' ');

}

function initPage() {

    if ($("#preseAula").is(":checked")) {
        $("#servizi_prese").css({opacity: 1});
    } else {
        $("#servizi_prese").css({opacity: 0.35});
    }
    if ($("#pcAula").is(":checked")) {
        $("#servizi_computer").css({opacity: 1});
    } else {
        $("#servizi_computer").css({opacity: 0.35});
    }
}

function setPrese() {
    var inp = $("#preseAula");
    var box = $("#servizi_prese");
    if ($(inp).is(":checked")) {
        $(inp).prop("checked", false);
        $(box).css({opacity: 0.35});
    } else {
        $(inp).prop("checked", true);
        $(box).css({opacity: 1});
    }
}

function setComputers() {
    var inp = $("#pcAula");
    var box = $("#servizi_computer");
    if ($(inp).is(":checked")) {
        $(inp).prop("checked", false);
        $(box).css({opacity: 0.35});
    } else {
        $(inp).prop("checked", true);
        $(box).css({opacity: 1});
    }
}
