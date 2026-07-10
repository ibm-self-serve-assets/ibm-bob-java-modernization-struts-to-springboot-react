<%@page import="com.pks.insurance.domain.User"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html>
<head>
<link rel="stylesheet" href="template/css/blue.css" type="text/css" />
<link rel="stylesheet" href="template/css/blue_bg.css" type="text/css" />

<link type="text/css" rel="stylesheet" href="template/css/jscal2.css" />
<link type="text/css" rel="stylesheet"
	href="template/css/border-radius.css" />
<link rel="stylesheet" type="text/css" href="template/css/gold/gold.css" />
<script src="template/js/jscal2.js"></script>
<script src="template/js/unicode-letter.js"></script>
<script src="template/js/lang/en.js"></script>
</head>
<body class="color_blue bg_blue width_fmax">
	<div class="center" align="center">
		<div id="area">
			<div id="leftcolumn">
				<div class="module_menu"></div>
			</div>
			<div id="maincolumn">
				<c:if test="${!empty error }">
					<!-- Display system error messages -->
					<dl id="system-message">
						<dt class="error">Error</dt>
						<dd class="error message fade">
							<ul>
								<li><c:out value="${error }" /></li>
							</ul>
						</dd>
					</dl>
				</c:if>
				<html:form method="post" action="user" styleClass="form-validate"
					styleId="registration">
					<!--  	<div class="componentheading">-->
					<c:if test="${!empty customer }">
						<fieldset>
							<legend>Update Profile</legend>
							<table class="contentpane" cellspacing="0" cellpadding="0"
								border="0" width="100%">
								<tbody>
									<tr>
										<td height="30"><label for="ssn">SSN </label>
										</td>
										<td><html:text name="UserForm" styleId="ssn"
											styleClass="inputbox validate-username" readonly="readonly"
									value="${customer.ssn }" property="ssn"/> <span class="serr" id="user">*</span>
										</td>
									</tr>
									<tr>
										<td height="30"><label for="passwd">Password</label></td>
										<td><html:password styleClass="inputbox"  styleId="passwd"
											name="UserForm" property="passwd" value="${customer.passwd }" /> <span
											class="serr" id="password">*</span>
										</td>
									</tr>
									<tr>
										<td height="30"><label for="cpasswd">Confirm
												Password</label>
										</td>
										<td><input type="password" class="inputbox"  id="cpasswd"  value="${customer.passwd }" /> <span
											class="serr" id="cpassword">*</span>
										</td>
									</tr>
									<tr>
										<td height="30"><label for="fName">First name</label></td>
										<td><html:text name="UserForm" styleId="fName" 
											styleClass="inputbox" size="15" value="${customer.firstName }" property="firstName"/><span
											class="serr" id="fname">*</span>
										</td>
									</tr>
									<tr>
										<td height="30"><label for="lName">Last name</label>
										</td>
										<td><html:text name="UserForm" styleId="lName" 
											styleClass="inputbox" size="15" value="${customer.lastName }" property="lastName"/><span
											class="serr" id="lname">*</span>
										</td>
									</tr>
									<tr>
										<td height="30"><label for="gender">Gender</label></td>
										<td><html:radio  styleId="gender" name="UserForm"
											value="M"  property="gender"> Male</html:radio> 
											<html:radio styleId="gender"
											name="gender" value="F"  property="gender">female</html:radio>
											<!--<c:if test="${customer.gender eq 'F' }">
										checked="checked"
										</c:if> />Female
										-->
										</td>
									</tr>
									<!-- 	<div id="calendar-container"></div> here we will display selection information -->

									<tr>
										<td height="30"><label for="dateOfBirth">Date of
												Birth</label>
										</td>
										<td>
											<!-- element that will contain the calendar --> <html:text
											 name="UserForm" styleId="dateOfBirth" style="width:10" 
											styleClass="inputbox" value="${customer.dateOfBirth } " property="dateOfBirthString"/><img
											src="template/images/calendar.png" id="calendar-trigger" />
											<script>
												Calendar
														.setup({
															trigger : "calendar-trigger",
															inputField : "dateOfBirth"
														});
											</script><span class="serr" id="dateOfBirth">*</span>
										</td>
									</tr>
									<tr>
										<td height="30"><label for="mobileNo">Contact no</label>
										</td>
										<td><html:text name="UserForm" styleId="mobileNo"
											styleClass="inputbox" value="${customer.mobileNo }" property="mobileNo"/><span
											class="serr" id="mobile">* </span>
										</td>
									</tr>

									<tr>
										<td height="30"><label for="emailId">Email id</label>
										</td>
										<td><html:text name="UserForm" styleId="emailId"
											styleClass="inputbox " value="${customer.email }" property="emailId"/><span
											class="serr" id="email">* </span>
										</td>
									</tr>
									<tr>
										<td height="30"><label for="city">City</label>
										</td>
										<td><html:text name="UserForm" styleId="city"
											styleClass="inputbox" value="${customer.city }" property="city"/> <span
											class="serr" id="cty">* </span>
										</td>
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
										<td><html:text name="UserForm" 
											styleClass="inputbox" value="${customer.drivingLicence }" property="drivingLicence"/><span
											class="serr" id="driving">* </span>
										</td>
									</tr>
									<tr>
										<td></td>
										<td height="30"><html:submit
											value="Update Profile" styleClass="button validate" style="height:50" property=""/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:reset
											value="Clear" styleClass="button validate" />
										</td>
									</tr>
								</tbody>
							</table>
						</fieldset>
					</c:if>
				</html:form>
			</div>
		</div>
	</div>
</body>
</html>
