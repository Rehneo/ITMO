<?php
    header('Access-Control-Allow-Origin: *');
    header('Access-Control-Allow-Methods: GET, POST');
    header("Access-Control-Allow-Headers: Content-Type, X-Auth-Token, Origin,  Authorization, Start-Request");
    
    session_start();
    if (!isset($_SESSION["response"])) {
        $_SESSION["response"] = array();
    }


    if ($_SERVER['REQUEST_METHOD'] == 'GET'){
        $isStartRequest = $_SERVER['HTTP_START_REQUEST'];
        if($isStartRequest == "true"){
            foreach (array_reverse($_SESSION["response"]) as $t) {
                echo "<tr>";
                echo "<td align='center'>" . $t["x"] . "</td>";
                echo "<td align='center'>" . $t["y"] . "</td>";
                echo "<td align='center'>" . $t["r"] . "</td>";
                echo "<td align='center'>" . $t["ans"] . "</td>";
                echo "<td align='center'>" . $t["time"] ."</td>";
                echo "<td align='center'>" . $t["runtime"] . "</td>";
                echo "</tr>";
            }
        }else if ($isStartRequest == "false"){
            date_default_timezone_set($_GET['timezone']);
            $r = $_GET['r'];
            $x = $_GET['x'];
            $y = $_GET['y'];
            $ans = '';
            $flag = true;
            $start_time = microtime(true);
            $maximum = 6;
            if(!is_numeric($r)){
                $flag = false;
            }
            if(!is_numeric($x)){
                $flag = false;
            }
            if(!is_numeric($y)){
                $flag = false;
            }
            if($flag){
                if (!in_array($x, array(-3,-2,-1,0,1,2,3,4,5)))
                    $flag = false;
                if (!in_array($r, array(1,2,3,4,5)))
                    $flag = false;
                if ($y < -3 || $y > 3)
                    $flag = false;
            }
            if (strlen($y) > $maximum || strlen($x) > $maximum || strlen($r) > $maximum)
                $flag = false;


            if($flag){
                if((($x*$x+$y*$y <= $r*$r) && ($x >= 0 && $y <= 0)) ||
                (($x >= (-1)*$r && $y <= 0.5*$r) && ($x <= 0 && $y >= 0)) ||
                (($x+$y > (-1)*$r) && ($x < 0 && $y < 0))){
                    $ans = "yes";
                }else{
                    $ans = "no";
                }
            }    


            if($flag){
                $response = array(
                    "x" => $x,
                    "y" => $y,
                    "r" => $r,
                    "ans" => $ans,
                    "time" => date("Y-m-d H:i:s"),
                    "runtime" => microtime(true) - $start_time
                );
                array_push($_SESSION["response"], $response);
            }else{
                $response = array(
                    "x" => $x,
                    "y" => $y,
                    "r" => $r,
                    "ans" => "non-valid-data",
                    "time" => date("Y-m-d H:i:s"),
                    "runtime" => microtime(true) - $start_time
                );
                array_push($_SESSION["response"], $response);
            }

            foreach (array_reverse($_SESSION["response"]) as $t) {
                echo "<tr>";
                echo "<td align='center'>" . $t["x"] . "</td>";
                echo "<td align='center'>" . $t["y"] . "</td>";
                echo "<td align='center'>" . $t["r"] . "</td>";
                echo "<td align='center'>" . $t["ans"] . "</td>";
                echo "<td align='center'>" . $t["time"] ."</td>";
                echo "<td align='center'>" . $t["runtime"] . "</td>";
                echo "</tr>";
            }
        }
    }else{
        http_response_code(407);
        return;
    }
?>