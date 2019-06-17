<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $name= $_POST['name'];
  $password= $_POST['password'];
 
 require_once('imgConnect.php');
 
$sql = "select * from logintable where name='$name' and password='$password'";
 
$res = mysqli_query($conn,$sql);
 
$check = mysqli_fetch_array($res);
 
if(isset($check)){
echo 'success';
}else{
echo 'failure';
}
 
mysqli_close($conn);}
?>