flag = false;
let x = 0;
let y = 0;
function setX(elem){
    let elems = document.getElementsByTagName("input");
    let currentState = elem.checked;
    let elemsLength = elems.length;
    for(i=0; i<elemsLength; i++)
    {
      if(elems[i].type === "checkbox")
      {
         elems[i].checked = false;   
      }
    }
  
    elem.checked = currentState;
    x = elem.value;
    document.getElementById("selectedX").innerText = "X: " + x;
}

function checkY(){
    y = document.getElementById("y1").value;
    flag = false;
    try{
        y = Number(y);
        if(isNaN(y)){
            alert("Значение Y должно быть числом!");
        }else{
            if(y < -3 || y > 3){
                alert("Значение Y должно быть в интервале [-3,3]!");
            }else{
                if(y % 1 == 0){
                    document.getElementById("selectedY").innerText = "Y: " +y;
                }else if(y*10 % 1 == 0){
                    document.getElementById("selectedY").innerText = "Y: " +y.toFixed(1);
                    y = Number(y.toFixed(1));
                }else{
                    document.getElementById("selectedY").innerText = "Y: " +y.toFixed(2);
                    y = Number(y.toFixed(2));
                }
                flag = true;
            }
        }
    }catch(error){
        alert("Значение Y не валидно!");
    }
}

let submit = document.getElementById("submit");

submit.onclick = function(){
    checkY();
    if(flag){
        r = document.getElementById("R").options[document.getElementById("R").options.selectedIndex].value;
        sendRequest();
    }
}