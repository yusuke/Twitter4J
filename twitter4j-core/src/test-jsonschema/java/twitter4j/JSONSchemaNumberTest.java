package twitter4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JSONSchemaNumberTest {
    @Test
    void integer() {
        var extract = JSONSchema.extract("#/", """
                {
                        "MediaHeight" : {
                         "type" : "integer",
                         "minimum" : 0,
                         "description" : "The height of the media in pixels",
                         "example" : "10"
                       }
                 
                }""");
        assertEquals(1, extract.size());
        JSONSchema mediaHeight = extract.get("#/MediaHeight");
        assertEquals("""
                        @Nullable
                        @Range(from = 0, to = Long.MAX_VALUE)
                        private final Long mediaHeight;""",
                mediaHeight.asFieldDeclaration(false, "twitter4j.v2", null).codeFragment());
        assertEquals("""
                        this.mediaHeight = json.getLongValue("MediaHeight");""",
                mediaHeight.asConstructorAssignment(false, null).codeFragment());
        assertEquals("this.mediaHeight = json.getLong(\"MediaHeight\");",
                mediaHeight.asConstructorAssignment(true, null).codeFragment());
        assertEquals("""
                        @Nullable
                        @Range(from = 0, to = Long.MAX_VALUE)
                        @Override
                        public Long getMediaHeight() {
                            return mediaHeight;
                        }
                        """,
                mediaHeight.asGetterImplementation(false, "twitter4j.v2", null, false).codeFragment());

        assertThrows(UnsupportedOperationException.class, () -> mediaHeight.asJavaImpl("twitter4j", "twitter4j.v2", false));
        assertThrows(UnsupportedOperationException.class, () -> mediaHeight.asInterface("twitter4j.v2", false));
    }

    @Test
    void number() {
        var extract = JSONSchema.extract("#/", """
                {
                    "items" : {
                           "type" : "number",
                           "format" : "double",
                           "minimum" : -180,
                           "maximum" : 180
                    }
                }""");
        assertEquals(1, extract.size());
        JSONSchema items = extract.get("#/items");
        assertEquals("""
                        @Nullable
                        @Range(from = -180, to = 180)
                        private final Double items;""",
                items.asFieldDeclaration(false, "twitter4j.v2", null).codeFragment());
        assertEquals("""
                        this.items = json.getDoubleValue("items");""",
                items.asConstructorAssignment(false, null).codeFragment());
        assertEquals("this.items = json.getDouble(\"items\");",
                items.asConstructorAssignment(true, null).codeFragment());
        assertEquals("""
                        @Nullable
                        @Range(from = -180, to = 180)
                        @Override
                        public Double getItems() {
                            return items;
                        }
                        """,
                items.asGetterImplementation(false, "twitter4j.v2", null, false).codeFragment());

        assertThrows(UnsupportedOperationException.class, () -> items.asJavaImpl("twitter4j", "twitter4j.v2", false));
        assertThrows(UnsupportedOperationException.class, () -> items.asInterface("twitter4j.v2", false));
    }

    @Test
    void integerMinMax() {

        var extract = JSONSchema.extract("#/", """
                {       "HTTPStatusCode" : {
                          "type" : "integer",
                          "minimum" : 100,
                          "maximum" : 599,
                          "description" : "HTTP Status Code."
                        }
                  
                }""");
        assertEquals(1, extract.size());
        JSONSchema hTTPStatusCode = extract.get("#/HTTPStatusCode");
        assertEquals("@Nullable\n@Range(from = 100, to = 599)\nprivate final Integer hTTPStatusCode;",
                hTTPStatusCode.asFieldDeclaration(false, "twitter4j.v2", null).codeFragment());
        assertEquals("""
                        this.hTTPStatusCode = json.getIntValue("HTTPStatusCode");""",
                hTTPStatusCode.asConstructorAssignment(false, null).codeFragment());
        assertEquals("this.hTTPStatusCode = json.getInt(\"HTTPStatusCode\");",
                hTTPStatusCode.asConstructorAssignment(true, null).codeFragment());
        assertEquals("""
                        @Nullable
                        @Range(from = 100, to = 599)
                        @Override
                        public Integer getHTTPStatusCode() {
                            return hTTPStatusCode;
                        }
                        """,
                hTTPStatusCode.asGetterImplementation(false, "twitter4j.v2", null, false).codeFragment());

        assertThrows(UnsupportedOperationException.class, () -> hTTPStatusCode.asJavaImpl("twitter4j", "twitter4j.v2", false));
        assertThrows(UnsupportedOperationException.class, () -> hTTPStatusCode.asInterface("twitter4j.v2", false));
    }

    @Test
    void int32() {

        var extract = JSONSchema.extract("#/", """
                {               "code" : {
                                     "type" : "integer",
                                             "format" : "int32"
                                 },
                                                           "HTTPStatusCode" : {
                            "type" : "integer",
                            "minimum" : 100,
                            "maximum" : 599,
                            "description" : "HTTP Status Code."
                          }

                  
                }""");
        assertEquals(2, extract.size());
        JSONSchema code = extract.get("#/code");
        assertEquals("@Nullable\nprivate final Integer code;",
                code.asFieldDeclaration(false, "twitter4j.v2", null).codeFragment());
        assertEquals("private final int code;",
                code.asFieldDeclaration(true, "twitter4j.v2", null).codeFragment());
        assertEquals("""
                        this.code = json.getIntValue("code");""",
                code.asConstructorAssignment(false, null).codeFragment());
        assertEquals("this.code = json.getInt(\"code\");",
                code.asConstructorAssignment(true, null).codeFragment());
        assertEquals("""
                        @Nullable
                        @Override
                        public Integer getCode() {
                            return code;
                        }
                        """,
                code.asGetterImplementation(false, "twitter4j.v2", null, false).codeFragment());
        assertEquals("""
                        @Override
                        public int getCode() {
                            return code;
                        }
                        """,
                code.asGetterImplementation(true, "twitter4j.v2", null, false).codeFragment());


        JSONSchema hTTPStatusCode = extract.get("#/HTTPStatusCode");
        assertEquals("""
                /**
                 * @return HTTP Status Code.
                 */
                @Nullable
                @Range(from = 100, to = 599)
                Integer getHTTPStatusCode();
                """, hTTPStatusCode.asGetterDeclaration(false, "twitter4j.v2", null, false).codeFragment());
        assertEquals("""
                /**
                 * @return HTTP Status Code.
                 */
                @Range(from = 100, to = 599)
                int getHTTPStatusCode();
                """, hTTPStatusCode.asGetterDeclaration(true, "twitter4j.v2", null, false).codeFragment());

    }

}
