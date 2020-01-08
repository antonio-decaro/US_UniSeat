$(function () {

    $("#toogle_prenotazione").click(function() {
        loadInfo($("#toogle_prenotazione").val());
    });
});

function loadInfo(idAula) {
    alert(idAula);
}

