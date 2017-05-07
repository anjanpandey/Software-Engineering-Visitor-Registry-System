<?php 
	$first_name = 'First Name: ';
	$middle_init = 'Middle Init: ';
	$last_name = 'Last Name: ';
	$email_address = 'Email: ';
	$number_in_party = 'Number in Party: ';
	$destination = 'Destination: ';
	$traveling_for = 'Traveling for: ';
	$heard = 'Heard: ';
	$overnight = 'Overnight: ';
	$newspaper = 'Newspaper: ';
	
	$data = array($first_name => $_POST['firstname'], $middle_init => $_POST['middleinit'], $last_name => $_POST['lastname'], $email => $_POST['emailinput'], $number_in_party => $_POST['numberinput'], $destination => $_POST['destinput'], $traveling_for => $_POST['reason'], $heard => $_POST['heardfrom'], $overnight => $_POST['radios'], $newspaper => $_POST['checkboxes']);
	$file = 'FormData.json';
	$json = json_encode($data);
	file_put_contents($file,$json);
	
?>