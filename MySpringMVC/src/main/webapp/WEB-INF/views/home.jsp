<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <style>
			.centered_div {
			   width: Xu;
			   height: Yu;
			   position: absolute;
			   top: 50%;
			   left: 40%;
			   margin-left: -(X/2)u;
			   margin-top: (Y/2)u;
			}
			.main_page_image_class{
			  width: 100px;
			  height: 100px;
			  position:absolute;
		   		top: 30%;
		   		left: 43%;
			}
		</style>
    </head>
    <body>
        <img class="main_page_image_class" src="resources\image\main_page_iamge.png" alt="Youtube Video Analysis">
    	<div class="centered_div">
	    	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

	  		<form class="example" method="POST" action="/MySpringMVC/output">
			  
			  <input type="text" placeholder="Search.." name="search">
			  <button type="submit"><i class="fa fa-search"></i></button>
			</form> 
		</div>

    </body>
</html>

