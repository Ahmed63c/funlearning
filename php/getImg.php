
<?php
 require_once('imgConnect.php');
 
 $sql = "select image from images";
 
 $res = mysqli_query($conn,$sql);
 
 $result = array();
 
 while($row = mysqli_fetch_array($res)){
 array_push($result,array('image'=>$row['image']));
 }
 
 echo json_encode($result);
 
 mysqli_close($conn);