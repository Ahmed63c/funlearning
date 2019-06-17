<?php


// Create connection
include 'imgconnect.php';
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 


$sql = "UPDATE submission SET en='enable' WHERE en='dis'";


if(mysqli_query($conn,$sql)){
 echo 'enable';
 }else{
 echo 'oops! Please try again!';
 }
 
 mysqli_close($conn);
 
?>