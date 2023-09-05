function addData(){
    let table = document.getElementById("response-data");
    const data = decodeURIComponent(document.cookie).split("; ");
    let output = "";
    if(data[data.length - 1] !== '' ){
        data.forEach(element => {
            if(element.indexOf("number") !== 0){
                t = element.substring(3).split(",");
                output += "<tr>";
                t.forEach(e => {
                    output += "<td align=\'center\'>";
                    output += e;
                    output += "</td>";
                })
                output += "</tr>";
            }
        })
    }
    table.innerHTML = output;
}
addData();