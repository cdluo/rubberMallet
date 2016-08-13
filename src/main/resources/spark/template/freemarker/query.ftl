<#assign content>

<h1> RubberMallet 
	<img src="http://www.picgifs.com/graphics/c/court/graphics-court-777227.gif" id="loading"></img>
</h1>

<div id = "topics">
</div>

<div id="menu">
	<form id="file-form" action="/file" method="POST" target="hidden-iframe">
	  <input type="file" id="file" name="file" class = "inputFile"/>
	  <label for="file">Click to choose a file</label>
	</form>

	<div id="options">
		<input type="number" id="numTops" class="numberInput"> <p style = "position:absolute; top:25px; left:60px; font-family:Helvetica"> Number of topics </p>
		<input type="number" id="accuracy" class="numberInput" style = "top:84px"> <p style = "position:absolute; top:64px; left:60px; font-family:Helvetica"> Number of Iterations</p>
	</div>

	<button id="submit">Mallet</button>
</div>

<iframe name="hidden-iframe" style="display: none;"></iframe>

</#assign>
<#include "main.ftl">
