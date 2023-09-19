function getHistoryFromLocalStorage () {
    const historyValue = window.localStorage.getItem('friendy');
    const history = JSON.parse(historyValue);
    return history;
}

function addHistoryToLocalStorage(newData) {
    window.localStorage.setItem('friendy', newData);
}

function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

function setCookie(name, value, options = {}) {
    options = {
        path: '/',
        ...options
    };
    if (options.expires instanceof Date) {
        options.expires = options.expires.toUTCString();
    }
    let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value);
    for (let optionKey in options) {
        updatedCookie += "; " + optionKey;
        let optionValue = options[optionKey];
        if (optionValue !== true) {
            updatedCookie += "=" + optionValue;
        }
    }
    document.cookie = updatedCookie;
}

function deleteCookie(name) {
    document.cookie = name + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}

function addHistory (school) {
    let history = getHistoryFromLocalStorage();
    if (!history) history = {};
    history[school.idx] = { ...school };
    const prevCookie = getCookie('friendy');
    const newCookie = prevCookie ? prevCookie+","+school.idx : school.idx;
    setCookie('friendy', newCookie);
    addHistoryToLocalStorage(JSON.stringify(history));
    renderHistoryList();
}

function dropHistory () {
    window.localStorage.clear();
    deleteCookie("friendy");
}