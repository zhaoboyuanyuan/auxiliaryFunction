<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
        xmlns:lxslt="http://xml.apache.org/xslt"
        xmlns:stringutils="xalan://org.apache.tools.ant.util.StringUtils">
<xsl:output method="html" indent="yes" encoding="utf-8"
  doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN" />
<xsl:decimal-format decimal-separator="." grouping-separator="," />
<!--
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 -->

<!--

 Sample stylesheet to be used with Ant JUnitReport output.

 It creates a non-framed report that can be useful to send via
 e-mail or such.

-->
    <xsl:template match="testsuites">

        <html>

            <head>

                <title>zhaoyongjian AppTestReport</title>

                <style type="text/css">

                    body {

                    font:normal 68% verdana,arial,helvetica;

                    color:#000000;

                    }

                    table tr td, table tr th {

                    font-size: 68%;

                    }

                    table.details tr th{

                    font-weight: bold;

                    text-align:left;

                    background:#a6caf0;

                    }

                    table.details tr td{

                    background:#eeeee0;

                    }



                    p {

                    line-height:1.5em;

                    margin-top:0.5em; margin-bottom:1.0em;

                    }

                    h1 {

                    margin: 0px 0px 5px; font: 165% verdana,arial,helvetica

                    }

                    h2 {

                    margin-top: 1em; margin-bottom: 0.5em; font: bold 125%verdana,arial,helvetica

                    }

                    h3 {

                    margin-bottom: 0.5em; font: bold 115% verdana,arial,helvetica

                    }

                    h4 {

                    margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica

                    }

                    h5 {

                    margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica

                    }

                    h6 {

                    margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica

                    }

                    .Error {

                    font-weight:bold; color:red;

                    }

                    .Failure {

                    font-weight:bold; color:purple;

                    }

                    .Properties {

                    text-align:right;

                    }

                </style>

                <script type="text/javascript" language="JavaScript">

                    var TestCases = new Array();

                    var cur;

                    <xsl:for-each select="./testsuite">

                        <xsl:apply-templates select="properties"/>

                    </xsl:for-each>



                </script>

                <script type="text/javascript" language="JavaScript"><![CDATA[

       function displayProperties (name) {

         var win =window.open('','JUnitSystemProperties','scrollbars=1,resizable=1');

         var doc = win.document;

         doc.open();

         doc.write("<html><head><title>Properties of" + name + "</title>");

         doc.write("<style>")

         doc.write("body {font:normal 68% verdana,arial,helvetica;color:#000000; }");

         doc.write("table tr td, table tr th { font-size: 68%; }");

         doc.write("table.properties { border-collapse:collapse;border-left:solid 1 #cccccc; border-top:solid 1 #cccccc; padding:5px; }");

         doc.write("table.properties th { text-align:left;border-right:solid 1 #cccccc; border-bottom:solid 1 #cccccc;background-color:#eeeeee; }");

         doc.write("table.properties td { font:normal; text-align:left;border-right:solid 1 #cccccc; border-bottom:solid 1 #cccccc;background-color:#fffffff; }");

         doc.write("h3 { margin-bottom: 0.5em; font: bold 115%verdana,arial,helvetica }");

         doc.write("</style>");

         doc.write("</head><body>");

         doc.write("<h3>Properties of" + name + "</h3>");

         doc.write("<div align=\"right\"><ahref=\"javascript:window.close();\">Close</a></div>");

         doc.write("<table class='properties'>");

         doc.write("<tr><th>Name</th><th>Value</th></tr>");

         for (prop in TestCases[name]) {

           doc.write("<tr><th>" + prop +"</th><td>" + TestCases[name][prop] +"</td></tr>");

         }

         doc.write("</table>");

         doc.write("</body></html>");

         doc.close();

         win.focus();

       }

     ]]>

                </script>

            </head>

            <body>

                <center><h2>Android 智能家居测试报告</h2></center>



                <!-- Summary part -->

                <xsl:call-template name="summary"/>

                <hr size="1" width="95%" align="left"/>

                <!-- For each class createthe  part -->

                <xsl:call-template name="classes"/>

            </body>

        </html>

    </xsl:template>



    <xsl:template name="classes">

        <h2>The Detail of Tests</h2>

        <xsl:for-each select ="testsuite">

            <xsl:sort select="@name"/>

            <!-- create an anchor to thisclass name -->

            <a name="{@name}"></a>

        <h3>TestCase <xsl:value-of select="@name"/></h3>

        <table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">

            <xsl:call-template name="testcase.test.header"/>

            <xsl:apply-templates select="./testcase" mode="print.test"/>

        </table>

        <p/>

    </xsl:for-each>

</xsl:template>



<xsl:template name="summary">

<h2>Summary</h2>

<xsl:variable name="testCount" select="count(testsuite/testcase)"/>

<xsl:variable name="errorCount" select="count(testsuite/testcase/error)"/>

<xsl:variable name="failureCount" select="count(testsuite/testcase/failure)"/>

<xsl:variable name="timeCount" select="sum(testsuite/testcase/@time)"/>

<xsl:variable name="successRate" select="($testCount -$failureCount - $errorCount) div $testCount"/>

<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">

    <tr valign="top">

        <th>Tests</th>

        <th>Failures</th>

        <th>Errors</th>

        <th>Successrate</th>

        <th>Time(s)</th>

    </tr>

    <tr valign="top">

        <xsl:attribute name="class">

            <xsl:choose>

                <xsl:when test="$failureCount > 0">Failure</xsl:when>

                <xsl:when test="$errorCount> 0">Error</xsl:when>

            </xsl:choose>

        </xsl:attribute>

        <td><xsl:value-of select="$testCount"/></td>

        <td><xsl:value-of select="$failureCount"/></td>

        <td><xsl:value-of select="$errorCount"/></td>

        <td>

            <xsl:call-template name="display-percent">

                <xsl:with-param name="value" select="$successRate"/>

            </xsl:call-template>

        </td>

        <td>

            <xsl:call-template name="display-time">

                <xsl:with-param name="value" select="$timeCount"/>

            </xsl:call-template>

        </td>

    </tr>

</table>

<table border="0" width="95%">

    <tr>

        <td style="text-align: justify;">
            Note:
            <i>failures</i> are anticipated and checked for withassertions while
            <i>errors</i> are unanticipated.

        </td>

    </tr>

</table>

</xsl:template>

<xsl:template match="testsuite" mode="header">

<tr valign="top">

    <th width="80%">Name</th>

    <th>Tests</th>

    <th>Errors</th>

    <th>Failures</th>

    <th nowrap="nowrap">Time(s)</th>

</tr>

</xsl:template>



        <!-- method header -->

<xsl:template name="testcase.test.header">

<tr valign="top">

    <th>Name</th>

    <th>Status</th>

    <th width="80%">Type</th>

    <th nowrap="nowrap">Time(s)</th>

</tr>

</xsl:template>



<xsl:template match="testcase" mode="print.test">

<tr valign="top">

    <xsl:attribute name="class">

        <xsl:choose>

            <xsl:when test="failure| error">Error</xsl:when>

        </xsl:choose>

    </xsl:attribute>



    <td><xsl:value-of select="@name"/></td>

    <xsl:choose>

        <xsl:when test="failure">

            <td>Failure</td>

            <td><xsl:apply-templates select="failure"/></td>

        </xsl:when>

        <xsl:when test="error">

            <td>Error</td>

            <td><xsl:apply-templates select="error"/></td>

        </xsl:when>

        <xsl:otherwise>

            <td>Success</td>

            <td></td>

        </xsl:otherwise>

    </xsl:choose>

    <td>

        <xsl:call-template name="display-time">

            <xsl:with-param name="value" select="@time"/>

        </xsl:call-template>

    </td>

</tr>

</xsl:template>



<xsl:template match="failure">

<xsl:call-template name="display-failures"/>

</xsl:template>



<xsl:template match="error">

<xsl:call-template name="display-failures"/>

</xsl:template>



<xsl:template name="display-failures">

<xsl:choose>

    <xsl:when test="not(@message)">N/A</xsl:when>

    <xsl:otherwise>

        <xsl:value-of select="@message"/>

    </xsl:otherwise>

</xsl:choose>

</xsl:template>



<xsl:template name="JS-escape">

<xsl:param name="string"/>

<xsl:param name="tmp1" select="stringutils:replace(string($string),'\','\\')"/>

<xsl:param name="tmp2" select="stringutils:replace(string($tmp1),&quot;'&quot;,&quot;\&apos;&quot;)"/>

<xsl:value-of select="$tmp2"/>

</xsl:template>



<xsl:template name="display-time">

<xsl:param name="value"/>

<xsl:value-of select="format-number($value,'0.000')"/>

</xsl:template>



<xsl:template name="display-percent">

<xsl:param name="value"/>

<xsl:value-of select="format-number($value,'0.00%')"/>

</xsl:template>

</xsl:stylesheet>