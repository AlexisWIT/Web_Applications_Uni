<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<body>
				<h3 style="text-align:center" >
					<xsl:value-of select="controller/@id"/>
				</h3>
				<table align="center" border="1">
					<tr bgcolor="yellow">
						<th style="text-align:center">Operation</th>
						<th style="text-align:center">Argument(s)</th>
						<th style="text-align:center">Return</th>
					</tr>
						<xsl:for-each select="controller/abstract_method">
						<tr>
							<td>
								<xsl:value-of select="@name"/>
							</td>
							<td>
								<xsl:for-each select="arguments/parameter">
									<xsl:value-of select="text()"/>
									<xsl:text>: </xsl:text>
									<xsl:value-of select="@type"/>
									<xsl:if test="position()!=last()">, </xsl:if>
								</xsl:for-each>
							</td>
							<td>
								<xsl:value-of select="return"/>
							</td>
						</tr>
						
						</xsl:for-each>
				</table>
			
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>