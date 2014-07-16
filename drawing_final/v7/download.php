<html>
<head>
</head>
<body>
<?php
                $file = "files.zip";

		// http headers for zip downloads
		header("Pragma: public");
		header("Expires: 0");
		header("Cache-Control: must-revalidate, post-check=0, pre-check=0");
		header("Cache-Control: public");
		header("Content-Description: File Transfer");
		header("Content-type: application/octet-stream");
		header("Content-Disposition: attachment; filename=\"".$file."\"");
		header("Content-Transfer-Encoding: binary");
		header("Content-Length: ".filesize($file));
		ob_end_flush();
		@readfile($file);
                //exit;
                //exec("rm muller.png");

?>	
</form>
</body>
</html>
