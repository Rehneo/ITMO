let response = "";
let responseText = "";
function sendRequest(){
    const xhr = new XMLHttpRequest();
    let url = new URL('http://localhost:3000/script.php');
    url.searchParams.set('x', x);
    url.searchParams.set('y', y);
    url.searchParams.set('r', r);
    xhr.onreadystatechange = function(){
        if(xhr.readyState === XMLHttpRequest.DONE){
            setPoint(x,y,r);
            setCookie(xhr.responseText);
            addData();
        }
    }
    xhr.open('GET', url);
    xhr.send();
}

function setCookie(response){
    const date = new Date();
    let n = getCookie("number");
    date.setTime(date.getTime()+(60*60*1000));
    let expires = "expires=" + date.toUTCString();
    if(n == undefined){
        n = 0
    }    
    document.cookie = `t${n}=${response}; ${expires}; path=/`;
    n = Number(n) + 1;
    document.cookie = `number=${n}; ${expires}; path=/`;
}

function getCookie(name){
    const cArray = decodeURIComponent(document.cookie).split("; ");
    let result;
    cArray.forEach(element => {
        if(element.indexOf(name) == 0){
            result = element.substring(name.length + 1);
        }
    })
    return result;
}