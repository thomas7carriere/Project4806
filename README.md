<H1>Mini-Survey Monkey</H1>
<H2> SYSC 4806 Project</H2>

[![Build Status](https://www.travis-ci.com/thomas7carriere/Project4806.svg?branch=master)](https://www.travis-ci.com/thomas7carriere/Project4806)

<H3>Group Members:</H3>

 - Aritra Sengupta
 - Dominic Kocjan
 - Kelly Harrison
 - Michael Evans
 - Thomas Carriere
 - Yu-Kai Yang

<H3>Project Description:</H3>
Surveyor can create a survey with a list of Questions. Questions can be open-ended (text), asking for a number within a range, or asking to choose among many options.  Users fill out a survey that is a form generated based on the type of questions in the survey. Surveyor can close the survey whenever they want (thus not letting in new users to fill out the survey), and at that point a survey result is generated, compiling the answers: for open-ended questions, the answers are just listed as-is, for number questions a histogram of the answers is generated, for choice questions a pie chart is generated

<H3>Milestones:</H3>

<table>
	<tr>
		<th>MileStone</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>1</td>
		<td>
			<ul>
				<li>Setup GitHub Repository</li>
				<li>Added Travis CI Integration</li>
				<li>Added deployment to Heroku</li>
				<li>Can create questions</li>
				<li>Can create surveys</li>
				<li>Can list surveys</li>
			</ul>
		</td>
	</tr>
	<tr>
		<td>2</td>
		<td>
			<ul>
				<li>Add login page for surveyor</li>
				<li>Answer surveys</li>
				<li>Convert survey answers to histogram</li>
				<li>Convert survey answers to pie chart</li>
				<li>Validate form inputs</li>
			</ul>
		</td>
	</tr>
	<tr>
		<td>3</td>
		<td></td>
	</tr>
</table>

<H3>Endpoints</H3>

<table>
	<tr>
		<th>URL</th>
		<th>Method</th>
	</tr>
	<tr>
		<td>/survey/create</td>
		<td>
			<ul>
				<li>GET</li>
				<li>POST</li>
			</ul>
		</td>
	</tr>
	<tr>
		<td>/survey/view</td>
		<td>
			<ul>
			    <li>GET</li>
			</ul>
		</td>
	</tr>
    <tr>
        <td>/survey/view/{surveyId}</td>
        <td>
            <ul>
                <li>GET</li>
            </ul>
        </td>
    </tr>
</table>

<H3>Links</H3>

- Heroku: https://project4806.herokuapp.com/
- TravisCI: https://www.travis-ci.com/github/thomas7carriere/Project4806
  
These need to be added to the README:
- database schema 
