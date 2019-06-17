<?php

//include 'imgconnect.php';
$color =isset($_POST['color'])?$_POST['color'] : '';


  if ( $color == ''){
 echo 'please fill all values';
 }
 else{
 require_once('imgconnect.php');
  
 

  $sql = "INSERT INTO traffic (color) VALUES ('$color')";
  
  if(mysqli_query($conn,$sql)){
 echo 'success';
 }else{
 echo 'oops! Please try again!';
 }
 
 mysqli_close($conn);
 }
 ?>