<?php 
	include './config.php';
	function send_notification ($tokens, $message)
	{
		$url = 'https://fcm.googleapis.com/fcm/send';
		$fields = array(
			'to' => $tokens[0],
			'data' => $message
		);

		$headers = array(
			'Authorization:key=AAAA3RVU2Yw:APA91bHI21QZ-OTViwAjdKJ2MD96tV2yBzWlQ4CwaKhUZblTTOghopgf-l93S5OM0AafugcSZJCVF-YSsuQQYjxylW_YUXqyGM4hQB5ygKcTqKt2bSwodrNAKxtesYu7-h__p_gNyyfK',	
			'Content-Type: application/json'
		);

	   $ch = curl_init();
       curl_setopt($ch, CURLOPT_URL, $url);
       curl_setopt($ch, CURLOPT_POST, true);
       curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
       curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
       curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);  
       curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
       curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
       $result = curl_exec($ch);           
       if ($result === FALSE) {
           die('Curl failed: ' . curl_error($ch));
       }
       curl_close($ch);
       return $result;
	}
	
	$conn = mysqli_connect($DB_HOST, $DB_USER, $DB_PASSWORD, $DB_NAME);

	$sql = "Select Token From users";

	$result = mysqli_query($conn,$sql);
	$tokens = array();

	if(mysqli_num_rows($result) > 0 ){
		while ($row = mysqli_fetch_assoc($result)) {
			$tokens[] = $row["Token"];
		}
	}

	mysqli_close($conn);
	$myMessage = $_POST["message"];

	$message = array("message" => $myMessage);
	$message_status = send_notification($tokens, $message);

	
	echo $message_status;
 ?>