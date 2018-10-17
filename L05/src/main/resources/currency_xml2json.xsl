<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:template match="/">
        {"currency_list" : [
        <xsl:for-each select="ValCurs/Valute[@ID = 'R01235' or @ID = 'R01375' or @ID = 'R01239']">
            <xsl:choose>
                <xsl:when test="position() > 1">
                    , {
                    "id": "<xsl:value-of select="@ID"/>",
                    "char_code": "<xsl:value-of select="CharCode"/>",
                    "name": "<xsl:value-of select="Name"/>",
                    "value": "<xsl:value-of select="Value"/>"
                    }
                </xsl:when>
                <xsl:otherwise>
                    {
                    "id": "<xsl:value-of select="@ID"/>",
                    "char_code": "<xsl:value-of select="CharCode"/>",
                    "name": "<xsl:value-of select="Name"/>",
                    "value": "<xsl:value-of select="Value"/>"
                    }
                </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>
        ]}
    </xsl:template>
</xsl:stylesheet>