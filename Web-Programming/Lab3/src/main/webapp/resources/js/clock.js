let hrs = document.getElementById("hrs");
let min = document.getElementById("min");
let sec = document.getElementById("sec");
let days = document.getElementById("days");
let month = document.getElementById("months");
let year = document.getElementById("year");
let currentTime = new Date();
days.innerHTML = (currentTime.getDate()<10?"0":"")+currentTime.getDate();
month.innerHTML = (currentTime.getMonth()<10?"0":"")+(currentTime.getMonth() + 1);
year.innerHTML = currentTime.getFullYear() + "";
setInterval(()=>{
    currentTime = new Date();
    hrs.innerHTML = (currentTime.getHours()<10?"0":"")+currentTime.getHours();
    min.innerHTML = (currentTime.getMinutes()<10?"0":"")+currentTime.getMinutes();
    sec.innerHTML = (currentTime.getSeconds()<10?"0":"")+currentTime.getSeconds();
}, 1000)