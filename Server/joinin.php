<?php
  $id = $_POST['id'];
  $pw = $_POST['pw'];
  $name = $_POST['name'];
  include './config.php';
  $conn = mysqli_connect($DB_HOST, $DB_USER, $DB_PASSWORD, $DB_NAME);
  $sql = "SELECT id FROM users";
  $result = mysqli_query($conn, $sql);
  while($row = mysqli_fetch_assoc($result){
    if($ow['id'] == $id){
      header("Result: Already Exists!");
      exit;
    }
  }
  $query = "INSERT INTO users(id, pw, name) Values ('$id', '$pw', '$name');";
	mysqli_query($conn, $query);
  header("Result: Success!");
  }
?>
