function localizarComponente(Componente, componentePai) {

    var c = document.getElementById(componentePai);
    var i;
    var idComponente;
    var idlocalizado;
    for (i = 0; c.children.length > i; i++) {
        idComponente = c.children[i].getAttribute('id');
        if (idComponente.includes(Componente)) {
            idlocalizado = idComponente;
        }
    }
    return idlocalizado;
}

function somenteNumeros(e) {
    var charCode = e.charCode ? e.charCode : e.keyCode;

    // charCode 8 = backspace
    // charCode 9 = tab
    if (charCode != 8 && charCode != 9) {

        // charCode 48 equivale a 0
        // charCode 57 equivale a 9
        if (charCode < 48 || charCode > 57) {
            return false;
        }
    }
}

function mascara(o, f) {
    v_obj = o
    v_fun = f
    setTimeout("execmascara()", 1)
}

function execmascara() {
    v_obj.value = v_fun(v_obj.value)
}

function mtel(v) {
    v = v.replace(/\D/g, "");             //Remove tudo o que não é dígito
    v = v.replace(/^(\d{2})(\d)/g, "($1) $2"); //Coloca parênteses em volta dos dois primeiros dígitos
    v = v.replace(/(\d)(\d{4})$/, "$1-$2");    //Coloca hífen entre o quarto e o quinto dígitos
    return v;
}

function id(el) {
    return document.getElementById(el);
}

window.onload = function () {
    id(localizarComponente('telefoneMaskTeste', 'mainPessoa')).onkeyup = function () {
        mascara(this, mtel);
    }
}