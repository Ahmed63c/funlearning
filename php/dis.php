<?php

include 'imgconnect.php';
// Create connection


if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "UPDATE submission SET en='dis' WHERE en='enable'";


if(mysqli_query($conn,$sql)){
 echo 'disable';
 }else{
 echo 'oops! Please try again!';
 }
 
 mysqli_close($conn);
 
?>