<?php
  $id = $_POST['id'];
  $pw = $_POST['pw'];
  $token = $_POST['token'];
  include './config.php';
  $conn = mysqli_connect($DB_HOST, $DB_USER, $DB_PASSWORD, $DB_NAME);
  $query = "SELECT id, pw FROM users WHERE id = '$id' AND pw = '$pw'";
  $result = mysqli_query($conn, $query);
  $row = mysqli_num_rows($result);
  if($row > 0){
      header("Result: 1");
  } else{
    header("Result: 0");
  }
  $query = "UPDATE users SET Token = '$token' WHERE id= '$id'";
  $result = mysqli_query($conn, $query);
  //완료
?>
