package twitter4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("unused")
class JSONSchemaEnumTest {
    /**
     * reason
     */
    public enum Reason {
        OFFICIAL_CLIENT_FORBIDDEN("official-client-forbidden"),
        CLIENT_NOT_ENROLLED("client-not-enrolled");
        public final String value;

        Reason(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static Reason of(String str) {
            for (Reason value : Reason.values()) {
                if (value.value.equals(str)) {
                    return value;
                }
            }
            return null;
        }
    }

    @Test
    void enumType() {
        var extract = JSONSchema.extract("#/", """
                {
                  "reason" : {
                     "type" : "string",
                     "enum" : [ "official-client-forbidden", "client-not-enrolled" ]
                  }
                }""");
        assertEquals(1, extract.size());
        JSONSchema reason = extract.get("reason");
        assertEquals("""
                        @Nullable
                        private final Reason reason;""",
                reason.asFieldDeclaration(false,"twitter4j.v2",null).codeFragment());
        assertEquals("@NotNull\nprivate final Reason reason;",
                reason.asFieldDeclaration(true,"twitter4j.v2",null).codeFragment());
        assertEquals("""
                        this.reason = Reason.of(json.getString("reason"));""",
                reason.asConstructorAssignment(false, null));
        assertEquals("""
                        this.reason = Reason.of(json.getString("reason"));""",
                reason.asConstructorAssignment(true, null));
        assertEquals("""
                        @Nullable
                        @Override
                        public Reason getReason() {
                            return reason;
                        }
                        """,
                reason.asGetterImplementation(false,"twitter4j.v2",null).codeFragment());
        assertEquals("""
                        /**
                         * reason
                         */
                        enum Reason {
                            OFFICIAL_CLIENT_FORBIDDEN("official-client-forbidden"),
                            CLIENT_NOT_ENROLLED("client-not-enrolled");
                            public final String value;
                                            
                            Reason(String value) {
                                this.value = value;
                            }
                                            
                            @Override
                            public String toString() {
                                return value;
                            }
                                            
                            public static Reason of(String str) {
                                for (Reason value : Reason.values()) {
                                    if (value.value.equals(str)) {
                                        return value;
                                    }
                                }
                                return null;
                            }
                        }
                        
                        /**
                         * @return reason
                         */
                        @Nullable
                        Reason getReason();
                        """,
                reason.asGetterDeclaration(false,"twitter4j.v2",null).codeFragment());


        assertThrows(UnsupportedOperationException.class, () -> reason.asJavaImpl("twitter4j", "twitter4j.v2"));
        assertThrows(UnsupportedOperationException.class, () -> reason.asInterface("twitter4j.v2"));
    }

    @Test
    void enumTypeImport() {
        var extract = JSONSchema.extract("#/", """
                {
                  "reason" : {
                     "type" : "string",
                     "enum" : [ "official-client-forbidden", "client-not-enrolled" ]
                  }
                  ,
                  "MyObject": {
                    "type": "object",
                    "properties":{
                      "MyReason": {
                        "$ref": "#/reason"}
                      }
                  }
                }""");
        assertEquals(3, extract.size());
        JSONSchema reason = extract.get("MyObject");

        assertEquals("""
                        package twitter4j;
                                                
                        import org.jetbrains.annotations.Nullable;
                                                
                        /**
                         * MyObject
                         */
                        class MyObjectImpl implements twitter4j.v2.MyObject {
                            @Nullable
                            private final Reason myReason;
                                                
                            MyObjectImpl(JSONObject json) {
                                this.myReason = Reason.of(json.getString("MyReason"));
                            }
                                                
                            @Nullable
                            @Override
                            public Reason getMyReason() {
                                return myReason;
                            }
                        }
                        """,
                reason.asJavaImpl("twitter4j","twitter4j.v2"));

        assertEquals("""
                        package twitter4j.v2;
                                                
                        import org.jetbrains.annotations.Nullable;
                                                
                        /**
                         * MyObject
                         */
                        public interface MyObject {
                            /**
                             * MyReason
                             */
                            enum Reason {
                                OFFICIAL_CLIENT_FORBIDDEN("official-client-forbidden"),
                                CLIENT_NOT_ENROLLED("client-not-enrolled");
                                public final String value;
                                                
                                Reason(String value) {
                                    this.value = value;
                                }
                                                
                                @Override
                                public String toString() {
                                    return value;
                                }
                                                
                                public static Reason of(String str) {
                                    for (Reason value : Reason.values()) {
                                        if (value.value.equals(str)) {
                                            return value;
                                        }
                                    }
                                    return null;
                                }
                            }
                        
                            /**
                             * @return MyReason
                             */
                            @Nullable
                            Reason getMyReason();
                        }
                        """,
                reason.asInterface("twitter4j.v2").content());
    }

}
