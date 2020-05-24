 <h1>BDD Test framework</h1>
<h2>Behavior Driven Development Cucumber - Selenium , Restassured based automation framework for UI and Webservice automation including Extent reports</h2>

<h2>Steps to install at your system:</h2>
	<ul>
	<li>Clone the repository using "git clone <repository url>"</li>
	<li>Run maven "clean install"</li>
	</ul>

<h2>Directory Structure</h2>
<img src="imgs/structure.png" title = "directory structure"/>

<h2>Steps to create UI test</h2>
<h4>Refer UIDemo.feature </h4>
 <li>Set Browser name to be used in Framework.properties.</li> 
 <li>For driver create instance to </li>
<code>WebDriver driver = WebDriverUtil.Webdriver();</code> 

<h2>Steps to get data from data file</h2>
<li>Add Data in Yaml file with Data set names</li>
 <img src="imgs/Yamldata.jpg" title="Yaml data"</img>

<li>Create instance for YAMLReader class with file name and path</li>
Ex
<code> YAMLReader yamlReader =new YAMLReader("src\\test\\resources\\Data\\Demo.yaml");</code> 
  and call 'GetDataSet(<Dataset Name>)' ,This method returns data in map
<br><code>LinkedHashMap DataMap= yamlReader.GetDataSet("Valid login");<br>
       String username = DataMap.get("Username");</code> 





	