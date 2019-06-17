
<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $image = $_POST['image'];
 
 require_once('imgConnect.php');
 
 $sql ="SELECT id FROM images ORDER BY id ASC";
 
 $res = mysqli_query($conn,$sql);
 
 //$id = 0;
 
 while($row = mysqli_fetch_array($res)){
 $id = $row['id'];
 }
 
 $path = "uploads/$id.jpeg";

 
 $actualpath = "http://192.168.0.104/$path";
 
 $sql = "INSERT INTO images(image) VALUES ('$actualpath')";
 
 if(mysqli_query($conn,$sql)){
 file_put_contents($path,base64_decode($image));
 echo "Successfully Uploaded";
 }
 
 mysqli_close($conn);
 }else{
 echo "Error";
 }