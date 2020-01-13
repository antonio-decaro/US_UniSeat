
function validate() {
    var Cpass = ConfPassword();
    var pass = validationPassword();
    var email = validationEmail();
    var cognome = validateCognome();
    var name = validateName();
    return  Cpass && pass && email && cognome && name;
}

function validationEmail() {
    var letters = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,30}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,30}[a-zA-Z0-9])?)*$/;

    var email = document.getElementById("email");
    if (email.value.match(letters) && (email.indexOf('@studenti.unisa.it') || email.indexOf('@unisa.it') )) {
        document.getElementById("erremail").innerHTML = " ";
        document.getElementById("erremail").style.display = "none";
        return true;
    } else {
        document.getElementById("email").focus();
        document.getElementById("erremail").innerHTML = "Inserire un e-mail valida";
        document.getElementById("erremail").style.display = "block";
        return false;
    }
}

function validateName() {
    var letters = /[A-Z][a-zA-Z][^#&<>\"~;$^%{}?{0-9}]{1,20}$/;


    if (document.getElementById("nome").value.match(letters)) {
        document.getElementById("errnome").innerHTML = "";
        document.getElementById("errnome").style.display = "none";
        return true;
    } else {
        document.getElementById("nome").focus();
        document.getElementById("errnome").innerHTML = "Deve essere formato solo da lettere e la prima lettera maiuscola";
        document.getElementById("errnome").style.display = "block";
        return false;
    }
}

function validationPassword() {
    var letters = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[.!@#\$%\^&\*])(?=.{8,32})/;

    if (document.getElementById("psw").value.match(letters)) {
        document.getElementById("errpass").innerHTML = "";
        document.getElementById("errpass").style.display = "none";
        return true;
    } else {
        document.getElementById("psw").focus();
        document.getElementById("errpass").innerHTML = "La password deve avere almeno 8 caratteti di cui 1 maiuscolo e 1 carattere speciale";
        document.getElementById("errpass").style.display = "block";
        return false;
    }
}

function ConfPassword() {

    var psw = document.getElementById("psw");
    var Cpse = document.getElementById("Cpsw");
    if (psw.value != Cpse.value) {
        document.getElementById("Cpsw").focus();
        document.getElementById("errCpass").innerHTML = "Le password non corrispondono";
        document.getElementById("errCpass").style.display = "block";
        return true;
    } else {
        document.getElementById("errCpass").innerHTML = "";
        document.getElementById("errCpass").style.display = "none";
        return false;
    }
}

function validateCognome() {
    var letters = /[A-Z][a-zA-Z][^#&<>\"~;$^%{}?{0-9}]{1,20}$/;

    if (document.getElementById("cognome").value.match(letters)) {
        document.getElementById("errcognome").innerHTML = "";
        document.getElementById("errcognome").style.display = "none";
        return true;
    } else {
        document.getElementById("cognome").focus();
        document.getElementById("errcognome").innerHTML = "Deve essere formato solo da lettere e la prima lettera maiuscola";
        document.getElementById("errcognome").style.display = "block";
        return false;
    }
}