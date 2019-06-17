<?php
include 'imgconnect.php';
 

$sql = "select name from attendance";
 

$res = mysqli_query($conn,$sql);
 
$result = array();
while($row = mysqli_fetch_array($res)){
array_push($result,
array(


"name"=>$row['name']





)

);}
echo json_encode(array("name"=>$result));
 
mysqli_close($conn);
 ?>
