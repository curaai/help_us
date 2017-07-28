<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title></title>
  </head>
  <body>
    <?php
    	$conn = mysqli_connect("localhost","id2349841_root","santajen05","id2349841_fcm");
    	$sql = " Select Token From users";
    	$result = mysqli_query($conn,$sql);
    	$tokens = array();
    	if(mysqli_num_rows($result) > 0 ){
    		while ($row = mysqli_fetch_assoc($result)) {
    			$tokens[] = $row["Token"];
          echo $row["Token"];
          echo "<br>";

    		}
    	}
    	mysqli_close($conn);
    	$message = array("message" => "FCM PUSH NOTIFICATION TEST MESSAGE");
    	$message_status = send_notification($tokens, $message);
    	echo $message_status;
      if  (in_array  ('curl', get_loaded_extensions())) {
        echo "cURL is installed on this server";
      }
      else {
        echo "cURL is not installed on this server";
      }
      if (!empty($_SERVER['HTTPS']) && $_SERVER['HTTPS'] != 'off') {
        echo "TRUE!!!!!!";
      }

      function send_notification($tokens, $message)
    	{
        $msg = array
          (
		          'body' 	=> 'Body  Of Notification',
		          'title'	=> 'Title Of Notification',
          );
    		$url = 'https://fcm.googleapis.com/fcm/send';
    		$fields = array(
    			 'registration_ids' => $tokens,
           "notification" => $msg
    			);
    		$headers = array(
    			'Authorization:key = AAAA3RVU2Yw:APA91bHI21QZ-OTViwAjdKJ2MD96tV2yBzWlQ4CwaKhUZblTTOghopgf-l93S5OM0AafugcSZJCVF-YSsuQQYjxylW_YUXqyGM4hQB5ygKcTqKt2bSwodrNAKxtesYu7-h__p_gNyyfK',
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
        echo curl_getinfo($ch);
        $result = curl_exec($ch);
        if ($result === FALSE) {
           die('Curl failed: ' . curl_error($ch));
        }
        curl_close($ch);
        return $result;
    	}
     ?>

  </body>
</html>
