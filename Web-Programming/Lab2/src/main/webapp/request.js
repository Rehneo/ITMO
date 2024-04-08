function sendRequest(){
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        try{
            if(xhr.readyState === XMLHttpRequest.DONE){
                if(xhr.status === 200){
                    setPoint();
                    document.getElementById("responseData").innerHTML = xhr.responseText;
                }else if(xhr.status === 444){
                    alert("Проверьте правильность введенных данных")
                }else {
                    alert("Не удалось получить данные");
                }
            }
        }catch(e){
            alert("Произошла ошибка: " + e.description);
        }
    }
    xhr.open('GET', 'controller?'+'&x='+x+'&y='+y+'&r='+r);
    xhr.send();
}