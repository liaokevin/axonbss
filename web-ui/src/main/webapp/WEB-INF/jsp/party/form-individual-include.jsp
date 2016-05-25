<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="../include.jsp" %>
<%--
  ~ Copyright (c) 2010-2012. Axon Framework
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>
<div class="row">
    <div class="span14">

        <form:form commandName="individual">
            <form:errors path="*" cssClass="alert-message block-message error" element="div"/>
            <form:hidden path="partyId"/>
            <table>
                <tr>
                    <td><spring:message code="individual.firstName"/>:</td>
                    <td><form:input path="firstName"/></td>
                    <td><form:errors path="firstName" cssClass="errorBox"/></td>
                </tr>
                <tr>
                    <td><spring:message code="individual.lastName"/>:</td>
                    <td><form:input path="lastName"/></td>
                    <td><form:errors path="lastName" cssClass="errorBox"/></td>
                </tr>
                <tr>
                    <td colspan="3">
                        <input class="btn primary" type="submit" name="submit" value="Create"/>
                        <input class="btn" type="reset" name="reset" value="Reset"/>
                        <a href="${ctx}/party/list" class="btn">Cancel</a>
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
</div>