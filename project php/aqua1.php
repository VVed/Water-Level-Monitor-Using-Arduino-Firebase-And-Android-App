<?php
#API access key from Google API's Console
    define( 'API_ACCESS_KEY', 'AAAAwakGBFQ:APA91bFJKSuxomsT-BBbxhrfIZmmqdM7O1pHDNrU3kpiSr_fR7A3-tHFMHEVS0CwnQXqzKLwcavj4gyVjA0ZOQL1XMbdwa5CqP7bAxrujAcur0MaMxaRM5-9XHjJZJu5DjOmvqPeqvgv' );
    $registrationIds = $_GET['id'];
#prep the bundle
     $msg = array
          (
		'body' 	=> 'Wtaer level Low!! Open app to order now.',
		'title'	=> 'Water Level Monitor',
             	'icon'	=> 'myicon',/*Default Icon*/
              	'sound' => 'mySound'/*Default sound*/
          );
	$fields = array
			(
				'to'		=> $registrationIds,
				'notification'	=> $msg
			);
	
	
	$headers = array
			(
				'Authorization: key=' . API_ACCESS_KEY,
				'Content-Type: application/json'
			);
#Send Reponse To FireBase Server	
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		curl_close( $ch );
#Echo Result Of FireBase Server
echo $result;