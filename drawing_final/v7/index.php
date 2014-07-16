<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Muller Form</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Muller Diagram Generator">
  <meta name="author" content="Thomas Tavolara">

	<!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
	<!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
	<!--script src="js/less-1.3.3.min.js"></script-->
	<!--append ‘#!watch’ to the browser URL, then refresh the page. -->
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link rel="icon" type="image/ico" href="urochester.ico">
  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

  <!-- Fav and touch icons -->
  <link rel="apple-touch-icon-precomposed" sizes="144x144" href="img/apple-touch-icon-144-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/apple-touch-icon-114-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/apple-touch-icon-72-precomposed.png">
  <link rel="apple-touch-icon-precomposed" href="img/apple-touch-icon-57-precomposed.png">
  
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/scripts.js"></script>
</head>

<body>
<?php 
$popError = $filterError = $customError = $distanceError = $linkageError = $clusteringError = $cluster_numError = $uploadError = "";
$pop_b = $filtering_b = $distance_b = $linkage_b = $clustering_b = FALSE;
$pop = $filtering = $distance = $linkage = $clustering = $uploadSuccess = $file_contents = $cluster_num = "";
$freq1 = .2; $gen1 = 3; $freq2 = .5; $gen2 = 1;
if ($_SERVER["REQUEST_METHOD"] == "POST")
{
	if(empty($_POST["filtering"]))
	{
		$filterError = " *please choose filtering option";
	}
	else
	{
		if($_POST["filtering"] == "custom")
		{
			if(empty($_POST["freq1"]) || empty($_POST["gen1"]) || empty($_POST["freq2"]) || empty($_POST["gen2"]))
			{
				$customError=" *please specify custom filter";
			}
			else
			{
				$filtering = "custom";
				$filtering_b = TRUE;
				$freq1 = $_POST["freq1"];
				$gen1 = $_POST["gen1"];
				$freq2 = $_POST["freq2"];
				$gen2 = $_POST["gen2"];
			} 
		}
		else
		{
			$filtering_b = TRUE;
			$filtering = "default";
		}
	}

	if(empty($_POST["dist_m"]))
	{
		$distanceError = " *please specify distance measure";
	}
	else
	{
		$distance_b = TRUE;
		$distance = $_POST["dist_m"];
	}

        if(empty($_POST["linkage"]))
        {
                $linkageError = " *please specify linkage";
        }
        else
        {
                $linkage_b = TRUE;
		$linkage = $_POST["linkage"];
		if(strcmp($linkage,"e")==0)
		{
			$linkage = "0";
		}
                if(strcmp($linkage,"r")==0)
                {
			$linkage = "1";
                }
                if(strcmp($linkage,"e")==0)
                {
			$linkage = "2";
                }
        }

        if(empty($_POST["clustering"]))
        {
                $clusteringError = " *please specify clustering options";
        }
        else
        {
		if(empty($_POST["cluster_num"]))
		{
			if($_POST["clustering"] == "specify")
			{
				$cluster_numError = " *please specify number of clusters"; 
			}
			if($_POST["clustering"] == "tolerate")
			{
				$cluster_numError = " *please specify maximum tolerated distance";
			}
		}
                else
		{
			$clustering_b = TRUE;
			$clustering = $_POST["clustering"];
			$cluster_num = $_POST["cluster_num"];
		}
        }

	if (empty($_POST["pop"]))
	{
		$popError = " *please enter population name";	
	}
	else
	{
		$pop_b = TRUE;
		$pop = $_POST["pop"];
	}





}
        function test_input($data)
        {
                $data = trim($data);
                $data = stripslashes($data);
                $data = htmlspecialchars($data);
                return $data;
        }
?>
	<div class="row clearfix">
		<div class="col-md-6 column">
			<h2>
				Muller Diagram Generator
			</h2>
			<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" enctype="multipart/form-data">
				<h4>Filtering options:</h4>
					<div class="tabit">
					<input type="radio" name="filtering" value="default" <?php if (isset($_POST["filtering"]) && $_POST["filtering"] == "default") echo 'checked="checked"';?>> Default (.2 frequency for 3 generations, .5 frequency for 1 generations)<br>
					<input type="radio" name="filtering" value="custom"<?php if (isset($_POST["filtering"]) && $_POST["filtering"] == "custom") echo 'checked="checked"';?>> Custom 
						<input type="text" class="box" name="freq1" value="<?php if(isset($_POST["filtering"]) && $_POST["filtering"] == "custom") { echo htmlentities ($_POST["freq1"]); }?>"> Freq 1
						<input type="text" class="box" name="gen1" value="<?php if(isset($_POST["filtering"]) && $_POST["filtering"] == "custom") { echo htmlentities ($_POST["gen1"]); }?>"> Gen 1
						<input type="text" class="box" name="freq2" value="<?php if(isset($_POST["filtering"]) && $_POST["filtering"] == "custom") { echo htmlentities ($_POST["freq2"]); }?>"> Freq 2
						<input type="text" class="box" name="gen2" value="<?php if(isset($_POST["filtering"]) && $_POST["filtering"] == "custom") { echo htmlentities ($_POST["gen2"]); }?>"> Gen 2
					<span class="error"><?php echo $filterError." ".$customError;?></span>
					</div>
				<h4>Clustering options:</h4>
					<div class="tabit">
					<strong>Distance Measure:  </strong>
						<input type="radio" name="dist_m" value="false" <?php if (isset($_POST["dist_m"]) && $_POST["dist_m"] == "false") echo 'checked="checked"';?>>  Euclidean    
						<input type="radio" name="dist_m" value="true" <?php if (isset($_POST["dist_m"]) && $_POST["dist_m"] == "true") echo 'checked="checked"';?>>  Manhattan
						<span class="error"><?php echo $distanceError;?></span>
						<br>
					<strong>Linkage:  </strong>
						<input type="radio" name="linkage" value="e" <?php if (isset($_POST["linkage"]) && $_POST["linkage"] == "e") echo 'checked="checked"';?>>  Single   	
						<input type="radio" name="linkage" value="r" <?php if (isset($_POST["linkage"]) && $_POST["linkage"] == "r") echo 'checked="checked"';?>>  Complete   
						<input type="radio" name="linkage" value="b" <?php if (isset($_POST["linkage"]) && $_POST["linkage"] == "b") echo 'checked="checked"';?>>  Average   
						<span class="error"><?php echo $linkageError;?></span>
						<br>
					<strong>Clustering?  </strong>
						<input type="radio" name="clustering" value="specify" <?php if (isset($_POST["clustering"]) && $_POST["clustering"] == "specify") echo 'checked="checked"';?>>  Specify clusters   
						<input type="radio" name="clustering" value="tolerate" <?php if (isset($_POST["clustering"]) && $_POST["clustering"] == "tolerate") echo 'checked="checked"';?>>  Tolerate distance
						<input type="text" class="box" name="cluster_num" maxlength="4" value="<?php if(isset($_POST["clustering"])) { echo htmlentities ($_POST["cluster_num"]); }?>">   
						<span class="error"><?php echo $clusteringError." ".$cluster_numError;?></span>
						<br>
					</div>
				<h4>Data:</h4>
					<div class="tabit">
					<strong>Name of population: </strong><input type="text" id="population" name="pop" value="<?php if(isset($_POST["pop"])) { echo htmlentities ($_POST["pop"]); }?>"><span class="error"><?php echo $popError;?></span><br>
					<strong>File input: </strong><input id="file_top" name="file" type="file"><span class="error">
<?php 
if ($_SERVER["REQUEST_METHOD"] == "POST")
{
        $allowedExts = array("txt");
        $temp = explode(".", $_FILES["file"]["name"]);
        $extension = end($temp);

        if (($_FILES["file"]["type"] == "text/plain") && (in_array($extension, $allowedExts)))
        {
                if ($_FILES["file"]["error"] > 0)
                {
                        echo "Error: " . $_FILES["file"]["error"] . "<br>";
                }
                else
                {
                        echo "Upload: " . $_FILES["file"]["name"] . "<br>";
                        echo "Type: " . $_FILES["file"]["type"] . "<br>";
                        echo "Size: " . ($_FILES["file"]["size"] / 1024) . " kB<br>";
                        echo "Stored in: " . $_FILES["file"]["tmp_name"] . "<br>";


			$execute_me = "java validate_input " . $_FILES["file"]["tmp_name"];
                        $output = exec($execute_me);

                        if(strcmp($output,"true") == 0)
			{
				$file_contents = file_get_contents($_FILES['file']['tmp_name']);
				echo "Success uploading and validating input file";				
			}
			else
			{
				echo "Error in reading file. Make sure that the format is correct.";
			}
			
                }
        }
        else
        {
                echo "Invalid file";
        }
}
?>
					</span>
					</div>



					<p class="help-block">
						Input format should look like this...
					</p>
			                <pre>
Population      Gene    Generation_x    Generation_y    ...     Generation_n
BYS2-C06        NHX1    0.0             0.1                     1.0
BYS2-C06        GAS1    0.0             0.0                     1.0
etc...
        			        </pre>
				<button type="submit" class="btn btn-default">Submit</button>
			</form>
		</div>
		<div class="col-md-6 column">
			<a href="http://validator.w3.org/check/referer">HTML 5 Validator</a>
			<br>
			<img src="muller.png" alt=" ">	
<?php
//include 'down.php';
if ($_SERVER["REQUEST_METHOD"] == "POST")
{
	//echo $filtering . " " . $freq1 . " " . $gen1 . " " . $freq2 . " " . $gen2 . "<br>". $distance ."<br>". $linkage ."<br>". $clustering . " " . $cluster_num . "<br>" . $pop;
	//echo "<br>" . $pop_b . " " . $filtering_b . " " . $distance_b . " " . $linkage_b . " " . $clustering_b;
	//echo "how";
	if($pop_b && $filtering_b && $distance_b && $linkage_b && $clustering_b)
	{
		//download("java driver " . $_FILES["file"]["tmp_name"] . " " . $freq1 . " " . $gen1 . " " . $freq2 . " " . $gen2 . " " . $distance . " " . $linkage . " " . $clustering . " " . $cluster_num . " " . $pop);
		$execute_me = "java -cp v2_f/ driver " . $_FILES["file"]["tmp_name"] . " " . $freq1 . " " . " " . $gen1 . " " . $freq2 . " " . $gen2 . " " . $distance . " " . $linkage . " " . $clustering . " " . $cluster_num . " " . $pop;
		//echo $execute_me."<br>";
		exec($execute_me);
		exec("chmod 777 muller.png");
		exec("mv muller.png muller_v2.png");

                $execute_me = "java -cp v3_f/ driver " . $_FILES["file"]["tmp_name"] . " " . $freq1 . " " . " " . $gen1 . " " . $freq2 . " " . $gen2 . " " . $distance . " " . $linkage . " " . $clustering . " " . $cluster_num . " " . $pop;
		//echo $execute_me."<br>";
                exec($execute_me);
                exec("chmod 777 muller.png");
                exec("mv muller.png muller_v3.png");

                $execute_me = "java -cp v4_f/ driver " . $_FILES["file"]["tmp_name"] . " " . $freq1 . " " . " " . $gen1 . " " . $freq2 . " " . $gen2 . " " . $distance . " " . $linkage . " " . $clustering . " " . $cluster_num . " " . $pop;
		//echo $execute_me."<br>";
                exec($execute_me);
                exec("chmod 777 muller.png");
                exec("mv muller.png muller_v4.png");

                $execute_me = "java -cp v5_f/ driver " . $_FILES["file"]["tmp_name"] . " " . $freq1 . " " . " " . $gen1 . " " . $freq2 . " " . $gen2 . " " . $distance . " " . $linkage . " " . $clustering . " " . $cluster_num . " " . $pop;
		//echo $execute_me."<br>";
                exec($execute_me);
                exec("chmod 777 muller.png");
                exec("mv muller.png muller_v5.png");

                $execute_me = "java -cp v6_f/ driver " . $_FILES["file"]["tmp_name"] . " " . $freq1 . " " . " " . $gen1 . " " . $freq2 . " " . $gen2 . " " . $distance . " " . $linkage . " " . $clustering . " " . $cluster_num . " " . $pop;
		//echo $execute_me."<br>";
                exec($execute_me);
                exec("chmod 777 muller.png");
                exec("mv muller.png muller_v6.png");

		exec("zip files.zip muller_v2.png muller_v3.png muller_v4.png muller_v5.png muller_v6.png");
		exec("rm *.png");

                //echo "<div id='download_link'><a href='download.php' target='_blank'>Download link</a></div>";
		echo "<div id='download_link'><a href='files.zip' target='_blank'>Download</a></div>";
		//exec("rm files.zip");
/*
		//echo get_file_contents($_FILES["file"]["tmp_name"] . "/" . $_FILES["file"]["name"]);
		//echo $filtering . " " . $freq1 . " " . $gen1 . " " . $freq2 . " " . $gen2 . "<br>". $distance ."<br>". $linkage ."<br>". $clustering . " " . $cluster_num . "<br>" . $pop;	
		$execute_me = "java driver " . $_FILES["file"]["tmp_name"] . " " . $freq1 . " " . $gen1 . " " . $freq2 . " " . $gen2 . " " . $distance . " " . $linkage . " " . $clustering . " " . $cluster_num . " " . $pop;
        	$output = exec($execute_me);
		//echo $output;
        	$file = "muller.png";
		
			
		header('Content-Description: File Transfer');
		header('Content-Type: application/octet-stream');
		header('Content-Disposition: attachment; filename='.basename($file));
		header('Expires: 0');
		header('Cache-Control: must-revalidate');
		header('Pragma: public');
		header('Content-Length: ' . filesize($file));
		ob_clean();
		flush();
		readfile($file);
		exit;
	//	exec("rm muller.png");
		

*/
	}
	else
	{
		//echo "fuck";
	}
}
?>
		</div>
	</div>
</body>
</html>
