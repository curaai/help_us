<?php
// $id = $_POST['id'];
// $title = $_POST['title'];
// $content = $_POST['content'];
// $receivers = $_POST['receivers'];
// $reportDate = $_POST['reportDate'];
// $accidentDate = $_POST['accidentDate'];
// $anonymous = $_POST['anonymous'];
// $gps = $_POST['gps'];

$uploaddir = 'uploads/';
$uploadfile = $uploaddir.basename($_FILES['file']['name']);
if (move_uploaded_file($_FILES['file']['tmp_name'], $uploadfile)) {
     echo "성공적으로 업로드 되었습니다.\n";
} else {
     echo "실패\n";
}
print_r($_FILES);
?>
