<?php
include 'imgconnect.php';
 
$sql = "select en from submission";
 $key=0;
$res = mysqli_query($conn,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array($row[0]







)

);
if($row[0]=='enable')
$key='en';
else if($row[0]=='dis')
	$key='dis';

}


$arrlength = count($row);




if($key=='en')
echo "enable";
else if($key=='dis')
	echo "dis";
else 
	 echo"error";


 
mysqli_close($conn);
 
?>