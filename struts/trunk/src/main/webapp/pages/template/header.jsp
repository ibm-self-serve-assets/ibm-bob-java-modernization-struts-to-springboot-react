<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Start of the Header page -->
<div id="border-top" class="h_green">
	<div>
		<div>
			<span class="title">Automobile Insurance</span>
		</div>
		<logic:present name="LoginForm" property="ssn" scope="session">
			<logic:notEmpty name="LoginForm" property="ssn">
				<!-- <div style="float: left;"> -->
				<html:link forward="home">Home</html:link>
				<p>
					Welcome
					<bean:write name="LoginForm" property="ssn" />
					[
					<html:link href="logout.do">Sign out</html:link>
					]
				</p>
			</logic:notEmpty>
		</logic:present>
	</div>
</div>
<!-- End of Header page -->