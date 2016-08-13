<#assign content>

<h1> RubberMallet 
	<img src="http://www.picgifs.com/graphics/c/court/graphics-court-777227.gif" id="loading"></img>
</h1>

<div id = "topics">
</div>

<form id="file-form" action="/file" method="POST" target="hidden-iframe">
  <input type="file" id="file-select" name="file"/>
  <button type="submit" id="upload-button">Upload</button>
</form>

<button id="submit">Submit</button>

<iframe name="hidden-iframe" style="display: none;"></iframe>

</#assign>
<#include "main.ftl">
