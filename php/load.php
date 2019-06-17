
<?php
 

 if($_SERVER['REQUEST_METHOD']=='GET'){
 $id = $_GET['id'];
 $sql = "select * from images where id = '$id'";
 require_once('Connect.php');
 
 $r = mysqli_query($conn,$sql);
 
 $result = mysqli_fetch_array($r);
 
 header('content-type: image/jpeg');
 

echo base64_decode($result['image']);
 
 mysqli_close($conn);
 
 }else{
 echo "Error";
 }