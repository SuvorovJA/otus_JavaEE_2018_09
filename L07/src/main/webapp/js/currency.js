function getCurrency() {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/L08/currency', true);
    xhr.onreadystatechange = function () {
        if (this.readyState != 4) return;
        var USD = document.getElementById("USD");
        var EUR = document.getElementById("EUR");
        var CNY = document.getElementById("CNY");
        var USDname = document.getElementById("USD-name");
        var EURname = document.getElementById("EUR-name");
        var CNYname = document.getElementById("CNY-name");
        if (this.status == 200) {
            var rates = JSON.parse(this.responseText);
            USD.innerHTML = rates.currency_list[0].value;
            EUR.innerHTML = rates.currency_list[1].value;
            CNY.innerHTML = rates.currency_list[2].value;
            USDname.innerHTML = USDname.innerHTML.split(" за ")[0] + " за " + rates.currency_list[0].nominal;
            EURname.innerHTML = EURname.innerHTML.split(" за ")[0] + " за " + rates.currency_list[1].nominal;
            CNYname.innerHTML = CNYname.innerHTML.split(" за ")[0] + " за " + rates.currency_list[2].nominal;
        } else {
            USD.innerHTML = "---";
            EUR.innerHTML = "---";
            CNY.innerHTML = "---";
        }
    };
    xhr.send();
}