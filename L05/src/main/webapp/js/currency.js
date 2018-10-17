function getCurrency() {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/L05/currency', true);
    xhr.onreadystatechange = function () {
        if (this.readyState != 4) return;
        var USD = document.getElementById("USD");
        var EUR = document.getElementById("EUR");
        var CNY = document.getElementById("CNY");
        if (this.status == 200) {
            var rates = JSON.parse(this.responseText);
            USD.innerHTML = rates.currency_list[0].value;
            EUR.innerHTML = rates.currency_list[1].value;
            CNY.innerHTML = rates.currency_list[2].value;
        } else {
            USD.innerHTML = "---";
            EUR.innerHTML = "---";
            CNY.innerHTML = "---";
        }
    };
    xhr.send();
}