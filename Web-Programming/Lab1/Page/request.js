function sendRequest(){
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        try{
            if(xhr.readyState === XMLHttpRequest.DONE){
                if(xhr.status == 200){
                    setPoint(x,y,r);
                    document.getElementById("response-data").innerHTML = xhr.responseText;
                }else{
                    alert("Не удалось получить данные");
                }
            }
        }catch(e){
            alert("Произошла ошибка: " + e.description);
        }
    }
    xhr.open('GET', 'script.php?'+'&x='+x+'&y='+y+'&r='+r+'&timezone='+Intl.DateTimeFormat().resolvedOptions().timeZone);
    xhr.setRequestHeader("Start-Request","false");
    xhr.send();
}

sendStartRequest();

function sendStartRequest(){
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        try{
            if(xhr.readyState === XMLHttpRequest.DONE){
                if(xhr.status == 200){
                    document.getElementById("response-data").innerHTML = xhr.responseText;
                }else{
                    alert("Не удалось получить данные о предыдущих результатах");
                }
            }
        }catch(e){
            alert("Произошла ошибка: " + e.description);
        }
    }
    xhr.open('GET', 'script.php?');
    xhr.setRequestHeader("Start-Request","true");
    xhr.send();
}
