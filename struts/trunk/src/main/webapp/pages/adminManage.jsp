<%@page import="com.pks.insurance.domain.User"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<html>
<head>
<script type="text/javascript" src="template/js/page/adminManage.js"></script>
<link href="template/css/icon.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="content-box">
		<div class="border">
			<div class="padding">
				<div class="clr"></div>
				<div id="toolbar-box">
					<div class="t">
						<div class="t">
							<div class="t"></div>
						</div>
					</div>
					<div class="m">
						<div class="toolbar" id="toolbar"></div>
						<div class="header icon-48-user">User Manager</div>

						<div class="clr"></div>
					</div>
					<div class="b">
						<div class="b">
							<div class="b"></div>
						</div>
					</div>
				</div>
				<div class="clr"></div>
				<c:if test="${!empty message}">
					<dl id="system-message">
						<dt class="message">Message</dt>
						<dd class="message message fade">
							<ul>
								<li><c:out value="${message}"/></li>
							</ul>
						</dd>
					</dl>
				</c:if>
				<div id="element-box">
					<div class="t">
						<div class="t">
							<div class="t"></div>
						</div>
					</div>
					<div class="m">
						<html:form action="searchUser" method="GET" styleId="adminForm">
							<table>
								<tbody>
									<tr>
										<td width="100%">Filter: <html:text name="SearchUserAdminForm"
											onchange="document.adminForm.submit();" property="ssn"/>
											<html:submit  value="Search" /> <html:reset 
											value="Reset" />
									</tr>
								</tbody>
							</table>
						</html:form>
						<html:form action="deleteUser" styleId="personForm">
							<html:hidden styleId="ssn" name="DeleteUserAdminForm" property="ssn"/>
							<display:table name="${customers}" requestURI="/searchUser.do" pagesize="3" export="true" class="adminlist" cellpadding="1">
<!-- 										<th class="title" width="15%">SSN</th> -->
<%-- 										<tr class="row${counter.count mod 2 }" id="${customer.ssn }"> --%>
											<display:column property="ssn" title="SSN" headerClass="title" group="1" sortable="true"/>
											<display:column property="firstName" title="First Name" headerClass="title" sortable="true" />
											<display:column property="lastName" title="Last Name" headerClass="title" sortable="true" />
											<c:choose>
													<c:when test="${customer.ssn eq 'adminadmin' }">Administrator</c:when>
													<c:otherwise>User</c:otherwise>
												</c:choose>
											
											<display:column property="mobileNo" title="Contact No" headerClass="title" sortable="true" />
											<display:column property="city" title="City" headerClass="title" sortable="true" />
											<display:column property="gender" title="Gender" headerClass="title" sortable="true" />
											<display:column property="email" title="E-Mail" autolink="true" headerClass="title" sortable="true" />
											<display:setProperty name="export.pdf.filename" value="UserList.pdf"/>
											<display:setProperty name="exprt.pdf" value="true"/>
											<%-- <c:if test="${customer.ssn ne 'adminadmin' }">
													<a onclick="del(this);">Delete</a>
												</c:if> --%>
							</display:table>
						</html:form>
						<div class="clr"></div>
					</div>
					<div class="b">
						<div class="b">
							<div class="b"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>