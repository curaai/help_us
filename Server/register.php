<?php

	if(isset($_POST["Token"])){

		$token = $_POST["Token"];
		include './config.php';
		$conn = mysqli_connect($DB_HOST, $DB_USER, $DB_PASSWORD, $DB_NAME);
                $query = "INSERT INTO users (Token) VALUES('".$token."')";
		mysqli_query($conn, $query);

		mysqli_close($conn);
	}
?>