<#assign content>

<h1> RubberMallet 
	<img src="http://www.picgifs.com/graphics/c/court/graphics-court-777227.gif" id="loading"></img>
</h1>

<div id = "topics">
</div>

<form id="file-form" action="/file" method="POST" target="hidden-iframe">
  <input type="file" id="file-select" name="file"/>
</form>

<div id="options">
	<input type="number" id="numTops"> Number of topics
	<input type="number" id="accuracy"> Accuracy 1(worst) - 3 (best)
</div>

<button id="submit">Submit</button>

<iframe name="hidden-iframe" style="display: none;"></iframe>

</#assign>
<#include "main.ftl">
