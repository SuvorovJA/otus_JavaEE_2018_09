function getNews() {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/L05/news', true);
    xhr.onreadystatechange = function() {
        if (this.readyState != 4) return;
        var container = document.getElementById("news-id");
        if (this.status == 200) {
            var news = JSON.parse(this.responseText);
            var arr = news.news;
            var html = "";
            var maxIndex = Math.min(arr.length, 10);
            for (var i = 0; i < maxIndex; i++) {
                html += "<div class = \"newstopic\"><a href = " + arr[i].href + ">" + arr[i].text + "</a></div>" ;
            }
            container.innerHTML = html;
        } else {
            container.innerHTML = "---";
        }
    }
    xhr.send();
}