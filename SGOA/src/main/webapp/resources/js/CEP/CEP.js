function pesquisacep(valor) {

    //Nova variável "cep" somente com dígitos.
    var cep = valor.replace(/\D/g, '');

    //Verifica se campo cep possui valor informado.
    if (cep != "") {

        //Expressão regular para validar o CEP.
        var validacep = /^[0-9]{8}$/;

        //Valida o formato do CEP.
        if (validacep.test(cep)) {

            //Preenche os campos com "..." enquanto consulta webservice.
            document.getElementById('rua').value = "...";
            document.getElementById('bairro').value = "...";
            document.getElementById('cidade').value = "...";
            //document.getElementById('estado').value="...";

            //Cria um elemento javascript.
            var url = 'https://viacep.com.br/ws/' + cep + '/json/';

            //Sincroniza com o callback.
            $.getJSON(url, function (json) {


                document.getElementById('cidade').value = (json.localidade);
                document.getElementById('bairro').value = (json.bairro);
                document.getElementById('rua').value = (json.logradouro);
                //document.getElementById('estado').value=(json.uf);
            });

        } //end if.
        else {
            //cep é inválido.
            alert("Formato de CEP inválido.");
            return false;
        }
    } //end if.
};
