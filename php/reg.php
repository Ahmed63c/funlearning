<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
		$name = $_POST['name'];
		$password = $_POST['password'];
		$email = $_POST['email'];
		
		
		if($name == '' || $password == '' || $email == '' ){
			echo 'please fill all values';
		}else{
			require_once('imgConnect.php');
			$sql = "SELECT * FROM logintable WHERE  email='$email'";
			
			$check = mysqli_fetch_array(mysqli_query($conn,$sql));
			
			if(isset($check)){
				echo ' email already exist';
			}else{				
				$sql = "INSERT INTO logintable (name,password,email) VALUES('$name','$password','$email')";
				if(mysqli_query($conn,$sql)){
					echo 'successfully registered';
				}else{
					echo 'oops! Please try again!';
				}
			}
			mysqli_close($conn);
		}
}else{
echo 'error';
}

?>