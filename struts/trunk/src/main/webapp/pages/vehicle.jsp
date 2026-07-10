<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html>
<head>
<link rel="stylesheet" href="template/css/blue.css" type="text/css" />
<link rel="stylesheet" href="template/css/blue_bg.css" type="text/css" />
</head>
<body>
	<div>
		<fieldset>
			<legend>Enter Vehicle Details</legend>
			<!-- Display system error messages -->
			<dl id="system-message">
				<dt class="error">Error</dt>
				<dd class="error message fade">
					<html:errors />
				</dd>
			</dl>
			<html:form action="vehicleRegister" styleId="vehicleForm">
				<table>
					<tr>
						<td>Vehicle Type</td>
						<td><html:select styleId="type" name="VehicleForm"
								property="type">
								<html:option value="">Select Vehicle Type</html:option>
								<html:option value="2">Two Wheeler</html:option>
								<html:option value="4">Four Wheeler</html:option>
							</html:select></td>
					</tr>
					<tr>
						<td>Make</td>
						<td><html:select name="VehicleForm" property="make"
								styleId="make"
								onchange="javascript: listboxchange(this.options[this.selectedIndex].value);">
								<html:option value="">Select make</html:option>
								<html:option value="maruti">Maruti</html:option>
								<html:option value="bmw">BMW</html:option>
							</html:select></td>
					</tr>
					<tr>
						<td>Model<span class="serr">*</span></td>
						<td><html:select name="VehicleForm" property="model"
								styleId="model">
								<html:option value="">Select model</html:option>
								<html:option value="bmw-100">BMW - 100</html:option>
								<html:option value="bmw-200">BMW - 200</html:option>
								<html:option value="alto">Alto</html:option>
								<html:option value="hondacity">Honda City</html:option>
							</html:select></td>
						<td><span class="serr" id="modelType"> </span></td>
					</tr>
					<tr>
						<td>Year</td>
						<td><html:select name="VehicleForm" property="mfYear">
								<c:forEach begin="1970" end="2012" var="year">
									<html:option value="${year}">${year}</html:option>
								</c:forEach>
							</html:select></td>
					</tr>
					<tr>
						<td>Vehicle Registration No.<span class="serr">*</span></td>
						<td><html:text name="VehicleForm" property="regNo" /></td>
						<td><span class="serr" id="registration"> </span></td>
					</tr>
					<tr>
						<td>No of Accident</td>
						<td><html:select name="VehicleForm" property="totalAccident">
								<c:forEach begin="0" end="9" var="accident">
									<html:option value="${accident}">${accident}</html:option>
								</c:forEach>
							</html:select></td>
						<td></td>
					</tr>


					<tr>
						<td>Premium Detail</td>
						<td><html:select styleId="policyType" name="VehicleForm"
								property="policyType" size="1">
								<html:option value=" ">-select Premium
								Detail-</html:option>
								<html:option value="Bodily Injury">Bodily Injury</html:option>
								<html:option value="Collision">Collision</html:option>
								<html:option value="Medical Payment">Medical Payment</html:option>
								<html:option value="Physical Damage">Physical Damage</html:option>
							</html:select> &nbsp;Premium Amount&nbsp;<html:select name="VehicleForm"
								property="policyAmount" size="1">
								<html:option value=" ">-select-</html:option>
								<html:option value="5000">5000</html:option>
								<html:option value="10000">10000</html:option>
								<html:option value="15000">15000</html:option>
							</html:select></td>
						<td><span class="serr" id="tpolicy"> </span></td>
					</tr>
					<tr>
						<td></td>
						<td><html:submit value="Submit" /></td>
						<td><html:reset value="Reset" /></td>
					</tr>
					<tr>
						<td></td>
						<td><span class="serr">* Vehicle Registration must be
								Alpha Numeric </span></td>
					</tr>
					<tr>
						<td></td>
						<td><span class="serr"> Should be of 6 characters </span></td>
					</tr>
				</table>
			</html:form>
		</fieldset>
	</div>
</body>
</html>