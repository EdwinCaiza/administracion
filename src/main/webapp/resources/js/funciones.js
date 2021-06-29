/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function validaEnteros(e)
{
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla == 8)
        return true;
    patron = /[\d\s]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}


function validaDecimales(e, valor)
{
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla == 46) {
        if (valor.indexOf(punto) >= 0) {
            return false;
        }
    }
    if (tecla == 8 || tecla == 0)
        return true;
    if (tecla == 13)
        return false;
    punto = ".";

    patron = /[-\d.\s]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}

function cambioMenu(idElem) {
    try {
        if (anterior !== -1) {
            document.getElementById(anterior).style.cssText = 'padding-left: -5px; font-weight:normal background: none; font-style: none; ';
        }
        document.getElementById(idElem).style.cssText = 'padding-left: 5px; font-weight: bolder; background: #0073ea;font-style: italic; color:#ffffff;';

        anterior = idElem;
    } catch (e) {

    }
}

function validaSoloLetras(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla == 8)
        return true;
    patron = /[A-Za-z\s]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);

}

function ponerMayusculas(text)
{
    text.value = text.value.toUpperCase().trim();
}

function hacerClick(idBoton, e) {
    if (e.keyCode === 13) {
        e.preventDefault();
        document.getElementById(idBoton).click();
        return false;
    }


}


function validarEmail(valor, e) {
    if (/^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i.test(valor)) {
    } else {
        alert("La dirección de email es incorrecta!");
        return document.getElementById(e).value = "";
    }



}

function resaltarBusqueda(clave, contenedorTexto) {
    var text = document.getElementById(clave).value;
    if (text.length > 1) {
        var query = new RegExp("(\\b" + text + "\\b)", "gim");
        var e = document.getElementById(contenedorTexto).innerHTML;
        var enew = e.replace(/(<b>|<\/b>)/igm, "");
        document.getElementById(contenedorTexto).innerHTML = enew;
        var newe = enew.replace(query, "<b>$1</b>");
        document.getElementById(contenedorTexto).innerHTML = newe;
    }
}


/* funcion creada para validar los tipos de datos entero en la metadata*/
function validaEnterosMetadata(e, tipo)
{

    if (tipo === 'INT') {
        tecla = (document.all) ? e.keyCode : e.which;
        if (tecla == 8)
            return true;
        patron = /[\d\s]/;
        te = String.fromCharCode(tecla);
        return patron.test(te);
    }
}

function comparaValoresMayores(valmin, txtmax, txtvalor) {
    var max = document.getElementById(txtmax).value;
    var valor = document.getElementById(txtvalor).value;
    max = parseFloat(max);
    valor = parseFloat(valor);
    if (valor > max) {
        alert('El valor Ingresado excede de lo permitido');
        document.getElementById(txtvalor).value = 0;
        document.getElementById(txtvalor).style.background = "#F5A9A9";
        document.getElementById(txtvalor).style.border = "1px #8A0808 solid";
    } else {
        document.getElementById(txtvalor).style.background = "#FFFFFF";
        document.getElementById(txtvalor).style.border = "1px #bebebe solid";
    }
}

function comparaMovIngreso(valCantEnt, valCantSaldo, txtEstado) {
    var cantEnt = document.getElementById(valCantEnt).value;
    var cantsaldo = document.getElementById(valCantSaldo).value;
    cantEnt = parseFloat(cantEnt);
    cantsaldo = parseFloat(cantsaldo);
    if (cantEnt === cantsaldo) {
        document.getElementById(txtEstado).value = 'INGRESADO';
        document.getElementById(txtEstado).style.background = "#47b604";
        document.getElementById(txtEstado).style.border = "1px #8A0808 solid";
    } else {
        document.getElementById(txtEstado).value = 'PENDIENTE';
        document.getElementById(txtEstado).style.background = "#F44823";
        document.getElementById(txtEstado).style.border = "1px #bebebe solid";
    }
}

function calculaSaldoMovimientos(txtValor1, txtValor2, txtSaldo) {
    var v1 = document.getElementById(txtValor1).value;
    var v2 = document.getElementById(txtValor2).value;
    var saldo = document.getElementById(txtSaldo).value;
    if (v1 !== '' && v2 !== '') {
        v1 = parseFloat(v1);
        v2 = parseFloat(v2);
        saldo = v1 - v2;
        if (saldo !== 0) {
            if (v1 === saldo) {
                document.getElementById(txtSaldo).style.background = "#FFFFFF";
                document.getElementById(txtSaldo).style.border = "1px #bebebe solid";
            } else {
                document.getElementById(txtSaldo).style.background = "#04a9b6";
                document.getElementById(txtSaldo).style.border = "1px #8A0808 solid";
            }
            document.getElementById(txtSaldo).value = saldo;
        } else {
            document.getElementById(txtSaldo).value = saldo;
            document.getElementById(txtSaldo).style.background = "#47b604";
            document.getElementById(txtSaldo).style.border = "1px #8A0808 solid";
        }
    }
}

function comparaValoresNulos(txtValor) {
    var valor = document.getElementById(txtValor).value;

    if (!valor) {
        valor = parseFloat(valor);
        alert('La cantidad No puede ser nulo');
        document.getElementById(txtValor).value = 0;
        document.getElementById(txtValor).style.background = "#F5A9A9";
        document.getElementById(txtValor).style.border = "1px #8A0808 solid";
    } else {
        document.getElementById(txtValor).style.background = "#FFFFFF";
        document.getElementById(txtValor).style.border = "1px #bebebe solid";
    }
    
}

function presionaEnter(e)
{
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla == 8)
        return true;
    patron = /[\d\s]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}



function comparaValoresNulosEgreso(txtValor) {
    var valor = document.getElementById(txtValor).value;
    if (!valor) {
        valor = parseFloat(valor);
        alert('La cantidad No puede ser nulo');
        document.getElementById(txtValor).value = 1;
       
    } else if(valor == 0) {
        valor = parseFloat(valor);
        alert('La cantidad No puede ser 0');
        document.getElementById(txtValor).value = 1;
        
    }
    
    
   
}


function comparaSaldosVenta(valmin, txtmax, txtvalor) {
    var max = document.getElementById(txtmax).value;
    var valor = document.getElementById(txtvalor).value;
    max = parseFloat(max);
    valor = parseFloat(valor);
    if (valor > max) {
        alert('El valor Ingresado excede de lo permitido');
        document.getElementById(txtvalor).value = 1;
        document.getElementById(txtvalor).style.background = "#F5A9A9";
        document.getElementById(txtvalor).style.border = "1px #8A0808 solid";
    } else {
        document.getElementById(txtvalor).style.background = "#FFFFFF";
        document.getElementById(txtvalor).style.border = "1px #bebebe solid";
    }
}



function calculaValorTotal(txtValor1, txtValor2, txtTotal) {
    var v1 = document.getElementById(txtValor1).value;
    var v2 = document.getElementById(txtValor2).value;
    var total = document.getElementById(txtTotal).value;
    v1 = parseFloat(v1);
    v2 = parseFloat(v2);
    total = v1 * v2;
    document.getElementById(txtTotal).value = parseFloat((total).toFixed(2));
            
     
    
}

