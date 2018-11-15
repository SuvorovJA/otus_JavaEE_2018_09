function timeStamp() {
    document.getElementById('clientDateTimeValue').value = Date.now();
    document.getElementById('clientTimeZoneValue').value = (new Date()).getTimezoneOffset();
    document.getElementById('theTimeForm').submit();
}