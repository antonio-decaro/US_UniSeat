$(function(){
    loadEdifici();

});

function loadEdifici() {

    $.post("/PrelevaEdificiServlet", "", function(data){
        var edifici = JSON.parse(data);
        for (i = 0; i < edifici.length; i++) {
            var e = edifici[i];
            aggiungi(e);
        }
    });
}

function aggiungi(edificio) {
    alert(edificio);
}