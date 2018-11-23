var wsocket;

function connect() {
    wsocket = new WebSocket("ws://localhost:8080/L08/ws");
    wsocket.onmessage = onMessage;
}

function onMessage(evt) {
    var json = JSON.parse(evt.data);
    if (typeof json.news === 'undefined') {
        setCurrency(json);
    } else {
        setNews(json);
    }
}

function setNews(json) {
    var arr = json.news;
    var html = "";
    var maxIndex = Math.min(arr.length, 7);
    for (var i = 0; i < maxIndex; i++) {
        html += "<div class = \"newstopic\"><a href = " + arr[i].href + ">" + arr[i].text + "</a></div>";
    }
    document.getElementById("news-id").innerHTML = html;
}

function setCurrency(json) {
    var USD = document.getElementById("USD");
    var EUR = document.getElementById("EUR");
    var CNY = document.getElementById("CNY");
    var USDname = document.getElementById("USD-name");
    var EURname = document.getElementById("EUR-name");
    var CNYname = document.getElementById("CNY-name");
    USD.innerHTML = json.currency_list[0].value;
    EUR.innerHTML = json.currency_list[1].value;
    CNY.innerHTML = json.currency_list[2].value;
    USDname.innerHTML = USDname.innerHTML.split(" за ")[0] + " за " + json.currency_list[0].nominal;
    EURname.innerHTML = EURname.innerHTML.split(" за ")[0] + " за " + json.currency_list[1].nominal;
    CNYname.innerHTML = CNYname.innerHTML.split(" за ")[0] + " за " + json.currency_list[2].nominal;
}

window.addEventListener("load", connect, false);