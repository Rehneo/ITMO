let x, y, r, testX, testY, testR;
let flagPoint = false;
let svg = document.getElementById("svg");
const MAX_LENGTH = 6;
const OFFSET_Y = 177;
const OFFSET_X = 20;

function setX(arg){
    x = arg;
    document.getElementById("selectedX").innerText = "X: " +arg;
}


function setY(arg){
    y = arg;
    document.getElementById("selectedY").innerText = "Y: " +y;
}

function setR(arg){
    if(flagPoint && arg !== r){
        let circle = svg.lastChild;
        circle.setAttribute("cx", (circle.getAttribute("cx")-250)*r/arg+250)
        circle.setAttribute("cy", (circle.getAttribute("cy")-250)*r/arg+250)
    }
    r = arg;
    document.getElementById("selectedR").innerText = "R: " +r;
    let t = document.querySelectorAll('#svg text');
    for(let i =0; i < t.length;i++){
        t[i].innerHTML = String(Math.pow(-1,i)*(r/2+r/2*(i>3)));
    }
}

document.getElementById("submitY").onclick = function(){
    testY = document.getElementById("Y").value.replace(",", ".");
    if(!testY){
        alert("Вы ничего не ввели!");
    }else if(testY.length > MAX_LENGTH) {
        alert("Вы ввели слишком длинную строку!")
    }else{
        testY = Number(testY);
        if(isNaN(testY)){
            alert("Значение Y должно быть числом!");
        }else{
            if(testY < -5 || testY > 3){
                alert("Значение Y должно быть в интервале [-5,3]!");
            }else{
                setY(testY);
            }
        }   
    }
}

document.getElementById("submitR").onclick = function(){
    testR = document.getElementById("R").value.replace(",", ".");
    if(!testR){
        alert("Вы ничего не ввели!");
    }else if(testR.length > MAX_LENGTH) {
        alert("Вы ввели слишком длинную строку!")
    }else{
        testR = Number(testR);
        if(isNaN(testR)){
            alert("Значение R должно быть числом!");
        }else{
            if(testR < 1 || testR > 4){
                alert("Значение R должно быть в интервале [1,4]!");
            }else{
                setR(testR);
            }
        }
    }
}

document.getElementById("submit").onclick = function () {
    if(r !== undefined && x !== undefined && y !== undefined){
        testX = x*(200/r)+250;
        testY = (y*(200/r*(-1))+250);
        if(testX > 500 || testY > 500|| testX < 0|| testY < 0){
            alert("Внимание! Точка выходит за пределы видимости координатной плоскости");
        }
        sendRequest();
    }else {
        alert("Пожалуйста, введите валидные значения X,Y,R")
    }
}

function setPoint(){
    if(flagPoint){
        svg.removeChild(svg.lastChild);
    }
    let circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    circle.setAttribute("cx", x * 100 *  2/r + 250);
    circle.setAttribute("cy", -y * 100 * 2/r + 250);
    circle.setAttribute("r", 3);
    circle.style.fill = "blue";
    flagPoint = true;
    svg.appendChild(circle);
}

svg.addEventListener("click", (event) =>{
    if(r !== undefined){
        testX = roundToThousandths((event.pageX - OFFSET_X - 250)/200*r);
        testY = roundToThousandths((-1)*(event.pageY - OFFSET_Y - 250)/200*r);
        if(testY < -5 || testY > 3) {
            alert("Значение Y должно быть в интервале [-5,3]!");
            return;
        }
        if(testX <-3 || testX > 5){
            alert("Значение X должно быть в интервале [-3,5]!");
            return;
        }
        setX(testX);
        setY(testY);
        sendRequest();
    }else {
        alert("Вы не ввели R!")
    }
})

function roundToThousandths(arg){
    if(arg === 0){
        return 0
    }
    if(arg*1000 % 1 !==0) {
        return Math.round(arg*1000)/1000;
    }
    if(arg*100 % 1 !==0){
        return Math.round(arg*100)/100;
    }
    if(arg*10 % 1 !==0){
        return Math.round(arg*10)/10;
    }
    return arg;
}
