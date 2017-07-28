<?php
  $id = $_POST['id'];
  $pw = $_POST['pw'];
  $name = $_POST['name'];
  $log  = "ID: ".$id.PHP_EOL.
        "PW: ".$pw.PHP_EOL.
        "NAME: ".$name.PHP_EOL.
        "-------------------------".PHP_EOL;
  file_put_contents('/public_html/log.txt', $log, FILE_APPEND);
  $conn = mysqli_connect('localhost', 'id2349841_root', 'santajen05', 'id2349841_fcm');
  $sql = "SELECT id FROM users";
  $result = mysqli_query($conn, $sql);
  while($row = mysqli_fetch_assoc($result)){
    if($row['id'] == $id){
      header("Result:0");
      exit;
    }
  }
  $query = "INSERT INTO users(id, pw, name) VALUES ('$id', '$pw', '$name')";
  mysqli_query($conn, $query);
  header("Result:1");
?>
