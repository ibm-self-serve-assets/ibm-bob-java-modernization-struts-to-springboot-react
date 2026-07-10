<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<link rel="stylesheet" href="pages/template/css/blue.css"
	type="text/css" />
<link rel="stylesheet" href="pages/template/css/blue_bg.css"
	type="text/css" />

<!-- Calender related CSS -->
<link type="text/css" rel="stylesheet"
	href="pages/template/css/jscal2.css" />
<link type="text/css" rel="stylesheet"
	href="pages/template/css/border-radius.css" />
<link rel="stylesheet" type="text/css"
	href="pages/template/css/gold/gold.css" />
<script type="text/javascript" src="pages/template/js/jscal2.js"></script>
<script type="text/javascript" src="pages/template/js/unicode-letter.js"></script>
<script type="text/javascript" src="pages/template/js/lang/en.js"></script>

<body class="color_blue bg_blue width_fmax">
	<div class="center" align="center">
		<div id="area">
			<div id="leftcolumn">
				<div class="module_menu"></div>
			</div>
			<div id="maincolumn">
				<!-- Display system error messages -->
				<dl id="system-message">
					<dt class="error">Error</dt>
					<dd class="error message fade">
						<html:errors />
					</dd>
				</dl>
				<html:form action="userRegister" styleClass="form-validate"
					styleId="registration">
					<!--  	<div class="componentheading">-->

					<fieldset>
						<legend>Registration</legend>
						<table class="contentpane" cellspacing="0" cellpadding="0"
							border="0" width="100%">
							<tbody>
								<tr>
									<td height="30"><label for="ssn">SSN </label></td>
									<td><html:text name="UserForm" styleId="ssn"
											styleClass="inputbox validate-username" property="ssn" /> <span
										class="serr" id="user">*</span></td>
								</tr>
								<tr>
									<td height="30"><label for="passwd">Password</label></td>
									<td><html:password styleClass="inputbox" styleId="passwd"
											name="UserForm" property="passwd" /> <span class="serr"
										id="password">*</span></td>
								</tr>
								<tr>
									<td height="30"><label for="cpasswd">Confirm
											Password</label></td>
									<td><html:password styleClass="inputbox" styleId="cpasswd"
											name="UserForm" property="cpasswd" /> <span class="serr"
										id="cpassword">*</span></td>
								</tr>
								<tr>
									<td height="30"><label for="fName">First name</label></td>
									<td><html:text name="UserForm" styleId="fName"
											styleClass="inputbox" size="15" property="firstName" /><span
										class="serr" id="fname">*</span></td>
								</tr>
								<tr>
									<td height="30"><label for="lName">Last name</label></td>
									<td><html:text name="UserForm" styleId="lName"
											styleClass="inputbox" size="15" property="lastName" /><span
										class="serr" id="lname">*</span></td>
								</tr>
								<tr>
									<td height="30"><label for="gender">Gender</label></td>
									<td><html:radio styleId="gender" name="UserForm" value="M"
											property="gender" />Male <html:radio name="UserForm"
											value="F" property="gender" />Female</td>
								</tr>
								<!-- 	<div id="calendar-container"></div> here we will display selection information -->

								<tr>
									<td height="30"><label for="dateOfBirth">Date of
											Birth</label></td>
									<td>
										<!-- element that will contain the calendar --> <html:text
											name="UserForm" readonly="readonly" style="width:10;"
											styleClass="inputbox" styleId="dateOfBirth"
											property="dateOfBirthString" /> <!-- <html:image
											src="pages/template/images/calendar.png"
											styleId="calendar-trigger" />
											-->
										<button id="calendar-trigger">...</button> <script
											type="text/javascript">
											Calendar.setup({
												trigger : "calendar-trigger",
												inputField : "dateOfBirth"
											});
										</script> <span class="serr" id="dateOfBirth">*</span>
									</td>
								</tr>
								<tr>
									<td height="30"><label for="mobileNo">Contact no</label></td>
									<td><html:text name="UserForm" styleId="mobileNo"
											styleClass="inputbox" property="mobileNo" /><span
										class="serr" id="mobile">* </span></td>
								</tr>

								<tr>
									<td height="30"><label for="emailId">Email id</label></td>
									<td><html:text name="UserForm" styleId="emailId"
											styleClass="inputbox " property="email" /><span class="serr"
										id="email">* </span></td>
								</tr>
								<tr>
									<td height="30"><label for="city">City</label></td>
									<td><html:text name="UserForm" styleId="city"
											styleClass="inputbox" property="city" /> <span class="serr"
										id="cty">* </span></td>
								</tr>
								<tr />

								<tr>
									<td height="30">Blood Group</td>
									<td><html:select name="UserForm" property="bloodGroup"
											styleClass="inputbox">
											<c:forEach items="O+,O-,A+,A-,B+,B-,AB+,AB-" var="item">
												<html:option value="${item}">${item}</html:option>
											</c:forEach>
										</html:select></td>
									<td></td>
								</tr>
								<tr>
									<td height="30">Driving License</td>
									<td><html:text name="UserForm" styleClass="inputbox"
											property="drivingLicence" /><span class="serr" id="driving">*
									</span></td>
								</tr>
								<tr>
									<td></td>
									<td height="30"><html:submit value="Register"
											styleClass="button validate" style="height:50;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:reset
											value="Clear" /></td>
								</tr>
							</tbody>
						</table>
					</fieldset>
				</html:form>
			</div>
		</div>
	</div>
</body>
</html>
