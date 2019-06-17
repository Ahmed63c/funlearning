
<?php
include 'imgConnect.php';
 
$sql = "select color from traffic  ";

 
$res = mysqli_query($conn,$sql);
 
$result = array();
 $r=0;
 $y=0;
 $g=0;
while($row = mysqli_fetch_array($res)){
array_push($result,
array($row[0]






)

);
if($row[0]=="red")
$r++;
else if($row[0]=="yellow")
	$y++;
else if($row[0]=="green")
	$g++;
}


$arrlength = count($row);





echo " red : $r ";
echo " yellow: $y ";
echo " green: $g ";
//echo json_encode(array("result"=>$result));
 
mysqli_close($conn);
 
?>