$(function(){
    //loadEdifici();

});

function loadEdifici() {

    $.post("/UniSeat_archive/PrelevaEdificiServlet", function(data){
        var edifici = JSON.parse(data);
        for (i = 0; i < edifici.length; i++) {
            var e = edifici[i];
            aggiungi(e);
        }
    }).fail(function(data){
        alert(data.toString());
    });
}

function aggiungi(edificio) {
    alert(edificio.nome);
}