<?php
    header('Access-Control-Allow-Origin: *');
    header('Access-Control-Allow-Methods: GET, POST');
    header("Access-Control-Allow-Headers: Content-Type, X-Auth-Token, Origin,  Authorization");
    date_default_timezone_set( 'Europe/Moscow' );
    $start_time = microtime(true);
    $ans = '';
    $response = '';
    $r = $_GET['r'];
    $x = $_GET['x'];
    $y = $_GET['y'];
    $flag = true;
    $maximum = 6;
    if ($_SERVER['REQUEST_METHOD'] === 'GET'){
        if(!is_numeric($r)){
            $flag = false;
        }
        if(!is_numeric($x)){
            $flag = false;
        }
        if(!is_numeric($y)){
            $flag = false;
        }
        if ($x < -3 || $x > 5)
            $flag = false;
        if ($r < 0 || $r > 5)
            $flag = false;
        if ($y < -3 || $y > 3)
            $flag = false;
        if (strlen($y) > $maximum || strlen($x) > $maximum || strlen($r) > $maximum)
            $flag = false;
        if((($x*$x+$y*$y <= $r*$r) && ($x >= 0 && $y <= 0)) ||
        (($x >= (-1)*$r && $y <= 0.5*$r) && ($x <= 0 && $y >= 0)) ||
        (($x+$y > (-1)*$r) && ($x < 0 && $y < 0))){
            $ans = "yes";
        }else{
            $ans = "no";
        }
        if($flag){
            $response .= $x;
            $response .= ",";
            $response .= $y;
            $response .= ",";
            $response .= $r;
            $response .= ",";
            $response .= $ans;
            $response .= ",";
            $response .= date("Y-m-d H:i:s");
            $response .= ",";
            $response .= microtime(true) - $start_time;
            echo $response;
        }else{
            $response .= $x;
            $response .= ",";
            $response .= $y;
            $response .= ",";
            $response .= $r;
            $response .= ",";
            $response .= "non-valid-data";
            $response .= ",";
            $response .= date("Y-m-d H:i:s");
            $response .= ",";
            $response .= microtime(true) - $start_time;
            echo $response;
        }
    }
?>