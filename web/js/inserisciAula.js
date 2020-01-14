$(function () {
    initPage();
    $("#setPrese").click(function () {
        setPrese()
    });
    $("#setComputer").click(function () {
        setComputers()
    });

    $("form[name='formInsA'] #submitButton").click(function () {
        inserisciNuovaAula($("form[name='formInsA']"));
    });
    $("form[name='formModA'] #submitButton").click(function () {
        modificaAula($("form[name='formModA']"));
    });
});

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

    if (edificio === " " || edificio.length < 1) {
        $("#errEdAula").text("Edificio non selezionato");
        verifica = false;
    }

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
    if (disp === "" || disp === " ") {
        $("#errDispAula").text("Orari di disponibilità errati");
        verifica = false;
    }

    return verifica;
}

function inserisciNuovaAula(form) {
    var data = form.serialize();
    $.post("/inserisciAula", data, function (msg) {
        showMessage(msg);
    }).fail(function (msg) {
        showError(msg.responseText);
    });
}

function modificaAula(form) {
    var data = form.serialize();
    $.post("/modificaAula", data, function (msg) {
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

function blankLabel(id1) {
    $('#' + id1).text(' ');

}

function initPage() {
    $("#servizi_computer").css({opacity: 0.35});
    $("#pcAula").prop("checked", false);
    $("#servizi_prese").css({opacity: 0.35});
    $("#preseAula").prop("checked", false);
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
