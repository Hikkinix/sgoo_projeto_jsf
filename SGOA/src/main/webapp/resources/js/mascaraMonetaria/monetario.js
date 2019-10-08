function mascaraMonetaria(arg) {
    //Set up the NumberMasks
    var separadorDecimal = ",";
    var separadorMilhar = ".";

    var formato = new NumberParser(2, separadorDecimal, separadorMilhar, true);
    formato.currencySymbol = "R$";
    formato.useCurrency = true;
    formato.allowNegative = false;
    formato.currencyInside = true;

    if (arg.className.indexOf('mascaraMonetaria') == -1) {
        var mascaraMonetariaFinal = new NumberMask(formato, arg, 20);
        mascaraMonetariaFinal.leftToRight = true;
        arg.setAttribute('autocomplete', 'off');
        arg.className += ' mascaraMonetaria';
    }
}

