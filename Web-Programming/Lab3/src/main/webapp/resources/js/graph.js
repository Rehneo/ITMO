let x, y, r, testX, testY;

let svg = document.getElementById("svg");


function setR(value){
    if(r === value){
        return;
    }
    let c = document.querySelectorAll('#svg circle');
    for(let i = 0; i < c.length; i ++){
        c[i].setAttribute("cx", (c[i].getAttribute("cx")-250)*r/value+250);
        c[i].setAttribute("cy", (c[i].getAttribute("cy")-250)*r/value+250);
    }
    let elements = document.querySelectorAll('.r-button');
    elements.forEach(element => {
        if(element.value === r){
            element.style.backgroundColor = '#fff';
        }else if(element.value === value){
            element.style.backgroundColor = '#2196f3';
        }
    });
    r = value;
    let t = document.querySelectorAll('#svg text');
    for(let i =0; i < t.length;i++){
        t[i].innerHTML = String(Math.pow(-1,i)*(r/2+r/2*(i>3)));
    }
}

function setPoint(x, y, r, result){
    let circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    circle.setAttribute("cx", x * 100 *  2/r + 250);
    circle.setAttribute("cy", -y * 100 * 2/r + 250);
    circle.setAttribute("r", 4);
    if(result === 'true'){
        circle.style.fill = "green";
    }else{
        circle.style.fill = "blue";
    }
    svg.appendChild(circle);
}


function setPoints(s){
    let points = s.split("/");
    for(let i = 0;i < points.length - 1;i++){
        let point = points[i].split(" ");
        setPoint(point[0], point[1], r, point[3]);
    }
}

svg.addEventListener("click", (event) =>{
    if(r !== undefined){
        testX = (event.offsetX - 250)/200*r;
        testY = (-1)*(event.offsetY - 250)/200*r;
        if(testY < -4 || testY > 4) {
            alert("Значение Y должно быть в интервале [-4,4]!");
            return;
        }
        if(testX <-3 || testX > 5){
            alert("Значение X должно быть в интервале [-3,5]!");
            return;
        }
        x = roundToThousandths(testX);
        y = roundToThousandths(testY);
        addPoint(
            [
                { name: "x", value: x.toString() },
                { name: "y", value: y.toString() },
                { name: "r", value: r.toString() }
            ]
        )
    }else {
        alert("Вы не ввели R!")
    }
})

function roundToThousandths(arg){
    arg = String(arg);
    if(arg.length > 6){
        arg = arg.slice(0, 6);
    }
    return Number(arg);
}
