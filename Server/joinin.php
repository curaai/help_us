<?php
  $id = $_POST['id'];
  $pw = $_POST['pw'];
  include './config.php';
  $conn = mysqli_connect($DB_HOST, $DB_USER, $DB_PASSWORD, $DB_NAME);
  $query = "SELECT id, pw FROM users WHERE id = '$id' AND pw = '$pw'";
  $result = mysqli_query($conn, $query);
  $row = mysqli_num_rows($result);
  print $row;
  if($row == 0){
    $query = "INSERT INTO users(id, pw) Values ('$id', '$pw');";
		mysqli_query($conn, $query);
  } else{
    echo "failed";
  }
?>
