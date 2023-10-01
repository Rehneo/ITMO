let canvas = document.getElementById("graph");
let context = canvas.getContext("2d");
let r = 0;
const pWidth = canvas.clientWidth;
const pHeight = canvas.clientHeight;
streakInterval = pWidth*0.2;
drawG(0);
function drawG(r){
    context.beginPath();
    context.moveTo(0, pHeight/2);
    context.lineTo(pWidth, pHeight/2);
    context.moveTo(pWidth/2, 0);
    context.lineTo(pWidth/2, pHeight);
    context.moveTo(pWidth/2, pHeight/2);
    context.arc(pWidth/2, pHeight/2, 2*streakInterval, 0, 0.5*Math.PI);
    context.moveTo(pWidth/2 - 2*streakInterval, pHeight/2);
    context.lineTo(pWidth/2,pHeight/2+2*streakInterval);
    context.lineTo(pWidth/2,pHeight/2);
    context.lineTo(pWidth/2 - 2*streakInterval, pHeight/2);
    context.fillStyle = "white";
    context.fillRect(pWidth/2, pHeight/2, -2*streakInterval, -1*streakInterval);
    context.fill();
    let xPos;
    context.font = "30px cursive";
    context.fillStyle = "black";
    for(let i = 0;i<4;i+=1){
        xPos = pWidth/2+streakInterval*(i%2+1)*Math.pow(-1,i<2);
        context.moveTo(xPos,pHeight/2-10);
        context.lineTo(xPos,pHeight/2+10);
        context.fillText(Math.pow(-1,i<2)*r/(((i+1)%2+1)), xPos-2, pHeight/2-15);
    }
    let yPos;
    for(let i = 0;i<4;i+=1){
        yPos = pHeight/2+streakInterval*(i%2+1)*Math.pow(-1,i<2);
        context.moveTo(pWidth/2-10,yPos);
        context.lineTo(pWidth/2+10,yPos);
        context.fillText(Math.pow(-1,i>=2)*r/(((i+1)%2+1)), pWidth/2+15, yPos+2);
    }
    context.stroke();
}

function setR(){
    r = document.getElementById("R").options[document.getElementById("R").options.selectedIndex].value;
    document.getElementById("selectedR").innerText = "R: " + r;
    context.clearRect(0,0,550,550);
    drawG(r);
}

function setPoint(x,y,r){
    xRel = pWidth/2+(2*streakInterval*x)/r;
    yRel = (pHeight/2-(2*streakInterval*y)/r);
    if(xRel > 550 || yRel > 550|| xRel < 0|| yRel < 0){
        alert("Выход за пределы видимости координатной плоскости!");
    }else {
        context.clearRect(0,0,550,550);
        drawG(r);
        context.fillStyle = "blue";
        context.beginPath();
        context.moveTo(xRel, yRel);
        context.arc(xRel, yRel, 5, 0, 2 * Math.PI);
        context.closePath();
        context.fill();
    }
}

